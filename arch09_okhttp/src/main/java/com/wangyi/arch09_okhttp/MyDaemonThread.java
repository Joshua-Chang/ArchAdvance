package com.wangyi.arch09_okhttp;

public class MyDaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    System.out.println("run...");
                }
            }
        };

//        thread.setDaemon(true);
        thread.start();
        // JVM main所持有的进程 该结束了..
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
