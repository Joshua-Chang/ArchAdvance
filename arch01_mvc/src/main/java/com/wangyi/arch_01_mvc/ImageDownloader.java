package com.wangyi.arch_01_mvc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wangyi.arch_01_mvc.bean.ImageBean;

import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {
    public static final int SUCCESS=200;
    public static final int ERROR=404;

    public void down(Callback callback, ImageBean imageBean) {
        new Thread(new Downloader(callback,imageBean)).start();
    }

    private class Downloader implements Runnable {
        Callback callback;
        ImageBean imageBean;
        public Downloader(Callback callback, ImageBean imageBean) {
            this.callback=callback;
            this.imageBean=imageBean;
        }

        @Override
        public void run() {
            try {
                URL url=new URL(imageBean.getRequestPath());
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(5000);
                if (httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                    Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    showUi(SUCCESS,bitmap);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showUi(ERROR,null);
            }

        }
        private void showUi(int resultCode, Bitmap bitmap) {
            if (callback != null) {
                imageBean.setBitmap(bitmap);
                callback.callback(resultCode, imageBean);
            }
        }
    }
}
