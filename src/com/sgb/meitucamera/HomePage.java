package com.sgb.meitucamera;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sgb.meitucamera.edit.EditActivity;
import com.sgb.meitucamera.edit.PhotoControl;

public class HomePage extends MeituActivity implements OnClickListener{
	ImageView camera,photo;
	private File tempFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.homepage);
		camera =(ImageView)findViewById(R.id.camera);
		photo =(ImageView)findViewById(R.id.photo);
		camera.setOnClickListener(this);
		photo.setOnClickListener(this);
		sdStatus();
		
	}
	
	// 检测sd是否可用
	public void sdStatus(){
		 String sdStatus = Environment.getExternalStorageState();  
         if (sdStatus.equals(Environment.MEDIA_MOUNTED)) {   
        	 String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
        	 File file = new File("/sdcard/myImage/");  
        	 file.mkdirs();// 创建文件夹  
         tempFile = new File("/sdcard/myImage/"+name); 
         String photoPath = "/sdcard/myImage/"+name;
         mMeituCameraApplication.setPhotoPath(photoPath);
         mMeituCameraApplication.setFile(tempFile);
        
         
         }  
	}
	public void onClick(View v) {
		if (v.getId() == R.id.camera) {
			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
			startActivityForResult(cameraintent,
					PhotoControl.PHOTO_REQUEST_TAKEPHOTO);
		} else if (v.getId() == R.id.photo) {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, PhotoControl.PHOTO_REQUEST_GALLERY);
		}
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case PhotoControl.PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempFile));
			break;
		case PhotoControl.PHOTO_REQUEST_GALLERY:
			if (data != null){
				startPhotoZoom(data.getData());
				}
			break;
		case PhotoControl.PHOTO_REQUEST_CUT:
			if (data != null) {
				Bitmap photo = data.getExtras().getParcelable("data");

				mMeituCameraApplication.setmBitmap(photo);
			//	Bundle b = new Bundle();
			//	b.putParcelable("bitmap", photo);
		//		b.putSerializable("file", file);
			//	data.putExtras(b);
				Intent intent = new Intent(this, EditActivity.class);
			//	intent.putExtras(b);
				startActivity(intent);
			}
			break;
		}
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		 intent.putExtra("aspectX", 1);
         intent.putExtra("aspectY", 1);
         intent.putExtra("outputX", 300);
         intent.putExtra("outputY", 300);
         intent.putExtra("return-data", true);
         intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PhotoControl.PHOTO_REQUEST_CUT);
	}

}

