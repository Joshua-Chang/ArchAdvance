package com.example.arch10_bitmappool;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.example.arch10_bitmappool.pool.BitmapPool;
import com.example.arch10_bitmappool.pool.BitmapPoolImpl;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Runnable{
    private final String PATH = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg";
    private ImageView mImage;
    private BitmapPool bitmapPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapPool= new BitmapPoolImpl(1024 * 1024 * 6);
        mImage = findViewById(R.id.image);
    }

    public void testAction(View view) {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            URL url=new URL(PATH);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            if (httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BitmapFactory.Options options=new BitmapFactory.Options();
//                options.inJustDecodeBounds=true;//只拿边界信息，false拿全部信息非常耗性能 顺序1.
//                BitmapFactory.decodeStream(inputStream,null,options);//顺序2.
//                int width = options.outWidth;//顺序3.
//                int heigth = options.outHeight;
                int width = 1920;
                int heigth = 1080;

                // 拿到复用池  条件： bitmap.isMutable() == true;
                Bitmap bitmapResult = bitmapPool.get(width, heigth, Bitmap.Config.RGB_565);
                // 如果设置为null，内部就不会去申请新的内存空间，无非复用，依然会照成：内存抖动，内存碎片
                options.inBitmap = bitmapResult; // 把复用池的Bitmap 给 inBitmap
                options.inPreferredConfig = Bitmap.Config.RGB_565; // 2个字节
                options.inJustDecodeBounds = false;
                options.inMutable = true; // 符合 复用机制
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

                bitmapPool.put(bitmap);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mImage.setImageBitmap(bitmap);
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
