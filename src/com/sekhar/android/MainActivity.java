package com.sekhar.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mkyong.android.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	final Context context = this;
	private Button settingsBtn = null;
	private Button accountMngBtn = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("Welcome to Account Management");
		settingsBtn = (Button) findViewById(R.id.settingsBtn);
		accountMngBtn = (Button) findViewById(R.id.accountMngBtn);

		settingsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ItemTypeList.class);
				startActivity(intent);
			}
		});

		accountMngBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, AccountList.class);
				startActivity(intent);
			}
		});
	}
}