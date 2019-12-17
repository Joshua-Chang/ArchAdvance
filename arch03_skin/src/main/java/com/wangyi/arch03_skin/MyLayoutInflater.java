package com.wangyi.arch03_skin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

public class MyLayoutInflater extends LayoutInflater {

    private Activity activity;

    protected MyLayoutInflater(LayoutInflater original, Context newContext, Activity activity) {
        super(original, newContext);
        this.activity = activity;
        setFactory2(SkinActivityLifecycleCallbacks.skinFactory);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new MyLayoutInflater(this,newContext,activity);
    }

}
