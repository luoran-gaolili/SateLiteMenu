package com.example.view;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.satelitemenu.R;
import com.example.util.RgkItemToolsInfo;
import com.example.util.RgkToolsBean;

public class RgkSateLiteToolsDialog extends RgkSateLiteDialog implements
		View.OnClickListener {
	/**
	 * 内部的装item的
	 */
	private GridLayout mGridLayout;

	private ArrayList<RgkItemToolsInfo> mDatalist;

	private ArrayList<RgkItemToolsInfo> mFixedList;

	private ArrayList<RgkItemToolsInfo> mSelectedList;

	public RgkSateLiteToolsDialog(Context context) {
		this(context, null);
	}

	public RgkSateLiteToolsDialog(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RgkSateLiteToolsDialog(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mPositiveBtn.setOnClickListener(this);
		mNegativeBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == mPositiveBtn) {
			mOnDialogListener.onPositive(this);
		} else if (v == mNegativeBtn) {
			mOnDialogListener.onNegative(this);
		} else if (v instanceof GridLayoutItemView) {
			/**
			 * 从tag中拿到当前点击的是那一项，然后改变mFixedDataList的数据 刷新视图
			 */
			int index = (int) v.getTag();
			if (mFixedList.get(index).isChecked) {
				mFixedList.get(index).isChecked = false;
				refreshView();
			} else {
				if (getNewSelectList().size() < 9) {
					mFixedList.get(index).isChecked = true;
					refreshView();
				} else {

					Toast.makeText(
							getContext(),
							getResources().getString(R.string.favorite_up_to_9),
							0).show();
				}
			}
		}
	}

	@Override
	public View createContentView() {
		mGridLayout = new GridLayout(getContext());
		mGridLayout.setColumnCount(4);
		return mGridLayout;
	}

	/**
	 * 全部的数据
	 * 
	 * @param switches
	 */
	public void setGridData(ArrayList<RgkItemToolsInfo> switches) {
		
		Log.d("LUORANHA","switches:"+switches.size());
		mDatalist = new ArrayList<>();
		mDatalist.addAll(switches);
	}

	/**
	 * 已经选中的数据
	 * 
	 * @param switchs
	 */
	public void setSelectedData(ArrayList<RgkItemToolsInfo> switchs) {
		mSelectedList = new ArrayList<>();
		mSelectedList.addAll(switchs);
		refreshGrid();
	}

	/**
	 * 刷新GridLayout
	 */
	public void refreshGrid() {
		mGridLayout.removeAllViews();

		ArrayList<RgkItemToolsInfo> select = new ArrayList<>();
		ArrayList<RgkItemToolsInfo> normal = new ArrayList<>();

		if (mDatalist != null && mDatalist.size() > 0) {
			for (int i = 0; i < mDatalist.size(); i++) {
				if (contains(mSelectedList, mDatalist.get(i))) {
					select.add(mDatalist.get(i));
				} else {
					normal.add(mDatalist.get(i));
				}
			}
		}
		mFixedList = new ArrayList<>();
		/**
		 * 把select、normal的数据合并到mFixedList中
		 */
		merge(mSelectedList, true);
		merge(normal, false);
		/**
		 * 刷新界面
		 */
		refreshView();
	}

	/**
	 * 判定一个item是否在List中
	 * 
	 * @param switches
	 *            目标itemlist
	 * @param swipeSwitch
	 *            目标
	 * @return
	 */
	public boolean contains(ArrayList<RgkItemToolsInfo> switches,
			RgkItemToolsInfo swipeSwitch) {
		for (int i = 0; i < switches.size(); i++) {
			if (switches.get(i).mAction.equals(swipeSwitch.mAction)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 遍历list生成View
	 */
	public void refreshView() {
		mGridLayout.removeAllViews();
		for (int i = 0; i < mFixedList.size(); i++) {
			final GridLayoutItemView itemview = (GridLayoutItemView) LayoutInflater
					.from(getContext()).inflate(
							R.layout.rgk_grid_item_layout, null);
			itemview.setTag(i);
			RgkToolsBean.getInstance().initView(getContext(), itemview,
					mFixedList.get(i));
			itemview.setTitle(mFixedList.get(i).mTitle.toString());
			itemview.setOnClickListener(this);
			/*itemview.setItemIconBackground(getResources().getDrawable(
					R.drawable.angle_item_bg));*/
			itemview.setChecked(mFixedList.get(i).isChecked);
			mGridLayout.addView(itemview, new LinearLayout.LayoutParams(mSize,
					mSize));
		}
		mDialogTitle.setText(String.format(mTitleFormat,
				String.valueOf(getNewSelectList().size()), "8"));
	}

	/**
	 * 合并list
	 * 
	 * @param list
	 * @param checked
	 */
	public void merge(ArrayList<RgkItemToolsInfo> list, boolean checked) {
		for (int i = 0; i < list.size(); i++) {
			RgkItemToolsInfo item = list.get(i);
			item.isChecked = checked;
			mFixedList.add(item);
		}
	}

	/**
	 * 从FixedList中读取到选中的list
	 * 
	 * @return
	 */
	public ArrayList<RgkItemToolsInfo> getNewSelectList() {
		ArrayList<RgkItemToolsInfo> newlist = new ArrayList<>();
		for (int i = 0; i < mFixedList.size(); i++) {
			if (mFixedList.get(i).isChecked) {
				newlist.add(mFixedList.get(i));
			}
		}
		return newlist;
	}

	public boolean compare() {
		return compare(getContext(), mSelectedList, getNewSelectList());
	}

	public boolean compare(Context context, ArrayList<RgkItemToolsInfo> oldlist,
			ArrayList<RgkItemToolsInfo> newlist) {
		/**
		 * 长度相等的时候经一步比较，负责直接更新
		 */
		if (newlist.size() == oldlist.size()) {
			boolean bool = false;
			for (int i = 0; i < newlist.size(); i++) {
				if (!newlist.get(i).mAction.equals(oldlist.get(i).mAction)) {
					bool = true;
				}
			}
			if (bool) {
				// deleteList(context, oldlist);
				deleteListAll(context);
				addList(context, newlist);
				return true;
			}
		} else {
			// 替换
			// deleteList(context, oldlist);
			deleteListAll(context);
			addList(context, newlist);
			return true;
		}
		return false;
	}

	/**
	 * 删除一个ItemApp List
	 * 
	 * @param context
	 * @param oldlist
	 *            需要删除的list数据
	 */
	public void deleteList(Context context, ArrayList<RgkItemToolsInfo> oldlist) {
		for (int i = 0; i < oldlist.size(); i++) {
			oldlist.get(i).delete(context);
		}
	}

	public void deleteListAll(Context context) {
		new RgkItemToolsInfo().deletedAll(context);
	}

	/**
	 * 新增的ItemList数据
	 * 
	 * @param context
	 * @param newlist
	 */
	public void addList(Context context, ArrayList<RgkItemToolsInfo> newlist) {
		ContentValues contentValues[] = new ContentValues[newlist.size()];
		for (int i = 0; i < newlist.size(); i++) {
			contentValues[i] = newlist.get(i).assembleContentValues(context, i);
		}
		new RgkItemToolsInfo().bulkInsert(context, contentValues);
	}

}
