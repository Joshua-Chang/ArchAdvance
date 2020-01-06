package com.wangyi.arch09_okhttp.chain;

public class Task2 extends BaseTask{
    public Task2(boolean isTask) {
        super(isTask);
    }

    @Override
    public void doAction() {
        System.out.println("Task2------->");
    }
}
