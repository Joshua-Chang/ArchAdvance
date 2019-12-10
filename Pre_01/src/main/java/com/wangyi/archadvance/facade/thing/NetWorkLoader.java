package com.wangyi.archadvance.facade.thing;

import android.graphics.Bitmap;

import java.io.InputStream;

public interface NetWorkLoader {
    InputStream loaderImageFromNet(String url);
}
