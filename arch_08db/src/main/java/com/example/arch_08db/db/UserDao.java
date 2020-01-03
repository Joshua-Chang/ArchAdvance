package com.example.arch_08db.db;

import android.util.Log;

import com.example.arch_08db.User;

import java.util.List;

// 维护用户的共有数据
public class UserDao extends BaseDao<User> {
    @Override
    public long insert(User entity) {
        boolean hasLogined = false;
        List<User> list = query(new User());//查询所有用户
        User where = null;

        for (User user : list) {
            where = new User();
            where.setId(user.getId());
            user.setStatus(0);
            update(user, where);
            Log.e(">>>", "用户" + user.getName() + "更改为未登录状态");
            if (user.getId().equals(entity.getId())) {
                hasLogined=true;
            }
        }
        if (hasLogined) {
            Log.e(">>>", "用户" + entity.getName() + "重新登录");
            User user = new User();
            user.setId(entity.getId());
            user.setStatus(1);
            user.setName(entity.getName());
            user.setPassword(entity.getPassword());
            return update(user,entity);
        }else {
            Log.e(">>>", "用户" + entity.getName() + "登录");
            entity.setStatus(1);
            return super.insert(entity);
        }
    }

    // 获取当前登录的User
    public User getCurrentUser() {
        User where = new User();
        where.setStatus(1);
        List<User> list = query(where);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
