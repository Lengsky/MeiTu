package com.sgb.meitucamera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;


public class MeituActivity extends FragmentActivity {
	protected MeituCameraApplication mMeituCameraApplication;
	/**
	 * 屏幕的宽度和高度
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMeituCameraApplication = (MeituCameraApplication) getApplication();
		/**
		 * 获取屏幕宽度和高度
		 */
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
	}

}
