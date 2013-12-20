package com.frame.easyandroid;

import com.frame.easyandroid.util.Constant;
import com.frame.easyandroid.util.Logger;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	private Button button;

	@Override
	protected int showLayoutView() {

		return R.layout.main;
	}

	@Override
	protected void setUpView() {
		if(Constant.isBack){
			Constant.isBack = false;
			this.finish();
		}
		button = getViewById(R.id.main);
	}

	@Override
	protected void fillData() {
		
	}

	@Override
	protected void setListener() {
		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

	}

}
