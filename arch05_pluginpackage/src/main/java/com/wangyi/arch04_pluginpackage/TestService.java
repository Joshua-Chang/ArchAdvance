package com.wangyi.arch04_pluginpackage;

import android.content.Intent;
import android.util.Log;

public class TestService extends BaseService{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 开启子线程，执行耗时任务
        new Thread(new Runnable(){

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Log.d(">>>", "插件里面的服务 正在执行中....");
                    }
                }
            }

        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}
