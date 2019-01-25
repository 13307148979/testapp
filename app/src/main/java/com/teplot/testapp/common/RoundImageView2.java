package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class RoundImageView2 extends ImageView {
	private Paint mRingPaint, mPathPaint, mRingPaint1;
	private float density;

	public RoundImageView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public RoundImageView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RoundImageView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();

		if (drawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		Bitmap b = ((BitmapDrawable) drawable).getBitmap();

		if (null == b) {
			return;
		}

		Bitmap bitmap = b.copy(Config.ARGB_8888, true);

		int w = getWidth();
		float density = getResources().getDisplayMetrics().density;
		//int width=(int)(3*AppData.DENSITY);
		int width = (int)(3*density);
		
		float radius=(float) (w / 2-width);
		
		// 多边形 要先绘制
		Path p = new Path();
		p.moveTo(w / 2, w + w / 4);
		p.lineTo((float) ((w / 2) + w / 2 / Math.sqrt(2)),
				(float) (w / 2 + radius/ Math.sqrt(2))- density);
		p.lineTo(w / 2, w - 20);
		p.lineTo((float) ((w / 2) - w / 2/ Math.sqrt(2)),
				(float) (w / 2 + radius / Math.sqrt(2))-density);
		p.close();
		canvas.drawPath(p, mPathPaint);
		// 以下交集取上层
		Bitmap roundBitmap = getCroppedBitmap(bitmap, w - width*2);
		canvas.drawBitmap(roundBitmap, width, width, null);
		// 外圈圆
		canvas.drawCircle(w / 2, w / 2, w / 2 - 3*density, mRingPaint);
		canvas.drawCircle(w / 2, w + w / 20, w / 20, mRingPaint1);

	}

	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);// 显示效果好
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
				sbmp.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 6.PorterDuff.Mode.SRC_IN 取两层绘制交集。显示上层。
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}

	void init() {
		mRingPaint = new Paint();
		mRingPaint.setColor(Color.rgb(0x33, 0x88, 0xff));
		// mRingPaint.setAlpha(100);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setAntiAlias(true);
		mRingPaint.setStrokeWidth(2);

		mRingPaint1 = new Paint();
		mRingPaint1.setColor(Color.WHITE);
		// mRingPaint.setAlpha(100);
		mRingPaint1.setStyle(Paint.Style.STROKE);
		mRingPaint1.setAntiAlias(true);
		mRingPaint1.setStrokeWidth(2);

		mPathPaint = new Paint();
		mPathPaint.setColor(Color.rgb(0x33, 0x88, 0xff));
		// mRingPaint.setAlpha(100);
		mPathPaint.setAntiAlias(true);
		mPathPaint.setStyle(Paint.Style.FILL);
		mPathPaint.setStrokeWidth(2);
	}
	/**
	 * 1.PorterDuff.Mode.CLEAR  
  所绘制不会提交到画布上。
2.PorterDuff.Mode.SRC
   显示上层绘制图片
3.PorterDuff.Mode.DST
  显示下层绘制图片
4.PorterDuff.Mode.SRC_OVER
  正常绘制显示，上下层绘制叠盖。
5.PorterDuff.Mode.DST_OVER
  上下层都显示。下层居上显示。
6.PorterDuff.Mode.SRC_IN
   取两层绘制交集。显示上层。
7.PorterDuff.Mode.DST_IN
  取两层绘制交集。显示下层。
8.PorterDuff.Mode.SRC_OUT
 取上层绘制非交集部分。
9.PorterDuff.Mode.DST_OUT
 取下层绘制非交集部分。
10.PorterDuff.Mode.SRC_ATOP
 取下层非交集部分与上层交集部分
11.PorterDuff.Mode.DST_ATOP
 取上层非交集部分与下层交集部分
12.PorterDuff.Mode.XOR
  异或：去除两图层交集部分
13.PorterDuff.Mode.DARKEN
  取两图层全部区域，交集部分颜色加深
14.PorterDuff.Mode.LIGHTEN
  取两图层全部，点亮交集部分颜色
15.PorterDuff.Mode.MULTIPLY
  取两图层交集部分叠加后颜色
16.PorterDuff.Mode.SCREEN
  取两图层全部区域，交集部分变为透明色*/
}
