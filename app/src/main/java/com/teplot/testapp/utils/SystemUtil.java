package com.teplot.testapp.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.inputmethod.InputMethodManager;


import java.util.ArrayList;
import java.util.List;

public class SystemUtil {

	private static String TAG = "SystemUtil";
	/**
	 * 判断后台
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 隐藏软键盘 hideSoftInputView
	 * 
	 * @param a
	 */
	public static void hideSoftInputView(Activity a) {
		if (a == null)
			return;
		InputMethodManager manager = ((InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE));
		// if (a.getWindow().getAttributes().softInputMode !=
		// WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
		if (a.getCurrentFocus() != null)
			manager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/////////////////// 短信////////////////////////
	/**
	 * 发送短信
	 * 
	 * @param num
	 *            短信号码
	 * @param content
	 *            短信内容
	 */
	public static boolean sendSMS(String num, String content) {
		if (StringUtil.isEmpty(num))
			return false;
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> listmsg = smsManager.divideMessage(content);
		for (int i = 0; i < listmsg.size(); i++) {
			smsManager.sendTextMessage(num, null, listmsg.get(i), null, null);
		}
		return true;
	}

	/**
	 * 用intent启动发送短信
	 * 
	 * @param c
	 * @param num
	 */
	public static boolean sendSMS(Context c, String num, String smsBody) {
		if (StringUtil.isEmpty(num))
			return false;
		Uri smsToUri = Uri.parse("smsto:" + num);
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", smsBody);
		c.startActivity(intent);
		return true;
	}

	///////////////// 电话//////////////////
//	/**
//	 * 用intent启动拨打电话
//	 *
//	 * @param ct
//	 * @param num
//	 *            拨号号码 sendNum 短信号码
//	 */
//	public static boolean callPhone(Context ct,String num) {
//		if (StringUtil.isEmpty(num))
//			return false;
//		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		ct.startActivity(intent);
//		return true;
//	}
	/**
	 * 用intent启动拨打电话
	 */
	public static  void takePhone(Context ct,String num){
		//创建打电话的意图
		Intent intent = new Intent();
		//设置拨打电话的动作
		intent.setAction(Intent.ACTION_CALL);
		//设置拨打电话的号码
		intent.setData(Uri.parse("tel:" + num));
		//开启打电话的意图
		ct.startActivity(intent);
	}
    /**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context ct) {
		PackageInfo info = null;

		try {
			info = ct.getPackageManager().getPackageInfo(ct.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
}
