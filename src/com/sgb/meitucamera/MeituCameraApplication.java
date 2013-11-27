package com.sgb.meitucamera;

import java.io.File;

import android.app.Application;
import android.graphics.Bitmap;

public class MeituCameraApplication extends Application {
	
	private Bitmap mBitmap;
	private String photoPath;
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	private File file;

	public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

}
