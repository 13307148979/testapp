package com.teplot.testapp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import java.util.List;

/**
 * List适配器基本父类
 * 
 * @author Administrator
 *
 * @param <M>
 */
public abstract class BaseListAdapter<M> extends BaseAdapter {

	protected List<M> list;

	protected Context mContext;

	protected LayoutInflater mInflater;

	protected MyOnCheckedChangeListener mMyOnCheckedChangeListener;

	public List<M> getListData() {
		return list;
	}

	public void setItem(int item, M itemData) {
		list.set(item, itemData);
		notifyDataSetChanged();
	}

	public void setAllItem(List<M> list) {
		if (list == null || list.size() == 0)
			return;
		this.list = list;
		notifyDataSetChanged();
	}

	public void addItem(M m) {
		this.list.add(m);
		notifyDataSetChanged();
	}
	public void addItem(M m ,int item) {
		this.list.add(item,m);
		notifyDataSetChanged();
	}
	public void addAllItem(List<M> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public int addAllItemToFirst(List<M> list) {
		if (list == null || list.size() == 0)
			return 0;
		int size = list.size();
		list.addAll(this.list);
		this.list = list;
		notifyDataSetChanged();
		return size;
	}

	public void removeItem(int position) {
		this.list.remove(position);
		notifyDataSetChanged();
	}

	public void removeAllItem() {
		this.list.clear();
		notifyDataSetChanged();
	}

	public BaseListAdapter(Context context, List<M> list) {
		super();
		this.mContext = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public M getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public long getItemId(M m) {
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).equals(m))
				return i;
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = bindView(position, convertView, parent);
		return convertView;
	}

	public abstract View bindView(int position, View convertView,
			ViewGroup parent);

	public void setMyOnCheckedChangeListener(
			MyOnCheckedChangeListener mMyOnCheckedChangeListener) {
		this.mMyOnCheckedChangeListener = mMyOnCheckedChangeListener;
	}

	public interface MyOnCheckedChangeListener {
		public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked, int position);
	}
}
