package com.wangyi.arch_01_mvp2.login;

import com.wangyi.arch_01_mvp2.bean.BaseEntity;

public interface LoginContract {
    interface Model{
        void executeLogin(String name,String pwd)throws Exception;//2
    }
    interface View<T extends BaseEntity>{
        void handlerResult(T t);//4
    }
    interface Presenter<T extends BaseEntity>{
        void requestLogin(String name,String pwd);//1
        void responseResult(T t);//3
    }
}
