package com.heyyou.WebviewPack.EtcUtils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Moon on 2018. 4. 5..
 */

public class BackPressHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "뒤로가기 2번 클릭시 종료 됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
