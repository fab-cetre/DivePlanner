package org.ediver.ascuba.db.model;

import org.ediver.ascuba.AScuba;
import org.ediver.ascuba.AScubaException;
import org.ediver.ascuba.db.DBManager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DiveManager {
	DBManager dbm;
	public DiveManager (DBManager d){
		dbm = d;
	}
	
	public Cursor getDivesCursor(){
		SQLiteDatabase db = dbm.getDatabase();
		Cursor query = db.query(Dive.TABLE, Dive.columns, null, null, null, null, Dive.columns[Dive.DATE]+" desc");
		return query;
	}
	
	public void insertDive(Dive d){
		
		SQLiteDatabase db = dbm.getDatabase();
		ContentValues values = new ContentValues();
        values.put(Dive.columns[Dive.DATE], d.getTimestamp());
        values.put(Dive.columns[Dive.DEPTH],d.getDepth());
        values.put(Dive.columns[Dive.DURATION],d.getDuration());
        values.put(Dive.columns[Dive.TEMP],d.getTemperature());
        values.put(Dive.columns[Dive.NOTES],d.getNotes());
        db.insert(Dive.TABLE, Dive.columns[Dive.ID], values);
        //db.close();
        Log.d(AScuba.TAG,"Dive :"+d.getId()+" Inserted");
	}
	public void updateDive( Dive d){
		SQLiteDatabase db = dbm.getDatabase();
		ContentValues values = new ContentValues();
        values.put(Dive.columns[Dive.DATE], d.getTimestamp());
        values.put(Dive.columns[Dive.DEPTH],d.getDepth());
        values.put(Dive.columns[Dive.DURATION],d.getDuration());
        values.put(Dive.columns[Dive.TEMP],d.getTemperature());
        values.put(Dive.columns[Dive.NOTES],d.getNotes());
        db.update(Dive.TABLE, values,Dive.columns[Dive.ID]+"=?",new String []{""+d.getId()});
        Log.d(AScuba.TAG,"Dive :"+d.getId()+" Updated");
        //db.close();
        
	}
	
	public void deleteDive(int id){
		SQLiteDatabase db = dbm.getDatabase();
		db.execSQL("delete from Dive where _id =  "+id);
		//db.close();
		Log.d(AScuba.TAG,"Dive :"+id+" Deleted");
	}
	public Dive getDive(int id){
		SQLiteDatabase db = dbm.getDatabase();
		Cursor c = db.query(Dive.TABLE, Dive.columns, Dive.columns[Dive.ID]+"=?" , new String []{""+id}, null, null, null);
		if (!c.moveToFirst())return null;
		Dive ret = new Dive();
		ret.setId(id);
		try {
			ret.setTimestamp(c.getString(c.getColumnIndex(Dive.columns[Dive.DATE])));
			ret.setDuration(c.getString(c.getColumnIndex(Dive.columns[Dive.DURATION])));
			ret.setNotes(c.getString(c.getColumnIndex(Dive.columns[Dive.NOTES])));
			ret.setDepth(c.getString(c.getColumnIndex(Dive.columns[Dive.DEPTH])));
			ret.setTemperature(c.getString(c.getColumnIndex(Dive.columns[Dive.TEMP])));
		} catch (AScubaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
//		}finally{
//			db.close();
		}
		
		return ret;
	}

}
