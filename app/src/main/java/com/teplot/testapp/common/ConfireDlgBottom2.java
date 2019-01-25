package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.teplot.testapp.R;


public class ConfireDlgBottom2 extends Dialog{

	public ConfireDlgBottom2(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ConfireDlgBottom2(Context context, int theme) {
        super(context, theme);
    }

    public ConfireDlgBottom2(Context context) {
        super(context);  
    }  
	
	public static class Builder {  
		
		private Context mContext;

		private OnClickListener oneListener;
		private OnClickListener twoListener;
        private OnClickListener threeListener;

        private OnClickListener cancleListener;
  
        public Builder(Context context) {  
            this.mContext = context;  
        }

        public Builder setThreeButton(OnClickListener listener){
            this.threeListener = listener;
            return this;
        }

        public Builder setOneButton(OnClickListener listener){
        	this.oneListener = listener;
        	return this;  
        }
        
        public Builder setTwoButton(OnClickListener listener){
        	this.twoListener = listener;
        	return this;  
        }

        public Builder setCancleButton(OnClickListener listener){
            this.cancleListener = listener;
            return this;
        }
        
        @SuppressLint("InflateParams")
		@SuppressWarnings("deprecation")
		public ConfireDlgBottom2 create() {
        	
            LayoutInflater inflater = (LayoutInflater) mContext  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final ConfireDlgBottom2 dialog = new ConfireDlgBottom2(mContext, R.style.pop_dialog_choose3);

            dialog.setCancelable(true);// 设置点击屏幕Dialog不消失
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            View layout = inflater.inflate(R.layout.pop_confirem_bottom2, null);
            dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));

            window.setWindowAnimations(R.style.DialogAnimation); // 添加动画
            WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = 0; // 新位置Y坐标
            lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels; // 宽度
            layout.measure(0, 0);
            lp.height = layout.getMeasuredHeight();

            lp.alpha = 9f; // 透明度
            window.setAttributes(lp);

            //layout.setBackground(null);
            //可以滑动的textview 配合xml的属性
//            contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());

            LinearLayout close_ll = (LinearLayout) layout.findViewById(R.id.close_ll);

            LinearLayout gongyi_ll = (LinearLayout) layout.findViewById(R.id.gongyi_ll);
            LinearLayout qiugou_ll = (LinearLayout) layout.findViewById(R.id.qiugou_ll);
            LinearLayout caiji_ll = (LinearLayout) layout.findViewById(R.id.caiji_ll);

            gongyi_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (oneListener != null) {
                        oneListener.onClick(dialog, 0);
                    }
                }
            });
            qiugou_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (twoListener != null) {
                        twoListener.onClick(dialog, 1);
                    }
                }
            });
            caiji_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (threeListener != null) {
                        threeListener.onClick(dialog, 2);
                    }
                }
            });
            close_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (cancleListener != null) {
                        cancleListener.onClick(dialog, 3);
                    }
                }
            });
            return dialog;  
        }

        public ConfireDlgBottom2 create2() {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ConfireDlgBottom2 dialog = new ConfireDlgBottom2(mContext, R.style.pop_dialog_choose3);

            dialog.setCancelable(true);// 设置点击屏幕Dialog不消失
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            View layout = inflater.inflate(R.layout.pop_confirem_bottom3, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));

            window.setWindowAnimations(R.style.DialogAnimation); // 添加动画
            WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = 0; // 新位置Y坐标
            lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels; // 宽度
            layout.measure(0, 0);
            lp.height = layout.getMeasuredHeight();

            lp.alpha = 9f; // 透明度
            window.setAttributes(lp);

            //layout.setBackground(null);
            //可以滑动的textview 配合xml的属性
//            contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());

            LinearLayout close_ll = (LinearLayout) layout.findViewById(R.id.close_ll);

            LinearLayout gongyi_ll = (LinearLayout) layout.findViewById(R.id.gongyi_ll);
            LinearLayout qiugou_ll = (LinearLayout) layout.findViewById(R.id.qiugou_ll);

            gongyi_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (oneListener != null) {
                        oneListener.onClick(dialog, 0);
                    }
                }
            });
            qiugou_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (twoListener != null) {
                        twoListener.onClick(dialog, 1);
                    }
                }
            });
            close_ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (cancleListener != null) {
                        cancleListener.onClick(dialog, 3);
                    }
                }
            });
            return dialog;
        }
    }
}
