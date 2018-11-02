package org.ediver.ascuba.db;

import org.ediver.ascuba.AScuba;
import org.ediver.ascuba.db.model.Dive;
import org.ediver.ascuba.db.model.DiveSite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

	public class DBOpenHelper extends SQLiteOpenHelper {
		
		private static final String DATABASE_NAME = "aScuba.db";
		private static final int DATABASE_VERSION = 19;
		public static final String TAG = AScuba.TAG;
		

		DBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
		
        @Override
        public void onCreate(SQLiteDatabase db) {
        	
        	Log.i(TAG, "Creating Dive database");
        	String query ="CREATE TABLE "+Dive.TABLE +" ("
            + Dive.columns[Dive.ID]+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Dive.columns[Dive.DATE]+ " TIMESTAMP,"
            + Dive.columns[Dive.DURATION]+ " INTEGER,"
            + Dive.columns[Dive.DEPTH]+ " FLOAT,"
            + Dive.columns[Dive.TEMP]+ " FLOAT,"
            + Dive.columns[Dive.NOTES]+ " TEXT,"
            + Dive.columns[Dive.DIVESITE]+ " INTEGER"
            + ");";
        	Log.d(TAG, "Creating "+Dive.TABLE +" database using query :"+ query);
            db.execSQL(query);
            query ="CREATE TABLE "+DiveSite.TABLE +" ("
            + DiveSite.columns[DiveSite.ID]+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DiveSite.columns[DiveSite.ALTITUDE]+ " FLOAT,"
            + DiveSite.columns[DiveSite.DIFFICUTY]+ " INTEGER,"
            + DiveSite.columns[DiveSite.LATITUDE]+ " FLOAT,"
            + DiveSite.columns[DiveSite.LONGITUDE]+ " FLOAT,"
            + DiveSite.columns[DiveSite.MAXDEPTH]+ " INTEGER,"
            + DiveSite.columns[DiveSite.NAME]+ " TEXT,"
            + DiveSite.columns[DiveSite.NOTES]+ " TEXT,"
            + DiveSite.columns[DiveSite.RATING]+ " INTEGER,"
            + DiveSite.columns[DiveSite.VISMAX]+ " INTEGER,"
            + DiveSite.columns[DiveSite.VISMIN]+ " INTEGER"
            + ");";
        	Log.d(TAG, "Creating "+DiveSite.TABLE +" database using query :"+ query);
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS Dive");
            db.execSQL("DROP TABLE IF EXISTS DiveSite");
            onCreate(db);
        }
        
    }