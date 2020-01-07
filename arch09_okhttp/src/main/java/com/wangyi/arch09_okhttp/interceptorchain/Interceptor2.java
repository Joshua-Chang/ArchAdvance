package com.wangyi.arch09_okhttp.interceptorchain;

import com.wangyi.arch09_okhttp.myhttp.Response2;

import java.io.IOException;

public interface Interceptor2 {
    Response2 doNext(Chain2 chain2)throws IOException;
}
