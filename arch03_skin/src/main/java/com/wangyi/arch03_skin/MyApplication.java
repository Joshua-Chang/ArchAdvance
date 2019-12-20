package com.wangyi.arch03_skin;

import android.app.Application;

import com.wangyi.arch03_skinlib.SkinManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        市面通用换肤
//        try {
//            SkinEngine.getInstance().skinApplicationInit(this);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        SkinManager.init(this);
    }
}
