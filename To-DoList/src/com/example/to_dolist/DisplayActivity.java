package com.example.to_dolist;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		final Tasks tsk = (Tasks) getIntent().getExtras().getSerializable("tasks");
		final int key = getIntent().getExtras().getInt("key");
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		TextView tv5 = (TextView) findViewById(R.id.textView5);
		ImageButton im1 = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton im2 = (ImageButton) findViewById(R.id.imageButton2);

		if (tsk != null) {
			tv2.setText("Name : " + tsk.getName().toString());
			tv3.setText("Date : " + tsk.getDate().toString());
			tv4.setText("Time : " + tsk.getTime().toString());
			tv5.setText("Priority : " + tsk.getPriority().toString());
		}

		im1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayActivity.this,EditActivity.class);
				intent.putExtra("key", key);
				startActivity(intent);
				finish();
			}
		});

		im2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ListofTasks.taskList.remove(key);
				Intent intent = new Intent(DisplayActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
	
	public void onBackPressed() {
		finish();
        Intent setIntent = new Intent(DisplayActivity.this, MainActivity.class);
        startActivity(setIntent); 
        return;
    }   
}
