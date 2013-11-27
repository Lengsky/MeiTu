package com.sgb.meitucamera;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
public class CameraPreview extends FragmentActivity implements SurfaceHolder.Callback{
	Camera mCamera;
	int numberOfCameras;
	int cameraCurrentlyLocked;

	SurfaceView mSurfaceView;
	SurfaceHolder mHolder;
	ImageButton btn_takephoto;
	Button btn_cancel,btn_done;
	//Size mPreviewSize;
	List<Size> mSupportedPreviewSizes;

	// The first rear facing camera
	int defaultCameraId;
	
	//perview width 
	int pwidth = 480;
	int pheight = 800;
	
	
	int fcghfghfgh = 600;

	private boolean isShutter = true;
	private int downX;
	private int downY;
	private float density;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		density = getResources().getDisplayMetrics().density;
		setContentView(R.layout.preview);
		init();
		
		
	}
	
	public void init(){
		mSurfaceView = (SurfaceView)findViewById(R.id.previewSV);
		btn_takephoto = (ImageButton)findViewById(R.id.btn_takephoto_camera);
		btn_cancel = (Button)findViewById(R.id.btn_cancel_camera);
		btn_done = (Button)findViewById(R.id.btn_done_camera);
		
		mHolder = mSurfaceView.getHolder();
		mHolder.setFormat(PixelFormat.TRANSLUCENT);
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	
		// Find the total number of cameras available
		numberOfCameras = Camera.getNumberOfCameras();

		// Find the ID of the default camera
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
				defaultCameraId = i;
			}
		}
		btn_takephoto.setOnClickListener(new takePhotoClick());
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
			// Open the default i.e. the first rear facing camera.
			mCamera = Camera.open();
			cameraCurrentlyLocked = defaultCameraId;
			//mPreview.setCamera(mCamera);
		
		
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera != null) {
			//mPreview.setCamera(null);
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate our menu which can gather user input for switching camera
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.camera_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.shutter:
			if(isShutter){
				ShutterCallback = mShutterCallback;
				isShutter = false;
			}else{
				ShutterCallback = null;
				isShutter = true;
			}
			return true;
		case R.id.switch_cam:
			// check for availability of multiple cameras
			if (numberOfCameras == 1) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(this.getString(R.string.camera_alert))
				.setNeutralButton("Close", null);
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			}

			// OK, we have multiple cameras.
			// Release this camera -> cameraCurrentlyLocked
			if (mCamera != null) {
				mCamera.stopPreview();
				//mPreview.setCamera(null);
				mCamera.release();
				mCamera = null;
			}

			// Acquire the next camera and request Preview to reconfigure
			// parameters.
			mCamera = Camera.open((cameraCurrentlyLocked + 1) % numberOfCameras);
			cameraCurrentlyLocked = (cameraCurrentlyLocked + 1)% numberOfCameras;
			switchCamera(mCamera);

			// Start the preview
			mCamera.startPreview();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int rawX = (int) event.getRawX();
		int rawY = (int) event.getRawY();
		int lenX = Math.abs(rawX - downX);
		int lenY = rawY - downY;
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			
			downX = rawX;
			downY = rawY;
		break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(lenY) > 100 * density) {
				
			}
			
		break;
		case MotionEvent.ACTION_UP:
			
			break;
		}
		return true;
	}


	public void saveJpeg(Bitmap bm){
		String savePath = "/mnt/sdcard/PhotoHaha/";
		File folder = new File(savePath);
		if(!folder.exists())
		{
			folder.mkdir();
		}
		long dataTake = System.currentTimeMillis();
		String jpegName = savePath + dataTake +".jpg";
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);

			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	ShutterCallback ShutterCallback;

	ShutterCallback mShutterCallback = new ShutterCallback(){
		public void onShutter() {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback myJpegCallback = new PictureCallback(){
		public void onPictureTaken(byte[] data, Camera camera){
			// TODO Auto-generated method stub
			Bitmap mBitmap = null;
			if(null != data){
				mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				mCamera.stopPreview();	
			}
			Matrix matrix = new Matrix();
			matrix.postRotate((float)90.0);
			Bitmap rotaBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
					mBitmap.getHeight(), matrix, false);
			if(null != rotaBitmap){
				saveJpeg(rotaBitmap);
			}
			mCamera.startPreview();
		}
	};


	public void switchCamera(Camera camera) {
		try {
			camera.setPreviewDisplay(mHolder);
		} catch (IOException exception) {

		}
		Camera.Parameters parameters = mCamera.getParameters();

		mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
		
		parameters.setPreviewSize(mSupportedPreviewSizes.get(0).width, mSupportedPreviewSizes.get(0).height);
		parameters.setPictureSize(mSupportedPreviewSizes.get(0).width, mSupportedPreviewSizes.get(0).height);
		
		Log.i("tag", mSupportedPreviewSizes.get(0).width+"^^^^^^^^^^^^"+mSupportedPreviewSizes.get(0).height);
		mCamera.setParameters(parameters);
		mCamera.setDisplayOrientation(90);
		if(parameters.getFocusMode().equals("FOCUS_MODE_AUTO"));
		mCamera.setAutoFocusMoveCallback(null);
		camera.setParameters(parameters);
	}


	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		try {
			if (mCamera != null) {
				mCamera.setPreviewDisplay(holder);
			}
		} catch (IOException exception) {
			//Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		if (mCamera != null) {
			mCamera.stopPreview();
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = mCamera.getParameters();

		mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
		
		parameters.setPreviewSize(mSupportedPreviewSizes.get(0).width, mSupportedPreviewSizes.get(0).height);
		parameters.setPictureSize(mSupportedPreviewSizes.get(0).width, mSupportedPreviewSizes.get(0).height);
		
		Log.i("tag", mSupportedPreviewSizes.get(0).width+"^^^^^^^^^^^^"+mSupportedPreviewSizes.get(0).height);
		mCamera.setParameters(parameters);
		mCamera.setDisplayOrientation(90);
		if(parameters.getFocusMode().equals("FOCUS_MODE_AUTO"));
		mCamera.setAutoFocusMoveCallback(null);
		mCamera.startPreview();

	}
	
	class takePhotoClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mCamera!=null){
				mCamera.takePicture(ShutterCallback, null, myJpegCallback);	
			}
		}
		
	}



}
