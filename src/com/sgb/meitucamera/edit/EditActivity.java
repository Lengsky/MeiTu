package com.sgb.meitucamera.edit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.sgb.meitucamera.MeituActivity;
import com.sgb.meitucamera.R;
import com.sgb.meitucamera.imageFilter.AutoAdjustFilter;
import com.sgb.meitucamera.imageFilter.BannerFilter;
import com.sgb.meitucamera.imageFilter.BigBrotherFilter;
import com.sgb.meitucamera.imageFilter.BlackWhiteFilter;
import com.sgb.meitucamera.imageFilter.BlindFilter;
import com.sgb.meitucamera.imageFilter.BrickFilter;
import com.sgb.meitucamera.imageFilter.BrightContrastFilter;
import com.sgb.meitucamera.imageFilter.CleanGlassFilter;
import com.sgb.meitucamera.imageFilter.ColorQuantizeFilter;
import com.sgb.meitucamera.imageFilter.ColorToneFilter;
import com.sgb.meitucamera.imageFilter.ComicFilter;
import com.sgb.meitucamera.imageFilter.EdgeFilter;
import com.sgb.meitucamera.imageFilter.FeatherFilter;
import com.sgb.meitucamera.imageFilter.FillPatternFilter;
import com.sgb.meitucamera.imageFilter.FilmFilter;
import com.sgb.meitucamera.imageFilter.FocusFilter;
import com.sgb.meitucamera.imageFilter.GaussianBlurFilter;
import com.sgb.meitucamera.imageFilter.Gradient;
import com.sgb.meitucamera.imageFilter.HslModifyFilter;
import com.sgb.meitucamera.imageFilter.IImageFilter;
import com.sgb.meitucamera.imageFilter.IllusionFilter;
import com.sgb.meitucamera.imageFilter.Image;
import com.sgb.meitucamera.imageFilter.InvertFilter;
import com.sgb.meitucamera.imageFilter.LensFlareFilter;
import com.sgb.meitucamera.imageFilter.LightFilter;
import com.sgb.meitucamera.imageFilter.LomoFilter;
import com.sgb.meitucamera.imageFilter.MirrorFilter;
import com.sgb.meitucamera.imageFilter.MistFilter;
import com.sgb.meitucamera.imageFilter.MonitorFilter;
import com.sgb.meitucamera.imageFilter.MosaicFilter;
import com.sgb.meitucamera.imageFilter.NeonFilter;
import com.sgb.meitucamera.imageFilter.NoiseFilter;
import com.sgb.meitucamera.imageFilter.NormalFilter;
import com.sgb.meitucamera.imageFilter.OilPaintFilter;
import com.sgb.meitucamera.imageFilter.OldPhotoFilter;
import com.sgb.meitucamera.imageFilter.PaintBorderFilter;
import com.sgb.meitucamera.imageFilter.PixelateFilter;
import com.sgb.meitucamera.imageFilter.PosterizeFilter;
import com.sgb.meitucamera.imageFilter.RadialDistortionFilter;
import com.sgb.meitucamera.imageFilter.RainBowFilter;
import com.sgb.meitucamera.imageFilter.RaiseFrameFilter;
import com.sgb.meitucamera.imageFilter.RectMatrixFilter;
import com.sgb.meitucamera.imageFilter.ReflectionFilter;
import com.sgb.meitucamera.imageFilter.ReliefFilter;
import com.sgb.meitucamera.imageFilter.SaturationModifyFilter;
import com.sgb.meitucamera.imageFilter.SceneFilter;
import com.sgb.meitucamera.imageFilter.SepiaFilter;
import com.sgb.meitucamera.imageFilter.SharpFilter;
import com.sgb.meitucamera.imageFilter.SmashColorFilter;
import com.sgb.meitucamera.imageFilter.SoftGlowFilter;
import com.sgb.meitucamera.imageFilter.SupernovaFilter;
import com.sgb.meitucamera.imageFilter.ThreeDGridFilter;
import com.sgb.meitucamera.imageFilter.ThresholdFilter;
import com.sgb.meitucamera.imageFilter.TileReflectionFilter;
import com.sgb.meitucamera.imageFilter.TintFilter;
import com.sgb.meitucamera.imageFilter.VideoFilter;
import com.sgb.meitucamera.imageFilter.VignetteFilter;
import com.sgb.meitucamera.imageFilter.VintageFilter;
import com.sgb.meitucamera.imageFilter.WaterWaveFilter;
import com.sgb.meitucamera.imageFilter.XRadiationFilter;
import com.sgb.meitucamera.imageFilter.YCBCrLinearFilter;
import com.sgb.meitucamera.imageFilter.ZoomBlurFilter;
import com.sgb.meitucamera.imageFilter.Distort.BulgeFilter;
import com.sgb.meitucamera.imageFilter.Distort.RippleFilter;
import com.sgb.meitucamera.imageFilter.Distort.TwistFilter;
import com.sgb.meitucamera.imageFilter.Distort.WaveFilter;
import com.sgb.meitucamera.imageFilter.Textures.CloudsTexture;
import com.sgb.meitucamera.imageFilter.Textures.LabyrinthTexture;
import com.sgb.meitucamera.imageFilter.Textures.MarbleTexture;
import com.sgb.meitucamera.imageFilter.Textures.TextileTexture;
import com.sgb.meitucamera.imageFilter.Textures.TexturerFilter;
import com.sgb.meitucamera.imageFilter.Textures.WoodTexture;
import com.sgb.meitucamera.view.TouchImageView;


public class EditActivity extends MeituActivity implements OnClickListener,OnSeekBarChangeListener {
	static Bitmap mBitmap,miniBitmap,bitmap,tempBitmap;
	private File file;
	private TouchImageView imageView;
	private GridView gallery;
	private AQuery aQuery;
	boolean b=true,b1=true,b3=false,b4=false,b2=false;
	private ToneLayer mToneLayer;
	private boolean isFirstTimeClickButton3 = true;
	private ProgressBar mProgressBar;
	private List<FilterInfo> filterArray = new ArrayList<FilterInfo>();
	private List<com.sgb.meitucamera.imageFilter.IImageFilter> imageArray = new ArrayList<IImageFilter>();
	private final static String   SDPATH = Environment.getExternalStorageDirectory() + "/meitu"; 
	private ImageAdapter filterAdapter ;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit);
		aQuery = new AQuery(this);
		init();
	}
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(EditActivity.this);
		builder.setMessage("是否保存图片?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(tempBitmap!=null){
					saveFile(tempBitmap);
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();		 
				
			}
		});
		builder.create().show();
	}

	public void saveFile(Bitmap bitmap){
		File file = new File(SDPATH);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");  
		Date date = new Date(System.currentTimeMillis());   
		String str = formatter.format(date);
		Log.i("tag", str);
		if (!file.exists()) {
			file.mkdir();
		}
		File imageFile = new File(file,str+".jpg");
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(EditActivity.this, "美化后的图片已保存到"+SDPATH, Toast.LENGTH_SHORT).show();
	}
	
	public void initFilterIcon(){
		if(isFirstTimeClickButton3){
			for(int i=0;i<imageArray.size();i++){			
				new processImageTask(EditActivity.this, imageArray.get(i)).execute();
			}
		}			
	}
	
	private void init() {
		imageView = (TouchImageView) findViewById(R.id.edit_imageView);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_large);
	    gallery = (GridView) findViewById(R.id.gallery);

	//	mBitmap = getIntent().getExtras().getParcelable("bitmap");
	    mBitmap = mMeituCameraApplication.getmBitmap();
		miniBitmap = ImageZoom.zoomImage(mBitmap, 60, 60);
	//	file = (File) getIntent().getExtras().getSerializable("file");
		imageView.setImageBitmap(mBitmap);

		//
		 mToneLayer = new ToneLayer(this); 
		 ((LinearLayout) findViewById(R.id.tone_view)).addView(mToneLayer.getParentView());  
		 
	        ArrayList<SeekBar> seekBars = mToneLayer.getSeekBars();  
	        for (int i = 0, size = seekBars.size(); i < size; i++)  
	        {  
	            seekBars.get(i).setOnSeekBarChangeListener(EditActivity.this);  
	        }  
	   loadBianXingFilter();
		aQuery.id(R.id.button12).clicked(this);
		aQuery.id(R.id.edit_back).clicked(this);
		aQuery.id(R.id.edit_share).clicked(this);
		aQuery.id(R.id.edit_imageView).clicked(this);
		aQuery.id(R.id.button2).clicked(this);
		aQuery.id(R.id.button3).clicked(this);
		aQuery.id(R.id.button4).clicked(this);
		aQuery.id(R.id.button1).clicked(this);
		aQuery.id(R.id.button5).clicked(this);
		aQuery.id(R.id.button6).clicked(this);
		aQuery.id(R.id.button7).clicked(this);
		aQuery.id(R.id.button8).clicked(this);
		aQuery.id(R.id.button9).clicked(this);
		aQuery.id(R.id.button10).clicked(this);
		aQuery.id(R.id.button11).clicked(this);
		aQuery.id(R.id.button12).clicked(this);
		aQuery.id(R.id.imageview1).clicked(this);
		aQuery.id(R.id.imageview2).clicked(this);
		aQuery.id(R.id.imageview3).clicked(this);
		aQuery.id(R.id.imageview4).clicked(this);
		aQuery.id(R.id.imageview5).clicked(this);
		aQuery.id(R.id.imageview6).clicked(this);
		aQuery.id(R.id.imageview7).clicked(this);
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK ){
			if(b1){
				finish();
			}
			if(b2){
				aQuery.id(R.id.imageFilter).gone();
				aQuery.id(R.id.toolbar).visible();
				b2=false;
				b1=true;
			}
			if(b3){
				aQuery.id(R.id.imageFilter).gone();
				aQuery.id(R.id.toolbar).visible();
				b3=false;
				b1=true;
			}
			if(b4){
				aQuery.id(R.id.toolbar).visible();
				aQuery.id(R.id.horizontalScrollView2).gone(); 
				b4=false;
				b1=true;
			}
			
			
				
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_back:
			this.finish();
			break;
		case R.id.edit_imageView:
			
			break;
		case R.id.edit_share:
			share();
			break;
		case R.id.button1:
			if(b){
				aQuery.id(R.id.horizontalScrollView1).visible();
				b=false;
			}
			else{
				aQuery.id(R.id.horizontalScrollView1).gone();
				b=true;
			}
			break;
		case R.id.button2:
			aQuery.id(R.id.imageFilter).visible();
			aQuery.id(R.id.toolbar).gone();
			if(imageArray.size()<1){
				loadMeiHuaFilter();
			}else{
				imageArray.clear();
				filterArray.clear();
				loadMeiHuaFilter();
				
			}
			LoadImageFilter();			
			System.out.println(">>>>>>>>"+imageArray.size());
			initFilterIcon();
			b4=false;
			b3=false;
			b1=false;
			b2=true;
			break;
		case R.id.button3:
			aQuery.id(R.id.imageFilter).visible();
			aQuery.id(R.id.toolbar).gone();
			aQuery.id(R.id.horizontalScrollView1).gone();
			if(imageArray.size()<1){
				loadBianXingFilter();
			}else{
				imageArray.clear();
				filterArray.clear();
				loadBianXingFilter();
				
			}
			LoadImageFilter();	
			initFilterIcon();
			b3=true;
			b4=false;
			b2=false;
			b1=false;
			break;
		case R.id.button4:
			aQuery.id(R.id.toolbar).gone();
			aQuery.id(R.id.horizontalScrollView2).visible();
			b4=true;
			b3=false;
			b2=false;
			b1=false;
			break;
		case R.id.button5:
			bitmap=new ImageUtil(EditActivity.this).getRoundedCornerBitmap(mBitmap, 50);
			imageView.setImageBitmap(bitmap);
			break; 
		case R.id.button6:
			bitmap=new ImageUtil(EditActivity.this).createReflectionImageWithOrigin(mBitmap);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.button7:
			bitmap=new ImageUtil(EditActivity.this).oldRemeber(mBitmap);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.button8:
			
			break; 	
		case R.id.button9:
			bitmap=new ImageUtil(EditActivity.this).film(mBitmap);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.button10:
			bitmap=new ImageUtil(EditActivity.this).sunshine(mBitmap, 50, 100);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.button11:
			bitmap=new ImageUtil(EditActivity.this).emboss(mBitmap);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.button12:
			aQuery.id(R.id.horizontalScrollView1).gone();
			aQuery.id(R.id.tone_view).visible();
			aQuery.id(R.id.toolbar).gone();
			break;
			
		case R.id.imageview1:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_0);
			imageView.setImageBitmap(bitmap);
			break;
		case R.id.imageview2:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_1);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.imageview3:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_2);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.imageview4:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_3);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.imageview5:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_4);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.imageview6:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_5);
			imageView.setImageBitmap(bitmap);
			break; 	
		case R.id.imageview7:
			bitmap=new ImageUtil(EditActivity.this).addBigFrame(mBitmap, R.drawable.nine_patch_small_0);
			imageView.setImageBitmap(bitmap);
			break; 	
		}
	}

	private void share() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		shareIntent.setType("image/jpeg");
		startActivity(Intent.createChooser(shareIntent, "分享"));
	}

	private void LoadImageFilter(){
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				filterAdapter.changeStatus(position);
				filterAdapter.notifyDataSetChanged();
				//IImageFilter filter = (IImageFilter) filterAdapter.getItem(position);
				new processImageTask(EditActivity.this, imageArray.get(position)).execute();
			}
		});
	}
	
	//美化部分滤镜
	private void loadMeiHuaFilter(){
		imageArray.add(new NormalFilter());
		imageArray.add(new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_STAGGERED));
		imageArray.add(new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_TRIPED));
		imageArray.add(new VideoFilter(VideoFilter.VIDEO_TYPE.VIDEO_3X3));
		imageArray.add(new TileReflectionFilter(20, 8, 45, (byte)2));
		imageArray.add(new TexturerFilter(new LabyrinthTexture(), 0.8f, 0.8f));
		imageArray.add(new TexturerFilter(new CloudsTexture(), 0.8f, 0.8f));
		imageArray.add(new TexturerFilter(new TextileTexture(), 0.8f, 0.8f));
		imageArray.add(new TexturerFilter(new WoodTexture(), 0.8f, 0.8f));
		imageArray.add(new FillPatternFilter(EditActivity.this, R.drawable.gallery_select));
		imageArray.add(new ZoomBlurFilter(30));
		imageArray.add(new HslModifyFilter(20f));
		imageArray.add(new HslModifyFilter(40f));
		imageArray.add(new HslModifyFilter(60f));
		imageArray.add(new HslModifyFilter(80f));
		imageArray.add(new HslModifyFilter(100f));
		imageArray.add(new HslModifyFilter(150f));
		imageArray.add(new HslModifyFilter(200f));
		imageArray.add(new HslModifyFilter(250f));
		imageArray.add(new HslModifyFilter(300f));
		imageArray.add(new SoftGlowFilter(10, 0.1f, 0.1f));
		imageArray.add(new ColorToneFilter(0x00FFFF, 192));
		imageArray.add(new ColorToneFilter(0xFF0000, 192));
		imageArray.add(new ColorToneFilter(0x00FF00,192));
		imageArray.add(new ColorToneFilter(Color.rgb(33, 168, 254), 192));
		imageArray.add(new ThreeDGridFilter(16, 100));
		imageArray.add(new SharpFilter());
		imageArray.add(new SharpFilter(50));
		imageArray.add(new PosterizeFilter(2));
		imageArray.add(new LensFlareFilter());
		imageArray.add(new SupernovaFilter(0x00FFFF,20,100));
		imageArray.add(new IllusionFilter(3));
		imageArray.add(new RippleFilter(38, 15, true));
		imageArray.add(new TwistFilter(27, 106));
		imageArray.add(new BulgeFilter(-97));

		//
		imageArray.add(new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.276f, 0.163f), new YCBCrLinearFilter.Range(-0.202f, 0.5f)));//13
		imageArray.add(new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.3f, 0.3f)));
		imageArray.add(new MirrorFilter(false));
		imageArray.add(new RaiseFrameFilter(20));
		imageArray.add(new BlindFilter(false, 96, 100, 0x000000));
		imageArray.add(new BlindFilter(false, 96, 100, 0xffffff));
		imageArray.add(new TileReflectionFilter(20,8));
		imageArray.add(new MistFilter());
		imageArray.add(new MosaicFilter());
		imageArray.add(new MirrorFilter(true));//16
		imageArray.add(new OilPaintFilter());
		imageArray.add(new RadialDistortionFilter());
		imageArray.add(new ReflectionFilter(true));
		imageArray.add(new ReflectionFilter(false));
		imageArray.add(new TexturerFilter(new MarbleTexture(), 1.8f, 0.8f));//10		
		imageArray.add(new WaveFilter(25, 10));
		imageArray.add(new PaintBorderFilter(0x00FFFF));
		imageArray.add(new PaintBorderFilter(0x00FF00));
		imageArray.add(new CleanGlassFilter());
		imageArray.add(new FocusFilter());
		imageArray.add(new FilmFilter(80f));
		imageArray.add(new SceneFilter(5f, Gradient.Scene3()));
		imageArray.add(new SceneFilter(5f, Gradient.Scene2()));
		imageArray.add(new SceneFilter(5f, Gradient.Scene1()));
		imageArray.add(new SceneFilter(5f, Gradient.Scene()));
		imageArray.add(new ComicFilter());
		imageArray.add(new NoiseFilter());
		imageArray.add(new BlackWhiteFilter());
		imageArray.add(new EdgeFilter());
		imageArray.add(new PixelateFilter());
		imageArray.add(new NeonFilter());
		imageArray.add(new BigBrotherFilter());
		imageArray.add(new MonitorFilter());
		imageArray.add(new ReliefFilter());
		imageArray.add(new BrightContrastFilter());
		imageArray.add(new SaturationModifyFilter());
		imageArray.add(new ThresholdFilter());
		imageArray.add(new NoiseFilter());
		imageArray.add(new BannerFilter(10, true));
		imageArray.add(new BannerFilter(10, false));
		imageArray.add(new RectMatrixFilter());
		imageArray.add(new BrickFilter());
		imageArray.add(new GaussianBlurFilter());
		imageArray.add(new LightFilter());
		imageArray.add(new SaturationModifyFilter());//81
		imageArray.add(new SmashColorFilter());
		imageArray.add(new TintFilter());
		imageArray.add(new VignetteFilter());
		imageArray.add(new AutoAdjustFilter());
		imageArray.add(new ColorQuantizeFilter());
		imageArray.add(new WaterWaveFilter());
		imageArray.add(new VintageFilter());
		imageArray.add(new OldPhotoFilter());
		imageArray.add(new SepiaFilter());
		imageArray.add(new RainBowFilter());
		imageArray.add(new FeatherFilter());
		imageArray.add(new XRadiationFilter());
		imageArray.add(new PaintBorderFilter(0xFF0000));
		imageArray.add(new LomoFilter());
		imageArray.add(new InvertFilter());
		
	}
	
	//变形部分滤镜
	private void loadBianXingFilter(){
		imageArray.add(new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.276f, 0.163f), new YCBCrLinearFilter.Range(-0.202f, 0.5f)));//13
		imageArray.add(new YCBCrLinearFilter(new YCBCrLinearFilter.Range(-0.3f, 0.3f)));
		imageArray.add(new MirrorFilter(false));
		imageArray.add(new RaiseFrameFilter(20));
		imageArray.add(new BlindFilter(false, 96, 100, 0x000000));
		imageArray.add(new BlindFilter(false, 96, 100, 0xffffff));
		imageArray.add(new TileReflectionFilter(20,8));
		imageArray.add(new MistFilter());
		imageArray.add(new MosaicFilter());
		imageArray.add(new MirrorFilter(true));//16
		//ON9
		imageArray.add(new OilPaintFilter());
		imageArray.add(new RadialDistortionFilter());
		imageArray.add(new ReflectionFilter(true));
		imageArray.add(new ReflectionFilter(false));
		
		
		
		
		imageArray.add(new TexturerFilter(new MarbleTexture(), 1.8f, 0.8f));//10		
		imageArray.add(new WaveFilter(25, 10));
		imageArray.add(new PaintBorderFilter(0x00FFFF));
		imageArray.add(new PaintBorderFilter(0x00FF00));
		imageArray.add(new CleanGlassFilter());
		imageArray.add(new FocusFilter());
		imageArray.add(new FilmFilter(80f));
		imageArray.add(new SceneFilter(5f, Gradient.Scene3()));
		imageArray.add(new SceneFilter(5f, Gradient.Scene2()));
		imageArray.add(new SceneFilter(5f, Gradient.Scene1()));
		imageArray.add(new SceneFilter(5f, Gradient.Scene()));
		imageArray.add(new ComicFilter());
		
		//ON7
		imageArray.add(new NoiseFilter());
		imageArray.add(new BlackWhiteFilter());
		imageArray.add(new EdgeFilter());
		imageArray.add(new PixelateFilter());
		imageArray.add(new NeonFilter());
		imageArray.add(new BigBrotherFilter());
		imageArray.add(new MonitorFilter());
		imageArray.add(new ReliefFilter());
		imageArray.add(new BrightContrastFilter());
		imageArray.add(new SaturationModifyFilter());
		
		//ON8
		imageArray.add(new ThresholdFilter());
		imageArray.add(new NoiseFilter());
		imageArray.add(new BannerFilter(10, true));
		imageArray.add(new BannerFilter(10, false));
		imageArray.add(new RectMatrixFilter());
		imageArray.add(new BrickFilter());
		imageArray.add(new GaussianBlurFilter());
		imageArray.add(new LightFilter());
		
		imageArray.add(new SaturationModifyFilter());//81
		imageArray.add(new SmashColorFilter());
		imageArray.add(new TintFilter());
		imageArray.add(new VignetteFilter());
		imageArray.add(new AutoAdjustFilter());
		imageArray.add(new ColorQuantizeFilter());
		
		//ON 10
		imageArray.add(new WaterWaveFilter());
		imageArray.add(new VintageFilter());
		imageArray.add(new OldPhotoFilter());
		imageArray.add(new SepiaFilter());
		imageArray.add(new RainBowFilter());
		imageArray.add(new FeatherFilter());
		imageArray.add(new XRadiationFilter());
		imageArray.add(new PaintBorderFilter(0xFF0000));
		imageArray.add(new LomoFilter());
		imageArray.add(new InvertFilter());
	}

	public class processImageTask extends AsyncTask<Void, Void, Bitmap> {
		private IImageFilter filter;
		private Activity activity = null;
		public processImageTask(Activity activity, IImageFilter imageFilter) {
			this.filter = imageFilter;
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		public Bitmap doInBackground(Void... params) {
			Image img = null;
			try
			{
				//Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.image);
				if(filterArray.size()<imageArray.size()){
					img = new Image(miniBitmap);
				}else{
					img = new Image(mBitmap);
				}
					
				
				if (filter != null) {
					img = filter.process(img);
					img.copyPixelsFromBuffer();
				}
				return img.getImage();
			}
			catch(Exception e){
				if (img != null && img.destImage.isRecycled()) {
					img.destImage.recycle();
					img.destImage = null;
					System.gc(); 
				}
			}
			finally{
				if (img != null && img.image.isRecycled()) {
					img.image.recycle();
					img.image = null;
					System.gc(); 
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if(result != null){
				super.onPostExecute(result);
				tempBitmap = result;				
				if(filterArray.size()<imageArray.size()){
					filterArray.add(new FilterInfo(result,false));
					filterAdapter = new ImageAdapter(EditActivity.this,filterArray);
					gallery.setAdapter(filterAdapter);
					gallery.setNumColumns(filterArray.size());
				}else{
					imageView.setImageBitmap(result);
				}
				filterAdapter.notifyDataSetChanged();
				
			}
			mProgressBar.setVisibility(View.GONE);
		}
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		 int flag = (Integer) seekBar.getTag();  
	        switch (flag)  
	        {  
	        case ToneLayer.FLAG_SATURATION:  
	            mToneLayer.setSaturation(progress);  
	            break;  
	        case ToneLayer.FLAG_LUM:  
	            mToneLayer.setLum(progress);  
	            break;  
	        case ToneLayer.FLAG_HUE:  
	            mToneLayer.setHue(progress);  
	            break;  
	        }  
	          
	        imageView.setImageBitmap(mToneLayer.handleImage(mBitmap, flag)); 
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
