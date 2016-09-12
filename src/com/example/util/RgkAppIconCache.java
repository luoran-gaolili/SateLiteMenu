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

	private final Bitmap mDefaultIcon;
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

		mDefaultIcon = makeDefaultIcon();
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

	public Drawable getFullResIcon(String packageName, int iconId) {
		Resources resources;
		try {
			resources = mPackageManager.getResourcesForApplication(packageName);
		} catch (PackageManager.NameNotFoundException e) {
			resources = null;
		}
		if (resources != null) {
			if (iconId != 0) {
				return getFullResIcon(resources, iconId);
			}
		}
		return getFullResDefaultActivityIcon();
	}

	public Drawable getFullResIcon(ResolveInfo info) {
		return getFullResIcon(info.activityInfo);
	}

	public Drawable getFullResIcon(ActivityInfo info) {

		Resources resources;
		try {
			resources = mPackageManager
					.getResourcesForApplication(info.applicationInfo);
		} catch (PackageManager.NameNotFoundException e) {
			resources = null;
		}
		if (resources != null) {
			int iconId = info.getIconResource();
			if (iconId != 0) {
				return getFullResIcon(resources, iconId);
			}
		}
		return getFullResDefaultActivityIcon();
	}

	private Bitmap makeDefaultIcon() {
		Drawable d = getFullResDefaultActivityIcon();
		Bitmap b = Bitmap.createBitmap(Math.max(d.getIntrinsicWidth(), 1),
				Math.max(d.getIntrinsicHeight(), 1), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		d.setBounds(0, 0, b.getWidth(), b.getHeight());
		d.draw(c);
		c.setBitmap(null);
		return b;
	}

	public void remove(ComponentName componentName) {
		synchronized (mCache) {
			mCache.remove(componentName);
		}
	}

	public void flush() {
		synchronized (mCache) {
			mCache.clear();
		}
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

	public boolean isDefaultIcon(Bitmap icon) {
		return mDefaultIcon == icon;
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

			entry.icon = Utilities.createIconBitmap(getFullResIcon(info),
					mContext);
		}
		return entry;
	}

}
