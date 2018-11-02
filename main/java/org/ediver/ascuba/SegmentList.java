package org.ediver.ascuba;

import java.util.List;

import mvplan.main.MvplanInstance;
import mvplan.segments.SegmentAbstract;
import mvplan.segments.SegmentDive;

import org.ediver.ascuba.gui.SegmentDialog;
import org.ediver.ascuba.gui.SegmentDialogCallback;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SegmentList extends AScubaActivity {
	Button add;
	ListView list;
	SegmentListAdaptor a;

	@Override
	protected void onStart() {
		List<SegmentAbstract> defseg;
		super.onStart();
		setContentView(R.layout.segment_list_view);

		list = (ListView) findViewById(R.id.segment_list_list);
		add = (Button) findViewById(R.id.segment_list_add);
		add.setOnClickListener(addButtonListener);
		MvplanInstance.getPrefs().setSPDefDefSegement();
		a = new SegmentListAdaptor(this.getApplicationContext(),
				R.layout.segment_list_label, R.id.segment_list_label_text,
				MvplanInstance.getPrefs().getPrefSegments());
		list.setAdapter(a);
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	android.view.View.OnClickListener addButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			new SegmentDialog(SegmentList.this, addCallback).show();
		}
	};

	SegmentDialogCallback addCallback = new SegmentDialogCallback() {
		public void notify(SegmentAbstract g) {
			MvplanInstance.getPrefs().getPrefSegments().add(g);
			System.out.println(g);
			a.notifyDataSetChanged();

		}
	};

	private class SegmentListAdaptor extends ArrayAdapter<SegmentAbstract> {

		public SegmentListAdaptor(Context context, int resource,
				int textViewResourceId, List<SegmentAbstract> objects) {
			super(context, resource, textViewResourceId, objects);
			// TOTO save selected somewhere;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View a = super.getView(position, convertView, parent);

			SegmentAbstract item = getItem(position);
			// handle buttons
			ImageButton b = (ImageButton) a
					.findViewById(R.id.segment_list_label_edit);
			b.setTag(new Integer(position));
			b.setOnClickListener(editButtonListener);
			b = (ImageButton) a.findViewById(R.id.segment_list_label_delete);
			b.setTag(new Integer(position));
			b.setOnClickListener(deleteButtonListener);

			// handle checkbox;
			CheckBox cb = (CheckBox) a
					.findViewById(R.id.segment_list_label_check_box);
			cb.setOnCheckedChangeListener(checkedListener);
			cb.setTag(new Integer(position));
			cb.setChecked(item.getEnable());

			TextView text = (TextView) a
					.findViewById(R.id.segment_list_label_text);
			text.setText("Duration:	" + item.getTime() + " min\nDepth:		"
					+ item.getDepth() + " "
					+ MvplanInstance.getPrefs().getDepthShortString() + "\nGas:			"
					+ item.getGas() + "\nSetPoint:	" + item.getSetpoint());
			return a;
		}

		android.view.View.OnClickListener editButtonListener = new android.view.View.OnClickListener() {
			public void onClick(View v) {

				SegmentAbstract g = a.getItem((Integer) v.getTag());
				EditCallback e = new EditCallback((Integer) v.getTag());
				new SegmentDialog(SegmentList.this, g, new EditCallback(
						(Integer) v.getTag())).show();
			}
		};

		android.view.View.OnClickListener deleteButtonListener = new android.view.View.OnClickListener() {
			public void onClick(View v) {
				a.remove(a.getItem((Integer) v.getTag()));
			}
		};

		OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int position = (Integer) buttonView.getTag();
				a.getItem(position).setEnable(isChecked);
			}
		};
	}

	private class EditCallback implements SegmentDialogCallback {
		public EditCallback(int position) {
			this.position = position;
		}

		int position;

		public void notify(SegmentAbstract g) {
			a.remove(a.getItem(position));
			a.insert(g, position);

		}
	}

}