package com.wangyi.arch09_okhttp.myhttp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Dispatcher2 {
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque<RealCall2.AsyncCall2> runningAsyncCalls = new ArrayDeque<>();
    private final Deque<RealCall2.AsyncCall2> readyAsyncCalls = new ArrayDeque<>();
    private ThreadPoolExecutor executorService;


    public void enqueue(RealCall2.AsyncCall2 call) {
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            readyAsyncCalls.add(call);
        }
    }

    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override public Thread newThread(Runnable runnable) {
                    Thread result = new Thread(runnable, "自定义的线程。。。。");
                    result.setDaemon(false);
                    return result;
                }
            });
        }
        return executorService;
    }

    private int runningCallsForHost(RealCall2.AsyncCall2 call) {
        if (runningAsyncCalls == null) {
            return 0;
        }
        int result = 0;
        SocketRequestServer socketRequestServer=new SocketRequestServer();
        for (RealCall2.AsyncCall2 c : runningAsyncCalls) {
//            if (c.get().forWebSocket) continue;
//            if (c.host().equals(call.host())) result++;
            if (socketRequestServer.getHost(c.request()).equals(socketRequestServer.getHost(call.request())))result++;
        }
        return result;
    }

    public void finished(RealCall2.AsyncCall2 call2) {
        runningAsyncCalls.remove(call2);
        if (readyAsyncCalls.isEmpty()) {//是否还有等待的任务需要执行
            return;
        }
        for (RealCall2.AsyncCall2 readyAsyncCall : readyAsyncCalls) {//移动等待队列去运行队列
            readyAsyncCalls.remove(readyAsyncCall);
            runningAsyncCalls.add(readyAsyncCall);
            executorService().execute(readyAsyncCall);//执行完再回收，添加-回收循环
        }

    }
}
