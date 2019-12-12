package com.wangyi.archadvance.factory;

public class SimpleFactory {
    public static Api createApi(){
        return new ApiImpl();
    }
}
