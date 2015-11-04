package com.example.hw04;

//key = MDE4MzQ2NjU2MDE0MjQ1NjQ2MDdlOTVhOQ001

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
//		Editor prefsEditor = p.edit();
//		prefsEditor.clear();
//		prefsEditor.commit();
		findViewById(R.id.exitbtn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		findViewById(R.id.progbtn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				intent.putExtra("KEY", 1);
				intent.putExtra("SKEY",0);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.topicsbtn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				intent.putExtra("KEY", 2);
				intent.putExtra("SKEY",0);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.favbtn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MainActivity.this,StoriesActivity.class);
				intent.putExtra("SKEY",1);
				startActivity(intent);
			}
		});
		
	}
}
