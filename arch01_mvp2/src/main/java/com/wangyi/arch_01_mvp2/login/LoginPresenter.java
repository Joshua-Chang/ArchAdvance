package com.wangyi.arch_01_mvp2.login;

import android.os.SystemClock;

import com.wangyi.arch_01_mvp2.LoginActivity;
import com.wangyi.arch_01_mvp2.base.BasePresenter;
import com.wangyi.arch_01_mvp2.bean.UserInfo;

public class LoginPresenter extends BasePresenter<LoginActivity,LoginModel,LoginContract.Presenter> {
    @Override
    public LoginContract.Presenter getContract() {
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                try {

//                    m.getContract().executeLogin(name, pwd);
//                    HttpEngine<LoginPresenter> engine = new HttpEngine<>(LoginPresenter.this);
//                    engine.post(name, pwd);
//                    if (name.equalsIgnoreCase("tom")&&pwd.equalsIgnoreCase("ttt")) {
//                        responseResult(new UserInfo("网易","Tom"));
//                    }else {
//                        responseResult(null);
//                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(5000);
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                getView().getContract().handlerResult(userInfo);
            }
        };
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }
}
