package com.wangyi.arch09_okhttp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {
    public static void main(String[] args) {


//    jdk 1.5线程复用,线程池完成线程的管理工作
        /**
         * todo 参数一：corePoolSize 核心线程数
         * todo 参数二：maximumPoolSize 最大线程数 线程池非核心线程数 线程池规定大小
         * todo 参数三/四：时间数值keepAliveTime， 单位：时分秒  60s
         *                正在执行的任务Runnable20 < corePoolSize --> 参数三/参数四 才会起作用
         *                作用：Runnable1执行完毕后 闲置60s，如果过了闲置60s,会回收掉Runnable1任务,，如果在闲置时间60s 复用此线程Runnable1
         *
         * todo 参数五：workQueue队列 ：会把超出的任务加入到队列中 缓存起来
         *
         */
        // 线程池里面 只有一个核心线程在跑任务
//        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
//        ExecutorService executorService = new ThreadPoolExecutor(5, 1, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());//核心超过最大奔溃
//        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());//正常
        //（OKHTTP）永远只有一个线程在跑：MAX_VALUE 》 0 --> 参数三/参数四 才会起作用
        //Runnable1执行完毕后 闲置60s，如果在闲置时间60s内 复用此线程Runnable1,如果过了闲置60s,会回收掉Runnable1任务,
//        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        // Java设计者 考虑到了不用使用线程池的参数配置，提供了API包装

        /**
         * new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());//
         */
//        ExecutorService executorService = Executors.newCachedThreadPool();//缓存线程池
        /**
         * new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()));
         */
//        ExecutorService executorService = Executors.newSingleThreadExecutor();//单例，线程池里只有一个核心线程，最大线程也一个
        /**
         * new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
         */
//        ExecutorService executorService = Executors.newFixedThreadPool(5);//固定线程池，线程池里有N个核心线程，N个最大线程

//        (okhttp)线程工厂
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread result = new Thread(r, "OkHttp Dispatcher");
                result.setDaemon(false);
                return result;
            }
        });

        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println(System.currentTimeMillis() + " 当前线程，执行耗时任务，线程是： " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}
