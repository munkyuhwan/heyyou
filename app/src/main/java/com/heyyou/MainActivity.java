package com.heyyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.heyyou.WebviewPack.Variables.Variables;
import com.heyyou.WebviewPack.WebPackMain;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {
    public WebPackMain getWp() {
        return wp;
    }
    private WebPackMain wp;
    private ImageButton menuBtn;
    private ConstraintLayout appBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String type = getIntent().getStringExtra("enterType");

        switch (type) {
            case "enter" :
                    Variables.WEBVIEW_URL = Variables.WEBVIEW_URL+"/login";
                break;
                case "join" :
                    Variables.WEBVIEW_URL = "https://bit.ly/3fgaINy";
                break;
            case "info" :
                    Variables.WEBVIEW_URL = Variables.WEBVIEW_URL+"/register";
                break;
        }


        appBar = (ConstraintLayout)findViewById(R.id.appBar);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        Map<String, String> headers = new HashMap<>();
        headers.put("application-platform", "MobileApplication");

        wp = new WebPackMain(this);
        wp.init(Variables.WEBVIEW_URL, (WebView)findViewById(R.id.webview), getPackageManager() )
                .setHeader(headers)
                .startLoadingWebview();

        menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wp.onMenuClick();
            }
        });

    }

    @Override
    public void onBackPressed() {
        wp.backPressClose();
        Log.d("tag","on backpress");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        wp.requestPermissionResult(requestCode,permissions,grantResults);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        wp.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void hideAppBar(boolean show) {
        if (show) {
            appBar.setVisibility(View.VISIBLE);
        }else {
            appBar.setVisibility(View.GONE);
        }
    }
}