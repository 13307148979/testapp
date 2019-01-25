package com.teplot.testapp.common.date;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.utils.DateTimeUtil;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePickerDlg extends Dialog{

    public static final int MODE_DATE_YMD_HM = 0;
	public static final int MODE_DATE_YMD = 1;
    public static final int MODE_DATE_HM = 2;
    public static final int MODE_DATE_Y = 3;
	//public static final int MIN_YEAR = 1970;
    public static final int MIN_YEAR = 2016;
	public static final int MAX_YEAR = 2030;
	
	public DatePickerDlg(Context context, boolean cancelable,  
            OnCancelListener cancelListener) {  
        super(context, cancelable, cancelListener);  
    }  
  
    public DatePickerDlg(Context context, int theme) {  
        super(context, theme);  
    }  
  
    public DatePickerDlg(Context context) {  
        super(context);  
    }
    
    public static class Builder {  
    	
    	private int mMode;
    	private Calendar mCalendar;
        private Context mContext; 
        private CharSequence mTitleText;  			
        private CharSequence mYesText;
        private String mInitTime;
        
        private WheelView mYearWv;
    	private WheelView mMonWv;
    	private WheelView mDayWv;
    	private WheelView mHourWv;
    	private WheelView mMinWv;
    	private NumericWheelAdapter mYearAdapter;
    	private NumericWheelAdapter mMonAdapter;
    	private NumericWheelAdapter mDayAdapter;
    	private NumericWheelAdapter mHourAdapter;
    	private NumericWheelAdapter mMinAdapter;
    	
    	private DatPickerListener mYesOnClickListener;
    	private OnClickListener mNoOnClickListener;
  
        public Builder(Context context) {  
            this.mContext = context;  
        }  
  
        public Builder setShowMode (int mode) {
        	this.mMode = mode;
            return this;
        }
        
        public Builder setTitle(int title) {  
            this.mTitleText = (String) mContext.getText(title);  
            return this;  
        }  
        
        public Builder setTitle(CharSequence title) {  
            this.mTitleText = title;  
            return this;  
        }
        
        public Builder setSelectDataTime(String data){
        	this.mInitTime = data;
        	return this;  
        }
        
        public String getSelectDataTime(){
            String time = "";
            SimpleDateFormat formatter;
            ParsePosition pos = new ParsePosition(0);
        	if (mMode == MODE_DATE_YMD_HM) {
                time = (mYearWv.getCurrentItem() + MIN_YEAR) + "-" + (mMonWv.getCurrentItem() + 1) + "-" + (mDayWv.getCurrentItem() + 1) + " " + mHourWv.getCurrentItem() + ":" + mMinWv.getCurrentItem() ;
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            } else if (mMode == MODE_DATE_YMD){
                time = (mYearWv.getCurrentItem() + MIN_YEAR) + "-" + (mMonWv.getCurrentItem() + 1) + "-" + (mDayWv.getCurrentItem() + 1) ;
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            } else if (mMode == MODE_DATE_Y){
                time = (mYearWv.getCurrentItem() + MIN_YEAR)+""; //+ "-" + (mMonWv.getCurrentItem() + 1) + "-" + (mDayWv.getCurrentItem() + 1) ;
                formatter = new SimpleDateFormat("yyyy");
            }else {
                time = mHourWv.getCurrentItem() + ":" + mMinWv.getCurrentItem()  ;
                formatter = new SimpleDateFormat("HH:mm");
            }
            return formatter.format(formatter.parse(time,pos));
        }

        public Builder setYesButton(CharSequence text, DatPickerListener listener) {
        	this.mYesText = text;
        	this.mYesOnClickListener = listener;
        	return this;
        }
        
        public Builder setNoButton(CharSequence text, OnClickListener listener) {
        	this.mNoOnClickListener = listener;
        	return this;
        }
        
        @SuppressWarnings("deprecation")
		public DatePickerDlg create() {  
            LayoutInflater inflater = (LayoutInflater) mContext  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            final DatePickerDlg dialog = new DatePickerDlg(  
            		mContext, R.style.pop_dialog_choose);

            mCalendar = Calendar.getInstance();
            if (mInitTime == null || mInitTime.length() == 0) {
                mCalendar.setTimeInMillis(System.currentTimeMillis());
            } else {
                try {
                    Date date = null;
                    switch (mMode) {
                        case MODE_DATE_Y:
                            date = new SimpleDateFormat("yyyy").parse(mInitTime);
                            break;
                        case MODE_DATE_YMD_HM:
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mInitTime);
                            break;
                        case MODE_DATE_YMD:
                            date = new SimpleDateFormat("yyyy-MM-dd").parse(mInitTime);
                            break;
                        case MODE_DATE_HM:
                            date = new SimpleDateFormat("HH:mm").parse(mInitTime);
                            break;
                    }
                    mCalendar.setTimeInMillis(date.getTime());
                } catch (Exception e) {
                    mCalendar.setTimeInMillis(System.currentTimeMillis());
                }
            }

//            Window window = dialog.getWindow();
//            window.setGravity(Gravity.TOP);

            dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);

            View layout = inflater.inflate(R.layout.pop_confirem_date, null);  
//            TextView titleTv = (TextView)layout.findViewById(R.id.tv_pathName);
//            titleTv.setText(mTitleText);
            TextView yesBtn = (TextView) layout.findViewById(R.id.yes_tv);
            TextView closeBtn = (TextView) layout.findViewById(R.id.close_tv);
            LinearLayout contentLl = (LinearLayout) layout.findViewById(R.id.ll_content);
    	    View contentView = null;

            switch (mMode) {
                case MODE_DATE_YMD:
                    contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_datepicker_ymd, null);
                case MODE_DATE_YMD_HM:
                    if (mMode == MODE_DATE_YMD_HM)
                        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_datepicker_ymd_hm, null);
                    mYearWv = (WheelView) contentView.findViewById(R.id.year);
                    mMonWv = (WheelView) contentView.findViewById(R.id.month);
                    mDayWv = (WheelView) contentView.findViewById(R.id.day);

                    mYearAdapter = new NumericWheelAdapter(MIN_YEAR,MAX_YEAR);//DateTimeUtil.getNowYearDate("yyyy")
                    mYearWv.setAdapter(mYearAdapter);
                    mYearWv.setCurrentItem(mCalendar.get(Calendar.YEAR) - MIN_YEAR);
                    //mYearWv.setLabel("年");
                    mYearWv.addChangingListener(new OnWheelChangedListener() {

                        @Override
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            int days = DateTimeUtil.getLastDayOfMonth(newValue + MIN_YEAR, mMonWv.getCurrentItem() + 1);
                            int oldDay = mDayWv.getCurrentItem();
                            mDayAdapter = new NumericWheelAdapter(1,days);
                            mDayWv.setAdapter(mDayAdapter);
                            if (oldDay > (days - 1))
                                mDayWv.setCurrentItem(days - 1);
                            else
                                mDayWv.setCurrentItem(oldDay);
                        }
                    });
                    mMonAdapter = new NumericWheelAdapter(1,12);
                    mMonWv.setAdapter(mMonAdapter);
                    mMonWv.setCurrentItem(mCalendar.get(Calendar.MONTH));
                  //  mMonWv.setLabel("月");
                    mMonWv.addChangingListener(new OnWheelChangedListener() {

                        @Override
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            int days = DateTimeUtil.getLastDayOfMonth(mYearWv.getCurrentItem() + MIN_YEAR, newValue + 1);
                            int oldDay = mDayWv.getCurrentItem();
                            mDayAdapter = new NumericWheelAdapter(1,days);

                            mDayWv.setAdapter(mDayAdapter);
                            if (oldDay > (days - 1))
                                mDayWv.setCurrentItem(days - 1);
                            else
                                mDayWv.setCurrentItem(oldDay);
                        }
                    });

                    mDayAdapter = new NumericWheelAdapter(1,
                            DateTimeUtil.getLastDayOfMonth(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1));
                    mDayWv.setAdapter(mDayAdapter);
                    mDayWv.setCurrentItem(mCalendar.get(Calendar.DAY_OF_MONTH) - 1);
                   // mDayWv.setLabel("日");
                    if (mMode == MODE_DATE_YMD)
                        break;
                case MODE_DATE_HM:
                    if (mMode == MODE_DATE_HM)
                        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_datepicker_hm, null);
                    mHourWv = (WheelView) contentView.findViewById(R.id.hour);
                    mMinWv = (WheelView) contentView.findViewById(R.id.mins);

                    mHourAdapter = new NumericWheelAdapter(0,23);
                    mHourWv.setAdapter(mHourAdapter);
                    mHourWv.setCurrentItem(mCalendar.get(Calendar.HOUR_OF_DAY));
                    mHourWv.setLabel("时");

                    mMinAdapter = new NumericWheelAdapter(0,59);
                    mMinWv.setAdapter(mMinAdapter);
                    mMinWv.setCurrentItem(mCalendar.get(Calendar.MINUTE));
                    mMinWv.setLabel("分");
                    break;
                case MODE_DATE_Y:
                    if (mMode == MODE_DATE_Y)
                        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_datepicker_y, null);
                    mYearWv = (WheelView) contentView.findViewById(R.id.year);
                    mYearAdapter = new NumericWheelAdapter(MIN_YEAR,MAX_YEAR);//DateTimeUtil.getNowYearDate("yyyy")
                    mYearWv.setAdapter(mYearAdapter);
                    mYearWv.setCurrentItem(mCalendar.get(Calendar.YEAR) - MIN_YEAR);
                    mYearWv.setLabel("年");
                    break;
            }
    		
    		yesBtn.setText(mYesText);
    		yesBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (mYesOnClickListener != null) {
						mYesOnClickListener.onSelected(dialog, getSelectDataTime());
					}
				}
			});
    		
    		closeBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (mNoOnClickListener != null) {
						mNoOnClickListener.onClick(dialog, 0);
					}
					dialog.dismiss();
				}
			});

            contentLl.addView(contentView);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            window.setWindowAnimations(R.style.DialogAnimation); // 添加动画
            WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = 0; // 新位置Y坐标
            lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels; // 宽度
            layout.measure(0, 0);
            lp.height = layout.getMeasuredHeight();
            lp.alpha = 9f; // 透明度
            window.setAttributes(lp);
            return dialog;  
        }    
	}
    
    public interface DatPickerListener{
    	 public abstract void onSelected(DialogInterface dlg, String date);
    }
    
}
