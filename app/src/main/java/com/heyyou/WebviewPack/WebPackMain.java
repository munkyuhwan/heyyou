package com.heyyou.WebviewPack;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.heyyou.WebviewPack.CustomJavascript.WebviewJavascriptInterface;
import com.heyyou.WebviewPack.EtcUtils.BackPressHandler;
import com.heyyou.WebviewPack.EtcUtils.SetWebviewPermission;
import com.heyyou.WebviewPack.Variables.Variables;
import com.heyyou.MainActivity;
import com.heyyou.R;

import java.util.Locale;
import java.util.Map;

public class WebPackMain extends Activity implements WebviewJavascriptInterface {

    MainActivity mMainActivity;
    private final Handler handler = new Handler();
    TextToSpeech t1;

    public WebView getmWebview() {
        return mWebview;
    }

    public void setmWebview(WebView mWebview) {
        this.mWebview = mWebview;
    }

    private WebView mWebview;
    private String url;
    private BackPressHandler backPressCloseHandler;

    private int REQUEST_PERMISSION_CODE = 998;

    private static final int RC_FILE_CHOOSE = 2833;
    private ValueCallback<Uri[]> mUploadMsg = null;
    private Uri outputFileUri;


    public void setChildView(WebView childView)
    {
        this.childView = childView;
    }

    private WebView childView;

    public void setChildURL(String childURL) {
        this.childURL = childURL;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String childURL = "";
    private int count = 1;

    public WebView getChildView() {
        return childView;
    }

    public String getChildURL() {
        return childURL;
    }

    public int getCount() {
        return count;
    }


    public WebPackMain(MainActivity mainActivity ) {
        //this.currentActivity = activity;
        this.mMainActivity = mainActivity;

    }

    private ConstraintLayout mConstraintLayout;
    private CustomWebviewSettings settings;

    private SetWebviewPermission setPermission;

    private PackageManager mPackageManager;

    public WebPackMain init(String url, WebView webView, PackageManager pm) {
        this.url = url;

        backPressCloseHandler = new BackPressHandler(mMainActivity); //뒤로가기 버튼 종료 클래스
        setPermission = new SetWebviewPermission(mMainActivity, REQUEST_PERMISSION_CODE);


        mWebview = webView;
        //this.mConstraintLayout = constraintLayout;
        //mWebview = new WebView(this.mMainActivity);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        //mWebview.setLayoutParams(params);
        //this.mConstraintLayout.addView(mWebview);
        settings = new CustomWebviewSettings(this.mWebview,this, mMainActivity);//웹뷰 설정 세팅
        settings.doWebviewSetting();

        this.mPackageManager = this.mMainActivity.getPackageManager();
        //getFCMToken();// FCM토큰 받아오기
        t1=new TextToSpeech(this.mMainActivity.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.KOREA);
                }
            }
        });

        return this;
    }

    public WebPackMain addPermissionList(String permissionList) {
        setPermission.addPermission(permissionList);
        return this;
    }

    public WebPackMain requestPermissionList() {
        setPermission.checkPermissions();
        return this;
    }

    public WebPackMain setHeader(Map<String, String> header) {
        Variables.headers = header;
        return this;
    }

    public void startLoadingWebview() {
        mWebview.loadUrl(url,Variables.headers);//웹뷰 로드
    }

    public void reloadURL(String url) {
        mWebview.loadUrl(url,Variables.headers);//웹뷰 로드
    }


    public void backPressClose() {
        if (mWebview.canGoBack()) {
            if (mWebview.getUrl().equals(Variables.WEBVIEW_URL) || mWebview.getUrl().equals(Variables.WEBVIEW_URL+"/index") ) {
                backPressCloseHandler.onBackPressed();
            }else {
                if (childURL.equals("") || childURL.equals(null) || childURL.isEmpty()) {
                    mMainActivity.getWp().getmWebview().removeView(mMainActivity.getWp().getChildView());
                    //mWebview.removeView(childView);
                    mWebview.goBack();
                }else if (!childURL.equals("") || !childURL.equals(null) || !childURL.isEmpty()) {
                    mMainActivity.getWp().getmWebview().removeView(mMainActivity.getWp().getChildView());
                    //mWebview.removeView(childView);
                    childURL = "";
                    mWebview.reload();
                }else {
                    mWebview.goBack();
                }
            }
        }else {
            backPressCloseHandler.onBackPressed();
        }
    }

    public void setWebviewGone() {
        getmWebview().setVisibility(View.GONE);
    }

    public void setWebviewVisible() {
        getmWebview().setVisibility(View.VISIBLE);
        //mMainActivity.getSignUpFl().bringToFront();
    }

    public void requestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if ( !setPermission.checkPermission() ) {

                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mMainActivity.getApplicationContext(),mMainActivity.getString(R.string.app_dismiss),Toast.LENGTH_SHORT).show();
                        mMainActivity.finish();
                    }
                });

            } else {

            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        /*
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
         */
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_FILE_CHOOSE && mUploadMsg != null) {
            //파일 선택후 처리
            Uri[]result = null;
            if ( data != null || resultCode == RESULT_OK ) {
                result = new Uri[1];
                if (data != null) {
                    result[0] = data.getData();
                } else {
                    result[0] = outputFileUri;
                }
                //GetPath getPath = new GetPath();
                //String imageRealPath = getPath.getPathFromUri(mMainActivity, result[0]);

                //ImageRotation imageRotation = new ImageRotation();

                //Uri[] rotatedImageUri = new Uri[1];
                //rotatedImageUri[0] = imageRotation.doRotaion(imageRealPath,mMainActivity);

                mUploadMsg.onReceiveValue(result);
                //mUploadMsg.onReceiveValue(rotatedImageUri);
                //mUploadMsg = null;
                return;
            }else {
                mUploadMsg.onReceiveValue(result);
                mUploadMsg = null;
                return;
            }
        }else {

        }

    }


    @Override
    public void fcmGo() {
    }

    @Override
    public void call_log(final String _message) {

        handler.post(new Runnable() {
            @Override
            public void run() {


                //처리하고싶은내용 넣기
                //create url
                //String term = ed1.getText().toString();
                String google = "https://www.google.com/search?q=";
                String url = google + _message;
                //Call Search term
                t1.speak(_message, TextToSpeech.QUEUE_FLUSH, null);

            }
        });
    }

    @Override
    public void jsCallback(String elements) {

        Log.e("tag","jscallback==========================================================");
        Log.e("tag","jscallback:"+elements);

        if (elements.indexOf("로그인") > 0) {
            Log.e("tag","jscallback 로그아웃 됨==========================================================");
            Variables.IS_LOGGEDIN = false;
        }else  {
            Log.e("tag","jscallback 로그인 됨==========================================================");
            Variables.IS_LOGGEDIN = true;
        }
    }

    public void onMenuClick() {
        getmWebview().loadUrl("javascript:document.getElementsByClassName(\"m_menu_btn\")[0].click()");
    }


    public void setmUploadMsg(ValueCallback<Uri[]> mUploadMsg) {
        this.mUploadMsg = mUploadMsg;
    }

    public void setOutputFileUri(Uri outputFileUri) {
        this.outputFileUri = outputFileUri;
    }

    public static int getRcFileChoose() {
        return RC_FILE_CHOOSE;
    }

    public ValueCallback<Uri[]> getmUploadMsg() {
        return mUploadMsg;
    }

    public Uri getOutputFileUri() {
        return outputFileUri;
    }

}
