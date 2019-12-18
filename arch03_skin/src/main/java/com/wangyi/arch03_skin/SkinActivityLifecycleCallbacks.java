package com.wangyi.arch03_skin;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;

public class SkinActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private String TAG = "xxx";
    public static SkinFactory skinFactory;

    /**
     * 在setContentView前设置factory2,在super.onCreate(savedInstanceState);之后
     * @param activity
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
//10.0非sdk接口限制 不能反射
        // 利用反射去修改mFactorySet的值为false，防止抛出 A factory has already been set on this...
        // 反正就是为了提高健壮性
//        try {
//            // 尽量使用LayoutInflater.class，不要使用layoutInflater.getClass()
//            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
//            // 源码312行
//            mFactorySet.setAccessible(true);
//            mFactorySet.set(layoutInflater, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "reflect failed e: " + e.toString());
//        }

        skinFactory = new SkinFactory(activity);
        // mFactorySet = true是无法设置成功的（源码312行）
//        LayoutInflaterCompat.setFactory2(layoutInflater, skinFactory);

        // 注册观察者（监听用户操作，点击了换肤，通知观察者更新）
        SkinEngine.getInstance().addObserver(skinFactory);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        // 每次打开一个Activity就需要去换肤
        skinFactory.update(null, null);

        // 每次打开一个Activity就需要去更新状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SkinEngine.getInstance().updatePhoneStatusBarAction(activity);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        // 解除注册的观察者
        SkinEngine.getInstance().deleteObserver(skinFactory);
    }
}
