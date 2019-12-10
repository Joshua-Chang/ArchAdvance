package com.wangyi.archadvance.facade.impl;

import com.wangyi.archadvance.facade.thing.NetWorkLoader;

import java.io.InputStream;

public class NetWorkLoaderImpl implements NetWorkLoader {
    @Override
    public InputStream loaderImageFromNet(String url) {
        System.out.println("通过图片Url,从网络加载图片");
        return null;
    }
}
