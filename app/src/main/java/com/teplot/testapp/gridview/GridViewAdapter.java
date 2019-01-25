package com.teplot.testapp.gridview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.teplot.testapp.R;
import com.teplot.testapp.base.BaseListAdapter;
import com.teplot.testapp.utils.FileUtil;
import com.teplot.testapp.utils.Utils;

import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public class GridViewAdapter extends BaseListAdapter<GridViewItem> {

	private HolderView holder;

	public GridViewAdapter(Context c, ArrayList<GridViewItem> arrayList) {
		super(c, arrayList);

	}
	@SuppressLint("InflateParams")
	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (HolderView) convertView.getTag();

		} else {
			convertView = mInflater.inflate(R.layout.item_test76_face_add_gv,
					null);
			holder = new HolderView();

			holder.imgIv = (ImageView)convertView
					.findViewById(R.id.set_gridimage);
			holder.cancelImg = (ImageView)convertView
					.findViewById(R.id.set_badge_iv);

			convertView.setTag(holder);
		}
		final GridViewItem dataInfo = getItem(position);
		int num  =  getCount();
		if (dataInfo != null) {
			//System.out.println("position=num==>"+position+"==>"+num);

			if (position < (num-1)&& !dataInfo.getId().equals("0")){
				if (dataInfo.getBitmap()!=null){
					holder.imgIv.setTag(position+"bitmap");
					holder.imgIv.setImageBitmap(dataInfo.getBitmap());
				}else {
					if (dataInfo.getImgUri()!=null){
						String type  = dataInfo.getType();
						if (type.equals("1")){
							holder.imgIv.setTag(position+dataInfo.getImgUri().toString());
							//holder.imgIv.setImageResource(R.drawable.vidio_test);
							Utils.loadImageBig2(dataInfo.getImgUri(),holder.imgIv,400);
						}else {
							holder.imgIv.setTag(position+"vidio_test");
							holder.imgIv.setImageResource(R.drawable.vidio_test);
						}
					}else{
						holder.imgIv.setTag(position+dataInfo.getImgId());
						holder.imgIv.setImageResource(dataInfo.getImgId());
					}
				}
				holder.cancelImg.setVisibility(View.VISIBLE);
			}else {
				holder.imgIv.setTag(position+dataInfo.getImgId());
				holder.imgIv.setImageResource(dataInfo.getImgId());
				holder.cancelImg.setVisibility(View.GONE);
			}
			holder.cancelImg.setImageResource(dataInfo.getCancelId());
			holder.cancelImg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Toast.makeText(mContext,"取消图片" + position+"文件地址为==》"+dataInfo.getFilePath(),Toast.LENGTH_SHORT).show();
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
		ImageView cancelImg,imgIv ;
	}
}
