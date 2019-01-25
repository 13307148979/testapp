package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.utils.StringUtil;


public class ConfireDlg extends Dialog{
	
	public ConfireDlg(Context context, boolean cancelable,  
            OnCancelListener cancelListener) {  
        super(context, cancelable, cancelListener);  
    }  
  
    public ConfireDlg(Context context, int theme) {  
        super(context, theme);  
    }  
  
    public ConfireDlg(Context context) {  
        super(context);  
    }  
	
	public static class Builder {  
		
		private Context mContext;
		private CharSequence mTitleText;
		private CharSequence mContentText;
		private CharSequence mYesText;
		@SuppressWarnings("unused")
		private CharSequence mNoText;
		
		private OnClickListener mYesBtnListener;
		private OnClickListener mNoBtnListener;
		private boolean  isCancel;

  
        public Builder(Context context) {  
            this.mContext = context;  
        }  
  
        public Builder setTitle(int title) {  
            this.mTitleText = (String) mContext.getText(title);  
            return this;  
        }  
  
        public Builder setTitle(CharSequence title) {  
            this.mTitleText = title;  
            return this;  
        }  
        
        public Builder setContent(int content){
        	this.mContentText = (String) mContext.getText(content);  
            return this;
        }
        
        public Builder setContent(CharSequence content) {  
            this.mContentText = content;  
            return this;  
        }
        public Builder setCancelable(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }
        
        public Builder setYesButton(CharSequence text,OnClickListener listener){
        	this.mYesText = text;
        	this.mYesBtnListener = listener;
        	return this;  
        }
        
        public Builder setNoButton(CharSequence text,OnClickListener listener){
        	this.mNoText = text;
        	this.mNoBtnListener = listener;
        	return this;  
        }
        
        @SuppressLint("InflateParams")
		@SuppressWarnings("deprecation")
		public ConfireDlg create() {  
            LayoutInflater inflater = (LayoutInflater) mContext  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final ConfireDlg dialog = new ConfireDlg(mContext, R.style.pop_dialog_choose);

            dialog.setCancelable(isCancel);
//            Window window = dialog.getWindow();
//            window.setGravity(Gravity.TOP);

            View layout = inflater.inflate(R.layout.pop_confirem, null);
            dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));  
            
            TextView titleTv = (TextView) layout.findViewById(R.id.tv_pathName);
            TextView contentTvc = (TextView) layout.findViewById(R.id.tv_content_center);
            TextView contentTvr = (TextView) layout.findViewById(R.id.tv_content_right);
            contentTvr.setVisibility(View.GONE);
            //可以滑动的textview 配合xml的属性
            contentTvc.setMovementMethod(ScrollingMovementMethod.getInstance());
            Button yesBtn = (Button) layout.findViewById(R.id.btn_yes);
            ImageView closeIv = (ImageView) layout.findViewById(R.id.iv_topbar_close);
    	    
            titleTv.setText(mTitleText);
            //titleTv.setGravity(Gravity.CENTER);
            if (mContentText == null || StringUtil.isEmpty(mContentText.toString()))
                contentTvc.setVisibility(View.GONE);
            else
                contentTvc.setText(mContentText);
            
            yesBtn.setText(mYesText);
            yesBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					if (mYesBtnListener != null) {
						mYesBtnListener.onClick(dialog, 0);
					}
				}
			});

            closeIv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (mNoBtnListener != null) {
						mNoBtnListener.onClick(dialog, 1);
					}
					dialog.dismiss();
				}
			});
            return dialog;  
        }  
        
        
    	public ConfireDlg create2() {  
            LayoutInflater inflater = (LayoutInflater) mContext  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final ConfireDlg dialog = new ConfireDlg(mContext, R.style.pop_dialog_choose);
//            Window window = dialog.getWindow();
//            window.setGravity(Gravity.TOP);
            dialog.setCancelable(isCancel);
            View layout = inflater.inflate(R.layout.pop_confirem, null);
            
            dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));  
            
            TextView titleTv = (TextView) layout.findViewById(R.id.tv_pathName);
            TextView contentTvc = (TextView) layout.findViewById(R.id.tv_content_center);
            TextView contentTvr = (TextView) layout.findViewById(R.id.tv_content_right);
            contentTvr.setVisibility(View.VISIBLE);
            contentTvc.setVisibility(View.GONE);
            
            //可以滑动的textview 配合xml的属性
            contentTvr.setMovementMethod(ScrollingMovementMethod.getInstance());
            Button yesBtn = (Button) layout.findViewById(R.id.btn_yes);
            ImageView closeIv = (ImageView) layout.findViewById(R.id.iv_topbar_close);
            closeIv.setVisibility(View.GONE);
            titleTv.setText(mTitleText);
            //titleTv.setGravity(Gravity.RIGHT);
            if (mContentText == null || StringUtil.isEmpty(mContentText.toString()))
                contentTvr.setVisibility(View.GONE);
            else
                contentTvr.setText(mContentText);
            
            yesBtn.setText(mYesText);
            yesBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					if (mYesBtnListener != null) {
						mYesBtnListener.onClick(dialog, 0);
					}
				}
			});

            closeIv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (mNoBtnListener != null) {
						mNoBtnListener.onClick(dialog, 1);
					}
					dialog.dismiss();
				}
			});
            return dialog;  
        }
        public ConfireDlg create3() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ConfireDlg dialog = new ConfireDlg(mContext, R.style.pop_dialog_choose);

            dialog.setCancelable(isCancel);
            View layout = inflater.inflate(R.layout.pop_confirem, null);

            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            TextView titleTv = (TextView) layout.findViewById(R.id.tv_pathName);
            TextView contentTvc = (TextView) layout.findViewById(R.id.tv_content_center);
            TextView contentTvr = (TextView) layout.findViewById(R.id.tv_content_right);
            contentTvr.setVisibility(View.VISIBLE);
            contentTvc.setVisibility(View.GONE);

            //可以滑动的textview 配合xml的属性
            contentTvr.setMovementMethod(ScrollingMovementMethod.getInstance());
            Button yesBtn = (Button) layout.findViewById(R.id.btn_yes);
            ImageView closeIv = (ImageView) layout.findViewById(R.id.iv_topbar_close);
          //  closeIv.setVisibility(View.GONE);
            titleTv.setText(mTitleText);
            //titleTv.setGravity(Gravity.RIGHT);
            if (mContentText == null || StringUtil.isEmpty(mContentText.toString()))
                contentTvr.setVisibility(View.GONE);
            else
                contentTvr.setText(mContentText);

            yesBtn.setText(mYesText);
            yesBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (mYesBtnListener != null) {
                        mYesBtnListener.onClick(dialog, 0);
                    }
                }
            });

            closeIv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (mNoBtnListener != null) {
                        mNoBtnListener.onClick(dialog, 1);
                    }
                    dialog.dismiss();
                }
            });
            return dialog;
        }
	}
}
