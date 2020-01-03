package com.example.arch_08db.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.example.arch_08db.annotation.DbField;
import com.example.arch_08db.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseDao<T> implements IBaseDao<T> {
    // 持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;
    // 表名
    private String tableName;
    // 操作数据库所对应的java类型
    private Class<T> entityClass;
    // 标识，用来标识是否已经做过初始化
    private boolean isInit = false;
    // 定义一个缓存空间(key 字段名 value 成员变量)
    private HashMap<String, Field> cacheMap;


    public boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        if (!isInit) {
            // 根据传入的Class进行数据表的创建 本例子中对应的是User对象；
            DbTable dt = entityClass.getAnnotation(DbTable.class);
            if (dt != null && !"".equals(dt.value())) {
                tableName = dt.value();
            } else {
                tableName = entityClass.getName();
            }
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            String createTableSql = getCreateTableSql();
            Log.e(">>>>", "execSQL(createTableSql):"+createTableSql);
            sqLiteDatabase.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    private void initCacheMap() {//缓存起来避免每次都反射，耗费性能
        //获取所有列名
        String sql="select * from "+tableName+" limit 1,0";//从第一条取，取零条数据：可以获得表结构，数据不会取出来，速度快。
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        Field[] columnFields = entityClass.getDeclaredFields();

        for (String columnName : columnNames) {
            Field columnField=null;
            for (Field field : columnFields) {
                field.setAccessible(true);//将字段访问权限打开
                String fieldName=null;
                DbField dbField = field.getAnnotation(DbField.class);
                if (dbField != null && !"".equals(dbField.value())) {
                    fieldName = dbField.value();
                } else {
                    fieldName = field.getName();
                }
                if (columnName.equals(fieldName)) {
                    columnField=field;
                    break;
                }
            }
            if (columnField!=null) {
                cacheMap.put(columnName,columnField);
            }
        }
    }

    private String getCreateTableSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists ");
        stringBuilder.append(tableName + "(");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName;
            Class<?> type = field.getType();
            DbField dbField = field.getAnnotation(DbField.class);
            if (dbField != null && !"".equals(dbField.value())) {
                fieldName = dbField.value();
            } else {
                fieldName = field.getName();
            }
            if (type == String.class) {
                stringBuilder.append(fieldName + " TEXT,");
            } else if (type == Integer.class) {
                stringBuilder.append(fieldName + " INTEGER,");
            } else if (type == Long.class) {
                stringBuilder.append(fieldName + " BIGINT,");
            } else if (type == Double.class) {
                stringBuilder.append(fieldName + " DOUBLE,");
            } else if (type == byte[].class) {
                stringBuilder.append(fieldName + " BLOB,");
            } else {
                //不支持的类型号
                continue;
            }
        }
        if (stringBuilder.charAt(stringBuilder.length()-1)==',') {//去除最后的逗号
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }


    @Override
    public long insert(T entity) {
        // user 对象 转换为contentvalues  new User(id 1,name = "netease","password");
        Map<String,String> map = getValues(entity);//对象转map
        ContentValues values = getContentValues(map);//map转ContentValues
        return sqLiteDatabase.insert(tableName,null,values);
    }

    @Override
    public long update(T entity, T where) {
        Map<String, String> map = getValues(entity);
        ContentValues values=getContentValues(map);

        Map<String, String> whereMap = getValues(where);
        Condition condition=new Condition(whereMap);
        return sqLiteDatabase.update(tableName,values,condition.whereCause,condition.whereArgs);
    }

    @Override
    public int delete(T where) {
        Map<String, String> map = getValues(where);
        Condition condition=new Condition(map);
        return sqLiteDatabase.delete(tableName,condition.whereCause,condition.whereArgs);    }

    @Override
    public List<T> query(T where) {
        return this.query(where,null,null,null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Map<String, String> map = getValues(where);
        // select * from tableName  limit 0,10;
        String limitString=null;
        if (startIndex!=null&&limit!=null){
            limitString=startIndex+" , "+limit;
        }
//        seclections、selectionArgs[]用来组装查询语句
        //String seclections = "where 1=1 and id=? and name=?";
        //String selectionArgs = String[]{,"1,"Tom"};
        //组装为select * from tableName where id=1 and name=Tom
        Condition condition=new Condition(map);
        Cursor cursor = sqLiteDatabase.query(tableName, null, condition.whereCause, condition.whereArgs, null, null, orderBy,limitString);
        // 定义一个解析游标的方法
        List<T> result = getResult(cursor,where);
        return result;
    }

    /**
     * 游标查询结果-----》转为List<User>查询结果
     * @param cursor
     * @param obj
     * @return
     */
    private List<T> getResult(Cursor cursor, T obj) {
        ArrayList list = new ArrayList();
        Object item = null;// User user = null;
        while (cursor.moveToNext()) {//查出user1，user2组成list
            try {
                item=obj.getClass().newInstance();// user1 = new User();
                Iterator iterator = cacheMap.entrySet().iterator();// 所有成员变量对应数据库字段
                while (iterator.hasNext()) {//遍历user1的u_id、name、password等成员变量
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String columnName = (String) entry.getKey();//列名 u_id
                    // 以列名拿到列名在游标中的位置
                    Integer columnIndex = cursor.getColumnIndex(columnName);
                    // 获取成员变量的类型
                    Field field = (Field) entry.getValue();
                    Class type = field.getType();
                    if (columnIndex!=-1) {
                        if (type==String.class) {
                            field.set(item,cursor.getString(columnIndex));
                        }else if(type== Double.class){
                            field.set(item,cursor.getDouble(columnIndex));
                        }else if(type== Integer.class){
                            field.set(item,cursor.getInt(columnIndex));//反射id.set(user,1);  等于user.setId(1);
                        }else if(type== Long.class){
                            field.set(item,cursor.getLong(columnIndex));
                        }else if(type==byte[].class){
                            field.set(item,cursor.getBlob(columnIndex));
                        }else{
                            continue;
                        }
                    }
                }

                list.add(item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        cursor.close();//关闭防止泄露
        return list;
    }

    /**
     * map--->ContentValues（googleAPI）
     * @param map
     * @return
     */
    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues=new ContentValues();
        Set keys = map.keySet();
        Iterator <String>iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value!=null) {
                contentValues.put(key,value);
            }
        }
        return contentValues;
    }

    /**
     * 对象转Map
     * @param entity
     * @return
     */
    private Map<String, String> getValues(T entity) {
        Map<String,String> map=new HashMap<>();//所有的成员变量
        Iterator<Field> fieldIterator = cacheMap.values().iterator();
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            field.setAccessible(true);
            try {
                Object object = field.get(entity);//获得成员变量的值
                if (object==null) {
                    continue;
                }
                String value = object.toString();
                String key;
                DbField dbField = field.getAnnotation(DbField.class);
                if (dbField != null && !"".equals(dbField.value())) {
                    key = dbField.value();
                } else {
                    key = field.getName();
                }
                if (!TextUtils.isEmpty(key)&&!TextUtils.isEmpty(value)) {
                    map.put(key,value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * map转seclections、selectionArgs[]用来组装查询语句
     */
    private class Condition{
        private String whereCause;
        private String[] whereArgs;

        public Condition(Map<String,String> whereMap){
            ArrayList list = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            /**
             * 其实，1=1 是永恒成立的，意思无条件的，也就是说在SQL语句中有没有这个1=1都可以。
             *
             * 这个1=1常用于应用程序根据用户选择项的不同拼凑where条件时用的。
             *
             * 如：web界面查询用户的信息，where默认为1=1，这样用户即使不选择任何条件，sql查询也不会出错。如果用户选择了姓名，那么where变成了where 1=1 and 姓名='用户输入的姓名'，如果还选择了其他的条件，就不断在where 条件后追加 and语句就行了。
             *
             * 如果不用1=1的话，每加一个条件，都要判断前面有没有where 条件，如果没有就写where ...，有就写and语句，因此此时用1=1可以简化了应用程序的复杂度。
             */
            stringBuilder.append("1=1");
            // 获取所有的字段名
            Set keys = whereMap.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()){
                String key = (String)iterator.next();
                String value = whereMap.get(key);
                if(value != null){
                    stringBuilder.append(" and "+key+" =?");
                    list.add(value);
                }
            }
            this.whereCause = stringBuilder.toString();
            this.whereArgs = (String[])list.toArray(new String[list.size()]);
        }
    }
}
