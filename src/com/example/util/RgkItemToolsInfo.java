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
		mType = RgkItemSettings.BaseColumns.ITEM_TYPE_SWITCH;
	}

	public RgkItemToolsInfo(RgkItemToolsInfo switchitem) {
		super(switchitem);
		mAction = switchitem.mAction;
		mDefaultIcon = switchitem.mDefaultIcon;
	}

	public int delete(Context context) {
		ContentResolver resolver = context.getContentResolver();
		return resolver.delete(RgkItemSettings.Favorites.CONTENT_URI,
				RgkItemSettings.BaseColumns.ITEM_ACTION + "=?",
				new String[] { mAction });
	}

	public int deletedAll(Context context) {
		ContentResolver resolver = context.getContentResolver();
		return resolver.delete(Utilities.Favorites.CONTENT_URI,
				Utilities.BaseColumns.ITEM_TYPE + "=?", new String[] { String
						.valueOf(Utilities.BaseColumns.ITEM_TYPE_SWITCH) });
	}

	public void insert(Context context, int index) {
		ContentResolver resolver = context.getContentResolver();
		resolver.insert(Utilities.Favorites.CONTENT_URI,
				assembleContentValues(context, index));
	}

	public ContentValues assembleContentValues(Context context, int index) {
		ContentValues values = new ContentValues();
		values.put(Utilities.BaseColumns.ITEM_TITLE, mTitle.toString());
		values.put(Utilities.BaseColumns.ITEM_INDEX, index);
		values.put(Utilities.BaseColumns.ITEM_TYPE,
				Utilities.BaseColumns.ITEM_TYPE_SWITCH);
		values.put(Utilities.BaseColumns.ITEM_ACTION, mAction);
		return values;
	}

	public void bulkInsert(Context context, ContentValues values[]) {
		ContentResolver resolver = context.getContentResolver();
		resolver.bulkInsert(Utilities.Favorites.CONTENT_URI, values);
	}
}
