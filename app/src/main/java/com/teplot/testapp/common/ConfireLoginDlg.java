package com.teplot.testapp.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.utils.StringUtil;


public class ConfireLoginDlg extends Dialog{

	public ConfireLoginDlg(Context context, boolean cancelable,
                           OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ConfireLoginDlg(Context context, int theme) {
        super(context, theme);
    }

    public ConfireLoginDlg(Context context) {
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
        private boolean isCancel;


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

        public Builder setContent(int content) {
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

        public Builder setYesButton(CharSequence text, OnClickListener listener) {
            this.mYesText = text;
            this.mYesBtnListener = listener;
            return this;
        }

        public Builder setNoButton(CharSequence text, OnClickListener listener) {
            this.mNoText = text;
            this.mNoBtnListener = listener;
            return this;
        }

        @SuppressLint("InflateParams")
        @SuppressWarnings("deprecation")
        public ConfireLoginDlg create() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ConfireLoginDlg dialog = new ConfireLoginDlg(mContext, R.style.pop_dialog_choose);

            dialog.setCancelable(isCancel);
//            Window window = dialog.getWindow();
//            window.setGravity(Gravity.TOP);

            View layout = inflater.inflate(R.layout.pop_confirem_login, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            TextView titleTv = (TextView) layout.findViewById(R.id.tv_content_title);
            TextView contentTvc = (TextView) layout.findViewById(R.id.tv_content_center);


            //可以滑动的textview 配合xml的属性
            contentTvc.setMovementMethod(ScrollingMovementMethod.getInstance());
            Button yesBtn = (Button) layout.findViewById(R.id.btn_yes);
            Button noBtn = (Button) layout.findViewById(R.id.btn_no);

            titleTv.setText(mTitleText);
            //titleTv.setGravity(Gravity.CENTER);
            if (mContentText == null || StringUtil.isEmpty(mContentText.toString()))
                contentTvc.setVisibility(View.GONE);
            else
                contentTvc.setText(mContentText);

            yesBtn.setText(mYesText);
            noBtn.setText(mNoText);
            yesBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (mYesBtnListener != null) {
                        mYesBtnListener.onClick(dialog, 0);
                    }
                }
            });

            noBtn.setOnClickListener(new View.OnClickListener() {

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
