package com.wangyi.arch09_okhttp.chain2;

import java.util.ArrayList;
import java.util.List;

public class ChainManager implements IBaseTask {
    private List<IBaseTask> iBaseTaskList=new ArrayList<>();
    private int index;
    public void addTask(IBaseTask iBaseTask){
        iBaseTaskList.add(iBaseTask);
    }
    @Override
    public void doRunAction(String isTask, IBaseTask iBaseTask) {
        if (iBaseTaskList.isEmpty()) {
            return;
        }

        if (index==iBaseTaskList.size()||index>iBaseTaskList.size()) {
            return;
        }
        IBaseTask iBaseTaskResult = iBaseTaskList.get(index);//t1
        index++;
        iBaseTaskResult.doRunAction(isTask,iBaseTask);//t1.doRunAction
    }
}
