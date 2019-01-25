package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.teplot.testapp.R;


public class ConfireDlgBottom extends Dialog{
	
	public ConfireDlgBottom(Context context, boolean cancelable,
                            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);  
    }  
  
    public ConfireDlgBottom(Context context, int theme) {
        super(context, theme);  
    }  
  
    public ConfireDlgBottom(Context context) {
        super(context);  
    }  
	
	public static class Builder {  
		
		private Context mContext;

		private CharSequence mYesText;
		private CharSequence mNoText;
        private CharSequence mCancleText;
		
		private OnClickListener mYesBtnListener;
		private OnClickListener mNoBtnListener;
        private OnClickListener mCancleBtnListener;
  
        public Builder(Context context) {  
            this.mContext = context;  
        }

        public Builder setThreeButton(CharSequence text,OnClickListener listener){
            this.mCancleText = text;
            this.mCancleBtnListener = listener;
            return this;
        }

        public Builder setOneButton(CharSequence text,OnClickListener listener){
        	this.mYesText = text;
        	this.mYesBtnListener = listener;
        	return this;  
        }
        
        public Builder setTwoButton(CharSequence text,OnClickListener listener){
        	this.mNoText = text;
        	this.mNoBtnListener = listener;
        	return this;  
        }
        
        @SuppressLint("InflateParams")
		@SuppressWarnings("deprecation")
		public ConfireDlgBottom create() {
        	
            LayoutInflater inflater = (LayoutInflater) mContext  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final ConfireDlgBottom dialog = new ConfireDlgBottom(mContext, R.style.pop_dialog_choose);

            dialog.setCancelable(true);// 设置点击屏幕Dialog不消失
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            View layout = inflater.inflate(R.layout.pop_confirem_bottom, null);
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
            Button oneBtn = (Button) layout.findViewById(R.id.btn_1);
            Button towBtn = (Button) layout.findViewById(R.id.btn_2);
            Button threeBtn = (Button) layout.findViewById(R.id.btn_3);

            if (mYesText != null){
                oneBtn.setText(mYesText);
                oneBtn.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View arg0) {
    					dialog.dismiss();
    					if (mYesBtnListener != null) {
    						mYesBtnListener.onClick(dialog, 0);
    					}
    				}
    			});
            } else {
                oneBtn.setVisibility(View.GONE);
            }

            if (mNoText != null) {
                towBtn.setText(mNoText);
                towBtn.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View arg0) {
    					if (mNoBtnListener != null) {
    						mNoBtnListener.onClick(dialog, 1);
    					}
    					dialog.dismiss();
    				}
    			});
            } else {
                towBtn.setVisibility(View.GONE);
            }
            if (mCancleText != null) {
                threeBtn.setText(mCancleText);
                threeBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (mCancleBtnListener != null) {
                            mCancleBtnListener.onClick(dialog, 3);
                        }
                        dialog.dismiss();
                    }
                });
            } else {
                threeBtn.setVisibility(View.GONE);
            }
            return dialog;  
        }    
	}
}
