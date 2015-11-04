package com.example.homework05;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	public static String USER_FIRST_NAME = "firstName";
	public static String USER_LAST_NAME = "lastName";
	EditText et1,et2,et3,et4,et5;
	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		et4 = (EditText) findViewById(R.id.editText4);
		et5 = (EditText) findViewById(R.id.editText5);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
				startActivity(i);
				finish();
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(et1.getText().toString().equals(""))
					Toast.makeText(SignUpActivity.this, "First Name Required", Toast.LENGTH_LONG).show();
				else if(et2.getText().toString().equals(""))
					Toast.makeText(SignUpActivity.this, "Last Name Required", Toast.LENGTH_LONG).show();
				else if(et3.getText().toString().equals(""))
					Toast.makeText(SignUpActivity.this, "Email Required", Toast.LENGTH_LONG).show();
				else if(et4.getText().toString().equals(""))
					Toast.makeText(SignUpActivity.this, "Password Required", Toast.LENGTH_LONG).show();
				else if(!(et4.getText().toString().equals(et5.getText().toString())))
					Toast.makeText(SignUpActivity.this, "Password didn't match", Toast.LENGTH_LONG).show();
				else
				{
					ParseUser user = new ParseUser();
					user.setUsername(et3.getText().toString());
					user.setPassword(et4.getText().toString());
					user.setEmail(et3.getText().toString());
					 
					// other fields can be set just like with ParseObject
					user.put(USER_FIRST_NAME, et1.getText().toString());
					user.put(USER_LAST_NAME, et2.getText().toString());
					 
					user.signUpInBackground(new SignUpCallback() {

					@Override
					public void done(com.parse.ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
					      // Hooray! Let them use the app now.
							Toast.makeText(SignUpActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(SignUpActivity.this, AppsActivity.class);
							startActivity(intent);
							finish();
					    } else {
					      Log.d("demo", e.toString());
					    	// Sign up didn't succeed. Look at the ParseException
					      // to figure out what went wrong
					    }
					}
					});
				}
			}
		});
	}
}
