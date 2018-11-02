package org.ediver.ascuba;

import java.util.Calendar;

import org.ediver.ascuba.db.DBManager;
import org.ediver.ascuba.db.model.Dive;
import org.ediver.ascuba.db.model.DiveManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewDive extends Activity {
	 // where we display the selected date and time
    private Button pickDate;
    private Button pickTime;

    // date and time
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newdive);

        pickDate = (Button) findViewById(R.id.pickDate);
        pickDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        pickTime = (Button) findViewById(R.id.pickTime);
        pickTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        Button save = (Button) findViewById(R.id.newdive_save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSave();
            }
        });

        final Calendar c = Calendar.getInstance();
        setTime (c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),
        		c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
        updateDisplay();
    }
    protected void setTime(int year, int month, int day, int hour, int minute) {
    	mYear = year;
    	mMonth = month;
        mDay = day;
        mHour = hour;
        mMinute = minute;
        updateDisplay();
	}
   
    private void alert(String alert){
    	Log.e(AScuba.TAG,alert);
    }
    void readValues(Dive d){
    	d.setDate(mYear, mMonth, mDay, mHour, mMinute);
        try {
			d.setDuration(((TextView)findViewById(R.id.newdive_duration)).getText().toString());
			d.setDepth(((TextView)findViewById(R.id.newdive_depth)).getText().toString());
			d.setTemperature(((TextView)findViewById(R.id.newdive_temperature)).getText().toString());
		} catch (AScubaException e) {
			alert(e.getAlert());
			return ; 
		}
		d.setNotes(((TextView)findViewById(R.id.newdive_notes)).getText().toString());
    }
    protected void onSave() {
    	DBManager dao = ((AScuba) getApplication()).DAO;
		DiveManager dm = new DiveManager(dao);
    	Dive d = new Dive();
        readValues(d);
		dm.insertDive(d);
		finish();
	}
   protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                            mDateSetListener,
                            mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case TIME_DIALOG_ID:
                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
                break;
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }    

    private void updateDisplay() {
    	StringBuilder date = new StringBuilder()
        // Month is 0 based so add 1
        .append(mYear).append("-")
        .append(pad(mMonth+ 1) ).append("-")
        .append(pad(mDay));
    	StringBuilder time = new StringBuilder()
        .append(pad(mHour)).append(":")
        .append(pad(mMinute)).append(":")
		.append("00");
        pickDate.setText(date);
        pickTime.setText(time);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                        int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    
   
}