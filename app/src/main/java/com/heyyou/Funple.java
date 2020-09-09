package com.heyyou;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import androidx.ads.identifier.AdvertisingIdClient;
import androidx.ads.identifier.AdvertisingIdClient;
import androidx.ads.identifier.AdvertisingIdInfo;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.heyyou.WebviewPack.Variables.Variables;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

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
        //AdvertisingIdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mContext);
            //AdvertisingIdInfo adInfo  = AdvertisingIdClient.getAdvertisingIdInfo(mContext).get().getId();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                ListenableFuture<AdvertisingIdInfo> listenableFuture = AdvertisingIdClient.getAdvertisingIdInfo(mContext);

                try {
                    Log.e("success","=========================================================================");
                    AdvertisingIdInfo info = listenableFuture.get();

                    Log.e("success","=========================================================================");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        /*
        if (AdvertisingIdClient.isAdvertisingIdProviderAvailable(mContext)) {

        }
        ListenableFuture<AdvertisingIdInfo> advertisingIdInfoListenableFuture = AdvertisingIdClient.getAdvertisingIdInfo(mContext.getApplicationContext());


        Futures.addCallback(advertisingIdInfoListenableFuture, new FutureCallback<AdvertisingIdInfo>() {
            @Override
            public void onSuccess(@NullableDecl AdvertisingIdInfo result) {
                String id = result.getId();
                String providerPackageName =
                        result.getProviderPackageName();
                boolean isLimitTrackingEnabled = result.isLimitAdTrackingEnabled();

                Log.e("success","=========================================================================");
                Log.e("success",id);
                Log.e("success",providerPackageName);
                Log.e("success", String.valueOf(isLimitTrackingEnabled));
                Log.e("success","=========================================================================");
                // Any exceptions thrown by getAdvertisingIdInfo()
                // cause this method to get called.

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, new Executor() {
            @Override
            public void execute(Runnable runnable) {

            }
        });

         */


    }

        public void doConnect() {

        final OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .build();
        Headers headers = new Headers.Builder()
                .add("x-client-id", Variables.ZZAL_USERID)
                .add("authentication", "Basic " + Base64.decode(Variables.ZZAL_USERID + ":" + Variables.ZZAL_PASS, Base64.DEFAULT))
                .add("content-type", "application/json")
                .add("accept-version", "1.0.0")
                .build();

        //request
        final Request request = new Request.Builder()
                .headers(headers)
                .url(Variables.ZZAL_URL_TEST)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = client.newCall(request).execute();
                    String message = response.body().string();
                    Log.e("response", "================================================================");
                    Log.e("response", String.valueOf(response.code()));
                    Log.e("response", message);
                    Log.e("response", "================================================================");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



}
