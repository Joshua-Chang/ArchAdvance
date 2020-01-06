package com.wangyi.arch09_okhttp.chain2;

public class Task1 implements IBaseTask{
    @Override
    public void doRunAction(String isTask, IBaseTask nextTask) {
        if ("ok".equals(isTask)){
            System.out.println("拦截器1处理了");
            return;
        }else {
            nextTask.doRunAction(isTask,nextTask);//ChainManager.doRunAction("ok",ChainManager)
        }
    }
}
