package com.example.hw04;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class StoryJSONParser {

	public ArrayList<MyStories> ParseJson(String toParse) throws JSONException,
			MalformedURLException {

		ArrayList<MyStories> stories = new ArrayList<MyStories>();

		JSONObject root = new JSONObject(toParse);
		JSONArray storiesArray = root.getJSONObject("list").getJSONArray(
				"story");

		for (int i = 0; i < storiesArray.length(); i++) {

			JSONObject jsonStory = storiesArray.getJSONObject(i);
			URL url;

			MyStories thisStory = new MyStories();
				thisStory.setId(jsonStory.getString("id"));
			if (jsonStory.has("byline"))
				thisStory.setPublic_name(jsonStory.getJSONArray("byline")
						.getJSONObject(0).getJSONObject("name").getString("$text"));
			if (jsonStory.has("audio"))
				thisStory.setDuration(jsonStory.getJSONArray("audio")
						.getJSONObject(0).getJSONObject("duration").getString("$text"));
			if (jsonStory.has("link"))
			thisStory.setLink(jsonStory.getJSONArray("link").getJSONObject(0)
					.getString("$text"));
			if (jsonStory.has("image"))
				thisStory.setImageUrl(jsonStory.getJSONArray("image")
						.getJSONObject(0).getString("src"));
			if (jsonStory.has("title"))
			thisStory.setTitle(jsonStory.getJSONObject("title").getString(
					"$text"));
			if (jsonStory.has("storyDate"))
			thisStory.setPubdate(jsonStory.getJSONObject("storyDate")
					.getString("$text"));
			if (jsonStory.has("teaser"))
			thisStory.setMiniteaser(jsonStory.getJSONObject("teaser")
					.getString("$text"));
			if (jsonStory.has("audio"))
			{
//				thisStory.setAudio(jsonStory.getJSONArray("audio")
//						.getJSONObject(0).getJSONObject("format").getJSONArray("mp3").getJSONObject(0)
//						.getString("$text"));
				String str = jsonStory.getJSONArray("audio")
						.getJSONObject(0).getJSONObject("format").getJSONArray("mp3").getJSONObject(0)
						.getString("$text");
				
				URL request = null;
				try {
					request = new URL(str);
					//	request = new URL("http://api.npr.org/query?key=MDE4MzQ2NjU2MDE0MjQ1NjQ2MDdlOTVhOQ001&output=JSON&numResults=25&id="+id);
					
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
						thisStory.setAudio(Str.toString());
					}
				}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			}
			stories.add(thisStory);
		}

		Log.d("Parser1", stories.toString());
		return stories;
	}

}
