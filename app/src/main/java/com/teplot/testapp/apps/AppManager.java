package com.teplot.testapp.apps;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.util.Stack;

public class AppManager {


	private static Stack<Activity> activityStack;
	private static AppManager instance;
	public static boolean isFinishAll = true;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getInstance() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		try {
			return activityStack.lastElement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = activityStack.size() - 1; i >= 0; i--) {
			try {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
//					System.out.println("finish->"
//							+ activityStack.get(i).getClass().getName());
				}
			} catch (Exception e) {
			}
		}
		activityStack.clear();
	}
	public void removeActivity(Activity activity) {
		if (activity != null)
			activityStack.remove(activity);
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		// finish所有activity
		finishAllActivity();
		// 再彻底结束app
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (currentActivity() == null) {
					//System.out.println("-----结束APP-----");
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		}, 3000);
	}
}
