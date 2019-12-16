// ILoginInterface.aidl
package com.wangyi.arch03_binder;

// Declare any non-default types here with import statements

interface ILoginInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
void login();
void loginCallback(boolean loginStatus,String loginUser);
}
