package com.wangyi.pre_02_aop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity implements DBOperation {
    private DBOperation db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        db=this;//oop

        /**
         * aop:方式一:运行时 动态代理
         */
        db = (DBOperation) Proxy.newProxyInstance(DBOperation.class.getClassLoader(), new Class[]{DBOperation.class}, new DBHandler(this));//aop
    }

    private class DBHandler implements InvocationHandler {
        private DBOperation db;

        public DBHandler(DBOperation db) {
            this.db = db;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (db != null) {
                Log.e("xxx", "操作数据库之前,开始备份...");
                save();
                Log.e("xxx", "数据备份完成,等待后续操作...");
                return method.invoke(db, args);
            }
            return null;
        }
    }


    public void jump(View view) {

        /**
         * oop写法
         */

//        db.save();
//        db.insert();
//        db.save();
//        db.delete();


        db.delete();
        startActivity(new Intent(this, OrderActivity.class));

    }

    @Override
    public void insert() {
        Log.e("xxx", "数据insert");

    }

    @Override
    public void update() {
        Log.e("xxx", "数据update");

    }

    @Override
    public void delete() {
        Log.e("xxx", "数据delete");

    }

    @Override
    public void save() {
        Log.e("xxx", "数据save");
    }


}
