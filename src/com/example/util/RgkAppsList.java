package com.example.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class RgkAppsList {

	public static final int DEFAULT_APPLICATIONS_NUMBER = 42;

	public ArrayList<RgkItemAppsInfo> data = new ArrayList<>(
			DEFAULT_APPLICATIONS_NUMBER);

	public ArrayList<RgkItemAppsInfo> added = new ArrayList<>(
			DEFAULT_APPLICATIONS_NUMBER);
	public ArrayList<RgkItemAppsInfo> removed = new ArrayList<>();
	public ArrayList<RgkItemAppsInfo> modified = new ArrayList<>();
	public ArrayList<RgkItemAppsInfo> homeapps = new ArrayList<>();

	private RgkAppIconCache mIconCache;

	public RgkAppsList(RgkAppIconCache iconCache) {
		mIconCache = iconCache;
	}

	public void add(RgkItemAppsInfo info) {
		if (findActivity(data, info.mComponentName)) {
			return;
		}
		data.add(info);
		added.add(info);
	}

	public void addHomePackage(RgkItemAppsInfo info) {
		if (findActivity(homeapps, info.mComponentName)) {
			return;
		}
		homeapps.add(info);
	}

	public void clear() {
		data.clear();
		// TODO: do we clear these too?
		added.clear();
		removed.clear();
		modified.clear();
	}

	public int size() {
		return data.size();
	}

	public RgkItemAppsInfo get(int index) {
		return data.get(index);
	}

	public void addHomePackage(Context context) {
		List<ResolveInfo> list = findHomePackage(context);
		if (list.size() > 0) {
			for (ResolveInfo info : list) {
				addHomePackage(new RgkItemAppsInfo(context.getPackageManager(),
						info, mIconCache, null));
			}
		}
	}

	private static boolean findActivity(ArrayList<RgkItemAppsInfo> apps,
			ComponentName component) {
		final int N = apps.size();
		for (int i = 0; i < N; i++) {
			final RgkItemAppsInfo info = apps.get(i);
			if (info.mComponentName.equals(component)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查找Homeapp
	 * 
	 * @param context
	 * @return
	 */
	private static List<ResolveInfo> findHomePackage(Context context) {
		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> apps = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return apps != null ? apps : new ArrayList<ResolveInfo>();
	}

}
