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

import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryAdapter extends ArrayAdapter<MyStories> {
	Context context;
	ArrayList<MyStories> objects;
	
	public StoryAdapter(Context context, ArrayList<MyStories> objects) {
		super(context, R.layout.row_item, objects);
		this.context = context;
		this.objects = objects;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_item, parent,false);
			holder = new ViewHolder();
			holder.texttitle = (TextView) convertView.findViewById(R.id.textView1);
			holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.textdetails = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(holder);			
		}
		
		holder = (ViewHolder) convertView.getTag();
		MyStories story = objects.get(position);
		
		holder.texttitle.setText(story.getTitle());
//		holder.image.setImageResource(R.drawable.ic_launcher);
		Picasso.with(context).load(story.getImageUrl()).into(holder.image);
		
//		holder.image.setImageResource(R.drawable.ic_launcher);
		holder.textdetails.setText(story.getPubdate() + "\n" + story.getMiniteaser());
		
		return convertView;
	}

	static class ViewHolder {
		TextView texttitle;
		ImageView image;
		TextView textdetails;
	}
	
	
	
	
}