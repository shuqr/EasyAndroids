package com.frame.easyandroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 常用的工具类集合！
 * 
 * @author liuzhao
 * 
 */
public class Utils {
	/**
	 * 判断SD卡是否存在！
	 * 
	 * @return
	 */
	public static boolean checkSD() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 判断当前用户手机有没有网络
	 * (wifi and traffic)
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false) {
			// 去提示用户没有网络
			return false;
		}
		return true;

	}

	/**
	 * 弹出Toast的工具类
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastShow(Context context, String msg) {
		Toast.makeText(context, msg,  Toast.LENGTH_LONG).show();
	}

	/**
	 * 弹出Toast的工具类
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastShow(Context context, int id) {
		Toast.makeText(context, id,  Toast.LENGTH_LONG).show();
	}

	/**
	 * 判断手机是否使用wifi连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWIFIConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断手机是否使用流量连接 大数据下提示用户使用wifi节省流量！
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMOBILEConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;

	}

}
