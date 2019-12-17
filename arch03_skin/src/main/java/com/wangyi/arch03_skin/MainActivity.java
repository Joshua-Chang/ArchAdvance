package com.wangyi.arch03_skin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        MyLayoutInflater layoutInflater = new MyLayoutInflater(getLayoutInflater(), this, this);
        View inflate = layoutInflater.inflate(R.layout.activity_main, null);
        setContentView(inflate);
//        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

    }

    // 申请权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private void verifyStoragePermissions(Activity activity) {
        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(activity,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }


    public void skinAction(View view) {
        Log.e("xxx >>> ", "-------------start-------------");
        long start = System.currentTimeMillis();

        String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "my.skin";
        try {
            SkinEngine.getInstance().updateSkin(skinPath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis() - start;
        Log.e("xxx >>> ", "换肤耗时（毫秒）：" + end);
        Log.e("xxx >>> ", "-------------end---------------");
    }

    public void revertDefault(View view) {
        Log.e("xxx >>> ", "-------------start-------------");
        long start = System.currentTimeMillis();

        try {
            SkinEngine.getInstance().updateSkin("分身乏术分身乏术发放松放松放松");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis() - start;
        Log.e("xxx >>> ", "还原耗时（毫秒）：" + end);
        Log.e("netexxxase >>> ", "-------------end---------------");
    }

    public void startActivityThis(View view) {
        startActivity(new Intent(this, this.getClass()));
    }
}
