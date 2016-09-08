package com.graphiceditor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		Button photoEdit = (Button) findViewById(R.id.button1);
		photoEdit.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this, PhotoActivity.class));
			}
		});

		Button paintButton = (Button) findViewById(R.id.button2);
		paintButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				startActivity(new Intent(MenuActivity.this, MainActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
