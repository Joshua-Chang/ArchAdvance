package com.wangyi.archadvance.facade;

import com.wangyi.archadvance.facade.impl.DiskCacheImpl;
import com.wangyi.archadvance.facade.impl.MemoryCacheImpl;
import com.wangyi.archadvance.facade.impl.NetWorkLoaderImpl;
import com.wangyi.archadvance.facade.thing.DiskCache;
import com.wangyi.archadvance.facade.thing.MemoryCache;
import com.wangyi.archadvance.facade.thing.NetWorkLoader;

public class Facade {
    private String url;
    private MemoryCache memoryCache;
    private DiskCache diskCache;
    private NetWorkLoader netWorkLoader;
    public Facade(String url) {
        this.url = url;
        memoryCache=new MemoryCacheImpl();
        diskCache=new DiskCacheImpl();
        netWorkLoader=new NetWorkLoaderImpl();
    }

    public void loader(){
        memoryCache.findByMemory(url);
        diskCache.findByDisk(url);
        netWorkLoader.loaderImageFromNet(url);
    }

}
