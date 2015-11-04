/*
 * Homework 03
 * Group 13C
 * CreateQuestionActivity.java
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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class CreateQuestionActivity extends Activity {

	EditText et1, et2, et3;
	ImageButton im1;
	Button b1;
	RadioGroup rg;
	ProgressDialog pd;
	int count = 0, index = 5;
	RelativeLayout rl;
	String text1 = "";
	String text="";
	RadioButton et4;

	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_question);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		rl = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		b1 = (Button) findViewById(R.id.button1);
		im1 = (ImageButton) findViewById(R.id.imageButton1);

		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				downloadmage task = new downloadmage();
				task.execute();
				

			}
		});
		im1.setOnClickListener(new View.OnClickListener() {

			
			@Override
			public void onClick(View v) {
				et4 = new RadioButton(getBaseContext());
				et4.setId(count);
				et4.setText(et2.getText());
				et4.setId(i);
				et2.setText("");
				i++;

				et4.setTextColor(Color.BLACK);
				rg.addView(et4);
				text1 = text1 + et4.getText() + ";";
			}
		});
	}

	private class downloadmage extends AsyncTask<Void, Void, Void> {

		Bitmap bmp;

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

		ProgressDialog pd = new ProgressDialog(CreateQuestionActivity.this);

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (!et1.getText().equals("") && !et2.getText().equals("")) {
				text = et1.getText() + ";" + text1 + et3.getText() + ";"
						+ rg.getCheckedRadioButtonId();
				
			}
			String requesturl = "http://dev.theappsdr.com/apis/trivia/saveNew.php";

			Log.d("demo", "Parsed Question " + text);
			// Map<String, String> params_1 = new HashMap<String, String>();
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
				
				String value = URLEncoder.encode(text, "UTF-8");
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append("q" + "=" + value);

				value = URLEncoder.encode("CXJZGLSMEJz6k9kEkCPX", "UTF-8");
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append("gid" + "=" + value);

				writer.write(sb.toString());
				writer.flush();
				writer.close();
				responseCode = con.getResponseCode();
				Log.d("demo", "Question posted");
			} catch (UnsupportedEncodingException e) {
				Log.d("demo", "Question encoding failed");
			} catch (Exception e) {
				Log.d("demo", e.toString());
			} finally {
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			finish();
			super.onPostExecute(result);
		}
	}
}
