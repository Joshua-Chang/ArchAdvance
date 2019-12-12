package com.wangyi.arch_01_mvp2;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wangyi.arch_01_mvp2.base.BaseView;
import com.wangyi.arch_01_mvp2.bean.UserInfo;
import com.wangyi.arch_01_mvp2.login.LoginContract;
import com.wangyi.arch_01_mvp2.login.LoginPresenter;

public class LoginActivity extends BaseView<LoginPresenter, LoginContract.View> {
    private EditText mEtName;
    private EditText mEtPwd;
    private Button mBtLogin;


    @Override
    public LoginContract.View getContract() {
//        return new LoginContract.View() {
//            @Override
//            public void handlerResult(BaseEntity baseEntity) {
//
//            }
//        };
        return new LoginContract.View<UserInfo>() {
            @Override
            public void handlerResult(UserInfo userInfo) {
                if (userInfo != null) {
                    Toast.makeText(LoginActivity.this, ""+userInfo.toString(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {

    }

    private void initView() {
        mEtName = findViewById(R.id.et_name);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtLogin = findViewById(R.id.bt_login);
    }

    public void doLoginAction(View view) {
        String name = mEtName.getText().toString();
        String pwd = mEtPwd.getText().toString();
        p.getContract().requestLogin(name,pwd);
    }
}
