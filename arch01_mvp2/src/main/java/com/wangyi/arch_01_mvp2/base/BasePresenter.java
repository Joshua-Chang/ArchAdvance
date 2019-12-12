package com.wangyi.arch_01_mvp2.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView,M extends BaseModel,CONTRACT> {
    protected M m;
    private WeakReference<V>vWeakReference;

    public void bindView(V v){
        vWeakReference=new WeakReference<>(v);
    }

    public void unBindView() {
        if (vWeakReference != null) {
            vWeakReference.clear();
            vWeakReference=null;
            System.gc();
        }
    }

    public V getView(){
        if (vWeakReference != null) {
            return vWeakReference.get();
        }
        return null;
    }

    public abstract CONTRACT getContract();
    public abstract M getModel();

    public BasePresenter() {
        m=getModel();
    }
}
