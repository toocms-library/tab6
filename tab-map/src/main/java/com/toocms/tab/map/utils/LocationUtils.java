package com.toocms.tab.map.utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.base.BaseFragment;

/**
 * Author：Zero
 * Date：2020/6/11 16:10
 */
public class LocationUtils {

    /**
     * 判断定位服务是否开启
     *
     * @return true 表示开启
     */
    public static boolean isLocationEnabled() {
        TooCMSApplication application = TooCMSApplication.getInstance();
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(application.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(application.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 提示用户去开启定位服务
     *
     * @param fragment
     */
    public static void toOpenGPS(@NonNull final BaseFragment fragment) {

        new AlertDialog.Builder(fragment.getBaseFragmentActivity())
                .setTitle("提示")
                .setMessage("手机定位服务未开启，无法获取到您的准确位置信息，是否前往开启？")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fragment.finishFragment();
                    }
                })
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        fragment.startActivity(intent);
                    }
                })
                .show();
    }
}
