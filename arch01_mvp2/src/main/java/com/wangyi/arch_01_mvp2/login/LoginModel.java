package com.wangyi.arch_01_mvp2.login;

import com.wangyi.arch_01_mvp2.base.BaseModel;
import com.wangyi.arch_01_mvp2.bean.UserInfo;

public class LoginModel extends BaseModel<LoginPresenter,LoginContract.Model> {
    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String pwd) throws Exception {
                if (name.equalsIgnoreCase("tom")&&pwd.equalsIgnoreCase("ttt")) {
                    P.getContract().responseResult(new UserInfo("网易","Tom"));
                }else {
                    P.getContract().responseResult(null);
                }
            }
        };
    }
}
