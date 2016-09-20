package com.example.util;

import java.util.HashMap;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class RgkAppIconCache {

	private static final int INITIAL_ICON_CACHE_CAPACITY = 50;

	private static class CacheEntry {
		public Bitmap icon;
		public String title;
	}

	private final RgkApplication mContext;
	private final PackageManager mPackageManager;
	private final HashMap<ComponentName, CacheEntry> mCache = new HashMap<ComponentName, CacheEntry>(
			INITIAL_ICON_CACHE_CAPACITY);
	private int mIconDpi;

	public RgkAppIconCache(RgkApplication context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		mContext = context;
		mPackageManager = context.getPackageManager();
		mIconDpi = activityManager.getLauncherLargeIconDensity();
	}

	public Drawable getFullResDefaultActivityIcon() {
		return getFullResIcon(Resources.getSystem(),
				android.R.mipmap.sym_def_app_icon);
	}

	public Drawable getFullResIcon(Resources resources, int iconId) {
		Drawable d;
		try {
			d = resources.getDrawableForDensity(iconId, mIconDpi);
		} catch (Resources.NotFoundException e) {
			d = null;
		}

		return (d != null) ? d : getFullResDefaultActivityIcon();
	}

	public void getTitleAndIcon(RgkItemAppsInfo application, ResolveInfo info,
			HashMap<Object, CharSequence> labelCache) {
		synchronized (mCache) {
			CacheEntry entry = cacheLocked(application.mComponentName, info,
					labelCache);

			application.mTitle = entry.title;
			application.mIconBitmap = entry.icon;
		}
	}

	private CacheEntry cacheLocked(ComponentName componentName,
			ResolveInfo info, HashMap<Object, CharSequence> labelCache) {
		CacheEntry entry = mCache.get(componentName);
		if (entry == null) {
			entry = new CacheEntry();

			mCache.put(componentName, entry);

			ComponentName key = RgkSateLiteModel
					.getComponentNameFromResolveInfo(info);
			if (labelCache != null && labelCache.containsKey(key)) {
				entry.title = labelCache.get(key).toString();
			} else {
				entry.title = info.loadLabel(mPackageManager).toString();
				if (labelCache != null) {
					labelCache.put(key, entry.title);
				}
			}
			if (entry.title == null) {
				entry.title = info.activityInfo.name;
			}

			entry.icon = RgkUtilities.createIconBitmap(getFullResIcon(info),
					mContext);
		}
		return entry;
	}

	public Drawable getFullResIcon(ResolveInfo info) {

		Resources resources;
		try {
			resources = mPackageManager
					.getResourcesForApplication(info.activityInfo.applicationInfo);
		} catch (PackageManager.NameNotFoundException e) {
			resources = null;
		}
		if (resources != null) {
			int iconId = info.activityInfo.getIconResource();
			if (iconId != 0) {
				return getFullResIcon(resources, iconId);
			}
		}

		return getFullResDefaultActivityIcon();
	}

}
