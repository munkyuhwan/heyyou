package com.heyyou.kr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");

        String [] arr = referrer.split("participateID%");
        Log.e("onReceive", "================================================================");
        Log.e("onReceive", referrer);
        Log.e("onReceive", "ID: "+arr[1]);
        Log.e("onReceive", "================================================================");

        Funple funple = new Funple(context);
        funple.doConnect(arr[1]);

    }
}
