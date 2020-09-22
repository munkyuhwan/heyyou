package com.heyyou.kr;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.heyyou.kr.WebviewPack.Variables.Variables;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class Funple {

    private Context mContext = null;

    public Funple(Context mContext) {
        this.mContext = mContext;
    }

    public void determineAdvertisingInfo() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String id = com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(mContext).getId();
                    Log.e("advetising", "success==================================================");
                    Log.e("advetising", "id: "+id);
                    Log.e("advetising", "==================================================");
                    doConnect(id);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }

            }

         });




    }

        public void doConnect(String id) {

        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("advertiseID",Variables.AD_ID)
                .add("participateID",id)
                .add("actionResult","1")
                .build();
        Headers headers = new Headers.Builder()
                .add("authentication", "Basic " + Base64.decode(Variables.ZZAL_USERID + ":" + Variables.ZZAL_PASS, Base64.DEFAULT))
                .add("content-type", "application/json")
                .add("accept-version", "1.0.0")
                .build();

        //request
        final Request request = new Request.Builder()
                .headers(headers)
                .post(body)
                .url(Variables.ZZAL_URL_TEST+Variables.ZZAL_URL_COMPLETE+"?advertiseID="+Variables.AD_ID+"&participateID="+id+"&actionResult=1")
                //.url(Variables.ZZAL_URL_TEST)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = client.newCall(request).execute();
                    String message = response.body().string();

                    Log.e("response", "================================================================");
                    Log.e("response", "="+String.valueOf(response.code()));
                    Log.e("response", "="+message);
                    Log.e("response", "="+response.headers());
                    Log.e("response", "="+response.body());
                    Log.e("response", "="+response.request());
                    Log.e("response", "================================================================");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



}
