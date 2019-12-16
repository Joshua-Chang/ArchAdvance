package com.wangyi.arch02_handler_m.core;

public class Looper {
    public MessageQueue mQueue;
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();

    private Looper(boolean quitAllowed) {
        mQueue=new MessageQueue();
    }

    public static void prepare(boolean quitAllowed) {
        // 主线程只有唯一一个Looper对象
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }
    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    // 轮询，提取消息
    public static void loop() {
        // 从全局ThreadLocalMap中获取唯一：Looper对象
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        // 从Looper对象中获取全局唯一消息队列MessageQueue对象
        final MessageQueue queue = me.mQueue;
        // 从消息队列中取消息
        while (true){
            Message msg=queue.next();
            if (msg != null) {
                msg.target.dispatchMessage(msg);            }
        }
    }
}
