package org.ediver.ascuba;

import android.app.Activity;
import android.content.Intent;

public class AScubaActivity extends Activity {
	static final int UNDEFINED = 0;
	static final int WELCOME = 1;
	static final int DIVELOG = 2;
	static final int DIVEPLANNER = 3;
	
	
	void  open(int  activity,boolean close){
		Intent i; 
		switch (activity){
			case  WELCOME:
				i = new Intent(getApplicationContext(),Welcome.class);
				this.startActivity(i);
				break;
			case  DIVELOG:
				i = new Intent(getApplicationContext(),Dives.class);
				this.startActivity(i);
				break;
			case  DIVEPLANNER:
				i = new Intent(getApplicationContext(),DivePlanner.class);
				this.startActivity(i);
				break;
		}
		if (close){
			finish();
		}
	}
	void  open(int  activity){
		
		open(activity,false);
	}
}