package com.teplot.testapp.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.adapter.SingleChoiceListAdapter;
import com.teplot.testapp.adapter.SingleChoiceListAdapter2;


public class SingleChoiceListDlg extends Dialog{

	public SingleChoiceListDlg(Context context, boolean cancelable,  
            OnCancelListener cancelListener) {  
        super(context, cancelable, cancelListener);  
    }  
  
    public SingleChoiceListDlg(Context context, int theme) {  
        super(context, theme);  
    }  
  
    public SingleChoiceListDlg(Context context) {  
        super(context);  
    }  
	
	public static class Builder {  
		
        private Context mContext;  
        private CharSequence mTitleText;  			
        private CharSequence[] mListItem;
        private int[] imgList;
        private int mClickedDialogEntryIndex;  
        private OnClickListener mOnClickListener;
        private OnClickListener mNoBtnListener;
  
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
  
        public CharSequence[] getItems() {  
            return mListItem;  
        }  
  
        public Builder setItems(CharSequence[] mListItem) {  
            this.mListItem = mListItem;  
            return this;  
        }  
          
        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener) {
            this.mListItem = items;  
            this.mOnClickListener = listener;  
            this.mClickedDialogEntryIndex = checkedItem;  
            return this;  
         }

        public Builder setSingleChoiceItems(CharSequence[] items,int[] imgList, final OnClickListener listener) {
            this.mListItem = items;
            this.imgList = imgList;
            this.mOnClickListener = listener;
            return this;
        }
        public Builder setNoButton(OnClickListener listener){
            this.mNoBtnListener = listener;
            return this;
        }
        public SingleChoiceListDlg create() {
            LayoutInflater inflater = (LayoutInflater) mContext  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final SingleChoiceListDlg dialog = new SingleChoiceListDlg(  
            		mContext, R.style.pop_dialog_choose);

//            Window window = dialog.getWindow();
//            window.setGravity(Gravity.TOP);

            View layout = inflater.inflate(R.layout.pop_listview,
                    null);  
            dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            if(mListItem == null){
                throw new RuntimeException("Entries should not be empty");  
            }

            ListView lvListItem = (ListView) layout.findViewById(R.id.listview);
            SingleChoiceListAdapter adapter = new SingleChoiceListAdapter(mContext, mListItem);
            adapter.setSelectedIndex(mClickedDialogEntryIndex);
            lvListItem.setAdapter(adapter);
            lvListItem.setOnItemClickListener(new OnItemClickListener(){  

                @Override  
                public void onItemClick(AdapterView<?> parent, View view,  
                        int position, long id) {  
                    mOnClickListener.onClick(dialog, position);  
                }  
            });  
            
//            lvListItem.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//            lvListItem.setItemChecked(mClickedDialogEntryIndex, true);
//            lvListItem.setSelection(mClickedDialogEntryIndex);

            TextView titleTv = (TextView)layout.findViewById(R.id.title_bumen);
            titleTv.setText(mTitleText);

//            TextView titleTv = (TextView)layout.findViewById(R.id.tv_pathName);
//            titleTv.setText(mTitleText);
//
//            ImageView closeBtn = (ImageView) layout.findViewById(R.id.iv_topbar_close);
//            closeBtn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//                    dialog.dismiss();
//                }
//            });

            return dialog;  
        }

        public SingleChoiceListDlg create2() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final SingleChoiceListDlg dialog = new SingleChoiceListDlg(
                    mContext, R.style.pop_dialog_choose);
            dialog.setCancelable(false);
//            Window window = dialog.getWindow();
//            window.setGravity(Gravity.TOP);

            View layout = inflater.inflate(R.layout.pop_listview2,
                    null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            if(mListItem == null){
                throw new RuntimeException("Entries should not be empty");
            }

            ListView lvListItem = (ListView) layout.findViewById(R.id.listview);
            RelativeLayout rl_topbar = (RelativeLayout)layout.findViewById(R.id.zl_headerBar);
            rl_topbar.setVisibility(View.GONE);
            SingleChoiceListAdapter2 adapter = new SingleChoiceListAdapter2(mContext, mListItem,imgList);
           // adapter.setSelectedIndex(mClickedDialogEntryIndex);
            lvListItem.setAdapter(adapter);
            lvListItem.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    mOnClickListener.onClick(dialog, position);
                }
            });

            TextView titleTv = (TextView)layout.findViewById(R.id.title_bumen);
            titleTv.setText(mTitleText);

//            lvListItem.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//            lvListItem.setItemChecked(mClickedDialogEntryIndex, true);
//            lvListItem.setSelection(mClickedDialogEntryIndex);

//            TextView titleTv = (TextView)layout.findViewById(R.id.tv_pathName);
//            titleTv.setText(mTitleText);
//
            LinearLayout closeBtn = (LinearLayout) layout.findViewById(R.id.zl_topright);
            closeBtn.setOnClickListener(new View.OnClickListener() {

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
