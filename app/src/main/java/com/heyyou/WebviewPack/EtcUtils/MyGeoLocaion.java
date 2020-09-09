package com.heyyou.WebviewPack.EtcUtils;

import android.app.Activity;

public class MyGeoLocaion {
    private static final MyGeoLocaion ourInstance = new MyGeoLocaion();

    public static MyGeoLocaion getInstance() {
        return ourInstance;
    }

    private MyGeoLocaion() {
    }

    public MyGeoLocaion getLocation(Activity mActivity) {



        return this;
    }
}
