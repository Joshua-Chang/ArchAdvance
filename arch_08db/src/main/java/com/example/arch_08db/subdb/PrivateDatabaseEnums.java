package com.example.arch_08db.subdb;

import com.example.arch_08db.User;
import com.example.arch_08db.db.BaseDaoFactory;
import com.example.arch_08db.db.UserDao;

import java.io.File;

public enum PrivateDatabaseEnums {

    database("");

    private String value;

    PrivateDatabaseEnums(String value) {

    }

    public String getValue() {
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (userDao != null) {
            User currentUser = userDao.getCurrentUser();
            if (currentUser != null) {
                File file = new File("data/data/com.example.arch_08db/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file.getAbsolutePath() + "/u_" + currentUser.getId() + "_private.db";
            }
        }
        return null;
    }
}