package com.teplot.testapp.base;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.teplot.testapp.common.TopBar;

// 所有Activity的父类
public abstract class BaseFragment extends Fragment {
	private final int MSG_CANCEL_AND_FINISH_ACTIVITY = -99;
	protected Handler mHandler;
	protected ProgressDialog mProdialog = null;
	protected TopBar mTopBar;

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast居中提示(来自String) **/
	protected void showShortCenterToast(String text) {
		Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongCenterToast(String text) {
		Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongTopToast(String text) {
		Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
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
		mProdialog = new ProgressDialog(getActivity());
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
		intent.setClass(getActivity(), activity);
		startActivity(intent);
	}
	
	public static void startActivity(Context context,Class<?> activity) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.setClass(context, activity);
		context.startActivity(intent);
	}
	public void showOut(String text){
		System.out.println(text);
	}

}
