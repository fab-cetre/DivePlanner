package org.ediver.ascuba.gui;

import org.ediver.ascuba.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.Toast;

public class MVPlanPreferences extends PreferenceActivity {
	private static final String NAME = "mvPlanSharedPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mvprefs);
		
		makeDecimal("lastStopDepth");
		makeNumber("gfHigh");
		makeNumber("gfLow");
		makeNumber("descentRate");
		makeNumber("ascentRate");
		makeNumber("altitude");
		makeDecimal("diveRVM");
		makeDecimal("decoRVM");
		
		// Get the custom preference
		Preference customPref = (Preference) findPreference("lastStopDepth");
		customPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
						
						SharedPreferences customSharedPreference = getSharedPreferences(
								NAME, Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = customSharedPreference
								.edit();
						try {
							editor.putString("lastStopDepth", ""+Double.parseDouble(newValue.toString()));
						} catch (NumberFormatException e) {
							Toast.makeText(getBaseContext(),
									"Could not set new depth :"+newValue+" make sure it is number",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
						editor.commit();
						
						return true;
					}


				});
	}
	
	private void makeDecimal(String key){
		EditTextPreference prefEditText = (EditTextPreference) findPreference(key);
		prefEditText.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}
	private void makeNumber(String key){
		EditTextPreference prefEditText = (EditTextPreference) findPreference(key);
		prefEditText.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();

		}
		return super.onKeyDown(keyCode, event);
	};
	
}
