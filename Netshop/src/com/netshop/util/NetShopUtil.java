package com.netshop.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;

public class NetShopUtil {
	
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getScreenWidth(Context context) {
		WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wmManager.getDefaultDisplay().getWidth();
	}

	public static int getScreenHeight(Context context) {
		WindowManager wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wmManager.getDefaultDisplay().getHeight();
	}
	/**
	 * 检测是否有网络连接
	 * 
	 * @return
	 */
	public static boolean isOnNet(Context mCon) {
		ConnectivityManager cm = (ConnectivityManager) mCon.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected() && info.isAvailable()) {

			return true;
		}
		return false;
	}
}
