package com.example.arch10_glide.load_data;

import android.content.Context;

import com.example.arch10_glide.resource.Value;

public interface ILoadData {
    public Value loadResource(String path, ResponseListener responseListener, Context context);//回调
}
