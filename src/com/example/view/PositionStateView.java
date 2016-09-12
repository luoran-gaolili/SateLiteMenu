package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.util.RgkPositionState;

public class PositionStateView extends ViewGroup {
	public int mPositionState = RgkPositionState.POSITION_STATE_LEFT;

	public PositionStateView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PositionStateView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public PositionStateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setPositionState(int state) {
		this.mPositionState = state;
		invalidate();
	}

	// ÅÐ¶Ï»¬¶¯ÇøÓòÊÇ·ñÔÚÆÁÄ»×ó±ß
	public boolean isLeft() {

		return mPositionState == RgkPositionState.POSITION_STATE_LEFT;
	}

	// ÅÐ¶Ï»¬¶¯ÇøÓòÊÇ·ñÔÚÆÁÄ»ÓÒ±ß
	public boolean isRight() {
		return mPositionState == RgkPositionState.POSITION_STATE_RIGHT;
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}

	public int getPositionState() {
		return mPositionState;
	}
}
