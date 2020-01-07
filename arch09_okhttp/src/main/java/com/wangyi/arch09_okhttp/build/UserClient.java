package com.wangyi.arch09_okhttp.build;

import android.graphics.Color;

import com.wangyi.arch09_okhttp.build.com.Building;
import com.wangyi.arch09_okhttp.build.com.Designer;
import com.wangyi.arch09_okhttp.build.com.House;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

public class UserClient {
    public static void main(String[] args) {
        Designer designer=new Designer().addFloor(3).addArea(600).addColor("Blue");
        designer.addColor("Red");
        House house = designer.build();
        System.out.println(house);

        Building building=new Building.Builder().setArea(100).setFloor(2).setColor("Grey").build();
        System.out.println(building);



        try {
            System.out.println("请输入网址");
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            URL url = new URL("https://" + line);
            String host = url.getHost();
            int port=80;
            Socket socket=new Socket(host,port);
            if (url.getProtocol().equalsIgnoreCase("Https")) {
                port=443;
                socket = SSLSocketFactory.getDefault().createSocket(host, port);
            }
            // TODO 写出去  请求
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            /**
             * GET / HTTP/1.1
             * Host: www.baud.com
             */
            bw.write("GET / HTTP/1.1\r\n");
            bw.write("Host: "+host+"\r\n\r\n");
            bw.flush();

            // TODO 读取数据 响应
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String readLine = null;
                if ((readLine = bufferedReader.readLine()) != null) {
                    System.out.println("响应的数据：" + readLine);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
