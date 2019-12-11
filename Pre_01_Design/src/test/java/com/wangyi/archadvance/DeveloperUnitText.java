package com.wangyi.archadvance;

import com.wangyi.archadvance.adapter.retrofit.RxjavaCallAdapterFactory;
import com.wangyi.pre_01_adapter.Retrofit;

import org.junit.Test;

public class DeveloperUnitText {
    @Test
    public void retrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(new RxjavaCallAdapterFactory())
                .build();
        retrofit.create();
    }
}
