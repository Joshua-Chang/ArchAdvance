package com.wangyi.arch09_okhttp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wangyi.arch09_okhttp.myhttp.Call2;
import com.wangyi.arch09_okhttp.myhttp.Callback2;
import com.wangyi.arch09_okhttp.myhttp.OkHttpClient2;
import com.wangyi.arch09_okhttp.myhttp.Request2;
import com.wangyi.arch09_okhttp.myhttp.Response2;

import java.io.IOException;

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
}
