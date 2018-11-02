package org.ediver.ascuba;

import java.util.ArrayList;
import java.util.List;

import mvplan.gas.Gas;
import mvplan.main.MvplanInstance;

import org.ediver.ascuba.gui.GasDialog;
import org.ediver.ascuba.gui.GasDialogCallback;

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

public class GasList extends AScubaActivity {
	Button add;
	ListView list;
	CheckBox ocbailout;
	GasListAdaptor a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gas_list_view);
		
		ocbailout = (CheckBox) findViewById(R.id.oc_bailout_checkbox);
		list = (ListView) findViewById(R.id.gas_list_list);
		add = (Button) findViewById(R.id.gas_list_add);
		add.setOnClickListener(addButtonListener);
		ocbailout.setChecked(MvplanInstance.getPrefs().getOcDeco());
		ocbailout.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				MvplanInstance.getPrefs().setOcDeco(isChecked);
				MvplanInstance.getPrefs().validatePrefs();
			}
		});
		
		a = new GasListAdaptor(this.getApplicationContext(),
				R.layout.gas_list_label, R.id.gas_list_label_text,
				MvplanInstance.getPrefs().getPrefGases());
		list.setAdapter(a);
		
	}

	android.view.View.OnClickListener addButtonListener = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			new GasDialog(GasList.this, addCallback).show();
		}
	};

	GasDialogCallback addCallback = new GasDialogCallback() {
		public void notify(Gas g) {
			MvplanInstance.getPrefs().getPrefGases().add(g);
			System.out.println(g);
			a.notifyDataSetChanged();

		}
	};

	private class GasListAdaptor extends ArrayAdapter<Gas> {

		List <Gas> selected;
		public GasListAdaptor(Context context, int resource,
				int textViewResourceId, List<Gas> objects) {
			super(context, resource, textViewResourceId, objects);
			selected = new ArrayList<Gas>();
			//TOTO save selected somewhere;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View a =  super.getView(position, convertView, parent);
			
			//handle buttons
			ImageButton b = (ImageButton) a.findViewById(R.id.gas_list_label_edit);
			b.setTag(new Integer(position));
			b.setOnClickListener(editButtonListener);
			b = (ImageButton) a.findViewById(R.id.gas_list_label_delete);
			b.setTag(new Integer(position));
			b.setOnClickListener(deleteButtonListener);
			
			//handle checkbox;
			CheckBox cb = (CheckBox) a.findViewById(R.id.gas_list_label_check_box);
			cb.setOnCheckedChangeListener(checkedListener);
			cb.setTag(new Integer(position));
			cb.setChecked(getItem(position).getEnable());
			return a;
		}
		
		android.view.View.OnClickListener editButtonListener = new android.view.View.OnClickListener() {
			public void onClick(View v) {
				
				Gas g = a.getItem((Integer) v.getTag());
				EditCallback e = new EditCallback((Integer) v.getTag()); 
				new GasDialog(GasList.this ,g, e).show();
			}
		};
		
		android.view.View.OnClickListener deleteButtonListener = new android.view.View.OnClickListener() {
			public void onClick(View v) {
				a.remove(a.getItem((Integer) v.getTag()));
			}
		};
		
		OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int position = (Integer) buttonView.getTag();
				a.getItem(position).setEnable(isChecked);
			}
		};
	}
	private class EditCallback implements GasDialogCallback{
		public EditCallback(int position) {
			this.position=position;
		}
		int position;
		public void notify(Gas g) {
			a.remove(a.getItem(position));
			a.insert(g, position);
			
		}
	}
	

}