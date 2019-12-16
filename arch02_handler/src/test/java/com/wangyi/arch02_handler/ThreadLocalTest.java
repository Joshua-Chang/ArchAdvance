package com.wangyi.arch02_handler;

import org.junit.Test;

public class ThreadLocalTest {
    /**
     * threadlocal而是一个线程内部的存储类，可以在指定线程内存储数据
     * 比喻:ThreadLocal相当于维护了一个map，key就是当前的线程，value就是需要存储的对象
     * 实际不是map,是ThreadLocal的静态内部类ThreadLocalMap为每个Thread都维护了一个Entry数组table，ThreadLocal确定了一个数组下标，而这个下标就是value存储的对应位置
     * 实例化ThreadLocalMap时创建了一个长度为16的Entry数组。通过hashCode(通过斐波那契算法散列hash均匀分布,避免hash冲突)与length位运算确定出一个索引值i，这个i就是被存储在table数组中的位置
     * 每个线程持有一个ThreadLocalMap对象
     */
////set 方法
//    public void set(T value) {
//        //获取当前线程
//        Thread t = Thread.currentThread();
//        //实际存储的数据结构类型
//        ThreadLocalMap map = getMap(t);
//        //如果存在map就直接set，没有则创建map并set
//        if (map != null)
//            map.set(this, value);
//        else
//            createMap(t, value);
//    }
//
//    //getMap方法
//    ThreadLocalMap getMap(Thread t) {
//        //thred中维护了一个ThreadLocalMap
//        return t.threadLocals;
//    }
//
//    //createMap
//    void createMap(Thread t, T firstValue) {
//        //实例化一个新的ThreadLocalMap，并赋值给线程的成员变量threadLocals
//        t.threadLocals = new ThreadLocalMap(this, firstValue);
//    }

    @Test
    public void test() {
        // 创建本地线程（主线程）
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>()
        {
            @Override
            protected String initialValue() {
                // 重写初始化方法，默认返回null，如果ThreadLocalMap拿不到值再调用初始化方法
                return "冯老师";
            }
        };

        // 从ThreadLocalMap中获取String值，key是主线程
        System.out.println("主线程threadLocal：" + threadLocal.get());

        //--------------------------thread-0
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 从ThreadLocalMap中获取key：thread-0的值？没有，拿不到值再调用初始化方法
                String value1 = threadLocal.get();
                System.out.println("thread-0：" + value1); // 冯老师

                // ThreadLocalMap存入：key:thread-0  value:"熊老师"
                threadLocal.set("熊老师");
                String value2 = threadLocal.get(); // 可以get到了
                System.out.println("thread-0  set  >>> " + value2); // 熊老师

                // 使用完成建议remove()，避免大量无意义的内存占用
                threadLocal.remove();
            }
        });
        thread.start();

        //--------------------------thread-1
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 从ThreadLocalMap中获取key：thread-1的值？没有，拿不到值再调用初始化方法
                String value1 = threadLocal.get();
                System.out.println("thread-1：" + threadLocal.get()); // 冯老师

                // ThreadLocalMap存入：key:thread-1  value:"彭老师"
                threadLocal.set("彭老师");
                String value2 = threadLocal.get(); // 可以get到了
                System.out.println("thread-1  set  >>> " + threadLocal.get()); // 彭老师

                // 使用完成建议remove()，避免大量无意义的内存占用
                threadLocal.remove();
            }
        });
        thread2.start();
    }
    /**
     * ThreadLocal和Synchronized都是为了解决多线程中相同变量的访问冲突问题，不同的点是
     *
     * Synchronized是通过线程等待，牺牲时间来解决访问冲突
     * ThreadLocal是通过每个线程单独一份存储空间，牺牲空间来解决冲突，并且相比于Synchronized，ThreadLocal具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问到想要的值。
     */
}