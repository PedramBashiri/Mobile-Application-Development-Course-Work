package com.example.homework05;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		WebView myWebView = (WebView) findViewById(R.id.webView);
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.loadUrl(getIntent().getExtras().getString("URL"));
	}
}
