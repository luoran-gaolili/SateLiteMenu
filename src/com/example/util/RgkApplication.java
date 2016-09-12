package com.example.util;

import java.lang.ref.WeakReference;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;

import com.example.service.RgkSateLiteService;

public class RgkApplication extends Application {

	private RgkSateLiteModel mModel;

	private RgkAppIconCache mIconCache;

	private WeakReference<RgkProvider> mSwipeProvider;

	@Override
	public void onCreate() {
		super.onCreate();
		mIconCache = new RgkAppIconCache(this);
		mModel = new RgkSateLiteModel(this, mIconCache);

	}

	public RgkSateLiteModel setLauncher(RgkSateLiteService service) {
		mModel.initCallBack(service);
		return mModel;
	}

	public void setProvider(RgkProvider provider) {
		mSwipeProvider = new WeakReference<>(provider);
	}

	public RgkProvider getProvider() {
		return mSwipeProvider.get();
	}

}
