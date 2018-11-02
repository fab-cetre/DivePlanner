package org.ediver.ascuba;

import java.util.ResourceBundle;

import mvplan.main.IMvplan;
import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;
import mvplan.util.Version;

import org.ediver.ascuba.db.DBManager;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AScuba extends Application implements IMvplan{
	public DBManager DAO = null;
	public static final String TAG = "org.ediver.ascuba";
	private Prefs prefs;
	private ResourceBundle bundle;
	@Override
	public void onCreate() {
		MvplanInstance.setMvplan(this);
		prefs = new Prefs();
		bundle = ResourceBundle.getBundle("mvplan/resources/strings");
		prefs.setDefaultPrefs();
		
		//Mvplan.prefs.setUnitsTo(Prefs.IMPERIAL);
		super.onCreate();
		try {
			Log.d(TAG,"Starting ediver version: "+ getPackageManager().getPackageInfo( "org.ediver.ascuba",0 ).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAO = DBManager.getInstance();
		DAO.initialize(this);
		Log.d(TAG,"Initializing application aScuba done");
	}
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		DAO.destroy();
	}
	
	private void test(){
		SQLiteDatabase database = DAO.getDatabase();
		
		
	}
	public String getResource(String res) {
		
		return bundle.getString(res);
	}
	public String getAppName() {
		return MvplanInstance.NAME+ " " + MvplanInstance.getVersion().toString();
	}
	public Prefs getPrefs() {
		return prefs;
	}
	public void setPrefs(Prefs p) {
		prefs=p;
		
	}
	public int getDebug() {
		return 0;
	}
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	
}
