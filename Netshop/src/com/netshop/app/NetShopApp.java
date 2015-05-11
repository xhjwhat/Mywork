package com.netshop.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class NetShopApp extends Application {
	private static NetShopApp instance;
	public SharedPreferences share;
	public static int MAX_HTTP_THREAD_COUNT = 5;
	public ExecutorService threadPool;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		share = getSharedPreferences("netshop", Context.MODE_PRIVATE);

		threadPool = Executors.newFixedThreadPool(MAX_HTTP_THREAD_COUNT,
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						AtomicInteger ints = new AtomicInteger(0);
						return new Thread(r, "httpThreadPool#"
								+ ints.getAndIncrement());
					}
				});
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

}