package com.sgb.meitucamera.edit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

public class PhotoControl {

	private static PhotoControl instance;

	public static synchronized PhotoControl getInstance() {
		if (instance == null) {
			instance = new PhotoControl();
		}
		return instance;
	}

	/**
	 * 获取当前时间，并格式化为IMG'_yyyyMMdd_HHmmss
	 * @return
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	
	/**
	 * 把bitmap保存成文件，
	 * @param bitmap 要保存的图片
	 * @param bitName 保存的名�?
	 * @return
	 */
	public File saveBitmap(Bitmap bitmap, String bitName) {
		File f = new File(Environment.getExternalStorageDirectory()+File.separator+"picShare"+ bitName + ".png");
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
	public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	public static final int PHOTO_REQUEST_CUT = 3;// 结果

	/**
	 * 
	 * @param uri
	 * @param context
	 */
	public void startPhotoZoom(Uri uri,Activity context) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		context.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

}
