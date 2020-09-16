package com.heyyou.kr.WebviewPack.EtcUtils;

import android.content.pm.PackageManager;

public class IsAppInstalled {
    private static final IsAppInstalled ourInstance = new IsAppInstalled();

    public static IsAppInstalled getInstance() {
        return ourInstance;
    }

    private IsAppInstalled() {
    }

    public boolean isInstalled(String packageName, PackageManager packageManager) {

        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }


    }
}
