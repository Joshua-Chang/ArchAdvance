package com.wangyi.arch09_okhttp.myhttp;

import com.wangyi.arch09_okhttp.interceptorchain.ConnectionServerInterceptor;
import com.wangyi.arch09_okhttp.interceptorchain.Interceptor2;
import com.wangyi.arch09_okhttp.interceptorchain.InterceptorChainManager;
import com.wangyi.arch09_okhttp.interceptorchain.RequestHeaderInterceptor2;
import com.wangyi.arch09_okhttp.interceptorchain.RetryInterceptor2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.platform.Platform;

public class RealCall2 implements Call2 {
    private boolean executed;
    private OkHttpClient2 client;
    private Request2 request2;
    final RetryInterceptor2 retryAndFollowUpInterceptor;

    public RealCall2(OkHttpClient2 okHttpClient2, Request2 request2) {
        this.client=okHttpClient2;
        this.request2=request2;
        this.retryAndFollowUpInterceptor = new RetryInterceptor2(client);
    }


    @Override
    public void enqueue(Callback2 responseCallback) {
        synchronized (this) {
            if (executed) throw new IllegalStateException("不能重复执行");
            executed = true;
        }
        client.dispatcher2().enqueue(new AsyncCall2(responseCallback));
    }

    public final class AsyncCall2 implements Runnable {
        private Callback2 callback2;

        public AsyncCall2(Callback2 callback2) {
            this.callback2 = callback2;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response2 response = getResponseWithInterceptorChain();
                if (client.isCanceled()) {
                    signalledCallback = true;
                    callback2.onFailure(RealCall2.this, new IOException("用户取消了Canceled"));
                } else {
                    signalledCallback = true;
                    callback2.onResponse(RealCall2.this, response);
                }
            } catch (IOException e) {
                if (signalledCallback) {//用户操作报错
                    System.out.println("用户在使用过程中出错");
                } else {
                    callback2.onFailure(RealCall2.this, e);
                }
            } finally {
                client.dispatcher2().finished(this);//回收
            }
        }

        Request2 request() {
            return request2;
        }

        private Response2 getResponseWithInterceptorChain() throws IOException {
            List<Interceptor2> interceptors = new ArrayList<>();
//            interceptors.add(retryAndFollowUpInterceptor);
            interceptors.add(new RequestHeaderInterceptor2());
            interceptors.add(new ConnectionServerInterceptor());
            InterceptorChainManager chainManager=new InterceptorChainManager(interceptors,0,request2,RealCall2.this);
            return chainManager.getResponse(request2);
        }

    }
}
