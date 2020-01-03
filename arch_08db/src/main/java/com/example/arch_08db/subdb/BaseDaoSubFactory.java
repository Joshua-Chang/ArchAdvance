package com.example.arch_08db.subdb;



import android.database.sqlite.SQLiteDatabase;

import com.example.arch_08db.db.BaseDao;
import com.example.arch_08db.db.BaseDaoFactory;


public class BaseDaoSubFactory extends BaseDaoFactory {
    private static BaseDaoSubFactory instance = null;
    private SQLiteDatabase subSqLiteDatabase;
    private BaseDaoSubFactory() {
    }

    public static BaseDaoSubFactory getInstance() {
        if (null == instance) {
            synchronized (BaseDaoSubFactory.class) {
                if (null == instance) {
                    instance = new BaseDaoSubFactory();
                }
            }
        }
        return instance;
    }


    //生成baseDao对象
    public <T extends BaseDao<M>, M> T getBaseDao(Class<T> daoClass, Class<M> entityClass) {
        BaseDao baseDao = null;
        if (map.get(PrivateDatabaseEnums.database.getValue())!=null){
            return (T)map.get(PrivateDatabaseEnums.database.getValue());
        }
        try {
            subSqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(PrivateDatabaseEnums.database.getValue(),null);
            baseDao = daoClass.newInstance();
            baseDao.init(subSqLiteDatabase, entityClass);
            map.put(PrivateDatabaseEnums.database.getValue(),baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }


}
