package com.wangyi.arch09_okhttp.chain;

public class Task1 extends BaseTask{
    public Task1(boolean isTask) {
        super(isTask);
    }

    @Override
    public void doAction() {
        System.out.println("Task1------->");
    }
}
