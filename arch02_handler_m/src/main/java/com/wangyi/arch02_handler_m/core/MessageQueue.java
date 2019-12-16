package com.wangyi.arch02_handler_m.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {
    // 阻塞队列
    BlockingQueue<Message> blockingDeque=new ArrayBlockingQueue<>(50);
    // 将Message消息对象存入阻塞队列中
    public boolean enqueueMessage(Message msg, long uptimeMillis) {
        try {
            blockingDeque.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 从消息队列中取出消息
    public Message next() {
        try {
            return blockingDeque.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
