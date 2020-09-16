package com.heyyou.kr.WebviewPack.CustomJavascript;

import android.webkit.JavascriptInterface;


/**
 * Created by Moon on 2018. 4. 6..
 */

public class CustomJavascriptInterface {

    private WebviewJavascriptInterface mInterface;

    public CustomJavascriptInterface(WebviewJavascriptInterface mInterface) {
        this.mInterface = mInterface;
    }

    @JavascriptInterface
    public void get_fcm_token() {
        mInterface.fcmGo();
    }

    @JavascriptInterface
    public void call_log(String _message){mInterface.call_log(_message);}

    @JavascriptInterface
    public void get_js_Callback(String elements){
        mInterface.jsCallback(elements);
    }

}
