package com.example.hw04;

public class StoryClass {

	String id;
	String title;
	
	@Override
	public String toString() {
		return "StoryClass [id=" + id + ", title=" + title + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StoryClass(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public StoryClass() {
		super();
	}
	
	
	
}
