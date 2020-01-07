package com.wangyi.arch09_okhttp.interceptorchain;

import com.wangyi.arch09_okhttp.myhttp.Request2;
import com.wangyi.arch09_okhttp.myhttp.Response2;
import com.wangyi.arch09_okhttp.myhttp.SocketRequestServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class ConnectionServerInterceptor implements Interceptor2 {
    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        Response2 response2 = new Response2();

        // 解析Reqeus
        SocketRequestServer srs = new SocketRequestServer();
        Request2 request = chain2.getRequest(); // 更新后的Request  hostName:  post:leng type
        String host = srs.getHost(request);
        int port = srs.getPort(request);
        String headerAll = srs.getRequestHeaderAll(request);
        Socket socket = new Socket(host, port);
        String result = srs.queryHttpOrHttps(request.getUrl());
        if (result != null) {
            if (result.equalsIgnoreCase("HTTP")) {
                // 只能访问HTTP，不能访问HTTPS  S SSL 握手
//                socket=new Socket()
            } else if (result.equalsIgnoreCase("HTTPS")) {
                socket = SSLSocketFactory.getDefault().createSocket(host, port);
            }
        }
        // todo 请求
        // output
        OutputStream socketOutputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socketOutputStream));
        bufferedWriter.write(headerAll);// 给服务器发送请求 --- 请求头信息 所有的
        bufferedWriter.flush(); // 真正的写出去...
        // todo 响应
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // todo 取出 响应码
        String readLine = bufferedReader.readLine();// 读取第一行 响应头信息
        // 服务器响应的:HTTP/1.1 200 OK
        String[] strings = readLine.split(" ");
        response2.setStatusCode(Integer.parseInt(strings[1]));
        // todo 取出响应体，只要是空行下面的，就是响应体
        String readerLine = null;
        while ((readerLine = bufferedReader.readLine()) != null) {
            if ("".equals(readerLine)) {
                // 读到空行了，就代表下面就是 响应体了
                response2.setBody(bufferedReader.readLine());
                break;
            }
        }

        // response2.setBody("流程走通....");
        return response2;
    }
}
