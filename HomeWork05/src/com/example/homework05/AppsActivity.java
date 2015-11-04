package com.example.homework05;

//import com.example.midterm.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AppsActivity extends Activity {

	public static ListView lv;
	ArrayList<AppInfo> todoItems;
	public static ArrayList<AppInfo> result2 = new ArrayList<AppInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apps);
		lv = (ListView) findViewById(R.id.listView1);
		new DoWork().execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i2 = new Intent(AppsActivity.this, PreviewActivity.class);
				i2.putExtra("Title", result2.get(position).getAppTitle());
				i2.putExtra("ImageURL", result2.get(position).getSmallPhotoUrl());
				i2.putExtra("AppURL", result2.get(position).getAppUrl());
				i2.putExtra("DevName", result2.get(position).getDevName());
				i2.putExtra("Price", result2.get(position).getPrice());
				startActivity(i2);
			}
		});
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apps_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_view_all) {
            
        	//lv.removeAllViews();
        	new DoWork().execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
//        	com.example.homework05.ListAdapter adap = new com.example.homework05.ListAdapter(AppsActivity.this, R.layout.row_layout, result2);
//        	lv.setAdapter(adap);
//        	adap.setNotifyOnChange(true);
        	return true;
        } else if(id == R.id.action_view_favorites){
        	
        	ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
    		query.whereEqualTo("User", ParseUser.getCurrentUser());
    		query.findInBackground(new FindCallback<ParseObject>() {
    			
    			@Override
    			public void done(List<ParseObject> arg0, ParseException arg1) {
    				// TODO Auto-generated method stub
    				if (arg1 == null) {
    				todoItems = new ArrayList<AppInfo>() ;
    					for(ParseObject p:arg0){
    						AppInfo app = new AppInfo();
    						app.setAppTitle(p.getString("Title"));
    						app.setSmallPhotoUrl(p.getString("ImageURL"));
    						app.setDevName(p.getString("DevName"));
    						app.setPrice(p.getString("Price"));
    						app.setAppUrl(p.getString("AppURL"));
    						todoItems.add(app);
    						
    					}
    					com.example.homework05.ListAdapter adapter = new com.example.homework05.ListAdapter(AppsActivity.this, R.layout.row_layout, todoItems);
    					lv.setAdapter(adapter);
    					adapter.setNotifyOnChange(true);
    					lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    						@Override
    						public void onItemClick(AdapterView<?> parent, View view,
    								int position, long id) {
    							// TODO Auto-generated method stub
    							Intent i2 = new Intent(AppsActivity.this, PreviewActivity.class);
    							i2.putExtra("Title", todoItems.get(position).getAppTitle());
    							i2.putExtra("ImageURL", todoItems.get(position).getSmallPhotoUrl());
    							i2.putExtra("AppURL", todoItems.get(position).getAppUrl());
    							i2.putExtra("DevName", todoItems.get(position).getDevName());
    							i2.putExtra("Price", todoItems.get(position).getPrice());
    							startActivity(i2);
    						}
    					});
//    					 ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppsActivity.this, android.R.layout.activity_list_item, android.R.id.text1, todoItems);
//    		           lv.setAdapter(adapter);
    		        } else {
    		            Log.d("score", "Error: " + arg1.getMessage());
    		        }
    			}
    		});
        	return true;
        } else if(id == R.id.action_clear_favorites){
    		ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
    		query.whereEqualTo("User", ParseUser.getCurrentUser());
    		query.findInBackground(new FindCallback<ParseObject>(){

				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					if (arg1 == null) {
						for(ParseObject p:arg0){
							p.deleteInBackground();
						}
						new DoWork().execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
					}
				}
    			
    		});
        	
        	return true;
        } else if (id == R.id.action_view_shared){
        	ParseQuery<ParseObject> query = ParseQuery.getQuery("Shared");
    		query.whereEqualTo("User", ParseUser.getCurrentUser());
    		query.findInBackground(new FindCallback<ParseObject>() {
    			
    			@Override
    			public void done(List<ParseObject> arg0, ParseException arg1) {
    				// TODO Auto-generated method stub
    				if (arg1 == null) {
    					todoItems = new ArrayList<AppInfo>() ;
    					for(ParseObject p:arg0){
    						AppInfo app = new AppInfo();
    						app.setAppTitle(p.getString("Title"));
    						app.setSmallPhotoUrl(p.getString("ImageURL"));
    						app.setDevName(p.getString("DevName"));
    						app.setPrice(p.getString("Price"));
    						app.setAppUrl(p.getString("AppURL"));
    						todoItems.add(app);
    						
    					}
    					com.example.homework05.ListAdapter adapter = new com.example.homework05.ListAdapter(AppsActivity.this, R.layout.row_layout, todoItems);
    					lv.setAdapter(adapter);
    					adapter.setNotifyOnChange(true);
    					lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    						@Override
    						public void onItemClick(AdapterView<?> parent, View view,
    								int position, long id) {
    							// TODO Auto-generated method stub
    							Intent i2 = new Intent(AppsActivity.this, PreviewActivity.class);
    							i2.putExtra("Title", todoItems.get(position).getAppTitle());
    							i2.putExtra("ImageURL", todoItems.get(position).getSmallPhotoUrl());
    							i2.putExtra("AppURL", todoItems.get(position).getAppUrl());
    							i2.putExtra("DevName", todoItems.get(position).getDevName());
    							i2.putExtra("Price", todoItems.get(position).getPrice());
    							startActivity(i2);
    						}
    					});
//    					 ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppsActivity.this, android.R.layout.activity_list_item, android.R.id.text1, todoItems);
//    		           lv.setAdapter(adapter);
    		        } else {
    		            Log.d("score", "Error: " + arg1.getMessage());
    		        }
    			}
    		});
        	return true;
        } else if (id == R.id.action_clear_shared){
        	ParseQuery<ParseObject> query = ParseQuery.getQuery("Shared");
    		query.whereEqualTo("User", ParseUser.getCurrentUser());
    		query.findInBackground(new FindCallback<ParseObject>(){

				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					if (arg1 == null) {
						for(ParseObject p:arg0){
							p.deleteInBackground();
						}
						new DoWork().execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
					}
				}
    			
    		});

        	
        	return true;
        } else if (id == R.id.action_logout){
        	ParseUser.logOut();
        	finish();
        	startActivity(new Intent(AppsActivity.this, LoginActivity.class));
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
    class DoWork extends AsyncTask<String, Void, ArrayList<AppInfo>>
	{

//		ProgressDialog pd;
		@Override
		protected ArrayList<AppInfo> doInBackground(String... params) {
			try {
				URL url= new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				int statusCode = con.getResponseCode();
				if(statusCode== HttpURLConnection.HTTP_OK);{
					InputStream in = con.getInputStream();
					return AppUtils_SAX.AppsSAXParser.parseApp(in);
				}
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			pd = new ProgressDialog(MainActivity.obj);
//			pd.setMessage("Loading Apps List..");
//			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			pd.setCancelable(false);
//			pd.show();
		}

		@Override
		protected void onPostExecute(ArrayList<AppInfo> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			pd.dismiss();
			if(result!=null)
			{
				Log.d("demo",result.toString());
				com.example.homework05.ListAdapter adapter = new com.example.homework05.ListAdapter(AppsActivity.this, R.layout.row_layout, result);
				lv.setAdapter(adapter);
				adapter.setNotifyOnChange(true);
				result2 = result;
				
			}
			else
			{
				Log.d("demo","No results received");
			}
		}

	}
    
    
}
