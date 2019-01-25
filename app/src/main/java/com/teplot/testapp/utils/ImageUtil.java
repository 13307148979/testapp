package com.teplot.testapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.teplot.testapp.been.details.FaceData;
import com.teplot.testapp.been.details.FaceListData;
import com.teplot.testapp.been.details.FaceShapeData;
import com.teplot.testapp.been.details.WeiZhiData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImageUtil {

	/**
	 * 将bitmap图片保存在指定的文件目录下
	 * @param context
	 * @param filePath
	 * @param fileName
	 * @param bitmap
	 * @throws IOException
	 */
	public static void saveImage(Context context, String filePath,String fileName, Bitmap bitmap)
			throws IOException {
		saveImage(context, filePath,fileName, bitmap, 100);
	}
	public static void saveImage(Context context,String filePath, String fileName,
			Bitmap bitmap, int quality) throws IOException {
		if (bitmap == null || fileName == null || context == null)
			return;
		File file = FileUtils.createFile(filePath,
				fileName);

		FileOutputStream fos  = new FileOutputStream(file);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, quality, stream);
		byte[] bytes = stream.toByteArray();
		fos.write(bytes);
		fos.close();
	}
	
	/**
	 * 图片压缩
	 * @param filePath
	 * @return
	 */
	public static Bitmap zoomBitmap(String filePath) {
		//Bitmap bitmap = getBitmapByPath(filePath);
		return comp(filePath);
	}
	/**
	 * 图片压缩       不会失真    但有时候上传小图片时，文件会变大     
	 * 
	 * @param imgPath   
	 * @return
	 */
	public static Bitmap comp(String imgPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();    
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容  
		newOpts.inJustDecodeBounds = true;  
		//newOpts.inPreferredConfig = Config.RGB_565;  
		// Get bitmap info, but notice that bitmap is null now    
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath,newOpts);  

		newOpts.inJustDecodeBounds = false;    
		int width = newOpts.outWidth;  
		int height = newOpts.outHeight;  
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
		float reqHeight = 800f;//这里设置高度为800f  
		float reqWidth = 480f;//这里设置宽度为480f  
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
		int be = 1;//be=1表示不缩放  
		if (height > reqHeight || width > reqWidth) {
			//高度缩小的倍数 
			int heightRatio = (int) (height/reqHeight);
			int widthRatio =  (int)  (width/reqWidth);
//			System.out.println("heightRatio的值==》"+heightRatio+
//					"==widthRatio的值==》"+widthRatio);
			be = heightRatio > widthRatio ? heightRatio : widthRatio;
		}
		if (be <= 0)  
			be = 1; 
		newOpts.inSampleSize = be;//设置缩放比例    这个实际上的值为2的倍数   若be = 3   则实际为2。
		//System.out.println("be的值为===》"+newOpts.inSampleSize);
		// 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了  
		bitmap = BitmapFactory.decodeFile(imgPath, newOpts);  
		// 压缩好比例大小后再进行质量压缩  
		return compressImage(bitmap); // 这里再进行质量压缩
		//return bitmap;
	}
	public static Bitmap compressImage(Bitmap image) {  

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		//System.out.println("图片的大小为==缩放后的===》"+baos.toByteArray().length/1024+"kb");
		int options = 100;  
		while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos  
			options -= 10;//每次都减少10  
			image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
		}  
		//System.out.println("图片大小为=质量压缩后==>"+baos.toByteArray().length / 1024+"kb");
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
		
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
		
		ByteArrayOutputStream baos2 = new ByteArrayOutputStream(); 
		bitmap.compress(CompressFormat.JPEG, 100, baos2);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		//System.out.println("图片的大小为==生成图片后的===》"+baos2.toByteArray().length/1024+"kb");
		
		return bitmap;  
	}  
	
	/**
	* 加载本地图片
	* http://bbs.3gstdy.com
	* @param url
	* @return
	*/
	public static Bitmap getLoacalBitmap(String url) {
	     try {
	          FileInputStream fis = new FileInputStream(url);
	          return BitmapFactory.decodeStream(fis);
	     } catch (FileNotFoundException e) {
	          e.printStackTrace();
	          return null;
	     }
	}

	/**
	 * 放大缩小图片
	 * @param filePath
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(String filePath, int w, int h) {
		Bitmap bitmap = getBitmapByPath(filePath);
		return zoomBitmap(bitmap, w, h);
	}

	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		Bitmap newbmp = null;
		if(bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			if(width < height) {
				int temp = w;
				w = h;
				h = temp;
			}
			Matrix matrix = new Matrix();
			float scaleWidht = ((float) w / width);
			float scaleHeight = ((float) h / height);
			matrix.postScale(scaleWidht, scaleHeight);
			newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		}
		return newbmp;
	}
	/**
	 * 获取bitmap
	 *
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapByPath(String filePath) {
	//	System.out.println("下载地址为==》"+filePath);
		return getBitmapByPath(filePath, null);
	}

	public static Bitmap getBitmapByPath(String filePath,
										 BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}
	public static Bitmap zoomBitmap(File file, int requiredSize) {
		try {
			// decode image size
			Bitmap bitmap = null;
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, o);
			if (requiredSize <= 0) {
				return bitmap;
			}
			// 计算合理的缩放指数（2的整幂数）
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;

			while (true) {
				if (width_tmp <= requiredSize || height_tmp <= requiredSize) {
					break;
				}
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;

			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			o2.inPreferredConfig = Bitmap.Config.ARGB_8888;
			bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
			return bitmap;
		} catch (FileNotFoundException e) {

		}
		return null;
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;

		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		final RectF rectF = new RectF(rect);

		final float roundPx = pixels;

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

//	public static Bitmap toRoundCorner(Bitmap bitmap,int radius) {
//		if(bitmap == null) {
//			throw new NullPointerException("Bitmap can't be null");
//		}
//		int outWidth = bitmap.getWidth();
//		int outHeight = bitmap.getHeight();
//
//		// 初始化绘制纹理图
//		BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//		// 根据控件大小对纹理图进行拉伸缩放处理
//		//bitmapShader.setLocalMatrix(matrix);
//
//		// 初始化目标bitmap
//		Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
//
//		// 初始化目标画布
//		Canvas targetCanvas = new Canvas(targetBitmap);
//
//		// 初始化画笔
//		Paint paint = new Paint();
//		paint.setAntiAlias(true);
//		paint.setShader(bitmapShader);
//
//		// 利用画笔将纹理图绘制到画布上面
//		targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outWidth), radius, radius, paint);
//
//		return targetBitmap;
//	}

	/**
	 * 将图片转换成Base64编码的字符串
	 * @param path
	 * @return base64编码的字符串
	 */
	public static String imageToBase64(String path){
		if(TextUtils.isEmpty(path)){
			return null;
		}
		InputStream is = null;
		byte[] data = null;
		String result = null;
		try{
			is = new FileInputStream(path);
			//创建一个字符流大小的数组。
			data = new byte[is.available()];
			Log.d("ImageUtil","imageToBase64===>"+is.available());
			//写入数组
			is.read(data);
			//用默认的编码格式进行编码
			result = Base64.encodeToString(data,Base64.NO_WRAP);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(null !=is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	public static Bitmap stringtoBitmap(String string) {
       //将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	public static Bitmap drawPointFaceInfoToBitmap(Bitmap photo, FaceData data) {

		int width = photo.getWidth();
		int hight = photo.getHeight();
		int minWH = width>hight?hight:width;

		float scale = minWH/480;


		//建立一个空的Bitmap
		Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
		// 初始化画布绘制的图像到icon上
		Canvas canvas = new Canvas(icon);
		// 建立画笔
		Paint photoPaint = new Paint();
		// 获取更清晰的图像采样，防抖动
		photoPaint.setDither(true);
		// 过滤一下，抗剧齿
		photoPaint.setFilterBitmap(true);

		Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(photo, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint
		//自定义的画笔
		TextPaint textPaint=myTextPaint(scale);

		Paint paint = myLinePaint(scale);

		drawTextOne(canvas,textPaint,paint,data,scale);

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return icon;
	}
	 public static Bitmap drawPointFaceShapeToBitmap2(Bitmap photo,  List<FaceData> list) {

		int width = photo.getWidth();
		int hight = photo.getHeight();
		 int minWH = width>hight?hight:width;

		 float scale = minWH/480;


		//建立一个空的Bitmap
		Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
		// 初始化画布绘制的图像到icon上
		Canvas canvas = new Canvas(icon);
		// 建立画笔
		Paint photoPaint = new Paint();
		// 获取更清晰的图像采样，防抖动
		photoPaint.setDither(true);
		// 过滤一下，抗剧齿
		photoPaint.setFilterBitmap(true);

		Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(photo, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint
      //自定义的画笔
		TextPaint textPaint=myTextPaint(scale);

		Paint paint = myLinePaint(scale);

        drawText(canvas,textPaint,paint,list,scale);

		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return icon;
	}

	public static Bitmap drawPointFaceShapeToBitmapLine(Bitmap photo,  List<WeiZhiData> list) {

		int width = photo.getWidth();
		int hight = photo.getHeight();
		int minWH = width>hight?hight:width;

		float scale = minWH/480;


		//建立一个空的Bitmap
		Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
		// 初始化画布绘制的图像到icon上
		Canvas canvas = new Canvas(icon);
		// 建立画笔
		Paint photoPaint = new Paint();
		// 获取更清晰的图像采样，防抖动
		photoPaint.setDither(true);
		// 过滤一下，抗剧齿
		photoPaint.setFilterBitmap(true);

		Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(photo, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint

		Paint paint = myLinePaint2(scale);
		drawLine(canvas,paint,list);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return icon;
	}
	public static Bitmap drawPointFaceShapeToBitmapLineOne(Bitmap photo, WeiZhiData data) {

		int width = photo.getWidth();
		int hight = photo.getHeight();
		int minWH = width>hight?hight:width;

		float scale = minWH/480;


		//建立一个空的Bitmap
		Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
		// 初始化画布绘制的图像到icon上
		Canvas canvas = new Canvas(icon);
		// 建立画笔
		Paint photoPaint = new Paint();
		// 获取更清晰的图像采样，防抖动
		photoPaint.setDither(true);
		// 过滤一下，抗剧齿
		photoPaint.setFilterBitmap(true);

		Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(photo, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint

		Paint paint = myLinePaint2(scale);
		drawLineOne(canvas,paint,data);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return icon;
	}
	public static Bitmap drawPointFaceShapeToBitmapPoint(Bitmap photo,  List<FaceShapeData> faceShapeData) {

		int width = photo.getWidth();
		int hight = photo.getHeight();
		int minWH = width>hight?hight:width;

		float scale = minWH/480;


		//建立一个空的Bitmap
		Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
		// 初始化画布绘制的图像到icon上
		Canvas canvas = new Canvas(icon);
		// 建立画笔
		Paint photoPaint = new Paint();
		// 获取更清晰的图像采样，防抖动
		photoPaint.setDither(true);
		// 过滤一下，抗剧齿
		photoPaint.setFilterBitmap(true);

		Rect src = new Rect(0, 0, photo.getWidth(), photo.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(photo, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint

		Paint paint = myLinePaint2(scale);
		drawPoint(canvas,paint,faceShapeData);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		return icon;
	}
	//设置画笔的字体和颜色
	public static TextPaint myTextPaint(float scale) {

		TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		//int TEXT_SIZE = Math.round(25 * getRATIO());
		textPaint.setTextSize((int) (14 * scale));// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.GREEN);
		//textPaint.setColor(Color.argb(255, 94, 38, 18));// 采用的颜色
		return textPaint;
	}
	public static Paint myLinePaint(float scale) {
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth((int)(1 * scale));
		paint.setStyle(Paint.Style.STROKE);
		return paint;
	}
	public static Paint myLinePaint2(float scale) {
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);

		paint.setStrokeWidth((int)(1 * scale));
		paint.setStyle(Paint.Style.STROKE);
		return paint;
	}
	public static void drawLine(Canvas canvas,Paint paint, List<WeiZhiData> list) {

		for (int i = 0;i<list.size();i++){
			WeiZhiData data = list.get(i);
			float x1  =  StringUtil.string2float(data.getX1());
			float y1 =  StringUtil.string2float(data.getY1());

			float x2  = StringUtil.string2float(data.getX2());
			float y2 = StringUtil.string2float(data.getY2());

			//String count  = (i+1)+"";
			canvas.drawRect(x1, y1, x2, y2, paint);
		}
	}
	public static void drawLineOne(Canvas canvas,Paint paint,WeiZhiData data) {

			float x1  =  StringUtil.string2float(data.getX1());
			float y1 =  StringUtil.string2float(data.getY1());

			float x2  = StringUtil.string2float(data.getX2());
			float y2 = StringUtil.string2float(data.getY2());

			//String count  = (i+1)+"";
			canvas.drawRect(x1, y1, x2, y2, paint);
	}
	public static void drawPoint(Canvas canvas,Paint paint, List<FaceShapeData> faceShapeData) {
		List<WeiZhiData> list = StringUtil.getFaceShapList(faceShapeData);

		for (int i = 0;i<list.size();i++){
			WeiZhiData data = list.get(i);
			int x =  data.getX();
			int y =  data.getY();
			canvas.drawPoint(x,y,paint);
		}
	}

	//写入文字，自动换行的方法
	public static void drawTextOne(Canvas canvas,TextPaint textPaint,Paint paint, FaceData data,float scale) {

			Rect bounds = new Rect();

			int x  =  data.getX();
			int y = data.getY();
			int w = data.getWidth();
			int h = data.getHeight();

			int x2  =  x+w;
			int y2 = y+h;

			//String count  = (i+1)+"";
			canvas.drawRect(x, y, x2, y2, paint);
			//canvas.drawText("测试数据",(x+w/2),(y+h/2),textPaint);
			//canvas.drawLine(x,y,x2,y,paint);

			String beauty ="魅力值"+ data.getBeauty();
			String age = "年龄："+data.getAge()+"岁";
			String gender = "性别："+(data.getGender()>50?"男":"女");
			String  expression = StringUtil.getExpressionString(data.getExpression());
			String  glass = data.getGlass()==0?"无眼镜":"有眼镜";

			String content= expression+"、"+glass;

			//System.out.println("drawPointFaceShapeToBitmap=i=>"+i+"===content===>"+content);

			textPaint.getTextBounds(age, 0, age.length(), bounds);
			int xd = x+w/2-(int)(36 * scale);
			int yd = y2+(int)(20 * scale);
			canvas.drawText(age,xd, yd , textPaint);

			textPaint.getTextBounds(gender, 0, gender.length(), bounds);
			int xd1 = x+w/2-(int)(36 * scale);
			int yd1 = y2+(int)(20 * scale)+(int)(16 * scale);
			canvas.drawText(gender,xd1, yd1 , textPaint);

			textPaint.getTextBounds(beauty, 0, beauty.length(), bounds);
			int xd2 = x+w/2-(int)(36 * scale);
			int yd2 = y2+(int)(20 * scale)+(int)(48 * scale);
			canvas.drawText(beauty,xd2, yd2 , textPaint);

			textPaint.getTextBounds(content, 0, content.length(), bounds);
			int xd3 = x+w/2-(int)(36 * scale);
			int yd3 = y2+(int)(20 * scale)+(int)(32 * scale);
			canvas.drawText(content,xd3, yd3 , textPaint);
	}
	//写入文字，自动换行的方法
	public static void drawText(Canvas canvas,TextPaint textPaint,Paint paint, List<FaceData> list,float scale) {

		for (int i = 0;i<list.size();i++){
			Rect bounds = new Rect();

			FaceData data = list.get(i);
			int x  =  data.getX();
			int y = data.getY();
			int w = data.getWidth();
			int h = data.getHeight();

			int x2  =  x+w;
			int y2 = y+h;

			//String count  = (i+1)+"";
			canvas.drawRect(x, y, x2, y2, paint);
			//canvas.drawText("测试数据",(x+w/2),(y+h/2),textPaint);
			//canvas.drawLine(x,y,x2,y,paint);

			String beauty ="魅力值"+ data.getBeauty();
			String age = "年龄："+data.getAge()+"岁";
			String gender = "性别："+(data.getGender()>50?"男":"女");
			String  expression = StringUtil.getExpressionString(data.getExpression());
			String  glass = data.getGlass()==0?"无眼镜":"有眼镜";

			String content= expression+"、"+glass;

			//System.out.println("drawPointFaceShapeToBitmap=i=>"+i+"===content===>"+content);

			textPaint.getTextBounds(age, 0, age.length(), bounds);
			int xd = x+w/2-(int)(36 * scale);
			int yd = y2+(int)(20 * scale);
			canvas.drawText(age,xd, yd , textPaint);

			textPaint.getTextBounds(gender, 0, gender.length(), bounds);
			int xd1 = x+w/2-(int)(36 * scale);
			int yd1 = y2+(int)(20 * scale)+(int)(16 * scale);
			canvas.drawText(gender,xd1, yd1 , textPaint);

			textPaint.getTextBounds(beauty, 0, beauty.length(), bounds);
			int xd2 = x+w/2-(int)(36 * scale);
			int yd2 = y2+(int)(20 * scale)+(int)(48 * scale);
			canvas.drawText(beauty,xd2, yd2 , textPaint);

			textPaint.getTextBounds(content, 0, content.length(), bounds);
			int xd3 = x+w/2-(int)(36 * scale);
			int yd3 = y2+(int)(20 * scale)+(int)(32 * scale);
			canvas.drawText(content,xd3, yd3 , textPaint);
		}
	}

//	public static Bitmap drawPointFaceShapeToBitmap(Bitmap bitmap,  List<FaceData> list) {
//
//		int width = bitmap.getWidth();
//		int hight = bitmap.getHeight();
//
//		int minWH = width>hight?hight:width;
//
//		float scale = minWH/480;
//
//		System.out.println("drawPointFaceShapeToBitmap===>宽"+width+"高"+hight+"密度"+scale);
//
//		android.graphics.Bitmap.Config bitmapConfig =
//				bitmap.getConfig();
//
//		// set default bitmap config if none
//		if(bitmapConfig == null) {
//			bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
//		}
//		// resource bitmaps are imutable,
//		// so we need to convert it to mutable one
//		bitmap = bitmap.copy(bitmapConfig, true);
//
//		Canvas canvas = new Canvas(bitmap);
//		// new antialised Paint
//		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		// text color - #3D3D3D
//		paint.setColor(Color.RED);
//		paint.setTextSize((int) (14 * scale));
//		paint.setDither(true); //获取跟清晰的图像采样
//		paint.setFilterBitmap(true);//过滤一些
//
//		Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
//		// text color - #3D3D3D
//		paint2.setColor(Color.RED);
//		//paint2.setTextSize((int) (14 * scale));
//		paint2.setStrokeWidth((int)(1 * scale));
//		//paint2.setFilterBitmap(true);//过滤一些
//		paint2.setStyle(Paint.Style.STROKE);
//
//		for (int i = 0;i<list.size();i++){
//			Rect bounds = new Rect();
//
//			FaceData data = list.get(i);
//			int x  =  data.getX();
//			int y = data.getY();
//			int w = data.getWidth();
//			int h = data.getHeight();
//
//			int x2  =  x+w;
//			int y2 = y+h;
//
//			String count  = (i+1)+"";
//			canvas.drawRect(x, y, x2, y2, paint2);
//			canvas.drawText(count,(x+w/2),(y+h/2),paint);
//			//canvas.drawLine(x,y,x2,y,paint);
//
//			String beauty ="魅力值"+ data.getBeauty();
//			String age = "年龄："+data.getAge()+"岁";
//			String gender = "性别："+(data.getGender()>50?"男":"女");
//			String  expression = StringUtil.getExpressionString(data.getExpression());
//			String  glass = data.getGlass()==0?"无眼镜":"有眼镜";
//
//			//String content= expression+"、"+glass;
//
//			//System.out.println("drawPointFaceShapeToBitmap=i=>"+i+"===content===>"+content);
//
////			paint.getTextBounds(count, 0, count.length(), bounds);
////			int xd = x+w/2-(int)(36 * scale);
////			int yd = y2+(int)(20 * scale);
////			canvas.drawText(count,xd, yd , paint);
//
////			paint.getTextBounds(gender, 0, gender.length(), bounds);
////			int xd1 = x+w/2-(int)(36 * scale);
////			int yd1 = y2+(int)(20 * scale)+(int)(16 * scale);
////			canvas.drawText(gender,xd1, yd1 , paint);
////
////			paint.getTextBounds(beauty, 0, beauty.length(), bounds);
////			int xd2 = x+w/2-(int)(36 * scale);
////			int yd2 = y2+(int)(20 * scale)+(int)(48 * scale);
////			canvas.drawText(beauty,xd2, yd2 , paint);
////
////			paint.getTextBounds(content, 0, content.length(), bounds);
////			int xd3 = x+w/2-(int)(36 * scale);
////			int yd3 = y2+(int)(20 * scale)+(int)(32 * scale);
////			canvas.drawText(content,xd3, yd3 , paint);
//		}
//		return bitmap;
//	}
}
