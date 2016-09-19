package com.example.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;

public class RgkItemToolsInfo extends ItemInfo {
	/**
	 * 快捷开关的action
	 */
	public String mAction;

	public Bitmap mDefaultIcon;

	public boolean isChecked;

	public RgkItemToolsInfo() {
		mType = RgkUtilities.BaseColumns.ITEM_TYPE_SWITCH;
	}

	public RgkItemToolsInfo(RgkItemToolsInfo switchitem) {
		super(switchitem);
		mAction = switchitem.mAction;
		mDefaultIcon = switchitem.mDefaultIcon;
	}

	public int delete(Context context) {
		ContentResolver resolver = context.getContentResolver();
		return resolver.delete(RgkUtilities.Favorites.CONTENT_URI,
				RgkUtilities.BaseColumns.ITEM_ACTION + "=?",
				new String[] { mAction });
	}

	public int deletedAll(Context context) {
		ContentResolver resolver = context.getContentResolver();
		return resolver.delete(RgkUtilities.Favorites.CONTENT_URI,
				RgkUtilities.BaseColumns.ITEM_TYPE + "=?", new String[] { String
						.valueOf(RgkUtilities.BaseColumns.ITEM_TYPE_SWITCH) });
	}

	public void insert(Context context, int index) {
		ContentResolver resolver = context.getContentResolver();
		resolver.insert(RgkUtilities.Favorites.CONTENT_URI,
				assembleContentValues(context, index));
	}

	// 组装数据
	public ContentValues assembleContentValues(Context context, int index) {
		ContentValues values = new ContentValues();
		values.put(RgkUtilities.BaseColumns.ITEM_TITLE, mTitle.toString());
		values.put(RgkUtilities.BaseColumns.ITEM_INDEX, index);
		values.put(RgkUtilities.BaseColumns.ITEM_TYPE,
				RgkUtilities.BaseColumns.ITEM_TYPE_SWITCH);
		values.put(RgkUtilities.BaseColumns.ITEM_ACTION, mAction);
		return values;
	}

	// 插入数据
	public void bulkInsert(Context context, ContentValues values[]) {
		ContentResolver resolver = context.getContentResolver();
		resolver.bulkInsert(RgkUtilities.Favorites.CONTENT_URI, values);
	}
}
