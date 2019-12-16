package com.wangyi.arch03_pms.pms;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public abstract class MyPackageManager {
    public abstract PackageInfo getPackageInfo(String packageName,
                                               int flags);
}
