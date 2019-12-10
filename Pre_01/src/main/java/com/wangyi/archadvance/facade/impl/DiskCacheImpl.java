package com.wangyi.archadvance.facade.impl;

import android.graphics.Bitmap;

import com.wangyi.archadvance.facade.thing.DiskCache;
import com.wangyi.archadvance.facade.thing.MemoryCache;

public class DiskCacheImpl implements DiskCache {

    @Override
    public Bitmap findByDisk(String url) {
        System.out.println("通过图片Url,寻找本地文件中缓存图片");
        return null;
    }
}
