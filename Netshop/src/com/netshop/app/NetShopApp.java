package com.netshop.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.netshop.util.NetShopUtil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap.CompressFormat;
import android.support.mdroid.cache.ImageCache;
import android.support.mdroid.cache.AbstractCache.CacheParams;

public class NetShopApp extends Application {
	private static NetShopApp instance;
	public SharedPreferences share;
	public static int MAX_HTTP_THREAD_COUNT = 5;
	public ExecutorService threadPool;
	public ImageCache mCache;
	public double[] location = new double[]{0,0};
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		share = getSharedPreferences("netshop", Context.MODE_PRIVATE);

		CacheParams cacheParams = new CacheParams("file_icon");
		cacheParams.compressFormat = CompressFormat.JPEG;
		// cacheParams.memCacheSize = (int) (Runtime.getRuntime().maxMemory() /
		// 8);
		mCache = new ImageCache(this, cacheParams);
		threadPool = Executors.newFixedThreadPool(MAX_HTTP_THREAD_COUNT,
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						AtomicInteger ints = new AtomicInteger(0);
						return new Thread(r, "httpThreadPool#"
								+ ints.getAndIncrement());
					}
				});
		NetShopUtil.getLocation(this, location);
	}
	public ImageCache getImageCache(){
		return mCache;
	}
	public static NetShopApp getInstance() {
		return instance;
	}

	public String getUserId() {
		return share.getString("user_id", "");
	}

	public String getPassword() {
		return share.getString("password", "");
	}

	public String getPhoneNum() {
		return share.getString("phone_num", "");
	}
	public double[] getLocation(){
		return location;
	}
}
