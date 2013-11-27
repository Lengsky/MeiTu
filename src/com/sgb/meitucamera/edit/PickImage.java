package com.sgb.meitucamera.edit;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.androidquery.AQuery;
import com.sgb.meitucamera.R;
public class PickImage extends Activity implements OnClickListener {

	private AQuery aQuery;
	private File tempFile;
	private PhotoControl mControl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_edit);
		Button btn1 = (Button)findViewById(R.id.edit_selete_camera);
		Button btn2 = (Button)findViewById(R.id.edit_selete_alumbs);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	//	aQuery = new AQuery(this);
		mControl = PhotoControl.getInstance();
//		aQuery.id(R.id.edit_selete_camera).clicked(this);
//		aQuery.id(R.id.edit_selete_alumbs).clicked(this);
	}



	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.edit_selete_camera) {
			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
			startActivityForResult(cameraintent,
					PhotoControl.PHOTO_REQUEST_TAKEPHOTO);
		} else if (v.getId() == R.id.edit_selete_alumbs) {
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
//				File file = mControl.saveBitmap(photo,
//						mControl.getPhotoFileName());
				Bundle b = new Bundle();
				b.putParcelable("bitmap", photo);
		//		b.putSerializable("file", file);
				data.putExtras(b);
				Intent intent = new Intent(this, EditActivity.class);
				intent.putExtras(b);
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

