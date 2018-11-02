package org.ediver.ascuba.mvplan;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;
import mvplan.prefs.PrefsDAO;
import mvplan.prefs.PrefsException;

public class SharedPreferencesDAO implements PrefsDAO{
	
	
	private final SharedPreferences prefs;

	public SharedPreferencesDAO(SharedPreferences prefs){
		this.prefs = prefs;
	}

	public void setPrefs(Prefs p) throws PrefsException {
	
		
	}

public Prefs loadPrefs() throws PrefsException {
		Prefs p;
		p = MvplanInstance.getPrefs();
		return p;
	}

public void savePrefs(Prefs p) throws PrefsException {
	
	
}

	public Prefs getPrefs() throws PrefsException {
		
		Prefs mvPrefs = MvplanInstance.getPrefs();
		double divide;
		
		double lastStopDepth;
		try {
			lastStopDepth = Double.parseDouble(prefs.getString("lastStopDepth",
					"" + mvPrefs.getLastStopDepth()));
		} catch (NumberFormatException e) {
			lastStopDepth = mvPrefs.getLastStopDepth();

			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("lastStopDepth", "" + lastStopDepth);
			editor.commit();
		}
		mvPrefs.setLastStopDepth(lastStopDepth);
		mvPrefs.setUnitsTo(Integer.parseInt(prefs.getString("units", ""+Prefs.METRIC)));
		mvPrefs.setGfLow(Double.parseDouble(prefs.getString("gfLow", ""+mvPrefs.getGfLow()*100.0))/100.0);
		mvPrefs.setGfHigh(Double.parseDouble(prefs.getString("gfHigh", ""+mvPrefs.getGfHigh()*100.0))/100.0);
		mvPrefs.setDiveRMV(Double.parseDouble(prefs.getString("diveRVM", ""+mvPrefs.getDiveRMV())));
		mvPrefs.setDecoRMV(Double.parseDouble(prefs.getString("decoRVM", ""+mvPrefs.getDecoRMV())));
		mvPrefs.setAscentRate(Double.parseDouble(prefs.getString("ascentRate", ""+mvPrefs.getAscentRate())));
		mvPrefs.setDescentRate(Double.parseDouble(prefs.getString("descentRate", ""+mvPrefs.getDescentRate())));
		mvPrefs.setAltitude(Double.parseDouble(prefs.getString("altitude", ""+mvPrefs.getAltitude())));
		mvPrefs.setModelClassName(prefs.getString("model","ZHL16B"));
		mvPrefs.setDefSetPoint(Double.parseDouble(prefs.getString( "setPoint", "" +mvPrefs.getDefSetPoint())));
		//if(prefs.getString("vType", "1").equals("1")){
		mvPrefs.setGfMultilevelMode(prefs.getBoolean("multilevel", false));
		if(prefs.getBoolean("vType", true)){
			mvPrefs.setOutputStyle(Prefs.EXTENDED);
		} else {
			mvPrefs.setOutputStyle(Prefs.BRIEF);
		}
		mvPrefs.validatePrefs();
		
		return mvPrefs;
	}

}
