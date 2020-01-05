package com.wangyi.arch09_okhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void http(View view) {
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("www.baidu.com")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();//同步
        } catch (IOException e) {
            e.printStackTrace();
        }


        call.enqueue(new Callback() {//异步
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                response.body().byteStream();
                response.body().charStream();

            }
        });
    }
}
