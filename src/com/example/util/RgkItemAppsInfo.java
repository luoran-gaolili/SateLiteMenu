package com.example.util;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

public class RgkItemAppsInfo extends ItemInfo {
	static final String TAG = "ItemApplication";
	/**
	 * app的Intent
	 */
	public Intent mIntent;
	/**
	 * bitmap
	 */
	public Bitmap mIconBitmap;
	/**
	 * app的ComonentName
	 */
	public ComponentName mComponentName;

	public boolean isFallbackIcon;

	public boolean isCustomIcon;

	int flags = 0;

	long mFirstInstallTime;

	static final int DOWNLOADED_FLAG = 1;

	static final int UPDATED_SYSTEM_APP_FLAG = 2;

	public RgkItemAppsInfo() {
		mType = RgkItemSettings.BaseColumns.ITEM_TYPE_APPLICATION;
	}

	public RgkItemAppsInfo(RgkItemAppsInfo appinfo) {
		super(appinfo);
		mIntent = appinfo.mIntent;
		mIconBitmap = appinfo.mIconBitmap;
		mComponentName = appinfo.mComponentName;
		mFirstInstallTime = appinfo.mFirstInstallTime;
	}

	public RgkItemAppsInfo(PackageManager manager, ResolveInfo info,
			RgkAppIconCache iconcache, HashMap<Object, CharSequence> lable) {
		String packageName = info.activityInfo.applicationInfo.packageName;
		mComponentName = new ComponentName(packageName, info.activityInfo.name);
		setActivity(mComponentName, Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		try {
			int appFlags = manager.getApplicationInfo(packageName, 0).flags;
			if ((appFlags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) == 0) {
				flags |= DOWNLOADED_FLAG;
				if ((appFlags & android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
					flags |= UPDATED_SYSTEM_APP_FLAG;
				}
			}
			mFirstInstallTime = manager.getPackageInfo(packageName, 0).firstInstallTime;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		iconcache.getTitleAndIcon(this, info, lable);
	}

	void setActivity(ComponentName clazzName, int flag) {
		mIntent = new Intent(Intent.ACTION_MAIN);
		mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mIntent.setComponent(clazzName);
		mIntent.setFlags(flag);
		mType = RgkItemSettings.BaseColumns.ITEM_TYPE_APPLICATION;
	}

	public int delete(Context context) {
		ContentResolver resolver = context.getContentResolver();
		return resolver.delete(RgkItemSettings.Favorites.CONTENT_URI,
				RgkItemSettings.BaseColumns.ITEM_INTENT + "=?",
				new String[] { mIntent.toUri(0) });
	}

	public int deleteAll(Context context) {
		ContentResolver resolver = context.getContentResolver();
		return resolver
				.delete(RgkItemSettings.Favorites.CONTENT_URI,
						RgkItemSettings.BaseColumns.ITEM_TYPE + "=?",
						new String[] { String
								.valueOf(RgkItemSettings.BaseColumns.ITEM_TYPE_APPLICATION) });
	}

	/**
	 * 组装ContentValues
	 * 
	 * @param context
	 * @param index
	 *            索引index，在表中表示Item的顺序
	 * @param intent
	 *            App Intent
	 * @param packageManager
	 * @return
	 */
	public ContentValues assembleContentValues(Context context, int index,
			Intent intent, PackageManager packageManager) {
		intent.setComponent(mIntent.getComponent());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		try {
			ApplicationInfo appinfo = packageManager.getApplicationInfo(mIntent
					.getComponent().getPackageName(), 0);
			Drawable drawable = appinfo.loadIcon(packageManager);
			BitmapDrawable bd = (BitmapDrawable) drawable;
			ContentValues values = new ContentValues();
			values.put(RgkItemSettings.BaseColumns.ITEM_TITLE, mTitle.toString());
			values.put(RgkItemSettings.BaseColumns.ITEM_INTENT, intent.toUri(0));
			values.put(RgkItemSettings.BaseColumns.ITEM_INDEX, index);
			values.put(RgkItemSettings.BaseColumns.ITEM_TYPE,
					RgkItemSettings.BaseColumns.ITEM_TYPE_APPLICATION);
			values.put(RgkItemSettings.BaseColumns.ICON_TYPE,
					RgkItemSettings.BaseColumns.ICON_TYPE_BITMAP);
			values.put(RgkItemSettings.BaseColumns.ICON_BITMAP,
					flattenBitmap(bd.getBitmap()));
			return values;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void bulkInsert(Context context, ContentValues values[]) {
		ContentResolver resolver = context.getContentResolver();
		resolver.bulkInsert(RgkItemSettings.Favorites.CONTENT_URI, values);
	}

}
