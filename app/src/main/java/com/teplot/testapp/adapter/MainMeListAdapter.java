package com.teplot.testapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseListAdapter;
import com.teplot.testapp.been.details.MainMeData;
import com.teplot.testapp.utils.Utils;

import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public class MainMeListAdapter extends BaseListAdapter<MainMeData> {

	private HolderView holder;
	private int chooseItem = -1;

	public MainMeListAdapter(Context c, ArrayList<MainMeData> arrayList) {
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
			convertView = mInflater.inflate(R.layout.item_main_me,
					null);
			holder = new HolderView();

			holder.addressName = (TextView) convertView
					.findViewById(R.id.address_name);
			holder.send_img = (ImageView) convertView
					.findViewById(R.id.send_img);

			convertView.setTag(holder);
		}
		MainMeData data = getItem(position);
		if (data!=null) {
			holder.addressName.setText(data.getTitle());

			if (position == chooseItem){
				holder.send_img.setImageResource(data.getImgId());
			}else {
				holder.send_img.setImageResource(data.getImgId2());
			}
		}
		return convertView;
	}

	private class HolderView {
		TextView addressName;
		ImageView send_img;
	}
}
