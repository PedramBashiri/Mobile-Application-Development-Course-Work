package com.example.hw04;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class AudioTest extends Activity implements OnPreparedListener, MediaPlayerControl {
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;
	private Handler handler = new Handler();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mediaController.show();
	    return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		String url = "http://pd.npr.org/anon.npr-mp3/npr/atc/2013/04/20130407_atc_04.mp3";
		mediaPlayer = new MediaPlayer();
		mediaController = new MediaController(this);
	    mediaPlayer.setOnPreparedListener(this);	
	    
	    try {
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	//    mediaController.setMediaPlayer(this);
	  //  mediaController.setAnchorView(findViewById(R.id.LinearLayout1));

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
