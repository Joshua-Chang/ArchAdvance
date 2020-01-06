package com.wangyi.arch09_okhttp.chain2;

public interface IBaseTask {

    public void doRunAction(String isTask,IBaseTask nextTask);
}
