package com.wangyi.arch09_okhttp;

import android.util.Log;
import android.view.View;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void http() throws IOException {
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
    }
    @Test
    public void main() {
        Executor executorService =
                /**
                 * 参数1：0            核心线程数 0
                 * 参数2：MAX_VALUE    线程池中最大值
                 * 参数3：60           单位值
                 * 参数4：秒钟          时 分 秒
                 * 参数5：队列          SynchronousQueue
                 *
                 * 执行任务大于（核心线程数） 启用（60s闲置时间）
                 * 60秒闲置时间，没有过，复用之前的线程， 60秒过的，新实例化
                 */
                new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                        60, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>());


        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    System.out.println("正在允许的 线程名：" + Thread.currentThread().getName());
                }
            });
        }

    }
}