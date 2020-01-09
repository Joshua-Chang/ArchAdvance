package com.example.arch10_glide.load_data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import com.example.arch10_glide.Tool;
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
//                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                int w = 1920;
                int h = 1080;

                // 不需要使用复用池，拿去图片内存
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                //   既然是外部网络加载图片，就不需要用复用池 Bitmap bitmapPoolResult = bitmapPool.get(w, h, Bitmap.Config.RGB_565);
                //   options2.inBitmap = bitmapPoolResult; // 如果我们这里拿到的是null，就不复用
                options2.inMutable = true;
                options2.inPreferredConfig = Bitmap.Config.RGB_565;
                options2.inJustDecodeBounds = false;
                // inSampleSize:是采样率，当inSampleSize为2时，一个2000 1000的图片，将被缩小为1000 500， 采样率为1 代表和原图宽高最接近
                options2.inSampleSize = Tool.sampleBitmapSize(options2, w, h);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options2); // 真正的加载

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
