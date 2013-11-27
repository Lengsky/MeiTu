package com.sgb.meitucamera.edit;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageZoom {
	
	private Bitmap imageDensity(Bitmap bitMap) { 
		//图片允许�?��空间   单位：KB 
		double maxSize =60.00; 
		Bitmap bitmap = null;
		//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）   
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos); 
		byte[] b = baos.toByteArray(); 
		//将字节换成KB 
		double mid = b.length/1024; 
		//判断bitmap占用空间是否大于允许�?��空间  如果大于则压�?小于则不压缩 
		if (mid > maxSize) { 
			//获取bitmap大小 是允许最大大小的多少�?
			double i = mid / maxSize; 
			//�?��压缩  此处用到平方�?将宽带和高度压缩掉对应的平方根�? �?.保持刻度和高度和原bitmap比率�?��，压缩后也达到了�?��大小占用空间的大小） 
			bitmap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), 
					bitMap.getHeight() / Math.sqrt(i)); 
		}
		return bitmap; 
	} 

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, 
			double newHeight) { 
		// 获取这个图片的宽和高 
		float width = bgimage.getWidth(); 
		float height = bgimage.getHeight(); 
		// 创建操作图片用的matrix对象 
		Matrix matrix = new Matrix(); 
		// 计算宽高缩放�?
		float scaleWidth = ((float) newWidth) / width; 
		float scaleHeight = ((float) newHeight) / height; 
		// 缩放图片动作 
		matrix.postScale(scaleWidth, scaleHeight); 
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, 
				(int) height, matrix, true); 
		return bitmap; 
	} 
}
