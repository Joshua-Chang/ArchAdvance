package com.example.arch10_glide.cache;

import com.example.arch10_glide.Tool;
import com.example.arch10_glide.resource.Value;
import com.example.arch10_glide.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 活动缓存 -- 真正被使用的资源
 */
public class ActiveCache {
    private Map<String, WeakReference<Value>> mapList = new HashMap<>();
    private ReferenceQueue<Value> queue;//监听弱引用是否被回收了
    private boolean isCloseThread;
    private Thread thread;
    private boolean isShoudonRemove;
    private ValueCallback callback;

    public ActiveCache(ValueCallback callback) {
        this.callback = callback;
    }

    /**
     * TODO 添加 活动缓存
     *
     * @param k
     * @param v
     */
    public void put(String k, Value v) {
        Tool.checkNotEmpty(k);
        // 绑定Value的监听 --> Value发起来的（Value没有被使用了，就会发起这个监听，给外界业务需要来使用）
        v.setCallback(callback);
        mapList.put(k, new CustomWeakReference(v,getQueue(),k));
    }

    /**
     * TODO 给外界获取Value
     * @param k
     * @return
     */
    public Value get(String k){
        WeakReference<Value> valueWeakReference = mapList.get(k);
        if (valueWeakReference != null) {
            return valueWeakReference.get();
        }
        return null;
    }

    /**
     * TODO 手动移除
     * @param key
     * @return
     */
    public Value remove(String key) {
        isShoudonRemove = true;
        WeakReference<Value> valueWeakReference = mapList.remove(key);
        isShoudonRemove = false; // 还原 目的是为了 让 GC自动移除 继续工作
        if (null != valueWeakReference) {
            return valueWeakReference.get();
        }
        return null;
    }

    /**
     * TODO 释放 关闭线程
     */
    public void closeThread(){
        isCloseThread=true;
//        if (thread != null) {
//            thread.interrupt(); // 中断线程
//            try {
//                thread.join(TimeUnit.SECONDS.toMillis(5)); // 线程稳定安全 停止下来
//                if (thread.isAlive()) { // 证明线程还是没有结束
//                    throw new IllegalStateException("活动缓存中 关闭线程 线程没有停止下来...");
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        mapList.clear();

        System.gc();
    }

    /**
     * 监听弱引用 成为弱引用的子类  为什么要成为弱引用的子类（目的：为了监听这个弱引用 是否被回收了）
     */
    public class CustomWeakReference extends WeakReference<Value> {
        private String key;

        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> queue, String key) {
            super(referent, queue);
            this.key = key;
        }

    }

    /**
     * 为了监听 弱引用被回收，被动移除的
     * @return
     */
    private ReferenceQueue<Value> getQueue() {
        if (queue != null) {
            queue = new ReferenceQueue<>();
            thread=new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (!isCloseThread) {
                        try {
                            if (!isShoudonRemove) {
                                Reference<? extends Value> remove = queue.remove();//阻塞方法，回收后才会往后执行
                                CustomWeakReference weakReference=(CustomWeakReference)remove;
                                // 移除容器     !isShoudonRemove：为了区分手动移除 和 被动移除
                                if (mapList != null&&!mapList.isEmpty()) {
                                    mapList.remove(weakReference.key);
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
        return queue;
    }
}
