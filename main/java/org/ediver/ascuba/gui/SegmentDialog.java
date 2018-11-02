package org.ediver.ascuba.gui;

import java.util.ArrayList;
import java.util.List;

import mvplan.gas.Gas;
import mvplan.main.MvplanInstance;
import mvplan.segments.SegmentAbstract;
import mvplan.segments.SegmentDive;

import org.ediver.ascuba.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SegmentDialog extends Dialog {
	SegmentDialogCallback callback;
	SegmentAbstract segment;

	EditText depth;
	EditText time;
	EditText setPoint;
	Spinner gases;

	Button ok;
	Button cancel;

	public SegmentDialog(Context context, SegmentDialogCallback callback) {
		super(context);
//		segment = new SegmentDive(0, 0, null, 0);
		ArrayList<Gas> prefGases = MvplanInstance.getPrefs().getPrefGases();
		segment = new SegmentDive(30.0,20.0,prefGases.get(0),MvplanInstance.getPrefs().getDefSetPoint());
		this.callback = callback;

	}

	public SegmentDialog(Context context, SegmentAbstract segment,
			SegmentDialogCallback callback) {
		super(context);
		this.callback = callback;
		this.segment = (SegmentAbstract) segment.clone();
	}

	public SegmentDialog(Context context, SegmentAbstract segment) {
		super(context);
		this.segment = segment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.segment_dialog);
		setTitle(R.string.gas_dialog_title);
		setCancelable(true);

		depth = (EditText) findViewById(R.id.SegmentDialogDepth);
		time = (EditText) findViewById(R.id.SegmentDialogTime);
		setPoint = (EditText) findViewById(R.id.SegmentDialogSetPoint);
		depth.setText("" + segment.getDepth());
		time.setText("" + segment.getTime());
		setPoint.setText("" + segment.getSetpoint());
		gases = (Spinner) findViewById(R.id.SegmentDialogGases);
		ok = (Button) findViewById(R.id.SegmentDialogOk);
		cancel = (Button) findViewById(R.id.SegmentDialogCancel);
		cancel.setOnClickListener(cancelListener);
		ok.setOnClickListener(okListener);

		ArrayList<Gas> prefGases = MvplanInstance.getPrefs().getPrefGases();
		ArrayList<Gas> prefGasesEnabled = new ArrayList<Gas>();
		Gas sgas = segment.getGas();
		int count = 0;
		int defgas = 0;
		for (Gas gas : prefGases) {
			//changed fce : any gas can be select
			//if (gas.getEnable()) {
				prefGasesEnabled.add(gas);
				if (gas.compareTo(sgas) == 0) {
					defgas = count;
				}
				count++;
			//}
		}

		gases.setAdapter(new ArrayAdapter<Gas>(this.getContext(),
				android.R.layout.simple_spinner_item, prefGasesEnabled));
		gases.setSelection(defgas);
		// gases.

		LayoutParams params = getWindow().getAttributes();
		// params.height = LayoutParams.FILL_PARENT;
		params.width = LayoutParams.FILL_PARENT;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		//
		super.onCreate(savedInstanceState);

	}

	private class SegmentListAdaptor extends ArrayAdapter<SegmentAbstract> {

		public SegmentListAdaptor(Context context, int resource,
				int textViewResourceId, List<SegmentAbstract> objects) {
			super(context, resource, textViewResourceId);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View a = super.getView(position, convertView, parent);

			// handle checkbox;
			CheckBox cb = (CheckBox) a
					.findViewById(R.id.gas_list_label_check_box);
			cb.setTag(new Integer(position));
			cb.setChecked(getItem(position).getEnable());
			return a;
		}

	}

	android.view.View.OnClickListener cancelListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			SegmentDialog.this.dismiss();
		}

	};

	android.view.View.OnClickListener okListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			try {
				double d = Double.parseDouble(depth.getText().toString());
				segment.setDepth(d);
			} catch (NumberFormatException e) {
				Toast.makeText(getContext(),
						"Could not parse Depth Setting it to default : 10",
						1000);
				segment.setDepth(10);
			}
			try {
				double t = Double.parseDouble(time.getText().toString());
				segment.setTime(t);
			} catch (NumberFormatException e) {
				Toast.makeText(getContext(),
						"Could not parse Time Setting it to default : 10", 1000);
				segment.setTime(10);
			}
			try {
				if (setPoint.getText() == null
						|| setPoint.getText().toString().trim().equals("")) {
					segment.setSetpoint(0);
				} else {
					double sp = Double.parseDouble(setPoint.getText()
							.toString());
					segment.setSetpoint(sp);
				}
			} catch (NumberFormatException e) {
				Toast.makeText(getContext(),
						"Could not parse Set Point Setting it to  : 0", 1000);
				segment.setSetpoint(0);

			}
			segment.setGas((Gas) gases.getSelectedItem());

			SegmentDialog.this.callback.notify(segment);
			SegmentDialog.this.dismiss();

		}

	};

}
