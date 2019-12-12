package com.wangyi.arch_01_mvp2.base;

public abstract class BaseModel<P extends BasePresenter,CONTRACT> {
    protected P P;

    public BaseModel(P p) {
        P = p;
    }

    public abstract CONTRACT getContract();

}
