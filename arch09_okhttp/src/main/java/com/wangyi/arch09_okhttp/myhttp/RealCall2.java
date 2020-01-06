package com.wangyi.arch09_okhttp.myhttp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.platform.Platform;

public class RealCall2 implements Call2 {
    private boolean executed;
    private OkHttpClient2 client;
    private Request2 request2;

    public RealCall2(OkHttpClient2 okHttpClient2, Request2 request2) {
        this.client=okHttpClient2;
        this.request2=request2;
    }


    @Override
    public void enqueue(Callback2 responseCallback) {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed");
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

        private Response2 getResponseWithInterceptorChain() {
            return null;
        }

    }
}
