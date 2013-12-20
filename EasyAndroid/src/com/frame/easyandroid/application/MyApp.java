package com.frame.easyandroid.application;

import android.app.Application;
import android.content.Intent;

import com.frame.easyandroid.biz.MyCrashHandler;

public class MyApp extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		/**
		 * 实现程序异常终止时候优雅的关闭以及重新启动app
		 */
		MyCrashHandler handler = MyCrashHandler.getMyCrashHandler();
		handler.init(getApplicationContext());
		Thread.currentThread().setUncaughtExceptionHandler(handler);
		
	}
	
	/**
	 * 低内存的时候发送广播：关闭正在访问的activity，清理内存！
	 * 目的避免因为OOM异常造成程序强行退出！
	 */
	@Override
	public void onLowMemory() {
		Intent intent = new Intent();
		intent.setAction("Low_Memory_Kill");
		sendBroadcast(intent);
		super.onLowMemory();
	}
}
