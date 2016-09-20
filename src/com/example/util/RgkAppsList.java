package com.example.util;

import android.content.ComponentName;
import java.util.ArrayList;

public class RgkAppsList {

	public ArrayList<RgkItemAppsInfo> data = new ArrayList<RgkItemAppsInfo>();

	public RgkAppsList() {
	}

	public void add(RgkItemAppsInfo info) {
		if (findActivity(data, info.mComponentName)) {
			return;
		}
		data.add(info);
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
