package com.teplot.testapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseListAdapter;
import com.teplot.testapp.been.details.FanYiData;

import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public class Test1FanYiListAdapter extends BaseListAdapter<FanYiData> {

	private HolderView holder;
	private int chooseItem = -1;

	public Test1FanYiListAdapter(Context c, ArrayList<FanYiData> arrayList) {
		super(c, arrayList);
		chooseItem = -1;
	}

	public void setChooseItem(int chooseItem ){
		this.chooseItem = chooseItem;
	};

	@SuppressLint("InflateParams")
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (HolderView) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.item_text_string,
					null);
			holder = new HolderView();

			holder.text_item_grid = (TextView) convertView
					.findViewById(R.id.text_item_grid);

			convertView.setTag(holder);
		}
		FanYiData data = getItem(position);
		if (data!=null) {
			holder.text_item_grid.setText("原文："+data.getSource_text()+"\n译文："+data.getTarget_text());
		}
		return convertView;
	}

	private class HolderView {
		TextView text_item_grid;
	}
}
