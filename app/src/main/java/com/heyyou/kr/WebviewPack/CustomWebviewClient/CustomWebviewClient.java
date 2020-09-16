package com.heyyou.kr.WebviewPack.CustomWebviewClient;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.heyyou.kr.WebviewPack.Variables.Variables;
import com.heyyou.kr.WebviewPack.WebPackMain;
import com.heyyou.kr.MainActivity;
import com.heyyou.kr.MainActivityInterface;

import java.net.URISyntaxException;


/**
 * Created by Moon on 2018. 10. 3..
 */
public class CustomWebviewClient extends WebViewClient {

    private Activity mActivity;
    public MainActivityInterface mainActivityInterface;
    public MainActivity mMainActivity;
    private WebPackMain mWebpackMain;
    private boolean isInit = true;

    public CustomWebviewClient(Activity activity, MainActivity mainActivity, WebPackMain webpackMain) {
        this.mActivity = activity;
        this.mainActivityInterface = mainActivity;
        this.mMainActivity = mainActivity;
        this.mMainActivity = mainActivity;
        this.mWebpackMain = webpackMain;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        String url = request.getUrl().toString();
        //String urlToDistinguish = request.getUrl().toString();
        //String protocol = url.split("://")[0];

        //Variables.WEBVIEW_URL = protocol+"://"+request.getUrl().getHost();

        //mainActivityInterface.tabSelection(urlToDistinguish.replace(Variables.WEBVIEW_URL, ""));

        this.isSignedIn(view);

        view.getOriginalUrl();
        if(view.getOriginalUrl() != null) {
            Log.e("originalURL",view.getOriginalUrl());
            if (view.getOriginalUrl().equals(Variables.REGISTER_URL) && url.equals(Variables.MAIN_URL)) {
                Log.e("url","===============================================================");
                Log.e("url","회원가입 완료");
                Log.e("url","===============================================================");

            }
        }

        if (url.equals(Variables.ORIGINAL_URL) ||
                url.equals(Variables.ORIGINAL_URL+"/main/index")||
                        url.equals(Variables.ORIGINAL_URL+"/main")
        ) {
            mainActivityInterface.hideAppBar(false);
        }else {
            mainActivityInterface.hideAppBar(true);
        }


        //if (view.getOriginalUrl().equals(""))



        if (url.startsWith("intent://")) {
            Intent intent = new Intent();
            try {

                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                if (intent != null) {

                    //앱실행

                    mMainActivity.startActivity(intent);

                }

            } catch (URISyntaxException e) {

                //URI 문법 오류 시 처리 구간



            } catch (ActivityNotFoundException e) {

                String packageName = intent.getPackage();

                if (!packageName.equals("")) {

                    // 앱이 설치되어 있지 않을 경우 구글마켓 이동

                    mMainActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));

                }

            }
        }else {


            switch (url) {
                case "http://hey-you.co.kr/register":
                    url = "https://bit.ly/3fgaINy";
                    break;

                case  "http://hey-you.co.kr/etc/profile":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/etc/point":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/etc/qna":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/etc/notice":
                    if(!Variables.IS_LOGGEDIN) {
                       // url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/etc":
                    if(!Variables.IS_LOGGEDIN) {
                       // url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/meet/near":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/meet/recommend":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/chat":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;
                case  "http://hey-you.co.kr/meet/recent":
                    if(!Variables.IS_LOGGEDIN) {
                      //  url = "http://hey-you.co.kr/login";
                    }
                    break;

            }

            view.loadUrl(url, Variables.headers);
        }
        //mainActivityInterface.startLoadingFragment();

        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);


        /*
        if (!url.equals(Variables.WEBVIEW_URL+"/maintenance.do")) {
            mainActivityInterface.startLoadingFragment();
        }
        if (url.equals(Variables.WEBVIEW_URL)) {
            mainActivityInterface.openSignupFragment();
        }
        if(url.startsWith(Variables.WEBVIEW_URL+"/memberInsert.do")) {
            mainActivityInterface.openSignupCompleteFragment();
        }

        if (isInit) {
            this.mWebpackMain.setWebviewGone();
            isInit = false;
        }

         */


    }

    @Override
    public void onPageFinished(WebView view, String url) {

        super.onPageFinished(view, url);

        //mainActivityInterface.stopLoadingFragment();
        this.mWebpackMain.setWebviewVisible();
        if (!url.equals(Variables.WEBVIEW_URL)) {
           // mainActivityInterface.closeSignupFragment();
        }

        Log.e("url","============================================================");
        Log.e("url",url);
        Log.e("url","============================================================");



    }


    private void isSignedIn(WebView view) {
        view.loadUrl("javascript: " +
                        //      " (function() {" +
                        //"App.get_js_Callback(document);"
                        "App.get_js_Callback(document.getElementsByClassName(\"nav f_r\")[0].innerHTML);"
                //      "})()"
        );

    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("tag","shoulde override loading");


        if (url.equals(Variables.WEBVIEW_URL+"/signup.do ")) {
            url = Variables.WEBVIEW_URL+"/signup.do?referer=plus489";
        }

        if (url.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                Intent existPackage = mActivity.getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                if (existPackage != null) {
                    mActivity.startActivity(intent);
                } else {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                    mActivity.startActivity(marketIntent);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            view.loadUrl(url, Variables.headers);
        }

        return true;

    }

    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {


        return super.onRenderProcessGone(view, detail);
    }
}
