package com.frame.easyandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.frame.easyandroid.util.Constant;
import com.frame.easyandroid.util.PromptManager;
import com.frame.easyandroid.util.PromptManager.dialogListener;

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
	 * @param id
	 *            具体控件的Id
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		PromptManager.showMenu(this, "确定退出程序？", new dialogListener() {

			@Override
			public void clickBut(boolean isOk) {
				if (isOk) {
					//点击确定将程序退出
					Constant.isBack = true;
					Intent in = new Intent(BaseActivity.this,
							MainActivity.class);
					in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(in);
					finish();
				} else {
					
				}
			}
		});
		// 将系统的菜单取消掉！
		return false;
	}
}
