package com.teplot.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.teplot.testapp.R;


public class SingleChoiceListAdapter extends BaseAdapter {

	private HolderView holder;
    private int selectedIndex;
    private CharSequence[] dataArr;
    protected LayoutInflater mInflater;
    protected Context mContext;

	public SingleChoiceListAdapter(Context c, CharSequence[] dataArr) {
        this.dataArr = dataArr;
        mContext = c;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedIndex = -1;
	}

    public int getCount() {
        return dataArr == null ? 0 : dataArr.length;
    }

    public Object getItem(int position) {
        return dataArr == null ? null : dataArr[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (HolderView) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.pop_listview_item, null);
			holder = new HolderView();
			holder.titleTv = (TextView)convertView.findViewById(R.id.tv_name);
            holder.checkRb = (RadioButton)convertView.findViewById(R.id.rb_check);
            convertView.setTag(holder);
		}
		String title = dataArr[position].toString();
		if (title != null) {
            holder.titleTv.setText(title);
            if (position == selectedIndex)
                holder.checkRb.setChecked(true);
            else
                holder.checkRb.setChecked(false);
		}
		return convertView;
	}

	private class HolderView {
        TextView titleTv;
        RadioButton checkRb;
	}
}
