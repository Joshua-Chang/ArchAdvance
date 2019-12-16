// MyIPackageManager.aidl
package com.wangyi.arch03_pms;

// Declare any non-default types here with import statements

interface MyIPackageManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    PackageInfo getPackageInfo(String packageName, int flags, int userId);

}
