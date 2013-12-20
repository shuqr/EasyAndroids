package com.frame.easyandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

@SuppressWarnings("unchecked")
public abstract class BaseActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(showLayoutView());
		setUpView();
		fillData();
		setListener();
	}

	/**
	 * 获取控件的实例对象的通用方法，避免每一次的强转！
	 * 
	 * @param id 具体控件的Id
	 * @return
	 */
	protected <T extends View> T getViewById(int id) {
		return (T) findViewById(id);
	}

	/**
	 * 每一个Activity真实的布局
	 * 
	 * @return gen目录中xml的id引用
	 */
	protected abstract int showLayoutView();

	/**
	 * 初始化控件
	 */
	protected abstract void setUpView();

	/**
	 * 对象的赋值操作
	 */
	protected abstract void fillData();

	/**
	 * 控件设置监听事件
	 */
	protected abstract void setListener();

	/**
	 * 弹出Toast的工具类
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastShow(Context context, String msg) {
		Toast.makeText(context, msg, 1).show();
	}

	/**
	 * 弹出Toast的工具类
	 * 
	 * @param context
	 * @param msg
	 */
	public static void toastShow(Context context, int id) {
		Toast.makeText(context, id, 1).show();
	}
}
