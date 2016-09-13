package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;


public class ItemInfo {

    /**
     * item类型
     */
    int mType;
    /**
     * Item的名字
     */
    public CharSequence mTitle;

    /**
     * Item的顺序索引
     */
    int mIndex;


    public ItemInfo() {

    }

    ItemInfo(ItemInfo info) {
        mType = info.mType;
        mTitle = info.mTitle;
        mIndex = info.mIndex;
    }

    static String getPackageName(Intent intent) {
        if (intent != null) {
            String packageName = intent.getPackage();
            if (packageName == null && intent.getComponent() != null) {
                return intent.getComponent().getPackageName();
            }
            if (packageName != null) {
                return packageName;
            }
        }
        return "";
    }

    void onAddToDatabase(ContentValues values) {
        values.put(RgkItemSettings.BaseColumns.ITEM_TYPE, mType);
        values.put(RgkItemSettings.BaseColumns.ITEM_TITLE, mTitle.toString());
        values.put(RgkItemSettings.BaseColumns.ITEM_INDEX, mIndex);
    }

    /**
     * 把图片保存到sqlite
     *
     * @param bitmap
     * @return
     */
    static byte[] flattenBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * bitmap转换成二进制然后放在数据库
     *
     * @param values
     * @param bitmap
     */
    static void writeBitmap(ContentValues values, Bitmap bitmap) {
        if (bitmap != null) {
            byte[] data = flattenBitmap(bitmap);
            values.put(RgkItemSettings.BaseColumns.ITEM_ICON, data);
        }
    }
}
