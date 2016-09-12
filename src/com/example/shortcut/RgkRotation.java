package com.example.shortcut;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.Settings;

import com.example.satelitemenu.R;


public class RgkRotation {

    public static int getRotationStatus(Context context) {
        int status = 0;
        try {
            status = android.provider.Settings.System.getInt(context.getContentResolver(),
                    android.provider.Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 设置
     *
     * @param resolver
     * @param status
     */
    public static void setRotationStatus(ContentResolver resolver, int status) {
        //得到uri
        Uri uri = android.provider.Settings.System.getUriFor("accelerometer_rotation");
        //沟�?设置status的�?改变屏幕旋转设置
        android.provider.Settings.System.putInt(resolver, "accelerometer_rotation", status);
        //通知改变
        resolver.notifyChange(uri, null);
    }

    /**
     * 获取图片
     *
     * @param context
     * @return
     */
    public static BitmapDrawable getDrawableState(Context context) {
        if (getRotationStatus(context) == 0) {
            return (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_auto_orientation_off);
        } else {
            return (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_auto_orientation_on);
        }
    }
}
