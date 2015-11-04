package com.example.hw04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StoriesActivity extends Activity {

	ProgressDialog PD;
	String id1, id;
	ListView listView;
	ArrayList<MyStories> stories, stories1;
	String[] arr;
	static final String storyObj = "storyObj";

//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.apps_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_clear_history){
//        	
//        	
//        	return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);
		stories1 = new ArrayList<MyStories>();
		Intent in = getIntent();
		int c = in.getExtras().getInt("SKEY");
		stories = new ArrayList<MyStories>();

		if (c == 1) {

			SharedPreferences sd = PreferenceManager
					.getDefaultSharedPreferences(this);
			Map<String, ?> map = (Map<String, ?>) sd.getAll();
			Log.d("map", map.toString());
			arr = new String[8];
			for (Map.Entry<String, ?> entry : map.entrySet()) {
				entry.getKey();
				Log.d("demo", entry.getValue().toString());

				id1 = entry.getValue().toString();
			}
			Log.d("id", id1);
			String[] str = id1.split(",");

			for (int j = 0; j < str.length; j++) {

				downloadFile async = new downloadFile();
				async.execute("http://api.npr.org/query?id="
						+ str[j]
						+ "&fields=title,teaser,storyDate,byline,audio,image&dateType=story&output=JSON&numResults=25&searchType=mainText&apiKey=MDE4MzQ2NjU2MDE0MjQ1NjQ2MDdlOTVhOQ001");
			}
			listView = (ListView) findViewById(R.id.listView2);
			StoryAdapter adapter = new StoryAdapter(StoriesActivity.this,
					stories1);
			adapter.setNotifyOnChange(true);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(StoriesActivity.this,
							StoryActivity.class);
					Log.d("story_id", stories1.get(position).getId());
					intent.putExtra("storyObj", stories.get(position));

					startActivity(intent);

				}
			});

		} else {

			// Intent intent = getIntent();
			id = getIntent().getStringExtra("id");
			listView = (ListView) findViewById(R.id.listView2);

			downloadFile async = new downloadFile();
			async.execute("http://api.npr.org/query?id="
					+ id
					+ "&fields=title,teaser,storyDate,byline,audio,image&dateType=story&output=JSON&numResults=25&searchType=mainText&apiKey=MDE4MzQ2NjU2MDE0MjQ1NjQ2MDdlOTVhOQ001");
			StoryAdapter adapter = new StoryAdapter(StoriesActivity.this,
					stories1);
			adapter.setNotifyOnChange(true);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(StoriesActivity.this,
							StoryActivity.class);
					Log.d("story_id", stories.get(position).getId());
					intent.putExtra("storyObj", stories.get(position));

					startActivity(intent);

				}
			});

		}
	}
	

	private class downloadFile extends
			AsyncTask<String, ArrayList<MyStories>, ArrayList<MyStories>> {

		@Override
		protected ArrayList<MyStories> doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL request = null;
			try {
				request = new URL(params[0]);
				Log.d("urldemo", params[0]);
				// request = new
				// URL("http://api.npr.org/query?key=MDE4MzQ2NjU2MDE0MjQ1NjQ2MDdlOTVhOQ001&output=JSON&numResults=25&id="+id);

				HttpURLConnection con = (HttpURLConnection) request
						.openConnection();
				con.setRequestMethod("GET");
				con.connect();

				Log.d("demo", "connected");
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					InputStream in = con.getInputStream();
					BufferedReader BF = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					StringBuilder Str = new StringBuilder();
					String line = BF.readLine();

					while (line != null) {
						Str.append(line);
						line = BF.readLine();
					}
					// Log.d("tag1", Str.toString());
					StoryJSONParser parser = new StoryJSONParser();
					stories = parser.ParseJson(Str.toString());
					// Log.d("tag1", stories.toString());
					return stories;

				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Log.d("demo", "in preexecute");
			PD = new ProgressDialog(StoriesActivity.this);
			PD.setMessage("Loading Results ...");
			PD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			PD.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<MyStories> result) {
			
			// ArrayList<MyStories> mystory = new ArrayList<String>();
			// for(MyStories r:result){
			// titles.add(r.getTitle());
			// //photoList.add(r);
			// }
			for (MyStories r : result) {
				stories1.add(r);
			}
			PD.dismiss();
			// return result;
			// StoryAdapter adapter = new StoryAdapter(StoriesActivity.this,
			// result);
			// adapter.setNotifyOnChange(true);
			// listView.setAdapter(adapter);

			super.onPostExecute(result);
		}

	}

}
