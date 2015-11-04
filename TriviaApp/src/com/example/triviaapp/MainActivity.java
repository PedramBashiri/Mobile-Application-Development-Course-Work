/*
 * Homework 03
 * Group 13C
 * MainActivity.java
 * Micheal Wong, Nitin Kabra, Peram Bashiri  
 */


package com.example.triviaapp;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button startBtn = (Button) findViewById(R.id.button1);
		Button createBtn = (Button) findViewById(R.id.button2);
		Button deleteBtn = (Button) findViewById(R.id.button3);
		Button exitBtn = (Button) findViewById(R.id.button4);

		startBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				take_quiz();
				// TODO Auto-generated method stub

			}
		});

		createBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				create_question();
				// TODO Auto-generated method stub

			}
		});

		deleteBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				downloadmage task = new downloadmage();
				task.execute();

			}
		});

		exitBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				// TODO Auto-generated method stub

			}
		});
	}

	private boolean IsConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cm.getActiveNetworkInfo();
		if (nf != null && nf.isConnected()) {
			return true;
		}
		return false;
	}

	public void take_quiz() {
		if (!IsConnected()) {
			Toast.makeText(MainActivity.this, "No Network Connection",
					Toast.LENGTH_LONG).show();
		} else {
			Intent i = new Intent(MainActivity.this, TriviaActivity.class);
			startActivity(i);

		}

	}

	public void create_question() {
		Intent i = new Intent(MainActivity.this, CreateQuestionActivity.class);
		startActivity(i);
	}


	private class downloadmage extends AsyncTask<Void, Void, Void> {


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			super.onPreExecute();
			pd.setTitle("Updating..");
			pd.setMessage("Loading");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.show();
		}

		ProgressDialog pd = new ProgressDialog(MainActivity.this);

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String requesturl = "http://dev.theappsdr.com/apis/trivia/deleteAll.php";
			OutputStreamWriter writer;
			StringBuilder sb = new StringBuilder();
			int responseCode = 0;
			try {
				URL url = new URL(requesturl);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("POST");
				con.setDoOutput(true);
				OutputStream os = con.getOutputStream();
				writer = new OutputStreamWriter(os);

				String value = URLEncoder.encode("CXJZGLSMEJz6k9kEkCPX",
						"UTF-8");
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append("gid" + "=" + value);

				writer.write(sb.toString());
				writer.flush();
				writer.close();
				responseCode = con.getResponseCode();
			} catch (UnsupportedEncodingException e) {
			} catch (Exception e) {
			} finally {
			}
			return null;
		}
	}
}
