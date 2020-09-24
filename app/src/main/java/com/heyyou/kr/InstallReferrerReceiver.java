package com.heyyou.kr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");

        String [] arr = referrer.split("participateID");
        try {
            String participateID = URLDecoder.decode(arr[1], "UTF-8");

            participateID = participateID.replace("=","");

            Log.e("decodded", "================================================================");
            Log.e("decodded", participateID);
            Log.e("decodded", "================================================================");

            Funple funple = new Funple(context);
            funple.doConnect(participateID);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
