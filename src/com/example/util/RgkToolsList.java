package com.example.util;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.example.satelitemenu.R;


public class RgkToolsList {

    public ArrayList<RgkItemToolsInfo> mSwipeDataList;

    public String mSwipeActionArrAy[];

    public String mSwipeTitleArrAy[];

    public RgkToolsList(Context context) {
        mSwipeDataList = new ArrayList<>();
        mSwipeActionArrAy = context.getResources().getStringArray(R.array.rgk_tools_action_array);
        Log.d("LUORAN85","mSwipeActionArrAy:"+mSwipeActionArrAy.length);
        mSwipeTitleArrAy = context.getResources().getStringArray(R.array.rgk_tools_title_array);
        for (int i = 0; i < mSwipeActionArrAy.length; i++) {
            RgkItemToolsInfo itemswitch = new RgkItemToolsInfo();
            itemswitch.mTitle = mSwipeTitleArrAy[i];
            itemswitch.mAction = mSwipeActionArrAy[i];
            mSwipeDataList.add(itemswitch);
        }
    }
}
