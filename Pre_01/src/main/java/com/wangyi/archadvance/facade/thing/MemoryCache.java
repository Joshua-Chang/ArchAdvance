package com.wangyi.archadvance.facade.thing;

import android.graphics.Bitmap;

public interface MemoryCache {
    Bitmap findByMemory(String url);
}
