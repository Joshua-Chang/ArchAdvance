package com.wangyi.arch_01_mvvm.vm;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.wangyi.arch_01_mvvm.databinding.ActivityMainBinding;
import com.wangyi.arch_01_mvvm.model.UserInfo;

public class LoginViewModel {
    public UserInfo userInfo;

    public LoginViewModel(ActivityMainBinding binding) {
        userInfo=new UserInfo();
        binding.setLoginViewModel(this);        // 将ViewModel和View进行绑定，通过DataBinding工具
    }
    public TextWatcher nameInputListener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // View层接收到用户的输入，改变Model层的javabean属性
            userInfo.name.set(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public TextWatcher pwdInputListener=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            userInfo.pwd.set(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public View.OnClickListener loginClickListener=new View.OnClickListener() {
        // 模拟网络请求
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Model层属性的变更，改变View层的显示
//                     userInfo.name.set("Mir Peng");
                    SystemClock.sleep(2000);
                    if ("Tom".equalsIgnoreCase(userInfo.name.get())&&"ttt".equalsIgnoreCase(userInfo.pwd.get())) {
                        Log.d("LoginViewModel", "登录成功");
                    }else {
                        Log.d("LoginViewModel", "登录失败");
                    }
                }
            }).start();
        }
    };


}
