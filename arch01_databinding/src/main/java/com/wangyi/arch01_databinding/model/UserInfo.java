package com.wangyi.arch01_databinding.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.databinding.library.baseAdapters.BR;

/**
 * 方式一:
 * 数据需要刷新时要注意:
 * 1.继承BaseObservable
 * 2.get加@Bindable注解
 * 3.set后加notifyPropertyChanged(BR.pwd);
 */
//public class UserInfo extends BaseObservable {
//
//
//
//    private String name;
//    private String pwd;
//
//    @Bindable
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//        notifyPropertyChanged(BR.name);
//    }
//
//    @Bindable
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//        notifyPropertyChanged(BR.pwd);
//    }
//
//    @Override
//    public String toString() {
//        return "UserInfo{" +
//                "name='" + name + '\'' +
//                ", pwd='" + pwd + '\'' +
//                '}';
//    }
//}

/**
 * 方式一
 * public
 * ObservableField
 */
public class UserInfo{
    public ObservableField<String>name=new ObservableField<>();
    public ObservableField<String>pwd=new ObservableField<>();

}


