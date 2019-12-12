package com.wangyi.arch_01_mvp2.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseView<P extends BasePresenter,CONTRACT> extends Activity {
    protected P p;
    public abstract CONTRACT getContract();
    public abstract P getPresenter();

    public BaseView() {
    }

    public BaseView(P p) {
        this.p = p;
    }
    public void error(Exception e){}


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p=getPresenter();
        p.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.unBindView();
    }
}
