package com.example.to_dolist;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends Activity {

	EditText et1,et2,et3;
	static final int TIME_DIALOG_ID = 999;
	TimePicker timePicker;
	int hour, minute;
	static final int DATE_DIALOG_ID = 0;

	private DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			int pYear = year;
			int pMonth = monthOfYear;
			int pDay = dayOfMonth;
			et2.setText(new StringBuilder()
					.append(pMonth + 1).append("/").append(pDay).append("/")
					.append(pYear).append(" "));
		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
			if(hour < 12)
			{
			et3.setText(new StringBuilder().append(hour)
					.append(":").append(minute).append(" AM"));
			}
			else
			{
				et3.setText(new StringBuilder().append(hour-12)
						.append(":").append(minute).append(" PM"));
			}
		}
	};

	public Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, Calendar.HOUR, Calendar.MINUTE,
					false);
		case DATE_DIALOG_ID:
			return new DatePickerDialog(CreateActivity.this, pDateSetListener,
					Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		}
		return null;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		et1 = (EditText) findViewById(R.id.editText1);
		et2 = (EditText) findViewById(R.id.editText2);
		et3 = (EditText) findViewById(R.id.editText3);
		Button b1 = (Button) findViewById(R.id.button1);
		et2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		et3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});

		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(et1.getText().toString().matches("") || et2.getText().toString().matches("")|| et3.getText().toString().matches(""))
				{
					Toast.makeText(getApplicationContext(), "Fill all the required information", Toast.LENGTH_LONG).show();
				}
				else if(et1.getText().toString().length() > 20)
				{
					Toast.makeText(getApplicationContext(), "Title cannot be more than 20 characters", Toast.LENGTH_LONG).show();
				}
				else
				{
					Tasks tmytask = new Tasks();
					tmytask.setName(et1.getText().toString());
					tmytask.setTime(et3.getText().toString());
					tmytask.setDate(et2.getText().toString());
					RadioButton radioBtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
					tmytask.setPriority(radioBtn.getText().toString());
					ListofTasks.taskList.add(tmytask);
					ArrayList<Tasks> task2= new ArrayList<Tasks>();
					task2=ListofTasks.taskList;
					Intent intent = new Intent(CreateActivity.this,MainActivity.class);
					intent.putExtra("tsk",task2);
					startActivity(intent);
					finish();
					
				}
			}
		});
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }
	
	public void onBackPressed() {
        
		finish();
        Intent setIntent = new Intent(CreateActivity.this, MainActivity.class);
        startActivity(setIntent); 
        return;
    }   
}