package com.teplot.testapp.apps;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.teplot.testapp.utils.SystemUtil;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AppException extends Exception implements UncaughtExceptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465180292563441000L;
	
	public static final String TAG ="AppException";

	public final static boolean Debug = true;// 是否保存错误日志

	/** 定义异常类型 */
	public final static int TYPE_NETWORK = 0x01;
	public final static int TYPE_SOCKET = 0x02;
	public final static int TYPE_HTTP_CODE = 0x03;
	public final static int TYPE_HTTP_ERROR = 0x04;
	public final static int TYPE_XML = 0x05;
	public final static int TYPE_IO = 0x06;
	public final static int TYPE_RUN = 0x07;
	public final static int TYPE_JSON = 0x08;
	public final static int TYPE_CUSTOM_HINT = 0x09;

	public int type;
	public int code;
	public String errHintStr;

	/** 系统默认的UncaughtException处理类 */
	public UncaughtExceptionHandler mDefaultHandler;

	public AppException() {
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public AppException(int type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
		if (Debug) {
			if (type == TYPE_NETWORK || type == TYPE_CUSTOM_HINT)
				return;
			//this.saveErrorLog(type, excp);
		}
	}

	public AppException(int type, String errInfo) {
		super(new Exception(errInfo));
		this.type = type;
		this.errHintStr = errInfo;
		if (Debug) {
			if (type == TYPE_NETWORK || type == TYPE_CUSTOM_HINT)
				return;
			//this.saveErrorLog(type, errInfo);
		}
	}

	public int getCode() {
		return this.code;
	}

	public int getType() {
		return this.type;
	}

	/**
	 * 提示友好的错误信息
	 *
	 */
//	public void makeToast(Activity ctx) {
//		if (ctx instanceof BaseActivity)
//			((BaseActivity) ctx).hideProdialog();
//		Toast.makeText(ctx, getErrorInfo(ctx), Toast.LENGTH_SHORT).show();
//	}

//	public String getErrorInfo(Context ctx) {
//		switch (this.getType()) {
//		case TYPE_HTTP_CODE:
//			return ctx.getString(R.string.http_status_code_error,
//					this.getCode());
//		case TYPE_HTTP_ERROR:
//			return ctx.getString(R.string.http_exception_error);
//		case TYPE_SOCKET:
//			return ctx.getString(R.string.socket_exception_error);
//		case TYPE_NETWORK:
//			return ctx.getString(R.string.network_not_connected);
//		case TYPE_XML:
//			return ctx.getString(R.string.data_parser_failed);
//		case TYPE_JSON:
//			return ctx.getString(R.string.data_parser_failed);
//		case TYPE_IO:
//			return ctx.getString(R.string.io_exception_error);
//		case TYPE_RUN:
//			return ctx.getString(R.string.app_run_code_error);
//		case TYPE_CUSTOM_HINT:
//			return errHintStr;
//		}
//		return errHintStr;
//	}

	/////------------------------------
	
	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}

	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0, e);
	}

	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, 0, e);
	}

	public static AppException io(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, 0, e);
		} else if (e instanceof IOException) {
			return new AppException(TYPE_IO, 0, e);
		}
		return run(e);
	}

	public static AppException xml(Exception e) {
		return new AppException(TYPE_XML, 0, e);
	}

	public static AppException json(Exception e) {
		return new AppException(TYPE_JSON, 0, e);
	}

	public static AppException network(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, 0, e);
		} else if (e instanceof SocketException) {
			return socket(e);
		}
		return io(e);
	}

	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}

	public static AppException customHint(String errInfo) {
		return new AppException(TYPE_CUSTOM_HINT, errInfo);
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * 
	 * @return
	 */
	public static AppException getAppExceptionHandler() {
		return new AppException();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
//		System.out.println("AppException=====>app崩溃啦 Thread "
//				+ Thread.currentThread().getName());
		if (!handleException(ex) && mDefaultHandler != null) {
			//如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				
				Log.e(TAG, "error : ", e); 
				//Logger.e(TAG, e.getMessage());
			}
			//--------------------------------------------
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * 
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {

			//System.out.println("AppException----ex=====>null");
			return false;
		}
		//20160620
		ex.printStackTrace();
		
		final Context context = AppManager.getInstance().currentActivity();

		if (context == null) {
			//System.out.println("context=====>null");
			return false;
		}
		
		final String crashReport = getCrashReport(context, ex);
		//Toast.makeText(context, crashReport, Toast.LENGTH_LONG).show();
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "程序开个小差 即将退出", Toast.LENGTH_LONG)
				.show();
				Looper.loop();
			}
		}.start();

		return true;
	}

	/**
	 * 获取APP崩溃异常报告
	 * 
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Context context, Throwable ex) {
		PackageInfo pinfo = SystemUtil.getPackageInfo(context);
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: " + pinfo.versionName + "("
				+ pinfo.versionCode + ")\n");
		exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
				+ "(" + android.os.Build.MODEL + ")\n");
		exceptionStr.append("Exception: " + ex.getMessage() + "\n");

		StackTraceElement[] elements;
		Throwable cause = ex.getCause();
		while (cause != null) {
			elements = cause.getStackTrace();
			for (int i = 0; i < elements.length; i++) {
				exceptionStr.append(elements[i].toString() + "\n");
			}
			cause = cause.getCause();
		}
		return exceptionStr.toString();
	}
}
