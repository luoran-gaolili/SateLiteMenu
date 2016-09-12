package com.example.shortcut;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Settings;

import com.example.satelitemenu.R;

/**
 * Created by mingwei on 3/27/16.
 *
 *
 * 微博�?    明伟小学�?http://weibo.com/u/2382477985)
 * Github:   https://github.com/gumingwei
 * CSDN:     http://blog.csdn.net/u013045971
 * QQ&WX�?  721881283
 *
 *
 */
public class RgkFlightMode {

    public static boolean getAirplaneMode(Context context) {
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0);
        return (isAirplaneMode == 1) ? true : false;
    }

    public static void setAirplaneModeOn(Context context, boolean enabling) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, enabling ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enabling);
        context.sendBroadcast(intent);
    }


    public static BitmapDrawable getDrawableState(Context context) {
        if (getAirplaneMode(context)) {
            return (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_airplane_mode_on);
        } else {
            return (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_airplane_mode_off);
        }
    }
}
