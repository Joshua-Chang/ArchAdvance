package com.wangyi.arch09_okhttp.chain;

public class Test {
    public static void main(String[] args) {
        Task1 task1=new Task1(false);
        Task2 task2=new Task2(false);
        Task3 task3=new Task3(true);
        Task4 task4=new Task4(false);

        task1.setNextTask(task2);
        task2.setNextTask(task3);
        task3.setNextTask(task4);

        task1.action();
    }
}
