package com.example.to_dolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
public class MainActivity extends Activity {

	TextView tv1;
	ImageButton i1;
	public static MainActivity main;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout l = (LinearLayout) findViewById(R.id.LinearLayout1);
		i1 = new ImageButton(this);
		tv1 = new TextView(this);
		int NoofTask = ListofTasks.taskList.size();
		tv1.setText(NoofTask + " TASKS");
		l.addView(tv1);

		i1.setImageResource(R.drawable.ic_add);
		l.addView(i1);

		i1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CreateActivity.class);
				startActivity(i);
				finish();
			}
		});

		for (final Tasks t : ListofTasks.taskList) {
			
			TableRow tr = new TableRow(this);
			final TextView tv = new TextView(this);
			tv.setText(t.getName() + "\n" + t.getDate() + "\n" + t.getTime());
			tr.addView(tv);
			tr.setClickable(true);
			l.addView(tr);
		
			tr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("demo", tv.getText().toString());
					Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
					intent.putExtra("tasks", t);
					intent.putExtra("key", ListofTasks.taskList.indexOf(t));
					startActivity(intent);
					finish();
				}
			});
		}
	}
}