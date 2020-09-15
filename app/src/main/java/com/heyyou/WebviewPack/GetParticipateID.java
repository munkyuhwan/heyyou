package com.heyyou.WebviewPack;

import android.content.Context;
import android.util.Log;

//import androidx.ads.identifier.AdvertisingIdClient;
//import androidx.ads.identifier.AdvertisingIdInfo;

//import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.common.util.concurrent.FutureCallback;
//import com.google.common.util.concurrent.Futures;
//import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class GetParticipateID {

    public void getIdThread(Context mContext) {
        //ListenableFuture<AdvertisingIdInfo> adInfo = null;
        //adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mContext);


        /*
        if (AdvertisingIdClient.isAdvertisingIdProviderAvailable()) {
            ListenableFuture<AdvertisingIdInfo> advertisingIdInfoListenableFuture =
                    AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
            Futures.addCallback(advertisingIdInfoListenableFuture,
                    new FutureCallback<AdvertisingIdInfo>() {
                        @Override
                        public void onSuccess(AdvertisingIdInfo adInfo) {
                            String id = adInfo.getId();
                            String providerPackageName =
                                    adInfo.getProviderPackageName();
                            boolean isLimitTrackingEnabled =
                                    adInfo.isLimitTrackingEnabled();

                            // Any exceptions thrown by getAdvertisingIdInfo()
                            // cause this method to get called.
                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.e("MY_APP_TAG",
                                        "Failed to connect to Advertising ID provider.");
                                // Try to connect to the Advertising ID provider again,
                                // or fall back to an ads solution that doesn't require
                                // using the Advertising ID library.
                            }
                        });
                    } else {
                // The Advertising ID client library is unavailable. Use a different
                // library to perform any required ads use cases.
            }

         */


    }


}
