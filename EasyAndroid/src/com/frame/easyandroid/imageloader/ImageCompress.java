package com.frame.easyandroid.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.frame.easyandroid.R;
import com.frame.easyandroid.util.ImageUtils;

/**
 * 图片压缩的示例Demo
 * 
 * @author liuzhao
 * 
 */
public class ImageCompress {

	/**
	 * 获取压缩之后的图片
	 * @param context
	 * @param resId res下资源图片的id
	 * @param reqWidth 期望的宽度
	 * @param reqHeight 期望的高度
	 * @return
	 */
	public static Bitmap getImageCompress(Context context,int resId, int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		try {
			bitmap = ImageUtils.decodeSampledBitmapFromResource(
					context.getResources(), resId, reqWidth, reqHeight);
		} catch (OutOfMemoryError e) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
			e.printStackTrace();
		}
		return bitmap;
	}
}
