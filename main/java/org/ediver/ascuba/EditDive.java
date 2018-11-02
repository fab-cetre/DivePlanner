package org.ediver.ascuba;

import org.ediver.ascuba.db.DBManager;
import org.ediver.ascuba.db.model.Dive;
import org.ediver.ascuba.db.model.DiveManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EditDive extends NewDive {
	private Dive dive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		DBManager dao = ((AScuba) getApplication()).DAO;
		DiveManager dm = new DiveManager(dao);
		int diveId = getIntent().getExtras().getInt("dive_id");
		Log.w(AScuba.TAG, "Dive id to edit:" + diveId);
		dive = dm.getDive(diveId);
		setTime(dive.getYear(), dive.getMonth(), dive.getDay(), dive.getHour(),
				dive.getMinute());
		setDepth(dive.getDepth());
		setDuration(dive.getDuration());
		setNotes(dive.getNotes());
		setTemperature(dive.getTemperature());
	}

	@Override
	protected void onSave() {
		readValues(dive);
		DBManager dao = ((AScuba) getApplication()).DAO;
		DiveManager dm = new DiveManager(dao);
		dm.updateDive(dive);
		finish();

	}

	protected void setDepth(Double depth) {
		String value = "";
		if (depth != null)
			value = depth.toString();
		((TextView) (findViewById(R.id.newdive_depth))).setText(value);
	}

	protected void setDuration(Integer duration) {
		String value = "";
		if (duration != null)
			value = duration.toString();
		((TextView) (findViewById(R.id.newdive_duration))).setText(value);
	}

	protected void setTemperature(Double temperature) {
		String value = "";
		if (temperature != null)
			value = temperature.toString();
		((TextView) (findViewById(R.id.newdive_temperature))).setText(value);
	}

	protected void setNotes(String notes) {
		((TextView) (findViewById(R.id.newdive_notes))).setText(notes);
	}
}