package com.teplot.testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.teplot.testapp.base.BaseActivity;


import java.util.List;


/**
 * 启动页
 * @author Administrator
 *
 */
public class WelcomeActivity extends BaseActivity {

	public final static String KET_REC_VER = "KET_REC_VER";

//	private ViewPager mPager;
//	private PageControl pageC;//封装好的圆点指示 setviewpager就可以了
//	private Button mButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setImmersionBarNon();
		//initState();
		//直接进入normal
		normal();

//		PackageInfo info = mApplication.getPackageInfo();//刚开始获取应用的版本号1
//		int versionCode = CfgProperties.getInstance(mApplication).getInt(
//				KET_REC_VER);//没有保存版本号，默认为0  （先判断再保存）
//
//		// 判断版本  大于 表示新装的应用 就会显示那3个页面
//		if (info.versionCode > versionCode) {
//			first();
//		} else {
//			normal();
//		}
	}

//	private void first() {
//		setContentView(R.layout.welcome);
//		mPager = (ViewPager) findViewById(R.id.app_first_vPager);
//		pageC = (PageControl) findViewById(R.id.app_first_pagecontrol);
//		pageC.setViewPager(mPager);
//		pageC.setDrawMode(MyPointView.DrawMode_Circle);
//
//		ArrayList<View> mListViews = new ArrayList<View>();
//		ImageView imageView = new ImageView(this);
//		imageView.setImageResource(R.drawable.app_first_page1);
//		imageView.setScaleType(ScaleType.CENTER_CROP);
//		mListViews.add(imageView);
//
//		imageView = new ImageView(this);
//		imageView.setImageResource(R.drawable.app_first_page2);
//		imageView.setScaleType(ScaleType.CENTER_CROP);
//		mListViews.add(imageView);
//
//		View page3 = View.inflate(this, R.layout.welcome_first_page, null);
//		mButton = (Button) page3.findViewById(R.id.app_first_btn);
//		mListViews.add(page3);
//
//		mPager.setAdapter(new VPagerAdapter(mListViews));
//		//mPager.addOnPageChangeListener(new MyOnPageChangeListener());
//		mButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 保存当前版本号 1
//				CfgProperties.getInstance(mApplication).set(KET_REC_VER,
//						mApplication.getPackageInfo().versionCode + "");//1
//				skip();
//			}
//		});
//	}

	/**
	 * ViewPager适配器
	 */
	public class VPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public VPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		/**
		 * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		 */
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		/**
		 * 获取要滑动的控件的数量(ImageView数量)
		 */
		@Override
		public int getCount() {
			return mListViews.size();
		}

		/**
		 * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入
		 * 到ViewGroup中，然后作为返回值返回即可
		 */
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		/**
		 * 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}
	}

	@SuppressLint("InflateParams")
	private void normal() {
		ImageView imageView = new ImageView(this);
		imageView.setScaleType(ScaleType.FIT_XY); //不保持比例，拉伸显示全图，填满ImageView
		imageView.setImageResource(R.drawable.splash);
		// 透明度动画效果，创建一个透明度从0.3 慢慢到不透明的原图的动画效果
		AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
		animation.setDuration(2088);
		animation.setAnimationListener(new AnimationListener() {

			/**
			 * 动画开始时调用
			 */
			@Override
			public void onAnimationStart(Animation animation) {
			}

			/**
			 * 动画重复时调用
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			/**
			 * 动画结束时调用
			 */
			@Override
			public void onAnimationEnd(Animation animation) {
				//跳转到登陆界面
				skip();
			}
		});
		imageView.setAnimation(animation);
		setContentView(imageView);
	}

	private void skip() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onBackPressed() {
		
	}
	/*
	public class MyOnPageChangeListener implements OnPageChangeListener {
		// 状态有三个0空闲，1是正在滑行中，2目标加载完毕
		int i = 0;
		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (arg0 == 1)
				mButton.setVisibility(View.GONE);
			else if (i == 2)
				mButton.setVisibility(View.VISIBLE);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			i = arg0;
			if (arg0 == 2) {
				mButton.setVisibility(View.VISIBLE);
			}

		}

	}*/

}
