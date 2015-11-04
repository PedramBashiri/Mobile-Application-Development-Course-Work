package com.example.hw04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.xml.sax.SAXException;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ListActivity extends Activity {
	int key ;
	ListView listView;
	ProgressDialog PD;
	ArrayList<StoryClass> stories;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		Intent intent = getIntent();
		key = intent.getIntExtra("KEY",0);
		
		downloadFile async = new downloadFile();
		async.execute();
		
		listView = (ListView) findViewById(R.id.listView2);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(ListActivity.this,StoriesActivity.class);
				intent.putExtra("id", stories.get(position).getId());
				startActivity(intent);
				
			}
		});
		
	}
	
	private class downloadFile extends AsyncTask<String, Void , ArrayList<StoryClass>>{

		@Override
		protected ArrayList<StoryClass> doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL request = null;
			try {
				if(key==1)
				{
					request = new URL("http://api.npr.org/list?id=3002&output=JSON");
					Log.d("demo", "key=1");
				}
				else if(key==2)
				{
					request = new URL("http://api.npr.org/list?id=3004&output=JSON");
					Log.d("demo", "key=2");
				}
				
				HttpURLConnection con = (HttpURLConnection) request.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				
				Log.d("demo", "connected");
				int statusCode = con.getResponseCode();
				if(statusCode == HttpURLConnection.HTTP_OK){
					InputStream in = con.getInputStream();
					BufferedReader BF = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder Str = new StringBuilder();
					String line = BF.readLine();
					
					while(line != null){
						Str.append(line);
						line=BF.readLine();
					}
					Log.d("tag1", Str.toString());
					JSONParser parser = new JSONParser();
					stories = parser.ParseJson(Str.toString());
					Log.d("tag1", stories.toString());
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
			 PD = new ProgressDialog(ListActivity.this); 
			 PD.setMessage("Loading Results ...");
			 PD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			 PD.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<StoryClass> result) {
			// TODO Auto-generated method stub
			//Log.d("debug", result.toString());
			ArrayList<String> titles = new ArrayList<String>();
			for(StoryClass r:result){
				titles.add(r.getTitle());
				//photoList.add(r);
			}
			PD.dismiss();
			
			Log.d("Titles", titles.toString());
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,titles);
			
			listView.setAdapter(adapter);
			
			
			super.onPostExecute(result);
		}
		
		
		
	}
}


