package com.example.hw04;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
	public ArrayList<StoryClass> ParseJson(String toParse) throws JSONException, MalformedURLException{
		
		ArrayList<StoryClass> stories = new ArrayList<StoryClass>();
		
		JSONObject root = new JSONObject(toParse);
		JSONArray storiesArray = root.getJSONArray("item");
		
		for(int i =0; i< storiesArray.length();i++){
			
			JSONObject jsonStory = storiesArray.getJSONObject(i);
			URL url;
			//url = new URL(jsonPhoto.getString("image_url"));
			//StoryClass thisPhoto = new StoryClass(jsonPhoto.getString("name"),jsonPhoto.getJSONObject("user").getString("fullname"), url);
			StoryClass thisStory = new StoryClass(jsonStory.getString("id"),jsonStory.getJSONObject("title").getString("$text"));
			stories.add(thisStory);
		}
		
//		Log.d("Parser", stories.toString());
		return stories;
	}

}
