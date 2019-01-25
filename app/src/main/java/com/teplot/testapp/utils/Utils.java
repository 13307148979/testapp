package com.teplot.testapp.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.common.CenterAlignImageSpan;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 杂项工具类
 * 
 * @author wader
 * 
 */
public class Utils {
	// 进行计算屏幕宽度，动态显示
	public static void setImageLoaderUtilInit(){
		//                    ImageLoader imageLoader = ImageLoader.getInstance();
//                    imageLoader.init(ImageLoaderConfiguration.createDefault(ShiChangDetailActivity.this));
		ImageLoaderUtil.init(AppContext.getInstance(), R.drawable.backgroud_nodata, AppData.SD_PATH + AppData.PICTURE_PATH);
	}

	public static void showUrlDetail(Context context,String url){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		context.startActivity(intent);
	}
	public static  void initStatusBarHeight(Activity mActivity,View home_bar_view){

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //有透明状态栏
			home_bar_view.setVisibility(View.VISIBLE);
			int height = getStatusBarHeight(mActivity);
			DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();
			//View home_bar_view = (View)findViewById(R.id.home_bar_view);
			LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) home_bar_view.getLayoutParams();
			linearParams.height = height;// 控件的高强制设成20
			linearParams.width = dm.widthPixels;// 控件的宽强制设成30
			// showShortToast("高度==》"+height+"屏幕宽度==》"+dm.widthPixels);
			home_bar_view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
		}else {
			home_bar_view.setVisibility(View.GONE);
		}
	}
	//
	public static int getStatusBarHeight(Activity activity) {
		int statusBarHeight = 0;
		if (activity != null) {
			int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
			statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return statusBarHeight;
	}
	/**
	 * 显示EditText输入的字数
	 * @param mtTextView   显示数字
	 * @param mEditText
	 * @param count	   字数限制
	 */
	public static void showTextViewNUM(final TextView mtTextView, final EditText mEditText, final int count){
		mEditText.addTextChangedListener(new TextWatcher() {

			private CharSequence temp;
			private int editStart ;
			private int editEnd ;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				editStart = mEditText.getSelectionStart();
				editEnd = mEditText.getSelectionEnd();
				mtTextView.setText(temp.length() + "/"+count);

				if (temp.length() > count) {
					//Toast.makeText(AppContext.getInstance(), "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
					s.delete(editStart-1, editEnd);
					int tempSelection = count;//editStart;
					mEditText.setText(s);
					mEditText.setSelection(tempSelection);
				}
			}
		});

	}

	/**
	 * 解决scallview中Edittext不能滑动的问题
	 * @param editText
	 */
	public static void showEditViewInScallview(EditText editText){
		editText.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_UP:
						v.getParent().requestDisallowInterceptTouchEvent(false);
						break;
				}

				return false;
			}
		});
	}
	//注意  listview与ScrollView 嵌套 使用时只显示一行，需计算高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	//view 转bitmap
	public static Bitmap convertViewToBitmap(View view) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}
//	/**
//	 * 加载头像
//	 * @param url
//	 * @param v
//	 * @return
//	 */
//	public static void loadUserImage (String url,ImageView v) {
//		if (StringUtil.isEmpty(url)){
//			//这里可以设置默认图片
//			v.setImageResource(R.drawable.backgroud_nodata);
//			return;
//		}
//
//		if (url.startsWith("/")) {
//			url = url.substring(1);
//		}
//		if (v != null)
//			v.setTag(null);
//		String imgUrL = AppData.URL_UP_GETIMG +url;
//		System.out.println("图片下载的imgUrL=====>"+imgUrL);
//		//ImageLoader.getInstances().loadImage(imgUrL, v,400);//AppData.WEB_URL +
//	}
//	/**
//	 * 加载圆角图片
//	 * @param url
//	 * @param v
//	 * @return
//	 */
//	public static void loadImageCorner (String url,ImageView v,int pixels) {
//		if (StringUtil.isEmpty(url)){
//			//这里可以设置默认图片
//			v.setImageResource(R.drawable.backgroud_nodata);
//			return;
//		}
//		if (url.startsWith("/")) {
//			url = url.substring(1);
//		}
//		if (v != null)
//			v.setTag(null);
//		ImageLoader.getInstances().loadImageCorner(url, v,pixels);//AppData.WEB_URL +
//	}
	/**
	 * 加载大图片
	 * @param url
	 * @param v
	 * @return
	 */
	public static void loadImageBig (String url,ImageView v,int requiredSize) {
		if (StringUtil.isEmpty(url)){
			//这里可以设置默认图片
			v.setImageResource(R.drawable.backgroud_nodata);
			return;
		}
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		//System.out.println("url==>"+url);
		if (v != null)
			v.setTag(null);

		//ImageLoader.getInstance().displayImage(url,v);
		ImageLoaderUtil.getInstances().loadImage(url, v,requiredSize);//AppData.WEB_URL +
	}

	/**
	 * 加载大图片
	 * @param url
	 * @param v
	 * @return
	 */
	public static void loadImageBig2(String url,ImageView v,int requiredSize) {
		if (StringUtil.isEmpty(url)){
			//这里可以设置默认图片
			v.setImageResource(R.drawable.backgroud_nodata);
			return;
		}
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		//System.out.println("url==>"+url);
		if (v != null)
			v.setTag(null);

		//ImageLoader.getInstance().displayImage(url,v);
		ImageLoaderUtil.getInstances().loadImage(url, v,requiredSize);//AppData.WEB_URL +
	}
//	/**
//	 * 加载圆角图片图片
//	 * @param url
//	 * @param v
//	 * @return
//	 */
//	public static void loadSheBeiImage (String url,ImageView v) {//,int pixels
//		if (StringUtil.isEmpty(url)){
//			//这里可以设置默认图片
//			v.setImageResource(R.drawable.backgroud_nodata);
//			return;
//		}
//		if (url.startsWith("/")) {
//			url = url.substring(1);
//		}
//		if (v != null)
//			v.setTag(null);
//		ImageLoader.getInstances().loadImage(url, v,400);//AppData.WEB_URL +
//	}
//	public static void showNetWorkCfgDlg(final Context c) {
//		ConfireDlg cfgDlg = new ConfireDlg.Builder(c).setTitle("网络不可用")
//				.setContent("是否现在设置网络？")
//				.setYesButton("设置网络", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						c.startActivity(new Intent(
//								Settings.ACTION_WIRELESS_SETTINGS));
//					}
//				}).setNoButton("取消", null).create();
//		cfgDlg.show();
//	}

	/**
	 *
	 * @param listView
	 * @param col  有多少列
	 */
	public static void setListViewHeightBasedOnChildren(GridView listView,int col) {
		// 获取listview的adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		// 固定列宽，有多少列
		//int col = 4;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		int count = listAdapter.getCount();
		for (int i = 0; i < count; i += col) {
			// 获取listview的每一个item

			int height;
			if (count-i<col){
				 height = getItemHeight(listView,listAdapter,(count-i),i);
			}else {
				 height = getItemHeight(listView,listAdapter,col,i);
			}
			// 获取item的高度和
			totalHeight += height;
//			View listItem = listAdapter.getView(i, null, listView);
//			listItem.measure(0, 0);
			//totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		/*
		 * // 设置margin ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		 */
		// 设置参数
		listView.setLayoutParams(params);
	}

	private static int getItemHeight (GridView listView,ListAdapter listAdapter,int count,int index){

		View listItem_0 = listAdapter.getView(0, null, listView);
		listItem_0.measure(0, 0);
		int hight = listItem_0.getMeasuredHeight();
		for (int i = index;i<(count+index);i++){

			View listItem_i = listAdapter.getView(i, null, listView);
			listItem_i.measure(0, 0);
			int height_i = listItem_i.getMeasuredHeight();
			//System.out.println("hight="+i+"==>"+hight+"==height_i===>"+height_i);
			if (height_i > hight){
				hight = height_i;
			}
		}
		return  hight;
	}
	/**
	 * 属性动画，api11 需导入工程
	 */
	@SuppressLint("NewApi")
	public static void propertyValuesHolder(final View view, float x, float y,
			int time, Handler mHandler) {

		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(
				"translationX", 0, x);
		PropertyValuesHolder pvhT = PropertyValuesHolder.ofFloat(
				"translationY", 0, y);

		ObjectAnimator ob = ObjectAnimator.ofPropertyValuesHolder(view, pvhX,
				pvhT);
		ob.setDuration(time);
		int sd = 300;
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				view.setVisibility(View.VISIBLE);
			}
		}, sd);
		ob.setStartDelay(sd);
		ob.setInterpolator(new BounceInterpolator());
		ob.start();
	}

	/**
	 * 用法:
	 * 
	 * ImageView bananaView = ViewHolder.get(convertView, R.id.banana); TextView
	 * phoneView = ViewHolder.get(convertView, R.id.phone);
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		if (view == null)
			return null;
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	/**
	 * 隐藏软键盘 hideSoftInputView
	 */
	public static void hideSoftInputView(Activity a) {
		if (a == null)
			return;
		InputMethodManager manager = ((InputMethodManager) a
				.getSystemService(Activity.INPUT_METHOD_SERVICE));
		// if (a.getWindow().getAttributes().softInputMode !=
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
		//System.out.println("隐藏键盘");
		if (a.getCurrentFocus() != null)
			manager.hideSoftInputFromWindow(a.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		// }
	}

	/**
	 * 移动方法
	 * 
	 * @param v
	 *            需要移动的View
	 * @param startX
	 *            起始x坐标
	 * @param toX
	 *            终止x坐标
	 * @param startY
	 *            起始y坐标
	 * @param toY
	 *            终止y坐标
	 */
	public static void moveFrontBg(View v, float startX, float toX,
			float startY, float toY) {
		TranslateAnimation anim = new TranslateAnimation(startX, toX, startY,
				toY);
		anim.setDuration(200);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}


	/**
	 * 判断后台
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				/*
				 * BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000
				 * PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
				Log.i(context.getPackageName(), "此appimportace ="
						+ appProcess.importance
						+ ",context.getClass().getName()="
						+ context.getClass().getName());
				if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "处于后台"
							+ appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "处于前台"
							+ appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;

	}

	/**
	 * 旋转图片一定角度 rotaingImageView
	 * 
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 保存bitmap方法
	 */
	public static void saveBitmap(Bitmap bitmap, File file) {
		if (file == null || bitmap == null)
			return;
		try {
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	public static void runOnUiThread(Runnable run) {
		new Handler(Looper.getMainLooper()).post(run);
	}

	public static void runOnUiThread(Runnable run, int delayed) {
		new Handler(Looper.getMainLooper()).postDelayed(run, delayed);
	}
	/**
	 * 使用SharedPreferences保存数据
	 */
	public static void saveSharedPreferences(Context ct, String key,
											 boolean value) {//String shareName,

		SharedPreferences sp = ct.getSharedPreferences(
				"sharePreferences", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		// 提交当前数据
		editor.commit();
	}

	public static void saveSharedPreferences(Context ct, String key,
											 int value) {//String shareName,

		SharedPreferences sp = ct.getSharedPreferences(
				"sharePreferences", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		// 提交当前数据
		editor.commit();
	}

	public static void saveSharedPreferences(Context ct,String key,
											 String value) {//String shareName,

		SharedPreferences sp = ct.getSharedPreferences(
				"sharePreferences", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		// 提交当前数据
		editor.commit();
	}

	public static String readSharedPreferences(Context ct,String key,
											   String defValue) {//String shareName,
		SharedPreferences sharedPreferences = ct
				.getSharedPreferences("sharePreferences", Activity.MODE_PRIVATE);
		String istrue = sharedPreferences.getString(key, defValue);

		return istrue;
	}

	public static int readSharedPreferences(Context ct, String key,
											int defValue) {//String shareName,
		SharedPreferences sharedPreferences = ct
				.getSharedPreferences("sharePreferences", Activity.MODE_PRIVATE);
		int istrue = sharedPreferences.getInt(key, defValue);

		return istrue;
	}

	public static boolean readSharedPreferences(Context ct,String key,
												boolean nunValue) {//String shareName,
		SharedPreferences sharedPreferences = ct
				.getSharedPreferences("sharePreferences", Activity.MODE_PRIVATE);
		boolean istrue = sharedPreferences.getBoolean(key, nunValue);

		return istrue;
	}

	// 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	public static int px2dip(Context ct,float pxValue) {
		final float scale = ct.getResources()
				.getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 显示控件，接收一个或多个view
	 */
	public static void setVisibility(View... views) {
		for (View view : views)
			view.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏控件，接收一个或多个view
	 */
	public static void setGone(View... views) {
		for (View view : views)
			view.setVisibility(View.GONE);
	}


	/**
	 * 隐藏软键盘？
	 * 
	 * @return boolean v:EditText event:驱动事件
	 */
	public static boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			// 点击的是输入框区域，保留点击EditText的事件
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {

				return false;
			} else {
				return true;
			}
		}
		return false;
	}



	/**
	 * 检测网络
	 * 
	 * @param context
	 * @return
	 */
//	public static boolean checkNetwork(Context context) {
//		ConnectivityManager connectivity = (ConnectivityManager) context
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (connectivity == null) {
//			return false;
//		} else {
//			NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null) {
//				for (int i = 0; i < info.length; i++) {
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						NetworkInfo netWorkInfo = info[i];
//						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//							return true;
//						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//							return true;
//						}
//					}
//				}
//			}
//		}
//		return false;
//	}

	/**
	 * 获取MsgContent实例
	 * 
	 * @param jsonStr
	 * @return
	 */
	/*public static MsgContent getMsgC(String jsonStr) {
		MsgContent mc = new MsgContent();
		try {// 将json字符串转换为json对象
			JSONObject jsonObj = new JSONObject(jsonStr);
			// 得到指定json key对象的value对象
			JSONObject mcObj = jsonObj.getJSONObject("mc");
			// 获取之对象的所有属性
			mc.setId(mcObj.getString("id"));
			mc.setTheme_id(mcObj.getString("theme_id"));
			mc.setContent(mcObj.getString("content"));
			mc.setType(mcObj.getString("type"));
			mc.setCreated_by(mcObj.getString("created_by"));
			mc.setCreated_time(mcObj.getString("created_time"));
			mc.setCreated_type(mcObj.getString("created_type"));
			mc.setCby_name(mcObj.getString("cby_name"));
			mc.setCby_face_img(mcObj.getString("cby_face_img"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mc;
	}
*/
	/**
	 * 获取MsgContent集合
	 * 
	 * @param jsonStr
	 * @return
	 */
	/*public static List<MsgContent> getMsgCList(String jsonStr) {
		List<MsgContent> mcList = new ArrayList<MsgContent>();

		JSONObject jsonObj;
		try {// 将json字符串转换为json对象
			jsonObj = new JSONObject(jsonStr);
			// 得到指定json key对象的value对象
			JSONArray mccList = jsonObj.getJSONArray("result");
			// 遍历jsonArray
			for (int i = 0; i < mccList.length(); i++) {
				// 获取每一个json对象
				JSONObject jsonItem = mccList.getJSONObject(i);
				// 获取每一个json对象的值
				MsgContent mc = new MsgContent();
				mc.setId(jsonItem.getString("id"));
				mc.setTheme_id(jsonItem.getString("theme_id"));
				mc.setContent(jsonItem.getString("content"));
				mc.setType(jsonItem.getString("type"));
				mc.setCreated_by(jsonItem.getString("created_by"));
				mc.setCreated_time(jsonItem.getString("created_time"));
				mc.setCreated_type(jsonItem.getString("created_type"));
				mc.setCby_name(jsonItem.getString("cby_name"));
				mc.setCby_face_img(jsonItem.getString("cby_face_img"));
				mcList.add(mc);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mcList;
	}
*/
	/**
	 * 得到amr的时长
	 * 
	 * @param file
	 * @return amr文件时间长度
	 * @throws IOException
	 */
	public static int getAmrDuration(File file) throws IOException {
		long duration = -1;
		int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0,
				0, 0 };
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			long length = file.length();// 文件的长度
			int pos = 6;// 设置初始位置
			int frameCount = 0;// 初始帧数
			int packedPos = -1;

			byte[] datas = new byte[1];// 初始数据值
			while (pos <= length) {
				randomAccessFile.seek(pos);
				if (randomAccessFile.read(datas, 0, 1) != 1) {
					duration = length > 0 ? ((length - 6) / 650) : 0;
					break;
				}
				packedPos = (datas[0] >> 3) & 0x0F;
				pos += packedSize[packedPos] + 1;
				frameCount++;
			}
			duration += frameCount * 20;// 帧数*20
		} finally {
			if (randomAccessFile != null) {
				randomAccessFile.close();
			}
		}
		return (int) ((duration / 1000) + 1);
	}

	/**
	 * 获取listView item
	 */
	public static View getViewByPosition(int pos, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition
				+ listView.getChildCount() - 1;

		if (pos < firstListItemPosition || pos > lastListItemPosition) {
			return listView.getAdapter().getView(pos, null, listView);
		} else {
			final int childIndex = pos - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}

	/**
	 * 设置Margins属性
	 * 
	 * @param v
	 *            需要修改的item子控件
	 * @param l
	 *            左
	 * @param t
	 *            上
	 * @param r
	 *            右
	 * @param b
	 *            下
	 */
	public static void setMargins(View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
					.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}

	/**
	 * 设置radio下方view背景色
	 * 
	 * @param context
	 * @param v1
	 *            选中色
	 * @param v2
	 *            未选中色
	 * @param v3
	 *            未选中色
	 */
	/*public static void setBgColor(Context context, View v1, View v2, View v3) {
		v1.setBackgroundColor(ContextCompat.getColor(context, R.color.rbtc1));
		v2.setBackgroundColor(ContextCompat.getColor(context,
				R.color.colourless));
		v3.setBackgroundColor(ContextCompat.getColor(context,
				R.color.colourless));
	}
*/
	/**
	 * 轨迹 - 移动到顶部动画
	 */
	public static TranslateAnimation moveToViewTop() {
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 5.5f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		mTranslateAnimation.setDuration(1000);
		return mTranslateAnimation;
	}

	/**
	 * 轨迹 - 移动到原位动画
	 */
	public static TranslateAnimation moveToViewSitu() {
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.9f);
		mTranslateAnimation.setDuration(1000);
		return mTranslateAnimation;
	}

	/**
	 * 轨迹 - 开始时间对话框
	 */
	/*public static void showTimeFromDatePickerDlg(Context context,
			final TextView mStartDateTv) {
		DatePickerDlg dlg = new DatePickerDlg.Builder(context)
				.setShowMode(DatePickerDlg.MODE_DATE_YMD_HM)
				.setTitle("开始时间")
				.setYesButton("确定", new DatePickerDlg.DatPickerListener() {

					@Override
					public void onSelected(DialogInterface dialog, String date) {
						dialog.dismiss();
						mStartDateTv.setText(date);
					}
				}).setNoButton("取消", null)
				.setSelectDataTime(mStartDateTv.getText().toString()).create();
		dlg.show();
	}*/

	/**
	 * 结束时间对话框
	 */
	/*public static void showTimeToDatePickerDlg(Context context,
			final TextView mEndDateTv) {
		DatePickerDlg dlg = new DatePickerDlg.Builder(context)
				.setShowMode(DatePickerDlg.MODE_DATE_YMD_HM)
				.setTitle("结束时间")
				.setYesButton("确定", new DatePickerDlg.DatPickerListener() {

					@Override
					public void onSelected(DialogInterface dialog, String date) {
						dialog.dismiss();
						mEndDateTv.setText(date);
					}
				}).setNoButton("取消", null)
				.setSelectDataTime(mEndDateTv.getText().toString()).create();
		dlg.show();
	}*/

	/**
	 * 增加控件的可点击范围，最大范围只能是父布局所包含的的区域
	 */
	public static void addDefaultScreenArea(final View view, final int top,
			final int bottom, final int left, final int right) { // 增大checkBox的可点击范围
		final View parent = (View) view.getParent();
		parent.post(new Runnable() {
			public void run() {

				Rect bounds = new Rect();
				view.setEnabled(true);
				view.getHitRect(bounds);

				bounds.top -= top;
				bounds.bottom += bottom;
				bounds.left -= left;
				bounds.right += right;

				TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

				if (View.class.isInstance(view.getParent())) {
					((View) view.getParent()).setTouchDelegate(touchDelegate);
				}
			}
		});

	}

	/**
	 * 将字符串集转为字符串
	 */
	public static String List2String(List list) throws IOException {
		// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 然后将得到的字符数据装载到ObjectOutputStream
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
		objectOutputStream.writeObject(list);
		// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
		String listString = new String(Base64.encode(
				byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
		// 关闭objectOutputStream
		objectOutputStream.close();
		return listString;
	}

	/**
	 * 将字符串转为字符串集
	 */
	@SuppressWarnings("unchecked")
	public static List String2List(String listString)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		byte[] mobileBytes = Base64.decode(listString.getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				mobileBytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		List list = (List) objectInputStream.readObject();
		objectInputStream.close();
		return list;
	}

	/**
	 * 通过联系人号码找到联系人名称
	 */
	public static String Number2Name(String mNumber, Context context) {
		String name = "";
		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, };
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				projection,
				ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
						+ mNumber + "'", null, null);
		if (cursor == null) {
			return "";
		}
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			int nameFieldColumnIndex = cursor
					.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
			cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
			name = cursor.getString(nameFieldColumnIndex);
			break;
		}
		if (cursor != null) {
			cursor.close();
		}
		return name;
	}

	/**
	 * 获取通讯录返回号码，并显示在edittext内
	 * 
	 * @param data
	 *            ：onActivityResult-返回值
	 * @param mEditText
	 *            ：号码显示的edittext
	 * @param mActicity
	 *            ：调用该方法的Acticity
	 */
	public static void getContactsNumber(Intent data, EditText mEditText,
			Activity mActicity) {
		if (data == null) {
			return;
		}
		String phoneNumber = null;
		Uri contactData = data.getData();
		if (contactData == null) {
			return;
		}
		Cursor cursor = mActicity.managedQuery(contactData, null, null, null,
				null);
		if (cursor.moveToFirst()) {
			String hasPhone = cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
			String id = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			if (hasPhone.equalsIgnoreCase("1")) {
				hasPhone = "true";
			} else {
				hasPhone = "false";
			}
			if (Boolean.parseBoolean(hasPhone)) {
				Cursor phones = mActicity.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + id, null, null);
				while (phones.moveToNext()) {
					phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
					mActicity.setTitle(phoneNumber);
				}
				phones.close();
			}
		}
		mEditText.setText(phoneNumber);
		mEditText.setSelection(mEditText.getText().length());
	}

	/** dp转px */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	public static void textEnddrawable(Context context, TextView content,String showText,int imgId,
	int wpx,int hpx) {
//		SpannableString ss = new SpannableString(showText + " ");
//		int len = ss.length();
//		//图片
		//Drawable d = ContextCompat.getDrawable(context, imgId);
		int wdp = Utils.dip2px(context, wpx);
		int hdp = Utils.dip2px(context, hpx);
		//d.setBounds(0, 0, wdp, hdp);
		//构建ImageSpan
		//ImageSpan is = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		CenterAlignImageSpan is = new CenterAlignImageSpan(context,imgId,ImageSpan.ALIGN_BASELINE,wdp,hdp);
//		ss.setSpan(span, len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		content.setText(ss);

		//ImageSpan is = new ImageSpan(context,BitmapFactory.decodeResource(context.getResources(),imgId));
		String str = showText;
		String space = " ";
		str = str + space;
		int strLength = str.length();
		SpannableString ss = new SpannableString(str);
		ss .setSpan(is,strLength-1, strLength, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		content.setText(ss.subSequence(0,strLength));
	}
	public static void textStartDrawable(Context context, TextView content,String showText,int imgId,
									   int wpx,int hpx) {
//		SpannableString ss = new SpannableString(showText + " ");
//		int len = ss.length();
//		//图片
		//Drawable d = ContextCompat.getDrawable(context, imgId);
		int wdp = Utils.dip2px(context, wpx);
		int hdp = Utils.dip2px(context, hpx);
		//d.setBounds(0, 0, wdp, hdp);
		//构建ImageSpan
		//ImageSpan is = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		CenterAlignImageSpan is = new CenterAlignImageSpan(context,imgId,ImageSpan.ALIGN_BASELINE,wdp,hdp);
//		ss.setSpan(span, len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		content.setText(ss);

		//ImageSpan is = new ImageSpan(context,BitmapFactory.decodeResource(context.getResources(),imgId));
		String str = showText;
		String space = " ";
		str = str + space;
		int strLength = str.length();
		SpannableString ss = new SpannableString(str);
		ss .setSpan(is,0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		content.setText(ss.subSequence(0,strLength));
	}
	public static  Drawable roundBitmapByBitmapDrawable(Activity activity,Bitmap bitmap, int radius) {
		if(bitmap == null) {
			throw new NullPointerException("Bitmap can't be null");
		}
		// 绘制圆角
		RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(activity.getResources(), bitmap);
		dr.setCornerRadius(radius);
		dr.setAntiAlias(true);
		return dr;
	}
	//固定宽度的情况下，自适应文本字体大小
	public static float adjustTvTextSize(TextView tv, int maxWidth, String text) {
		int avaiWidth = maxWidth - tv.getPaddingLeft() - tv.getPaddingRight() - 10;

		if (avaiWidth <= 0 || StringUtil.isEmpty(text)) {
			return tv.getPaint().getTextSize();
		}
		TextPaint textPaintClone = new TextPaint(tv.getPaint());
		// note that Paint text size works in px not sp
		float trySize = textPaintClone.getTextSize();

		while (textPaintClone.measureText(text) > avaiWidth) {
			trySize--;
			textPaintClone.setTextSize(trySize);
		}
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
		return trySize;
	}/**
	 * 查看URL相关的图片
	 * @param c
	 * @param url
	 */
	public static void showImgDlgPhotoView2(Context c,String url) {
		// 全屏显示的方法
		final Dialog dialog = new Dialog(c, R.style.pop_dialog_choose4 );//android.R.style.Theme_Black_NoTitleBar_Fullscreen
		PhotoView imgView = getViewPhotoView(c,url);
		dialog.setContentView(imgView);
		dialog.show();

		// 点击图片消失
		imgView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}

	private static PhotoView getViewPhotoView(Context c,String url) {
		PhotoView imgView = new PhotoView(c);
		imgView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

		//EasyImageLoader.getInstance(c).bindBitmap(url,imgView);
		loadImageBig(url,imgView,1000);
		return imgView;
	}
	/**
	 * 查看bitmap相关的图片
	 * @param c
	 * @param bitmap
	 */
	public static void showImgDlgBitmapPhotoView2(Context c, Bitmap bitmap) {
		// 全屏显示的方法
		final Dialog dialog = new Dialog(c, R.style.pop_dialog_choose4 );//android.R.style.Theme_Black_NoTitleBar_Fullscreen
		PhotoView imgView = getBitmapViewPhotoView(c,bitmap);
		dialog.setContentView(imgView);
		dialog.show();

		// 点击图片消失
		imgView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	public static PhotoView getBitmapViewPhotoView(Context c, Bitmap bitmap) {
		PhotoView imgView = new PhotoView(c);
		imgView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
		imgView.setImageBitmap(bitmap);
		//Utils.loadImageBig(url,imgView,400);
		return imgView;
	}
	/**
	 * 水平滚动的GridView的控制
	 */
	public static void setHorizontalGridView(Context mContext,int size, GridView gridView,int itemDp) {

		int itemWidth = Utils.dip2px(mContext,itemDp);
		int gridviewWidth = size * itemWidth;

		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
		gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
		gridView.setColumnWidth(itemWidth); // 设置列表项宽
		gridView.setHorizontalSpacing(0); // 设置列表项水平间距
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setNumColumns(size); // 设置列数量=列表集合数

	}

	//复制文本
	public static void onClickCopy(Context context,String text) {
		// 从API11开始android推荐使用android.content.ClipboardManager
		// 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		// 将文本内容放到系统剪贴板里。
		cm.setText(text);
	}

	public static void toContacts(Context context,String phone,String name) {
		Intent it = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(
				Uri.parse("content://com.android.contacts"), "contacts"));
		it.setType("vnd.android.cursor.dir/person");
        // 联系人姓名
		it.putExtra(ContactsContract.Intents.Insert.NAME, name);
        // 公司
//		it.putExtra(android.provider.ContactsContract.Intents.Insert.COMPANY,
//				"北京XXXXXX公司");
//       // email
//		it.putExtra(android.provider.ContactsContract.Intents.Insert.EMAIL,
//				"123456@qq.com");
       // 手机号码
		it.putExtra(ContactsContract.Intents.Insert.PHONE,
				phone);
       // 单位电话
//		it.putExtra(
//				android.provider.ContactsContract.Intents.Insert.SECONDARY_PHONE,
//				"18600001111");
//       // 住宅电话
//		it.putExtra(
//				android.provider.ContactsContract.Intents.Insert.TERTIARY_PHONE,
//				"010-7654321");
//        // 备注信息
//		it.putExtra(android.provider.ContactsContract.Intents.Insert.JOB_TITLE,
//				"名片");
		context.startActivity(it);
	}

	//平移
	public static void translationAnim(Context context ,ImageView imageView,float dp){
		int dip =  Utils.dip2px(context,dp);
		// translationX 沿着x轴移动 上个数大于下个数，左移。反之右移
		ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(imageView,"translationX",0f,dip,dip);  //左移-->右移
		// translationX 沿着x轴移动 上个数大于下个数，上移。反之下移
		// ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(imageView,"translationY",0f,300f,0f);  //下移-->上移
		objectAnimator.setDuration(400);
		objectAnimator.start();
	}
	public static void translationAnim2(Context context ,ImageView imageView,float dp){
		int dip =  Utils.dip2px(context,dp);
		// translationX 沿着x轴移动 上个数大于下个数，左移。反之右移
		ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(imageView,"translationX",dip,0,0);  //左移-->右移
		// translationX 沿着x轴移动 上个数大于下个数，上移。反之下移
		// ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(imageView,"translationY",0f,300f,0f);  //下移-->上移
		objectAnimator.setDuration(400);
		objectAnimator.start();
	}

	public static void showDeffentColorText(String text,TextView textView,int start,int end,int color){
		SpannableStringBuilder mSpannable = new SpannableStringBuilder(text);
		//mSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		mSpannable.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		textView.setText(mSpannable);
	}
	public static String teDianStr(String tedian){
		String[] strs= StringUtil.string2array1(tedian,"&");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0;i<strs.length;i++){
			buffer.append((i+1)+"."+strs[i]);
			if (i<strs.length-1){
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}
	public static String teDianStr2(String tedian){
		String[] strs= StringUtil.string2array1(tedian,"&");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0;i<strs.length;i++){
			buffer.append("（"+(i+1)+"）"+strs[i]);
			if (i<strs.length-1){
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}
	public static ArrayList<String> getImgList(String fileList,int count){
		ArrayList<String> list = new ArrayList<String>();
		if (!StringUtil.isEmpty(fileList)){
			if (fileList.contains("{")){
				fileList = fileList.replaceAll("\\{","");
			}
			if (fileList.contains("}")){
				fileList = fileList.replaceAll("\\}","");
			}
			String[] fileImgs = StringUtil.string2array1(fileList,",");
			int len = fileImgs.length;
			//System.out.println("len的值为==》"+len+"fileImgs==>"+fileList);

			if (count!=-1&&len>count){
				len = count;
			}

			for (int i = 0;i<len;i++){
				list.add(fileImgs[i]);
			}
		}
		return list;
	}

//	public static void delVersionApk(VersionInfoData data){
//		String version = StringUtil.trimNull(data.getVersionName());
//		File downFile = FileUtils.getFile(AppData.BASE_PATH, "ynt-"+version+".apk");
//		FileUtil.deleteFile2(downFile.getAbsolutePath());
//	}

}
