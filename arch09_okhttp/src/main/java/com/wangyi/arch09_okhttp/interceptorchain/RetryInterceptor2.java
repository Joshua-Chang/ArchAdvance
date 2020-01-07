package com.wangyi.arch09_okhttp.interceptorchain;

import com.wangyi.arch09_okhttp.myhttp.OkHttpClient2;
import com.wangyi.arch09_okhttp.myhttp.Response2;

import java.io.IOException;

public class RetryInterceptor2 implements Interceptor2 {

    private final OkHttpClient2 client;


    public RetryInterceptor2(OkHttpClient2 client) {
        this.client = client;
    }

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException{
        InterceptorChainManager chainManager = (InterceptorChainManager) chain2;
        IOException exception = null;
        if (client.getRecount()!=0) {
            for (int i = 0; i < client.getRecount(); i++) {
                try {
                    Response2 response = chain2.getResponse(chainManager.getRequest());
                    return response;
                } catch (IOException e) {
                    exception = e;
                }
            }
        }
        throw exception;
    }
}
