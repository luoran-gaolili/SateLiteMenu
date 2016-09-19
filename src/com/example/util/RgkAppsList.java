package com.example.util;

import android.content.ComponentName;
import java.util.ArrayList;

public class RgkAppsList {

	public static final int DEFAULT_APPLICATIONS_NUMBER = 42;

	public ArrayList<RgkItemAppsInfo> data = new ArrayList<>(
			DEFAULT_APPLICATIONS_NUMBER);

	private RgkAppIconCache mIconCache;

	public RgkAppsList(RgkAppIconCache iconCache) {
		mIconCache = iconCache;
	}

	public void add(RgkItemAppsInfo info) {
		if (findActivity(data, info.mComponentName)) {
			return;
		}
		data.add(info);
	}

	public int size() {
		return data.size();
	}

	public RgkItemAppsInfo get(int index) {
		return data.get(index);
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

}
