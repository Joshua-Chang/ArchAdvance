package com.wangyi.arch_01_mvp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wangyi.arch_01_mvp.Constant;
import com.wangyi.arch_01_mvp.DownLoaderContract;
import com.wangyi.arch_01_mvp.presenter.DownLoaderPresenter;

import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoaderEngine implements DownLoaderContract.M {

    private final DownLoaderPresenter presenter;

    public DownLoaderEngine(DownLoaderPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestDownLoader(ImageBean imageBean) throws Exception {
        new Thread(new DownLoader(imageBean)).start();
    }

    private class DownLoader implements Runnable {

        private final ImageBean imageBean;

        public DownLoader(ImageBean imageBean) {
            this.imageBean = imageBean;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageBean.getRequestPath());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(5000);
                if (httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                    Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    imageBean.setBitmap(bitmap);
                    showUi(Constant.SUCCESS, bitmap);
                } else {
                    showUi(Constant.ERROR, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showUi(Constant.ERROR, null);
            }
        }
        private void showUi(int resultCode, Bitmap bitmap) {
            imageBean.setBitmap(bitmap);
            presenter.responseDownLoaderResult(resultCode==Constant.SUCCESS,imageBean);
        }

    }
}
