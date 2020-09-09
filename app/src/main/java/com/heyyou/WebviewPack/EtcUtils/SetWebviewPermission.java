package com.heyyou.WebviewPack.EtcUtils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SetWebviewPermission {

    private Activity mActivity;
    private int REQUEST_PERMISSION_CODE;

    private ArrayList<String>permissionList = new ArrayList<>();

    public SetWebviewPermission(Activity activity, int REQUEST_PERMISSION_CODE) {
        this.mActivity = activity;
        this.REQUEST_PERMISSION_CODE = REQUEST_PERMISSION_CODE;
    }

    public SetWebviewPermission addPermission(String permitList) {
        this.permissionList.add(permitList);
        return this;
    }

    public void checkPermissions() {

        String[] permitArray = new String[permissionList.size()];
        int idx = 0;

        for (String permitStr: permissionList) {
            permitArray[idx] = permitStr;
            idx++;
        }

        if (!checkPermission()) {
            ActivityCompat.requestPermissions(mActivity, permitArray,
                    REQUEST_PERMISSION_CODE);
        }

    }

    public Boolean checkPermission() {
        int i=0;
        boolean permissionResult = true;
        do {

            if (ContextCompat.checkSelfPermission(this.mActivity, permissionList.get(i) ) != PackageManager.PERMISSION_GRANTED) {
                permissionResult = false;
                break;
            }
            i++;
        }while (i < (permissionList.size()) );
        return permissionResult;
    }


    public void permissionRequest() {

        if (ContextCompat.checkSelfPermission(this.mActivity, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this.mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this.mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this.mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this.mActivity, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED
        ) {

            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)||
                    ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)||
                    ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CALL_PHONE)
            ) {

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CALL_PHONE
                        },
                        REQUEST_PERMISSION_CODE);

            } else {

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CALL_PHONE
                        },
                        REQUEST_PERMISSION_CODE);

            }

        }else {


        }


    }
}
