package com.teplot.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teplot.testapp.R;


public class SingleChoiceListAdapter2 extends BaseAdapter {

	private HolderView holder;
    private int selectedIndex;
    private CharSequence[] dataArr;
    private int[] imgIds;
    protected LayoutInflater mInflater;
    protected Context mContext;

	public SingleChoiceListAdapter2(Context c, CharSequence[] dataArr,int[] imgIds) {
        this.dataArr = dataArr;
        this.imgIds = imgIds;
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
			convertView = mInflater.inflate(R.layout.pop_listview_item2, null);
			holder = new HolderView();
			holder.titleTv = (TextView)convertView.findViewById(R.id.tv_name);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(holder);
		}
		String title = dataArr[position].toString();
		if (title != null) {
            holder.titleTv.setText(title);
            holder.iv_img.setImageResource(imgIds[position]);
//            Drawable nav_up = mContext.getResources().getDrawable(imgIds[position]);
//            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
//            holder.titleTv.setCompoundDrawables(nav_up, null, null, null);

//            if (position == selectedIndex)
//                holder.checkRb.setChecked(true);
//            else
//                holder.checkRb.setChecked(false);
		}
		return convertView;
	}

	private class HolderView {
        TextView titleTv;
        ImageView iv_img;
	}
}
