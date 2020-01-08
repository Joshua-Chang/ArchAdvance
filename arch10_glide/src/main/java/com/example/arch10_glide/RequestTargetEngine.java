package com.example.arch10_glide;

import android.util.Log;
import android.widget.ImageView;

import com.example.arch10_glide.fragment.LifecycleCallback;

import static com.example.arch10_glide.Cons.TAG;

public class RequestTargetEngine implements LifecycleCallback {
    @Override
    public void glideInitAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经开启了 初始化了....");
    }

    @Override
    public void glideStopAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经停止中 ....");
    }

    @Override
    public void glideRecycleAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 进行释放操作 缓存策略释放操作等 >>>>>> ....");
    }


    public void into(ImageView imageView) {

    }
}
