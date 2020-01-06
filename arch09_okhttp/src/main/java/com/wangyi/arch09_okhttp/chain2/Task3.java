package com.wangyi.arch09_okhttp.chain2;

public class Task3 implements IBaseTask{
    @Override
    public void doRunAction(String isTask, IBaseTask nextTask) {
        if ("ok".equals(isTask)){
            System.out.println("拦截器3处理了");
            return;
        }else {
            nextTask.doRunAction(isTask,nextTask);
        }
    }
}
