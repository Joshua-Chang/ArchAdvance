package com.example.arch10_glide.load_data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.example.arch10_glide.resource.Value;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;


public class LoadDataManager implements ILoadData, Runnable {

    private String path;
    private ResponseListener responseListener;
    private Context context;

    @Override
    public Value loadResource(String path, ResponseListener responseListener, Context context) {
        this.path = path;
        this.responseListener = responseListener;
        this.context = context;

        Uri uri = Uri.parse(path);
        if (uri.getScheme().equalsIgnoreCase("HTTPS")||uri.getScheme().equalsIgnoreCase("HTTP")) {
            Executors.newCachedThreadPool().execute(this);
        }
        // TODO: 2020-01-08 加载本地资源
        return null;
    }

    @Override
    public void run() {
        InputStream inputStream=null;
        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            final int responseCode = urlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK==responseCode) {
                inputStream = urlConnection.getInputStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                // 成功 切换主线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Value value = Value.getInstance();
                        value.setBitmap(bitmap);
                        responseListener.ResponseSuccess(value);
                    }
                });
            }else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        responseListener.ResponseException(new IllegalStateException("请求失败："+responseCode));
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
