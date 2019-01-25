package com.teplot.testapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseListAdapter;
import com.teplot.testapp.been.details.ImgData;

import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public class Test6ImgAdapter extends BaseListAdapter<ImgData> {

	private HolderView holder;

	public Test6ImgAdapter(Context c, ArrayList<ImgData> arrayList) {
		super(c, arrayList);

	}
	@SuppressLint("InflateParams")
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (HolderView) convertView.getTag();

		} else {
			convertView = mInflater.inflate(R.layout.item_test6_img_list,
					null);
			holder = new HolderView();

			holder.imgIv = (ImageView)convertView
					.findViewById(R.id.set_badge_img);
			holder.set_badge_tv = (TextView) convertView
					.findViewById(R.id.set_badge_tv);
			convertView.setTag(holder);
		}

		ImgData dataInfo = getItem(position);
		if (dataInfo != null) {
			//holder.imgIv.setImageResource(dataInfo.getImgId());
			holder.imgIv.setImageBitmap(dataInfo.getBitmap());

			holder.set_badge_tv.setText(dataInfo.getTitle());

			holder.imgIv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemDetailListener.onClick(position);
				}
			});
		}
		return convertView;
	}

	/**
	 //	 * 详情界面的监听接口
	 //	 */
	public interface onItemDetailListener {
		void onClick(int i);
	}

	private onItemDetailListener mOnItemDetailListener;

	public void setOnItemDetailClickListener(onItemDetailListener mOnItemDetailListener) {
		this.mOnItemDetailListener = mOnItemDetailListener;
	}
	private class HolderView {
		ImageView imgIv ;
		TextView set_badge_tv ;
	}
}
