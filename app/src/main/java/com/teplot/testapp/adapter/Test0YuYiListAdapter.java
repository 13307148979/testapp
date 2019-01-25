package com.teplot.testapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseListAdapter;
import com.teplot.testapp.been.details.FanYiData;
import com.teplot.testapp.been.details.YuYIData;
import com.teplot.testapp.utils.StringUtil;

import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public class Test0YuYiListAdapter extends BaseListAdapter<YuYIData> {

	private HolderView holder;
	private int chooseItem = -1;

	public Test0YuYiListAdapter(Context c, ArrayList<YuYIData> arrayList) {
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
		YuYIData data = getItem(position);
		if (data!=null) {
			int  com_type = data.getCom_type();
			String com = StringUtil.getTypeComTokensString(com_type);
			holder.text_item_grid.setText("单词："+data.getCom_word()+"\n成分描述："+com);
		}
		return convertView;
	}

	private class HolderView {
		TextView text_item_grid;
	}
}
