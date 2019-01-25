package com.teplot.testapp.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.apps.AppManager;
import com.teplot.testapp.common.TopBar;
import com.teplot.testapp.utils.Utils;

// 所有Activity的父类
public abstract class BaseActivity extends FragmentActivity {
	private final int MSG_CANCEL_AND_FINISH_ACTIVITY = -99;
	protected Handler mHandler;
	protected ProgressDialog mProdialog = null;
	protected TopBar mTopBar;

	public AppContext mApplication;

	public ImmersionBar mImmersionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取AppContext对象
		mApplication = (AppContext) getApplication();
		AppManager.getInstance().addActivity(this);

		mImmersionBar = ImmersionBar.with(this);

	}
	protected void setImmersionBar(View view){
		mImmersionBar
//					.fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//					.statusBarColor(R.color.white) //状态栏颜色，不写默认透明色
				.statusBarView(view)
				.statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
				.init();   //所有子类都将继承这些相同的属性
	};
	protected void setImmersionBarNon(){
		mImmersionBar
//					.fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//					.statusBarColor(R.color.white) //状态栏颜色，不写默认透明色
//				.statusBarView(view)
//				.statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
				.init();   //所有子类都将继承这些相同的属性
	};

	/** 创建handler 
	 */
	@SuppressLint("HandlerLeak")
	protected void createHandler() {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == MSG_CANCEL_AND_FINISH_ACTIVITY)
					finishSelf();
				else
					dealHandlerMsg(msg);
			}
		};
	}

	/** handler 处理方法 **/
	protected void dealHandlerMsg(Message msg) {

	}

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast居中提示(来自String) **/
	protected void showShortCenterToast(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	protected  void initBarView(){
		int height = Utils.getStatusBarHeight(this);
		DisplayMetrics dm = getResources().getDisplayMetrics();

		View home_bar_view = (View)findViewById(R.id.home_bar_view);
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) home_bar_view.getLayoutParams();
		linearParams.height = height;// 控件的高强制设成20
		linearParams.width = dm.widthPixels;// 控件的宽强制设成30
		// showShortToast("高度==》"+height+"屏幕宽度==》"+dm.widthPixels);
		home_bar_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
	}
	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongCenterToast(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongTopToast(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.show();
	}

	protected Message getPDCancelMsg() {
		Message msg = new Message();
		msg.what = MSG_CANCEL_AND_FINISH_ACTIVITY;
		return msg;
	}

	/** 显示含有标题、内容的进度对话框 **/
	public void showProdialog(String sTitle, String sMsg, final Message msg) {
		hideProdialog();
		mProdialog = new ProgressDialog(this);
		mProdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		if (sTitle != null)
			mProdialog.setTitle(sTitle);
		mProdialog.setMessage(sMsg);
		mProdialog.setIndeterminate(true); //
		if (msg != null) {
			mProdialog.setCancelable(true);
			mProdialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
							mHandler.sendMessage(msg);
						}
					});
		} else
			mProdialog.setCancelable(false);
		mProdialog.show();
	}

	/** 隐藏进度对话框 **/
	public void hideProdialog() {
		if (mProdialog != null && mProdialog.isShowing())
			mProdialog.dismiss();
	}

	/*
	 * public void finish() {
	 * 
	 * }
	 */

//	public void finish() {
//		super.finish();
//
//		if (animationResid == R.style.Theme_right_left) {
//			// 右滑退出
//			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
//		} else if (animationResid == R.style.Theme_fade) {
//			// 退出渐变
//			overridePendingTransition(R.anim.hold, R.anim.fade2);
//		} else
//		// 其他非默认主题的右滑退出 默认主题没动画
//		if (animationResid != R.style.app_theme
//				&& animationResid != R.style.Transparent
//				&& animationResid != R.style.AppHolo) {
//			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
//		}
//	}

	/**
	 * 实现页面监听过程，页面跳转
	 * */
	public class tzListener implements View.OnClickListener {

		private Class<?> mactivity = null;


		public tzListener(Class<?> activity) {
			super();
			this.mactivity = activity;

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(mactivity);
		}

	}
	
	public void startActivity(Class<?> activity) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setClass(this, activity);
		startActivity(intent);
	}
	
	public static void startActivity(Context context,Class<?> activity) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setClass(context, activity);
		context.startActivity(intent);
	}


	public void finishSelf() {
		Utils.hideSoftInputView(BaseActivity.this);
		AppManager.getInstance().finishActivity(this);
	}
	public void finishResult(){
		Intent it = new Intent();
		setResult(Activity.RESULT_OK, it);
		finishSelf();
	}
	public void showOut(String text){
		System.out.println(text);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mProdialog=null;

		if (mImmersionBar != null)
			mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
	}

	/**
	 * 沉浸式状态栏
	 */
//	public void initState() {
//
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//			//设置状态栏文字颜色及图标为深色
//			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//		}
//	}

//	private  boolean useThemestatusBarColor = false;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
//	private boolean useStatusBarColor = false;//是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
//
//	protected void initState() {
//		getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, getNavigationBarHeight());
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
//			View decorView = getWindow().getDecorView();
//			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//			decorView.setSystemUiVisibility(option);
//			//根据上面设置是否对状态栏单独设置颜色
//			if (useThemestatusBarColor) {
//				getWindow().setStatusBarColor(getResources().getColor(R.color.main_backcolor));
//			} else {
//				getWindow().setStatusBarColor(Color.TRANSPARENT);
//			}
//		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//		}
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
//			getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//		}
//	}
//
//	public int getNavigationBarHeight() {
//
//		boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
//		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
//		if (!hasMenuKey && !hasBackKey) {
//			Resources resources = getResources();
//			int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//			//获取NavigationBar的高度
//			int height = resources.getDimensionPixelSize(resourceId);
//			return height;
//		} else{
//			return 0;
//		}
//	}


}
