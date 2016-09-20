package com.example.util;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.example.satelitemenu.R;

public class RgkUtilities {

	private static final String TAG = "Launcher.Utilities";

	public static class BaseColumns {

		public static final String ITEM_INDEX = "item_index";

		public static final String ITEM_TITLE = "item_title";

		public static final String ITEM_URI = "item_uri";

		public static final String ITEM_INTENT = "item_intent";

		public static final String ITEM_TYPE = "item_type";

		public static final int ITEM_TYPE_APPLICATION = 1;

		public static final int ITEM_TYPE_SWITCH = 2;

		public static final String ITEM_ACTION = "item_action";

		public static final String ITEM_ICON = "item_icon";

		public static final String ICON_TYPE = "icon_type";

		public static final String ICON_PACKAGENAME = "icon_package";

		public static final String ICON_BITMAP = "icon_bitmap";

		public static final int ICON_TYPE_BITMAP = 1;

	}

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

	public static class Favorites {

		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ RgkProvider.AUTHORITY + "/" + RgkProvider.TABLE_FAVORITES);

	}
}
