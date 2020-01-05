package com.example.arch_08db;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.arch_08db.db.BaseDao;
import com.example.arch_08db.db.BaseDaoFactory;
import com.example.arch_08db.db.UserDao;
import com.example.arch_08db.subdb.BaseDaoSubFactory;
import com.example.arch_08db.subdb.PhotoDao;
import com.example.arch_08db.update.UpdateManager;
import com.facebook.stetho.Stetho;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class,User.class);
        initView();
    }

    private void initView(){
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class,User.class);
                baseDao.insert(new User(1,"netease1","111"));
                baseDao.insert(new User(2,"netease2","111"));
                baseDao.insert(new User(3,"netease3","111"));
                baseDao.insert(new User(4,"netease4","111"));
                baseDao.insert(new User(5,"netease5","111"));
                baseDao.insert(new User(6,"netease6","111"));
            }
        });

        // 如何自动创建数据库
        // 如何自动创建数据表
        // 如何让用户在使用的时候非常方便
        // 将user对象里面的类名 属性 转换成 创建数据库表的sql语句
        // create table user(id integer,name text,password text);

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 基类  user 商品表 订单表（关联查询）
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class,User.class);
                User where = new User();
//                where.setName("netease");
//                where.setPassword("111");
                List<User> list = baseDao.query(where);
                Log.e(">>>>>"," list size is "+list.size());
                for(int i=0;i<list.size();i++){
                    Log.e(">>>>>", list.get(i).toString());
                }
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update tb_user where uid=2 set password='111'
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class,User.class);
                User user = new User();
                user.setId(2);
                user.setName("netease111111");
                user.setPassword("111");

                User where = new User();
                where.setId(2);
                baseDao.update(user,where);
                Toast.makeText(MainActivity.this,"执行成功!",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OrderDao orderDao = BaseDaoFactory.getInstance().getBaseDao(OrderDao.class,User.class);


                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class,User.class);
                User where = new User();
//                where.setName("netease111111");
//                where.setPassword("111");

                baseDao.delete(where);



//                orderDao.delete(where);
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 服务器返回的信息
                User user = new User();
                user.setName("netease"+(i++));
                user.setPassword("154657567");
                user.setId(i);
                // 数据插入
                userDao.insert(user);
                Toast.makeText(MainActivity.this,"执行成功",Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.subInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = new Photo();
                photo.setPath("/data/data/xxx.jpg");
                photo.setTime(new Date().toString());
                PhotoDao photoDao = BaseDaoSubFactory.getInstance().getBaseDao(PhotoDao.class,Photo.class);
                photoDao.insert(photo);
            }
        });
        findViewById(R.id.newVersion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateManager updateManager = new UpdateManager();
                updateManager.startUpdateDb(MainActivity.this);
            }
        });
    }
}

