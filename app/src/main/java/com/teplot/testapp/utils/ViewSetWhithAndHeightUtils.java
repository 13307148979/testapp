package com.teplot.testapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teplot.testapp.apps.AppContext;

/**
 * 动态设置相关的长和宽
 */
public class ViewSetWhithAndHeightUtils {

    // 进行计算屏幕宽度，动态显示
    public static void setTextViewMaxWidth(Context context, TextView textView, int dpValue, int count){
        DisplayMetrics dm = AppContext.getInstance().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int widthArea = (width - Utils.dip2px(context, dpValue))/count;
        //	System.out.println("width=Utils=>"+(width - dip2px(context, dpValue)));
        textView.setMaxWidth(widthArea);
    }

    /**
     * 设置图片的相同宽高   与屏幕相关  FrameLayout
     * @param context
     * @param imageView
     * @param dpValue
     * @param count
     */
    public static void setImagViewHeightEqualsWidthFrameLayout(Context context, View imageView, int dpValue, int count,
                                                               int dwValue, int dhValue){
        DisplayMetrics dm = AppContext.getInstance().getResources().getDisplayMetrics();

        int width = dm.widthPixels;// 屏幕宽
        int height = (width - Utils.dip2px(context, dpValue))/count;
        FrameLayout.LayoutParams linearParams =(FrameLayout.LayoutParams) imageView.getLayoutParams();

        linearParams.width = height;// 控件的宽
        linearParams.height = (height*dhValue)/dwValue;// 控件的高

        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }
    /**
     * 设置图片的相同宽高   与屏幕相关  RelativeLayout
     * @param context
     * @param imageView
     * @param dpValue
     * @param count
     */
    public static void setImagViewHeightEqualsWidthRelativeLayout(Context context, View imageView, int dpValue, int count,
                                                               int dwValue,int dhValue){
        DisplayMetrics dm = AppContext.getInstance().getResources().getDisplayMetrics();

        int width = dm.widthPixels;// 屏幕宽
        int height = (width - Utils.dip2px(context, dpValue))/count;
        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) imageView.getLayoutParams();

        linearParams.width = height;// 控件的宽
        linearParams.height = (height*dhValue)/dwValue;// 控件的高

        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }
    /**
     * 设置图片的相同宽高   与屏幕相关  LinearLayout
     * @param context
     * @param imageView
     * @param dpValue
     * @param count
     */
    public static void setImagViewHeightEqualsWidthLinearLayout(Context context, View imageView, int dpValue, int count,
                                                                  int dwValue,int dhValue){
        DisplayMetrics dm = AppContext.getInstance().getResources().getDisplayMetrics();

        int width = dm.widthPixels;// 屏幕宽
        int height = (width - Utils.dip2px(context, dpValue))/count;
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) imageView.getLayoutParams();

        linearParams.width = height;// 控件的宽
        linearParams.height = (height*dhValue)/dwValue;// 控件的高
        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    public static int getTextViewLineWidth(Context context,int dpValue){
        DisplayMetrics dm = AppContext.getInstance().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int widthArea = (width - Utils.dip2px(context, dpValue));
        //	System.out.println("width=Utils=>"+(width - dip2px(context, dpValue)));
       return widthArea;
    }
//    //设置图片宽高
//    public static void setImagViewHeightEqualsWidthLinearLayout2(View imageView,int dwValue,int dhValue,
//                                                                int dwValue2){
//        DisplayMetrics dm = AppContext.getInstance().getResources().getDisplayMetrics();
//
//        int width = dm.widthPixels;// 屏幕宽
//        int height = (width *dhValue)/dwValue2;
//        FrameLayout.LayoutParams linearParams =(FrameLayout.LayoutParams) imageView.getLayoutParams();
//
//        linearParams.height = height;// 控件的宽
//        linearParams.width = (height*dwValue)/dhValue;// 控件的高
//        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
//    }
}
