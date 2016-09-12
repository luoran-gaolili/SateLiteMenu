package com.example.util;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class RgkUtils {

	/**
	 * 获取状态栏高度
	 * 
	 * @param c
	 * @return
	 */
	public static int getStatusBarHeight(Context c) {
		int h = 0;
		try {
			Class<?> z = Class.forName("com.android.internal.R$dimen");
			Object o = z.newInstance();
			Field f = z.getField("status_bar_height");
			int x = (Integer) f.get(o);
			h = c.getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return h;
	}

	/**
	 * 判断程序是否安装
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isApkInstalled(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName))
			return false;
		try {
			@SuppressWarnings("unused")
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 获取应用程序版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
