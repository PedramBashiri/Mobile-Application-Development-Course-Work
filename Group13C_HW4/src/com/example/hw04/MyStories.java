package com.example.hw04;

import java.io.Serializable;

public class MyStories implements Serializable {

	String id, link, imageUrl, Title, pubdate, miniteaser, duration,
			public_name, audio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPublic_name() {
		return public_name;
	}

	public void setPublic_name(String public_name) {
		this.public_name = public_name;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public MyStories() {
		super();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getMiniteaser() {
		return miniteaser;
	}

	public void setMiniteaser(String miniteaser) {
		this.miniteaser = miniteaser;
	}

	@Override
	public String toString() {
		return id + "|" + link + "|" + imageUrl + "|" + Title + "|" + pubdate
				+ "|" + miniteaser + "|" + duration + "|" + public_name + "|"
				+ audio;
	}

}
