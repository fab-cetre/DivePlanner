package org.ediver.ascuba.db.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ediver.ascuba.AScuba;
import org.ediver.ascuba.AScubaException;
import org.ediver.ascuba.db.DBManager;

import android.provider.BaseColumns;
import android.util.Log;

public class Dive implements BaseColumns{
	public final static String TABLE = "Dive";
	public final static int ID  = 0;
	public final static int DATE  = 1;
	public final static int DURATION  = 2;
	public final static int DEPTH  = 3;
	public final static int TEMP  = 4;
	public final static int NOTES  = 5;
	public final static int DIVESITE  = 6;
	public final static String [] columns = {BaseColumns._ID,"date","duration","greatestdepth","temperature","notes","diveSiteID"};
	private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    
    
    private int id;
    
    private Integer  duration ;
    private Double  depth ;
    private Double  temperature ;
    private String  notes ;
    public void setDate(int year,int month,int day,int hour,int minute) {
		this.year = year;
		this.month= month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
    public String getTimestamp(){
    	StringBuilder date = new StringBuilder()
        // Month is 0 based so add 1
        .append(year).append("-")
        .append(pad(month+ 1) ).append("-")
        .append(pad(day)).append(" ")
        .append(pad(hour)).append(":")
        .append(pad(minute)).append(":")
		.append("00");
        return date.toString();
    };
    
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	public Integer getDuration() {
		return duration;
	}
	public void setDepth(double depth) {
		this.depth = depth;
	}
	public Double getDepth() {
		return depth;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getNotes() {
		return notes;
	}
	private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
	public void setDuration(String d) throws AScubaException {
		if (d == null||d.length() == 0){
			duration = null;
			return;
		}
		try {
			duration = Integer.parseInt(d);
		} catch (NumberFormatException e) {
			throw new AScubaException("Duration must be a number");
		}
			
		
	}
	public void setDepth(String d) throws AScubaException {
		if (d == null||d.length() == 0){
			depth = null;
			return;
		}
		try {
			depth = Double.parseDouble(d);
		} catch (NumberFormatException e) {
			throw new AScubaException("Depth must be a number");
		}
	}
	public void setTemperature(String d) throws AScubaException {
		if (d == null||d.length() == 0){
			temperature = null;
			return;
		}
		try {
			temperature = Double.parseDouble(d);
		} catch (NumberFormatException e) {
			throw new AScubaException("Temperature must be a number");
		}		
	}
	public void setId(int id){
		this.id = id;
	}
	public void setTimestamp(String timestamp) throws AScubaException{
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date d = sdf.parse(timestamp);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			this.year = c.get(Calendar.YEAR);
			this.month = c.get(Calendar.MONTH);
			this.day = c.get(Calendar.DAY_OF_MONTH);
			this.hour = c.get(Calendar.HOUR_OF_DAY);
			this.minute = c.get(Calendar.MINUTE);
			Log.d(AScuba.TAG,"orginal timestamp: "+timestamp+" parsed timestamp: "+getTimestamp());
		} catch (ParseException e) {
			throw new AScubaException("Wrong timestamp format accepted format is :" +format);
		}
		
	}
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public int getId() {
		return id;
	}
	
}
