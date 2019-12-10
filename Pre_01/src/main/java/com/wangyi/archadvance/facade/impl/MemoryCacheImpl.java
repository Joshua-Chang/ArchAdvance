package com.wangyi.archadvance.facade.impl;

import android.graphics.Bitmap;

import com.wangyi.archadvance.facade.thing.MemoryCache;

public class MemoryCacheImpl implements MemoryCache {
    @Override
    public Bitmap findByMemory(String url) {
        System.out.println("通过图片Url,寻找内存中缓存图片");
        return null;
    }
}
