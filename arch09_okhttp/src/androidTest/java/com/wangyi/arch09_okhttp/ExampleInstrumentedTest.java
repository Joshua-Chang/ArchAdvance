package com.wangyi.arch09_okhttp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.wangyi.arch09_okhttp", appContext.getPackageName());
    }

    @Test
    public void http() {
        System.out.println(111111);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .get()
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
                System.out.println(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                response.body().byteStream();
                response.body().charStream();
                System.out.println(string);
            }
        });
    }
}
