package com.heyyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        Log.e("onReceive", "================================================================");
        Log.e("onReceive", referrer);
        Log.e("onReceive", "================================================================");

        Funple funple = new Funple(context);
        funple.doConnect(referrer);

    }
}
