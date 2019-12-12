package com.wangyi.arch_01_mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangyi.arch_01_mvp.model.ImageBean;
import com.wangyi.arch_01_mvp.presenter.DownLoaderPresenter;

public class MainActivity extends AppCompatActivity implements DownLoaderContract.PV{
    private ImageView imageView;
    private DownLoaderPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv_image);
        presenter = new DownLoaderPresenter(this);
    }

    @Override
    public void requestDownLoader(ImageBean imageBean) {
        if (presenter!=null) presenter.requestDownLoader(imageBean);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(50000);
            }
        });
    }

    @Override
    public void responseDownLoaderResult(boolean isSuccess, ImageBean imageBean) {
        Toast.makeText(this, isSuccess ? "下载成功" : "下载失败", Toast.LENGTH_SHORT).show();
        if (isSuccess&&imageBean.getBitmap()!=null) {
            imageView.setImageBitmap(imageBean.getBitmap());
        }
    }

    public void getImage(View view) {
        ImageBean imageBean = new ImageBean();
        imageBean.setRequestPath(Constant.PATH);
        requestDownLoader(imageBean);

    }
}
