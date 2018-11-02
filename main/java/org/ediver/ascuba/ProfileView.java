package org.ediver.ascuba;

import mvplan.dive.Profile;
import mvplan.dive.printer.TextProfilePrinter;
import mvplan.main.MvplanInstance;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileView extends AScubaActivity {

	TextView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_text_view);
		view = (TextView) findViewById(R.id.profile_text_view_text);

	}

	public void calculate() {
		super.onResume();

		Profile p = new Profile(MvplanInstance.getPrefs().getPrefSegments(),
				MvplanInstance.getPrefs().getPrefGases(), null);
		// m.printModel();
		int returnCode = p.doDive();
		Profile currentProfile;
		switch (returnCode) {
		case Profile.SUCCESS:
			currentProfile = p; // Save as current profile
			p.doGasCalcs(); // Calculate gases
			// Save Model
			// Model currentModel = p.getModel();
			// System.out.println("Dive Metadata:"+currentModel.getMetaData());
			StringBuffer b = new StringBuffer();
			new TextProfilePrinter(currentProfile, b).print();

			view.setText(b);
			break;

		case Profile.CEILING_VIOLATION:
			view.setText(MvplanInstance.getMvplan()
					.getResource("mvplan.gui.MainFrame.ceilingViolation.text"));
			break;

		case Profile.NOTHING_TO_PROCESS:
			view.setText(MvplanInstance.getMvplan()
					.getResource("mvplan.gui.MainFrame.noSegments.text"));
			break;
		case Profile.PROCESSING_ERROR:
			view.setText(MvplanInstance.getMvplan()
					.getResource("mvplan.gui.MainFrame.processingError.text"));
		case Profile.INFINITE_DECO:
			view.setText(MvplanInstance.getMvplan()
					.getResource("mvplan.gui.MainFrame.decoNotPossible.text"));

		default:
			view.setText("Can not calculate");
			break;

		}
	}

}