package com.wangyi.arch09_okhttp.chain;

public abstract class BaseTask {
    private boolean isTask;

    public BaseTask(boolean isTask) {
        this.isTask = isTask;
    }

    private BaseTask nextTask;

    public void setNextTask(BaseTask nextTask) {
        this.nextTask = nextTask;
    }

    public abstract void doAction();

    public void action(){
        if (isTask) {
            doAction();
        }else {
            if (nextTask != null) {
                nextTask.action();
            }
        }
    }
}
