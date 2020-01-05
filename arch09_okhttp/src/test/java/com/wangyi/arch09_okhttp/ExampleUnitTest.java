package com.wangyi.arch09_okhttp;

import android.util.Log;
import android.view.View;

import org.junit.Test;

import java.io.IOException;

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
    public void http() {
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();//同步
            String string = response.body().string();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }


        call.enqueue(new Callback() {//异步
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println(string);
            }
        });
    }
}