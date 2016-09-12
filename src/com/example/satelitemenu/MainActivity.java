package com.example.satelitemenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.service.RgkSateLiteService;

public class MainActivity extends Activity implements OnClickListener {

	private Button startButton, closeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startButton = (Button) findViewById(R.id.start_button);
		closeButton = (Button) findViewById(R.id.close_button);
		startButton.setOnClickListener(this);
		closeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, RgkSateLiteService.class);
		switch (arg0.getId()) {
		case R.id.start_button:
			// ø™∆ÙŒ¿–«≤Àµ•
			startService(intent);

			break;
		case R.id.close_button:
			// πÿ±’Œ¿–«≤Àµ•
			stopService(intent);
			break;
		default:
			break;
		}

	}

}
