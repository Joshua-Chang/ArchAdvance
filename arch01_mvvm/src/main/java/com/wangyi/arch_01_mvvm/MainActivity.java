package com.wangyi.arch_01_mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.wangyi.arch_01_mvvm.databinding.ActivityMainBinding;
import com.wangyi.arch_01_mvvm.vm.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1、必须先ReBuilder，2、书写代码绑定(根据布局名称)

        ActivityMainBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        new LoginViewModel(binding);
    }
}
