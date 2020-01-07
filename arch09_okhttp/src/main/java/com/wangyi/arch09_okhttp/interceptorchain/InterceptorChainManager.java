package com.wangyi.arch09_okhttp.interceptorchain;

import com.wangyi.arch09_okhttp.myhttp.Call2;
import com.wangyi.arch09_okhttp.myhttp.Request2;
import com.wangyi.arch09_okhttp.myhttp.Response2;

import java.io.IOException;
import java.util.List;

public class InterceptorChainManager implements Chain2 {
    private final List<Interceptor2> interceptors;
    private final int index;
    private final Request2 request;
    private final Call2 call;


    public InterceptorChainManager(List<Interceptor2> interceptors, int index, Request2 request, Call2 call) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
        this.call = call;
    }


    @Override
    public Request2 getRequest() {
        return request;
    }

    @Override
    public Response2 getResponse(Request2 request2) throws IOException {
        if (index >= interceptors.size()) throw new AssertionError();
        if (interceptors.isEmpty()) {
            throw new IOException("参数异常");
        }
        InterceptorChainManager manager=new InterceptorChainManager(interceptors,index+1,request,call);
        Interceptor2 interceptor2 = interceptors.get(index);
        Response2 response2 = interceptor2.doNext(manager);
        return response2;
    }
}
