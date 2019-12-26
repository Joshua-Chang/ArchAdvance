package com.wangyi.arch05_plugin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void loadPlugin(View view) {
        PluginManager.getInstance(this).loadPlugin();
    }
    // 启动插件里面的Activity
    public void startPluginActivity(View view) {
        String path = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk").getAbsolutePath();
        // 获取插件包 里面的 Activity
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        ActivityInfo activityInfo = packageInfo.activities[0];
        // 占位  代理Activity
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", activityInfo.name);
        startActivity(intent);
    }
    // 注册 插件里面 配置的 静态广播接收者
    public void loadStaticReceiver(View view) {
        PluginManager.getInstance(this).parserApkAction();
    }

    // 发送静态广播
    public void sendStaticReceiver(View view) {
        Intent intent = new Intent();
        intent.setAction("plugin.static_receiver");
        sendBroadcast(intent);
    }
}
