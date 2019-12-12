package com.wangyi.arch_01_mvp2.http_lib;


import com.wangyi.arch_01_mvp2.base.BasePresenter;
import com.wangyi.arch_01_mvp2.bean.UserInfo;
import com.wangyi.arch_01_mvp2.login.LoginPresenter;

public class HttpEngine <P extends BasePresenter>{
     private P p;

    public HttpEngine(P p) {
        this.p = p;
    }
    public void post(String name,String pwd){//本应为共有业务,此处具体
        if (name.equalsIgnoreCase("tom")&&pwd.equalsIgnoreCase("ttt")) {
            ((LoginPresenter) p).getContract().responseResult(new UserInfo("网易","Tom"));
        }else {
            ((LoginPresenter) p).getContract().responseResult(null);
        }
    }
}
