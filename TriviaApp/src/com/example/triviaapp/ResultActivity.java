/*
 * Homework 03
 * Group 13C
 * ResultActivity.java
 * Micheal Wong, Nitin Kabra, Peram Bashiri  
 */


package com.example.triviaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		double finalResult = ((double) intent.getIntExtra("Correct", 1))/intent.getIntExtra("Total", 1);
		tv3.setText(Double.toString(finalResult));
		
		ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setMax(100);
		Log.d("demo12345",(String.valueOf(TriviaActivity.correct_answer)));
		pb.setProgress((TriviaActivity.correct_answer / TriviaActivity.index) * 100);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();

			}
		});
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ResultActivity.this, TriviaActivity.class);
				startActivity(i);
				finish();

			}
		});
	}
}
