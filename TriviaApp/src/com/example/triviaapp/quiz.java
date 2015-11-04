/*
 * Homework 03
 * Group 13C
 * quiz.java
 * Micheal Wong, Nitin Kabra, Peram Bashiri  
 */

package com.example.triviaapp;

import java.util.ArrayList;

public class quiz {
	String question,url;
	ArrayList<String> options;
	String ans;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<String> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}
	@Override
	public String toString() {
		return "quiz [question=" + question + ", url=" + url + ", options="
				+ options + ", ans=" + ans + "]";
	}
	
	
}
