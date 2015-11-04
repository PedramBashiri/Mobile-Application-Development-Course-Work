/*
 * Assignnemt HomeWork 5
 * LoginActivity.java
 * Names: Pedram Bashiri, Michael Wong, Nitin Kabra
 * 
 */

package com.example.homework05;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText et1,et2;
	Button b1,b2;
	public static Context obj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		obj = LoginActivity.this;
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et1.getText().toString().equals("")||et2.getText().toString().equals(""))
					Toast.makeText(LoginActivity.this, "Enter Email or Password", Toast.LENGTH_LONG).show();
				else
				{
					ParseUser.logInInBackground(et1.getText().toString(), et2.getText().toString(), new LogInCallback() {
						  public void done(ParseUser user, ParseException e) {
						    if (user != null) {
						      // Hooray! The user is logged in.
						    	Intent i = new Intent(LoginActivity.this,AppsActivity.class);
						    	startActivity(i);
						    	finish();
						    } else {
						      // Signup failed. Look at the ParseException to see what happened.
						    	Toast.makeText(LoginActivity.this, "Login not successful", Toast.LENGTH_LONG).show();
						    }
						  }

						});
				}
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i2 = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(i2);
				finish();
				
			}
		});
		
	}
}
