package com.example.arch10_bitmappool.pool;

import android.graphics.Bitmap;

public interface BitmapPool {
    /**
     * 存入到复用池
     * @param bitmap
     */
    void put(Bitmap bitmap);


    /**
     * 获取匹配可用复用的Bitmap
     * @param width
     * @param height
     * @param config
     * @return
     */
    Bitmap get(int width, int height, Bitmap.Config config);

}
