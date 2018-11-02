package org.ediver.ascuba.db;

import org.ediver.ascuba.AScuba;
import org.ediver.ascuba.AScubaException;
import org.ediver.ascuba.db.model.Dive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBManager {
	private DBOpenHelper dbo = null ;
	private static DBManager me = null; 
	private SQLiteDatabase db;
	private DBManager(){
		
	}
	public SQLiteDatabase getDatabase(){
		return dbo.getWritableDatabase();
	}
	
	public static synchronized DBManager getInstance(){
		if (me == null){
			Log.d(AScuba.TAG,"Creating new DBManager !!!!");
			me = new DBManager();
		}
		return me;
	}
	public void initialize(Context c){
		if (dbo == null)
			dbo = new DBOpenHelper(c);
	}
	public void destroy(){
		dbo.close();
	}
	
	public boolean isInitialized(){
		if (dbo == null) 
			return false;
		return true;
	}
	
	
}
