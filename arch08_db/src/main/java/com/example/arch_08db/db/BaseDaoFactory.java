package com.example.arch_08db.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseDaoFactory {
    private static BaseDaoFactory instance = null;
    private SQLiteDatabase sqLiteDatabase;
    private String sqlitePath;
    // 设计要给数据库连接池，new 容器，只要new个一次，下次就不会再创建了。考虑多线程的问题
    protected Map<String,BaseDao> map = Collections.synchronizedMap(new HashMap<String, BaseDao>());

    protected BaseDaoFactory() {
        sqlitePath = "data/data/com.example.arch_08db/net.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqlitePath, null);
    }

    public static BaseDaoFactory getInstance() {
        if (null == instance) {
            synchronized (BaseDaoFactory.class) {
                if (null == instance) {
                    instance = new BaseDaoFactory();
                }
            }
        }
        return instance;
    }


    //生成baseDao对象
//    public <T>BaseDao<T> getBaseDao(Class<T> entityClass){
    public <T extends BaseDao<M>, M> T getBaseDao(Class<T> daoClass, Class<M> entityClass) {
        BaseDao baseDao = null;
        if (map.get(daoClass.getSimpleName())!=null){
            return (T)map.get(daoClass.getSimpleName());
        }
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            map.put(daoClass.getSimpleName(),baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }


}
