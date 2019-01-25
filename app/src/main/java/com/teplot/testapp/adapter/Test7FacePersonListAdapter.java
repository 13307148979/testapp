package com.teplot.testapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teplot.testapp.R;
import com.teplot.testapp.apps.AppContext;
import com.teplot.testapp.apps.AppData;
import com.teplot.testapp.base.BaseListAdapter;
import com.teplot.testapp.been.details.FacePersonData;
import com.teplot.testapp.been.details.XianLiaoData;
import com.teplot.testapp.utils.FileUtil;
import com.teplot.testapp.utils.ImageUtil;
import com.teplot.testapp.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;


/**
 * @author Administrator
 *
 */
public class Test7FacePersonListAdapter extends BaseListAdapter<String> {

	private HolderView holder;
	private int chooseItem = -1;

	public Test7FacePersonListAdapter(Context c, ArrayList<String> arrayList) {
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
			convertView = mInflater.inflate(R.layout.item_test7_face_person_list,
					null);
			holder = new HolderView();

			holder.set_badge_img = (ImageView) convertView
					.findViewById(R.id.set_badge_img);
			holder.name_tv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.complain_tv = (TextView) convertView
					.findViewById(R.id.complain_tv);
			convertView.setTag(holder);
		}
		String data = getItem(position);
		if (data!=null) {
			FacePersonData facePersonData= AppContext.getInstance().getFacePersonMsg(data);
			String name = facePersonData.getPerson_name();
			String complain = facePersonData.getTag();
			String fileName = StringUtil.getAgeFaceImgFile(facePersonData.getImage_list());

			holder.name_tv.setText(name);
			holder.complain_tv.setText(complain);


			if (!StringUtil.isEmpty(fileName)){

				File file = FileUtil.getFile2(mContext, AppData.PICTURE_PATH,fileName);
				Bitmap bitmap = ImageUtil.getLoacalBitmap(file.getAbsolutePath());
				System.out.println("Test7FacePersonListAdapter-----data---"+data+"--->imgs===="+file.getAbsolutePath());
				holder.set_badge_img.setImageBitmap(bitmap);
			}else {
				holder.set_badge_img.setImageResource(R.drawable.backgroud_nodata);
			}

		}
		return convertView;
	}

	private class HolderView {
		ImageView set_badge_img;
		TextView name_tv,complain_tv;
	}
}
