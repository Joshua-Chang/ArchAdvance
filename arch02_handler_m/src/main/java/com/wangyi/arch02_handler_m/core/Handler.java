package com.wangyi.arch02_handler_m.core;

import android.util.Log;

public class Handler {
    private Looper mLooper;
    private MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }

    public void handleMessage(Message msg) {
    }

    public boolean sendMessage(Message msg, long uptimeMillis) {
        // 将消息放入消息队列中
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }

    private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        msg.target = this;

        return queue.enqueueMessage(msg, uptimeMillis);

    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}
