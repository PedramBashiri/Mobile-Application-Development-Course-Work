/*
 * Homework 03
 * Group 13C
 * TriviaActivity.java
 * Micheal Wong, Nitin Kabra, Peram Bashiri  
 */





package com.example.triviaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class TriviaActivity extends Activity {

	Button quitbtn, nextbtn;
	TextView que, time, queno;
	RadioGroup opt;
	ImageView iv;
	static ArrayList<quiz> quiz_ques;
	static int index;
	int z = 0;
	static int correct_answer = 0, total_question = 0;
	ProgressDialog pd;
	downloadFile task = null;
	CountDownTimer ct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trivia);
		index = 0;
		opt = (RadioGroup) findViewById(R.id.radioGroup1);
		que = (TextView) findViewById(R.id.textView3);
		queno = (TextView) findViewById(R.id.textView1);
		time = (TextView) findViewById(R.id.textView2);
		iv = (ImageView) findViewById(R.id.imageView1);
		quitbtn = (Button) findViewById(R.id.button1);
		nextbtn = (Button) findViewById(R.id.button2);

		
		// while()
		// {
		task = new downloadFile();
		task.execute();
		// }

		quitbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

		nextbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				iv.setImageResource(R.drawable.untitled);
				//iv.setVisibility(View.INVISIBLE);
				

				
				
				
				// TODO Auto-generated method stub
				if (index < quiz_ques.size()) {
					if (opt.getCheckedRadioButtonId() == -1) {
						
					} else {
						int answer=-1;
						try {
							answer = (Integer.parseInt(quiz_ques.get(index).getAns()));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (answer == (opt.getCheckedRadioButtonId()))
							correct_answer++;

						
					}
					index++;
					opt.removeAllViews();
					LoadNextQuestion(quiz_ques);
					ct.cancel();
					ct = new CountDownTimer(24000, 1000) {

						@Override
						public void onTick(long millisUntilFinished) {
							time.setText("Time Left: " + millisUntilFinished / 1000
									+ " seconds");
						}

						@Override
						public void onFinish() {
							Log.d("Time", "asd");
							try {
								nextbtn.performClick();
								// task.cancel(true);

							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}.start();
					
				} else {
					Intent i = new Intent(TriviaActivity.this,
							ResultActivity.class);
					i.putExtra("Total", quiz_ques.size());
					i.putExtra("Correct", correct_answer);
					startActivity(i);
					finish();

				}
				
				
			}
		});

	}
	
	private void LoadNextQuestion(ArrayList<quiz> result) {
		total_question++;
		for (String opt_opt : result.get(index).options) {
			RadioButton opt1 = new RadioButton(getBaseContext());
			opt1.setId(z);
			z++;
			opt1.setText(opt_opt);
			opt1.setTextColor(Color.BLACK);
			opt.addView(opt1);
		}
		queno.setText("Q" + (index + 1));
		time.setText("Time Left: ");
		que.setText(result.get(index).getQuestion());

		URL url;
		try {
			url = new URL(result.get(index).getUrl());
			if (!url.equals("") && url != null ) {
				
				downloadmage dlImage = new downloadmage();
				dlImage.execute(url);
			} else {
			
				//iv.setImageResource(R.drawable.untitled);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private class downloadFile extends AsyncTask<Void, Void, ArrayList<quiz>> {

		ProgressDialog pd = new ProgressDialog(TriviaActivity.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setTitle("Updating..");
			pd.setMessage("Loading");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.show();

		}

		@Override
		protected ArrayList<quiz> doInBackground(Void... params) {

			URL request = null;

			int i;
			try {
				quiz_ques = new ArrayList<quiz>();
				request = new URL(
						"http://dev.theappsdr.com/apis/trivia/getAll.php");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						request.openStream()));
				String text;
				text = br.readLine();
				while ((text = br.readLine()) != null) {
					quiz Q = new quiz();
					ArrayList<String> options = new ArrayList<String>();

					int numOfSCs = StringUtils.countMatches(text, ";");
					String[] accepted_chars={"0","1","2","3","4","5","6","7","8","9","10","11","12"};
					
					if (StringUtils.endsWithAny(text, accepted_chars) && numOfSCs >= 4) {
							String[] parts = text.split(";");
							if (parts.length > 1 && parts[0] != "") {
								Q.setQuestion(parts[0]);
								for (int j = 1; j < parts.length - 2; j++) {
									options.add(parts[j]);
								}
								if (parts[parts.length - 2] !="" && StringUtils.startsWith(parts[parts.length - 2], "http"))
									Q.setUrl(parts[parts.length - 2]);
								if (parts[parts.length - 1] != "")
									Q.setAns(parts[parts.length - 1]);
								Q.setOptions(options);

								Log.d("Demo1", Q.toString());
								quiz_ques.add(Q);


						}
					}
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return quiz_ques;
		}

		@Override
		protected void onPostExecute(ArrayList<quiz> result) {
			// TODO Auto-generated method stub

			LoadNextQuestion(result);
			ct = new CountDownTimer(24000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					time.setText("Time Left: " + millisUntilFinished / 1000
							+ " seconds");
				}

				@Override
				public void onFinish() {
					Log.d("Time", "asd");
					try {
						nextbtn.performClick();
						// task.cancel(true);

					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			

			pd.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub

			super.onProgressUpdate(values);
		}

	}

	private class downloadmage extends AsyncTask<URL, Void, Bitmap> {

		Bitmap bmp;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			//iv.setVisibility(View.VISIBLE);
			super.onPreExecute();
			pd.setTitle("Updating..");
			pd.setMessage("Loading");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.show();
		}

		ProgressDialog pd = new ProgressDialog(TriviaActivity.this);

		@Override
		protected Bitmap doInBackground(URL... params) {
			// TODO Auto-generated method stub
			
					try {
						bmp = BitmapFactory.decodeStream(params[0].openConnection()
								.getInputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return bmp;
			

			
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub

			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			pd.dismiss();
			iv.setImageBitmap(result);

			super.onPostExecute(result);
		}

	}
	
	
}
