package com.wangyi.archadvance;

import com.wangyi.archadvance.facade.Facade;
import com.wangyi.archadvance.facade.impl.DiskCacheImpl;
import com.wangyi.archadvance.facade.impl.MemoryCacheImpl;
import com.wangyi.archadvance.facade.impl.NetWorkLoaderImpl;
import com.wangyi.archadvance.facade.thing.DiskCache;
import com.wangyi.archadvance.facade.thing.MemoryCache;
import com.wangyi.archadvance.facade.thing.NetWorkLoader;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {
    private final static String URL = "http://www.baidu.com/logo.jpg";

    @Test
    public void onCreate() {
        //常规写法
//        MemoryCache memoryCache = new MemoryCacheImpl();
//        memoryCache.findByMemory(URL);
//        DiskCache diskCache=new DiskCacheImpl();
//        diskCache.findByDisk(URL);
//        NetWorkLoader netWorkLoader=new NetWorkLoaderImpl();
//        netWorkLoader.loaderImageFromNet(URL);

        //外观模式
        Facade facade=new Facade(URL);
        facade.loader();

    }
}