// ILoginInterface.aidl
package com.wangyi.arch03_binder;
//包名必须一致

// Declare any non-default types here with import statements

interface ILoginInterface {
//    /**
//     * Demonstrates some basic types that you can use as parameters
//     * and return values in AIDL.
//     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
void login();
void loginCallback(boolean loginStatus,String loginUser);
}
