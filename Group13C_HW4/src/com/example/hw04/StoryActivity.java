package com.example.hw04;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.MediaController.MediaPlayerControl;

public class StoryActivity extends Activity implements OnPreparedListener,
		MediaPlayerControl {
	private MediaPlayer mediaPlayer;// = new MediaPlayer();
	private MediaController mediaController;// = new
											// MediaController(StoryActivity.this);
	private Handler handler = new Handler();
	SharedPreferences preferences;
	TextView t1, t2, t3, t4;
	int i=0;
	ImageButton back, web, speaker, star;
	MyStories mystory;
	String id1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		mediaPlayer = new MediaPlayer();
		mediaController = new MediaController(StoryActivity.this);

		mystory = (MyStories) getIntent().getSerializableExtra(
				StoriesActivity.storyObj);

		t1 = (TextView) findViewById(R.id.textView1);
		t1.setText(mystory.getTitle());
		t2 = (TextView) findViewById(R.id.textView2);
		t2.setText(mystory.getPublic_name());
		t3 = (TextView) findViewById(R.id.textView3);
		int a = Integer.parseInt(mystory.getDuration());

		t3.setText(mystory.getPubdate() + "\n" + a / 60 + "min " + a % 60
				+ "sec");

		t4 = (TextView) findViewById(R.id.textView4);
		t4.setText(mystory.getMiniteaser());

		back = (ImageButton) findViewById(R.id.imageButton1);
		web = (ImageButton) findViewById(R.id.imageButton2);
		speaker = (ImageButton) findViewById(R.id.imageButton3);
		star = (ImageButton) findViewById(R.id.imageButton4);

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StoryActivity.this,MainActivity.class);
				startActivity(i);
			}
		});

		web.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(mystory.getLink()));
				startActivity(browserIntent);
			}
		});

		speaker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = mystory.getAudio();

				mediaPlayer.setOnPreparedListener(StoryActivity.this);

				mediaController.show();

				try {
					mediaPlayer.setDataSource(url);
					mediaPlayer.prepareAsync();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		final SharedPreferences.Editor editor = preferences.edit();

		star.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					SharedPreferences sd = PreferenceManager
							.getDefaultSharedPreferences(StoryActivity.this);
					Map<String, ?> map = (Map<String, ?>) sd.getAll();
					Log.d("map", map.toString());
					String[] arr = new String[8];
					for (Map.Entry<String, ?> entry : map.entrySet()) {
						entry.getKey();
						Log.d("demo", entry.getValue().toString());

						id1 = entry.getValue().toString();
					}
					
					editor.putString(String.valueOf(i), id1+","+mystory.getId().toString());
					i++;
					//editor.apply();
					editor.commit();
					star.setImageResource(R.drawable.fav_1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public void pause() {
		mediaPlayer.pause();
	}

	@Override
	public void seekTo(int pos) {
		mediaPlayer.seekTo(pos);
	}

	@Override
	public void start() {
		mediaPlayer.start();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();
		// mediaController.setMediaPlayer(this);
		// mediaController.setAnchorView(findViewById(R.id.LinearLayout1));

		handler.post(new Runnable() {
			public void run() {
				mediaController.setEnabled(true);
				mediaController.show();
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		mediaController.hide();
		mediaPlayer.stop();
		mediaPlayer.release();
	}

	@Override
	public int getAudioSessionId() {
		return 0;
	}
}
