package org.ediver.ascuba;

import mvplan.main.MvplanInstance;
import mvplan.prefs.Prefs;
import mvplan.prefs.PrefsException;
import mvplan.gas.Gas;

import org.ediver.ascuba.gui.MVPlanPreferences;
import org.ediver.ascuba.mvplan.SharedPreferencesDAO;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.*;


public class DivePlanner extends ActivityGroup {
	LinearLayout view;
	Window gasListAction;
	Window segmentAction;
	Window calculateAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dive_planner_view);
		
		//restore gas list
		getPrefs();
		MvplanInstance.getPrefs().getPrefGases();
		view = (LinearLayout) findViewById(R.id.testme);
		LocalActivityManager localActivityManager = getLocalActivityManager();
		segmentAction = localActivityManager.startActivity("segmentAction",
				new Intent(this, SegmentList.class));
		calculateAction = localActivityManager.startActivity("calculateAction",
				new Intent(this, ProfileView.class));
		gasListAction = localActivityManager.startActivity("gasListAction",
				new Intent(this, GasList.class));
		view.addView(gasListAction.getDecorView(), LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		Button b = (Button) findViewById(R.id.dive_planner_calculate);
		b.setOnClickListener(calculateButtonListener);

		b = (Button) findViewById(R.id.dive_planner_gasList);
		b.setOnClickListener(gasListButtonListener);

		b = (Button) findViewById(R.id.dive_planner_profile);
		b.setOnClickListener(profileButtonListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.plannermenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.plan_menu_preferences:
			Intent settingsActivity = new Intent(getBaseContext(),
					MVPlanPreferences.class);
			startActivity(settingsActivity);
			return true;
		case R.id.quit:
			this.finish();
			return true;
			// Generic catch all for all the other menu resources
		default:
			// Don't toast text when a submenu is clicked
			if (!item.hasSubMenu()) {
				Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
			break;
		}

		return false;
	}

	android.view.View.OnClickListener calculateButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			ProfileView p = (ProfileView) (DivePlanner.this
					.getLocalActivityManager().getActivity("calculateAction"));
			p.calculate();
			view.addView(calculateAction.getDecorView(),
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}
	};
	android.view.View.OnClickListener gasListButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(gasListAction.getDecorView(),
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}
	};
	android.view.View.OnClickListener profileButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			view.removeAllViews();
			view.addView(segmentAction.getDecorView(),
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}
	};
	//private int ;

	protected void onStart() {
		super.onStart();
		getPrefs();
	};
	
	protected void onPause(){
		super.onPause();
		try {
			Context context = this.getBaseContext();
			FileOutputStream file = context.openFileOutput(getString(R.string.backupfile),Context.MODE_PRIVATE);
			String sbuffer;
		
		for(Gas gas  : MvplanInstance.getPrefs().getPrefGases()){
						if(!(gas.getFHe() == 0 && gas.getFO2() == 0.21)){
						sbuffer = "gas:" + gas.getFHe() + ":" + gas.getFO2() + ":" + gas.getMod() + "\n";
						file.write(sbuffer.getBytes());
						}
						}
		file.close();
		
		} catch (Exception e ) {
			e.printStackTrace();
		}	
	}

 protected void onResume(){
	super.onResume();
	if (MvplanInstance.getPrefs().getPrefGases().size() <= 1){
	try {
		Context context = this.getBaseContext();
		FileInputStream fstream = context.openFileInput(getString(R.string.backupfile));
		DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String sbuffer;
		sbuffer="";
		String gases[];
		Gas gas;
	
		while((sbuffer=br.readLine()) != null){
			gases=sbuffer.split(":");
			gas = new Gas(Double.parseDouble(gases[1]), Double.parseDouble(gases[2]), Double.parseDouble(gases[3]));	
			MvplanInstance.getPrefs().getPrefGases().add(gas);
		}
		fstream.close();
		for(Gas gasit  : MvplanInstance.getPrefs().getPrefGases()){
			gasit.setEnable(false);
		}
	
	} catch (Exception e ) {
		e.printStackTrace();
	}
	}
}
private void getPrefs() {
		// Get the xml/preferences.xml preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		SharedPreferencesDAO dao = new SharedPreferencesDAO (prefs);
		try {
			dao.getPrefs();
			
		} catch (PrefsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}