package com.example.homework05;

import com.parse.Parse;

import android.app.Application;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		// Enable Local Datastore.
//      Parse.enableLocalDatastore(this);
       
		Parse.initialize(this, "cun5adli0kfrocjExuiD1YcFPehJnBgagmtb5iQJ", "H8ExnpSE4o20CyQ3xoY52naRNTpIUfyeCX1fP02i");
		super.onCreate();
	}

}
