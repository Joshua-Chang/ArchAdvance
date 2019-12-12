package com.wangyi.archadvance.facade.thing;

import android.graphics.Bitmap;

public interface DiskCache {
    Bitmap findByDisk(String url);
}
