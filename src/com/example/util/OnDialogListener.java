package com.example.util;

import android.view.View;

//弹出框的确定与取消按钮
public interface OnDialogListener {

	void onPositive(View view);

	void onNegative(View view);
}
