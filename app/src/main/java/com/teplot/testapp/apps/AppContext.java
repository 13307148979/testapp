package com.teplot.testapp.apps;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.WindowManager;

import com.teplot.testapp.R;
import com.teplot.testapp.been.details.FacePersonData;
import com.teplot.testapp.been.details.LoginInfo;
import com.teplot.testapp.db.FacePersonDB;
import com.teplot.testapp.db.UserLoginDB;
import com.teplot.testapp.utils.FileUtils;
import com.teplot.testapp.utils.ImageLoaderUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SpeechConstant;


public class AppContext extends Application {
	private static AppContext instance;
	private int mScreenHeight;
	private int mScreenWidth;
	private HashMap<String, Object> cacheMap;
	public static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
		getScreenSize();
		if (Build.VERSION.SDK_INT < 23) {
			FileUtils.createDirectory(AppData.PICTURE_PATH);
			FileUtils.createDirectory(AppData.VIDIO_PATH);
			ImageLoaderUtil.init(this, R.drawable.backgroud_nodata, AppData.SD_PATH + AppData.PICTURE_PATH);
		}
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				//其他配置
				.build();

		OkHttpUtils.initClient(okHttpClient);
		SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5adefaad");
	}
	@SuppressWarnings("deprecation")
	private void getScreenSize() {
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		mScreenWidth = wm.getDefaultDisplay().getWidth();
		mScreenHeight = wm.getDefaultDisplay().getHeight();
		AppData.DENSITY = getResources().getDisplayMetrics().density;
		AppData.HEIGTH = getResources().getDisplayMetrics().heightPixels;
		AppData.WIGTH = getResources().getDisplayMetrics().widthPixels;
	}
	public int getScreenWidth() {
		return mScreenWidth;
	}

	public int getScreenHeight() {
		return mScreenHeight;
	}
	/**
	 * 结束app
	 */
	public void exitApp() {
		//getUser().setLogined(false);
		AppManager.getInstance().AppExit(getApplicationContext());
	}

	private LoginInfo mLogin;
	public LoginInfo getLoginMsg2() {
		if (mLogin == null) {
			UserLoginDB mySQLiteOpenHelper = new UserLoginDB(getApplicationContext());
			mLogin = mySQLiteOpenHelper.getUser();
			mySQLiteOpenHelper.close();
		}
		return mLogin;
	}

	public FacePersonData getFacePersonMsg(String person_id) {

		FacePersonDB mFaceDb = new FacePersonDB(getApplicationContext());
		FacePersonData facePersonData = mFaceDb.getFacePerson(person_id);
		mFaceDb.close();
		return facePersonData;
	}

	public void delFacePersonMsg(String person_id) {

		FacePersonDB mFaceDb = new FacePersonDB(getApplicationContext());
		mFaceDb.delTable(person_id);
		mFaceDb.close();
	}
	/**
	 * 获取App安装包信息
	 *
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;

		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
	/**
	 * 取得当前用户名
	 * @return
	 */
	public String getUserName() {
		return getLoginMsg2().getUserName();
	}
}
