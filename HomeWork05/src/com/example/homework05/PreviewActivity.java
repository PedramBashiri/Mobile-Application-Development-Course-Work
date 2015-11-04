package com.example.homework05;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends Activity {

	Intent intent;
	TextView tv;
	ImageView iv, iv2, iv3;
	public ParseObject favorites, shared;
	static ArrayList<String> todoitems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		favorites = new ParseObject("Favorites");
		shared = new ParseObject("Shared");
		intent = getIntent();
		tv = (TextView) findViewById(R.id.textView1);
		iv = (ImageView) findViewById(R.id.imageView1);
		iv2 = (ImageView) findViewById(R.id.imageView2);
		iv3 = (ImageView) findViewById(R.id.imageView3);
		tv.setText(intent.getExtras().getString("Title"));
		Picasso.with(LoginActivity.obj)
				.load(intent.getExtras().getString("ImageURL")).into(iv);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
		query.whereEqualTo("Title", intent.getExtras().getString("Title"));
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(final List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null) {
					Log.d("title123", arg0.toString());
					if (arg0.size() == 0) {

						iv2.setImageResource(R.drawable.rating_not_important);
						iv2.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								iv2.setImageResource(R.drawable.rating_important);
								favorites.put("User",
										ParseUser.getCurrentUser());
								favorites.put("Title", intent.getExtras()
										.getString("Title"));
								favorites.put("ImageURL", intent.getExtras()
										.getString("ImageURL"));
								favorites.put("DevName", intent.getExtras()
										.getString("DevName"));
								favorites.put("Price", intent.getExtras()
										.getString("Price"));
								favorites.put("AppURL", intent.getExtras()
										.getString("AppURL"));
								favorites.saveInBackground();

							}
						});
					} else {
						iv2.setImageResource(R.drawable.rating_important);
						// favorites.remove("Title");
						iv2.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								iv2.setImageResource(R.drawable.rating_not_important);
								arg0.get(0).deleteInBackground();
							}
						});

					}
				}
			}
		});

		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(intent.getExtras().getString("AppURL")));
					startActivity(browserIntent);

				} catch (ActivityNotFoundException e) {
					Toast.makeText(
							LoginActivity.obj,
							"No application can handle this request."
									+ " Please install a webbrowser",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		});
		todoitems = new ArrayList<String>();

		iv3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ParseQuery<ParseObject> query1 = ParseQuery.getQuery("_User");
				// query1.whereNotEqualTo("user", ParseUser.getCurrentUser());
				query1.findInBackground(new FindCallback<ParseObject>() {
					public void done(List<ParseObject> arg0, ParseException e) {
						if (e == null) {
							
							for (ParseObject p : arg0) {

								todoitems.add(p.getString("firstName")+" "+p.getString("lastName"));

							}
							
						}
						 AlertDialog.Builder alertDialog = new
						 AlertDialog.Builder(PreviewActivity.this);
						 LayoutInflater inflater = getLayoutInflater();
						 View convertView = (View) inflater.inflate(R.layout.custom,
						 null);
						 ListView lv = (ListView)
						 convertView.findViewById(R.id.listView1);
						 alertDialog.setView(convertView);
						 alertDialog.setTitle("User");
						 ArrayAdapter<String> adapter = new
						 ArrayAdapter<String>(PreviewActivity.this,android.R.layout.simple_list_item_1,todoitems);
						 Log.d("zxc",adapter.toString());
						 lv.setAdapter(adapter);
						 alertDialog.show();
						 lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								
							}
						});
					}
					
				});
				Log.d("zxc", todoitems.toString());
				// AlertDialog.Builder alertDialog = new
				// AlertDialog.Builder(PreviewActivity.this);
				// LayoutInflater inflater = getLayoutInflater();
				// View convertView = (View) inflater.inflate(R.layout.custom,
				// null);
				// ListView lv = (ListView)
				// convertView.findViewById(R.id.listView1);
				// alertDialog.setView(convertView);
				// alertDialog.setTitle("User");
				// ArrayAdapter<String> adapter = new
				// ArrayAdapter<String>(PreviewActivity.this,android.R.layout.simple_list_item_1,todoitems);
				// Log.d("zxc",adapter.toString());
				// lv.setAdapter(adapter);
				// alertDialog.show();
				shared.put("User", ParseUser.getCurrentUser());
				shared.put("Title", intent.getExtras().getString("Title"));
				shared.put("ImageURL", intent.getExtras().getString("ImageURL"));
				shared.put("DevName", intent.getExtras().getString("DevName"));
				shared.put("Price", intent.getExtras().getString("Price"));
				shared.put("AppURL", intent.getExtras().getString("AppURL"));
				shared.saveInBackground();

			}
		});
	}
}
