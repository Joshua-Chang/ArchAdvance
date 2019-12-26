package com.wangyi.arch05_plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wangyi.arch05_plugin_std.ServiceInterface;

public class ProxyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("className");
        // com.netease.plugin_package.TestService
        try {
            Class mTestServiceClass = PluginManager.getInstance(this).getDexClassLoader().loadClass(className);
            Object mTestService = mTestServiceClass.newInstance();
            ServiceInterface serviceInterface = (ServiceInterface) mTestService;
            serviceInterface.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
