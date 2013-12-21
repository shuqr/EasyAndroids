package com.frame.easyandroid.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import android.os.Environment;
import android.util.Log;

public class Logger {
	private static int LOGLEVEL = 6;
	private static int VERBISE = 1;
	private static int DEBUG = 2;
	private static int INFO = 3;
	private static int WARN = 4;
	private static int ERROR = 5;

	// 根据需要将Log存放到SD卡中
	private static String path;
	private static File file;
	private static FileOutputStream outputStream;
	private static String pattern = "yyyy-MM-dd HH:mm:ss";

	static {
		if (Utils.checkSD()) {
			File externalStorageDirectory = Environment
					.getExternalStorageDirectory();
			path = externalStorageDirectory.getAbsolutePath() + "/Log/";
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			file = new File(new File(path), "Log.txt");
			try {
				outputStream = new FileOutputStream(file, true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void v(String tag, String msg) {
		if (LOGLEVEL > VERBISE) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LOGLEVEL > DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LOGLEVEL > INFO) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LOGLEVEL > WARN) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LOGLEVEL > ERROR) {
			Log.e(tag, msg);
		}
	}

	/**
	 * 将错误信息保存到SD卡中去！可选的操作！
	 * 
	 * @param msg
	 *            传递的String类型
	 */
	public static void save2Sd(String msg) {
		Date date = new Date();
		String time = DateFormatUtils.format(date, pattern);
		save(time, msg);
	}

	/**
	 * 将错误信息保存到SD卡中去！可选的操作！
	 * 
	 * @param e
	 *            传递的是Exception类型
	 */
	public static void save2Sd(Exception e) {
		Date date = new Date();
		String time = DateFormatUtils.format(date, pattern);
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		String msg = writer.toString();
		save(time, msg);
	}

	/**
	 * 保存的核心方法
	 * @param time 保存的时间
	 * @param msg 保存的信息
	 */
	private static void save(String time, String msg) {
		if (Utils.checkSD()) {
			if (outputStream != null) {
				try {
					outputStream.write(time.getBytes());
					outputStream.write("\r\n".getBytes());
					outputStream.write(msg.getBytes());
					outputStream.write("\r\n".getBytes());
					outputStream.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				android.util.Log.i("SDCAEDTAG", "file is null");
			}
		}
	}
}
