package se.lanzen.LunchToday;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class AppPreference extends PreferenceActivity {
	
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		addPreferencesFromResource(R.xml.main_preference); 
	}
}
