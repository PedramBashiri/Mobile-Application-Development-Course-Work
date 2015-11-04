package com.example.homework05;
/*Arun Sai Sangawar Vijay*/
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapter extends ArrayAdapter<AppInfo>{

	List<AppInfo> mData;
	Context mContext;
	int mResource;
	public ListAdapter(Context context, int resource, List<AppInfo> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.mData = objects;
		this.mContext = context;
		this.mResource = resource;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);
		}
		AppInfo info = mData.get(position);
		
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
		try
		{
			Picasso.with(LoginActivity.obj).load(info.getSmallPhotoUrl()).into(iv);
		}
		catch(IllegalArgumentException e)
		{
			Log.d("Exception", e.toString());
		}
		
		TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
		tv1.setText(info.getAppTitle());
		
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
		tv2.setText(info.getDevName());
		
		TextView tv3 = (TextView) convertView.findViewById(R.id.textView4);
		tv3.setText(info.getPrice());
		
		return convertView;
	}
	
	

}