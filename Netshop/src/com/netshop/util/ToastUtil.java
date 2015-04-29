package com.netshop.util;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;


public class ToastUtil {

    private static Toast toast = null;
    public static void toastOnUiThread(Activity activity, final String message) {
        toastOnUiThread(activity, message, Toast.LENGTH_SHORT);
    }
    
    public static void toastOnUiThread(Activity activity, final String message, final int showTime) {

        final Application application = activity.getApplication();
        activity.runOnUiThread(new Runnable() {
            public void run() {
            	if (null == toast) {
            		toast = Toast.makeText(application, message, showTime);
				}
            	toast.setText(message);
            	toast.show();
            }
        });
    }
    
    public static void toastOnUiThread(Activity activity, final int resId, final int showTime) {
        toastOnUiThread(activity, activity.getString(resId), showTime);
    }

    public static void toastOnUiThread(Activity activity, final int resId) {
        toastOnUiThread(activity, activity.getString(resId));
    }
}