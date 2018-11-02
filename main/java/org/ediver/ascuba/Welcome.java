package org.ediver.ascuba;

import mvplan.gas.Gas;

import org.ediver.ascuba.gui.GasDialog;
import org.ediver.ascuba.gui.GasDialogCallback;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AScubaActivity implements GasDialogCallback{
	TextView view;
	Button b;
	Context c ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = this;
		setContentView(R.layout.welcome);
		/*b =  (Button) findViewById(R.id.ButtonOpenDivelog);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				open(DIVELOG);
			}
		});*/
		b =  (Button) findViewById(R.id.ButtonOpenPlanner);
		
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				open(DIVEPLANNER);
			}
		});
		
//		view =  (TextView) findViewById(R.id.TextView0);
//		b =  (Button) findViewById(R.id.Button01);
//		b.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				Mvplan mv = new Mvplan();
//				Prefs prefs = new Prefs();
//				Mvplan.stringResource = ResourceBundle.getBundle("mvplan/resources/strings");
//				Mvplan.prefs = prefs;
//				Mvplan.prefs.setDefaultPrefs();
//
//
//				ArrayList<SegmentAbstract> knownSegments = new ArrayList<SegmentAbstract>();
//				ArrayList<Gas> knownGases = new ArrayList<Gas>();
//
//				Gas g = new Gas(0, 0.21, 66.0);
//				knownGases.add(g);
//				SegmentDive s = new SegmentDive(20, 40, g, 0);
//				knownSegments.add(s);
//				
//
//				Profile p = new Profile(knownSegments, knownGases, null);
//				//m.printModel();
//				int returnCode = p.doDive();
//				Profile currentProfile;
//				switch (returnCode) {
//				case Profile.SUCCESS:
//					currentProfile = p; // Save as current profile
//					p.doGasCalcs(); // Calculate gases
//					// Save Model
//					Model currentModel = p.getModel();
//					// System.out.println("Dive Metadata:"+currentModel.getMetaData());
//					StringBuffer b = new StringBuffer();
//					new ProfilePrinter(currentProfile, b).doPrintTable();
//
//					System.out.println(b);
//					view.setText(b);
//					break;
//
//				default:
//					break;
//				}
//				
//			}
//		});
		
	}

	public void notify(Gas g) {
		// TODO Auto-generated method stub
		
	}

}