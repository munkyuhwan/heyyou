package com.heyyou.WebviewPack;

import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.heyyou.WebviewPack.CustomJavascript.CustomJavascriptInterface;
import com.heyyou.WebviewPack.CustomWebviewClient.CustomWebChrome;
import com.heyyou.WebviewPack.CustomWebviewClient.CustomWebviewClient;
import com.heyyou.MainActivity;

public class CustomWebviewSettings {
    private WebView mWebview;
    private WebPackMain mWebPackMain;
    private MainActivity mainActivity;


    public CustomWebviewSettings(WebView webView, WebPackMain webPackMain, MainActivity mainActivity) {
        this.mWebview = webView;
        this.mWebPackMain = webPackMain;
        this.mainActivity = mainActivity;
    }

    public void doWebviewSetting() {

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setLoadWithOverviewMode(true);

        mWebview.addJavascriptInterface(new CustomJavascriptInterface(this.mWebPackMain ), "App");

        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setSupportMultipleWindows(true);
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebview.getSettings().setAllowContentAccess(true);
        mWebview.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);

        mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.getSettings().setEnableSmoothTransition(true);
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWebview.setWebContentsDebuggingEnabled(true);

        // mWebview.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        mWebview.getSettings().setGeolocationEnabled(true);
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setSupportMultipleWindows(true);

        mWebview.getSettings().setAppCacheEnabled(false);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebview.setWebChromeClient(new CustomWebChrome(this.mWebPackMain, this.mWebPackMain, mainActivity));
        mWebview.setWebViewClient(new CustomWebviewClient(mWebPackMain, this.mainActivity, mWebPackMain));

        String userAgent = mWebview.getSettings().getUserAgentString();
        mWebview.getSettings().setUserAgentString(userAgent+" MobileApp ");

        //mWebview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false);
        mWebview.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
        mWebview.getRendererPriorityWaivedWhenNotVisible();

        mWebview.getSettings().setDatabaseEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setAppCacheEnabled(true);

    }
}
