package com.heyyou.kr;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.heyyou.kr.WebviewPack.Variables.Variables;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


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
                //.add("actionResult","1")
                .build();


/*            RequestBody body = new MultipartBody.Builder()
                    .addFormDataPart("advertiseID",Variables.AD_ID)
                    .addFormDataPart("participateID",id)
                    .addFormDataPart("actionResult","1")
                .build();

 */



        Log.e("base64", "="+java.util.Base64.getEncoder().encodeToString( (Variables.ZZAL_USERID + ":" + Variables.ZZAL_PASS).getBytes() )  );
        String base64Encoded = java.util.Base64.getEncoder().encodeToString( (Variables.ZZAL_USERID + ":" + Variables.ZZAL_PASS).getBytes() );




        Headers headers = new Headers.Builder()
                .add("x-client-id", Variables.ZZAL_USERID)
                .add("authentication", " Basic " + base64Encoded )
                .add("content-type", "application/json")
                .add("accept-version", "1.0.0")
                .build();

        //request
        final Request request = new Request.Builder()
                .headers(headers)
                .method("POST", body)
                .url(Variables.ZZAL_URL_TEST+Variables.ZZAL_URL_COMPLETE)
                //.url(Variables.ZZAL_URL_TEST)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = client.newCall(request).execute();
                    String message = response.body().string();


                    Log.e("request", "================================================================");
                    Log.e("request", "="+String.valueOf(request.body().contentType()  ));
                    Log.e("request", "="+request.headers());
                    Log.e("request", "="+request.body());
                    Log.e("request", "="+request.url());
                    Log.e("request", "================================================================");

                    Log.e("response", "================================================================");
                    Log.e("response", "="+String.valueOf(response.body().contentType()  ));
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
