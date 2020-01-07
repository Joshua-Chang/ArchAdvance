package com.wangyi.arch09_okhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wangyi.arch09_okhttp.myhttp.Call2;
import com.wangyi.arch09_okhttp.myhttp.Callback2;
import com.wangyi.arch09_okhttp.myhttp.OkHttpClient2;
import com.wangyi.arch09_okhttp.myhttp.Request2;
import com.wangyi.arch09_okhttp.myhttp.Response2;
import com.wangyi.arch09_okhttp.pool.ConnectionPool;
import com.wangyi.arch09_okhttp.pool.UseConnectionPool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends AppCompatActivity {
    private final String PATH = "http://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void useOkhttp(View view) {
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(PATH)
//                .get()
                .build();
        Call call = okHttpClient.newCall(request);
//        try {
//            Response response = call.execute();//同步
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        call.enqueue(new Callback() {//异步
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(">>>",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(">>>",response.body().string());
            }
        });
    }


    public void useMyOkhttp(View view) {
        urlAction();
        OkHttpClient2 okHttpClient2=new OkHttpClient2.Builder().build();
        Request2 request = new Request2.Builder()
                .url(PATH)
                .build();
        Call2 call2 = okHttpClient2.newCall(request);
        call2.enqueue(new Callback2() {
            @Override
            public void onFailure(Call2 call, IOException e) {
                Log.e(">>>",e.toString());
            }

            @Override
            public void onResponse(Call2 call, Response2 response) throws IOException {
                Log.e(">>>",response.string());
            }
        });
    }

    private void urlAction() {
        try {
            URL url=new URL(PATH);
            Log.d(Cons.TAG, url.getProtocol());
            Log.d(Cons.TAG, url.getHost ());
            Log.d(Cons.TAG, url.getFile());
            Log.d(Cons.TAG, url.getQuery());
            Log.d(Cons.TAG, url.getPort()==-1?url.getDefaultPort()+"":url.getPort()+"");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void useSocket(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                socketHttp();
                socketHttpPost();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                socketHttps();
            }
        }).start();
    }

    private void socketHttp() {
        try {
            Socket socket=new Socket("restapi.amap.com",80);
            // TODO 写出去  请求
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("GET /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
            bufferedWriter.write("Host: restapi.amap.com\r\n\r\n");
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                String readLine=null;
                if ((readLine=bufferedReader.readLine())!=null){
                    Log.e(Cons.TAG+"Http",readLine);
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void socketHttpPost() {
        /**
         * POST /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1
         * Content-Length: 48
         * Host: restapi.amap.com
         * Content-Type: application/x-www-form-urlencoded
         *
         * city=110101&key=13cb58f5884f9749287abbead9c658f2
         */

        try {
            Socket socket=new Socket("restapi.amap.com",80);
            // TODO 写出去  请求
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write("POST /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
            bufferedWriter.write("Content-Length: 48\r\n");
            bufferedWriter.write("Content-Type: application/x-www-form-urlencoded\r\n");
            bufferedWriter.write("Host: restapi.amap.com\r\n\r\n");

//            请求体
            bufferedWriter.write("city=110101&key=13cb58f5884f9749287abbead9c658f2\r\n");

            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                String readLine=null;
                if ((readLine=bufferedReader.readLine())!=null){
                    Log.e(Cons.TAG+"Http Post",readLine);
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void socketHttps() {
        try {
            Socket socket = SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443);

            // TODO 写出去  请求
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            /**
             * GET / HTTP/1.1
             * Host: www.baud.com
             */
            bw.write("GET / HTTP/1.1\r\n");
            bw.write("Host: www.baidu.com\r\n\r\n");
            bw.flush();

            // TODO 读取数据 响应
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String readLine = null;
                if ((readLine = br.readLine()) != null) {
                    Log.e(Cons.TAG+"Https",readLine);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void request(View view) {
        final ConnectionPool connectionPool = new ConnectionPool();

        new Thread(){
            @Override
            public void run() {
                super.run();
                UseConnectionPool useConnectionPool = new UseConnectionPool();
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com", 80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com", 80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com", 80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com", 80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com", 80);
            }
        }.start();
    }
}
