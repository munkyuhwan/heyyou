package com.heyyou.WebviewPack.CustomWebviewClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.heyyou.WebviewPack.WebPackMain;
import com.heyyou.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moon on 2018. 5. 12..
 */

public class CustomWebChrome extends WebChromeClient {

    Activity mActivity;
    WebPackMain mWebPackMain;
    MainActivity mMainActivity;
    //private PackageManager packageManager;

    public CustomWebChrome(Activity activity, WebPackMain webPackMain, MainActivity mainActivity) {
        //super();
        this.mActivity = activity;
        this.mWebPackMain = webPackMain;
        //this.packageManager = packageManager;
        this.mMainActivity = mainActivity;
    }

    public void openFileChooser(ValueCallback<Uri[]> uploadMsg) {


        mWebPackMain.setmUploadMsg(uploadMsg);

        final File root = new File(Environment.getExternalStorageDirectory() + "/"+Environment.DIRECTORY_DCIM + "/Camera/" );
        root.mkdir();
        final String fname = "img_" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        mWebPackMain.setOutputFileUri(Uri.fromFile(sdImageMainDirectory));

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        final PackageManager packageManager = mMainActivity.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, mWebPackMain.getOutputFileUri());
            intent.setData(mWebPackMain.getOutputFileUri());
            cameraIntents.add(intent);
            Log.d("df", "camera intent get: " + cameraIntents.get(0));
        }

        //FileSystem
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        //galleryIntent.addCategory(Intent.CATEGORY_APP_GALLERY);
        galleryIntent.setType("image/*");


        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "File Browser");

        // Add the camera options.
        Log.d("intent", "choose to array: " + cameraIntents.toArray(new Parcelable[]{}));

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        mMainActivity.startActivityForResult(chooserIntent, mWebPackMain.getRcFileChoose());

    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        openFileChooser(filePathCallback);

        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);

    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
    {

        Log.e("onCreateWindow","=======================================================");
        Log.e("onCreateWindow","url: "+view.getUrl().toString());
        Log.e("onCreateWindow","=======================================================");

        mMainActivity.getWp().setCount(1);
        mMainActivity.getWp().getmWebview().removeAllViews();
        mMainActivity.getWp().setChildView( new WebView(mMainActivity) );


        WebView childView = mMainActivity.getWp().getChildView();

        childView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        childView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        childView.getSettings().setSupportMultipleWindows(true);
        childView.getSettings().setAllowFileAccess(true);
        childView.getSettings().setDomStorageEnabled(true);
        childView.getSettings().setPluginState(WebSettings.PluginState.ON);
        childView.getSettings().setAllowContentAccess(true);
        childView.getSettings().setAllowFileAccessFromFileURLs(true);
        childView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        childView.getSettings().setJavaScriptEnabled(true);
        childView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        childView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        childView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        childView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        childView.getSettings().setEnableSmoothTransition(true);
        childView.getSettings().setLoadsImagesAutomatically(true);
        childView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        childView.setWebContentsDebuggingEnabled(true);


        childView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        childView.getSettings().setSupportMultipleWindows(true);

        childView.getSettings().setAppCacheEnabled(false);
        childView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            childView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        String userAgent = childView.getSettings().getUserAgentString();
        childView.getSettings().setUserAgentString(userAgent+" MobileApp ");

        //mWebview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false);
        childView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
        childView.getRendererPriorityWaivedWhenNotVisible();

        childView.getSettings().setDatabaseEnabled(true);
        childView.getSettings().setDomStorageEnabled(true);
        childView.getSettings().setAppCacheEnabled(true);

        childView.setWebChromeClient(this);

        /*
        mMainActivity.getWp().getChildView().getSettings().setJavaScriptEnabled(true);
        mMainActivity.getWp().getChildView().setWebChromeClient(this);

        String userAgent = mMainActivity.getWp().getChildView().getSettings().getUserAgentString();
        mMainActivity.getWp().getChildView().getSettings().setUserAgentString(userAgent+" MobileApp ");

         */




        mMainActivity.getWp().getChildView().setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {

                Log.e("onRenderProcessGone","=======================================================");
                Log.e("onRenderProcessGone","detail: "+detail.didCrash());
                Log.e("onRenderProcessGone","=======================================================");

                return super.onRenderProcessGone(view, detail);
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += "\"SSL Certificate Error\" Do you want to continue anyway?.. YES";

                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);

                Log.e("onCreateWindow","onCreateWindow=======================================================");
                Log.e("onCreateWindow",url);
                Log.e("onCreateWindow","=======================================================");

                //mMainActivity.getWp().getmWebview().loadUrl(url);
                mMainActivity.getWp().setChildURL(url);

                if (mMainActivity.getWp().getCount() == 1) mMainActivity.getWp().setCount(0);


            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                mMainActivity.getWp().setCount(1);
            }

        });

        mMainActivity.getWp().getChildView().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMainActivity.getWp().getChildView().setY(mMainActivity.getWp().getmWebview().getScrollY());
        mMainActivity.getWp().getmWebview().addView(mMainActivity.getWp().getChildView());
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(mMainActivity.getWp().getChildView());
        resultMsg.sendToTarget();

        return true;
    }

    @Override
    public void onCloseWindow(WebView window)
    {
        super.onCloseWindow(window);

        Log.e("onCloseWindow","=======================================================");
        Log.e("onCloseWindow","onCloseWindow");
        Log.e("onCloseWindow","=======================================================");

        String userAgent = mMainActivity.getWp().getChildView().getSettings().getUserAgentString();
        mMainActivity.getWp().getChildView().getSettings().setUserAgentString(userAgent+" MobileApp ");

        mMainActivity.getWp().getmWebview().removeView(mMainActivity.getWp().getChildView());


    }






}
