package org.ediver.ascuba;

import org.ediver.ascuba.db.DBManager;
import org.ediver.ascuba.db.model.Dive;
import org.ediver.ascuba.db.model.DiveManager;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Dives extends ListActivity {
	private Cursor cursor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBManager dao = ((AScuba)getApplication()).DAO;
		DiveManager dm = new DiveManager(dao);
		cursor = dm.getDivesCursor();
		startManagingCursor(cursor);
		ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.divelabel,
				cursor, new String[] { Dive.columns[Dive.DATE],
						Dive.columns[Dive.DURATION], Dive.columns[Dive.DEPTH],
						Dive.columns[Dive.NOTES], Dive.columns[Dive.TEMP] },
				new int[] { R.id.divelabel_date, R.id.divelabel_duration,
						R.id.divelabel_depth, R.id.divelabel_notes,
						R.id.divelabel_temperature });
		setListAdapter(adapter);
		registerForContextMenu(getListView());
		setBackground(getResources().getConfiguration().orientation);

		// this is a trick to enable image background on the list
		// http://www.curious-creature.org/2008/12/22/why-is-my-list-black-an-android-optimization/
		getListView().setCacheColorHint(0);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menunewdive:
			Intent intent = new Intent();
			intent.setClass(Dives.this, NewDive.class);
			startActivity(intent);
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

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 99, 0, "Edit");
		menu.add(0, 98, 0, "Delete");
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		DBManager dao = ((AScuba)getApplication()).DAO;
		DiveManager dm = new DiveManager(dao);
		switch (item.getItemId()) {
		case 98:
			dm.deleteDive((int)info.id);
			cursor.requery();
			return true;
			
		case 99:
			Intent intent = new Intent();
			intent.setClass(Dives.this, EditDive.class);
			intent.putExtra("dive_id", (int)info.id);
			startActivity(intent);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	private void setBackground(int orientatoin){
		if (orientatoin == Configuration.ORIENTATION_PORTRAIT)
			getListView().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setBackground(newConfig.orientation);
	}

}