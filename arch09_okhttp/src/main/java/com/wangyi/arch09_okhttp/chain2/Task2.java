package com.wangyi.arch09_okhttp.chain2;

public class Task2 implements IBaseTask{
    @Override
    public void doRunAction(String isTask, IBaseTask nextTask) {
        if ("ok".equals(isTask)){
            System.out.println("拦截器2处理了");
            return;
        }else {
            nextTask.doRunAction(isTask,nextTask);
        }
    }
}
