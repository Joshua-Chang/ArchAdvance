package com.wangyi.arch03_pms.pms;

import android.content.pm.PackageInfo;
import android.os.RemoteException;

import com.wangyi.arch03_pms.MyIPackageManager;

public class MyPackageManagerService extends MyIPackageManager.Stub {
    @Override
    public PackageInfo getPackageInfo(String packageName, int flags, int userId) throws RemoteException {
        return null;
    }
}
