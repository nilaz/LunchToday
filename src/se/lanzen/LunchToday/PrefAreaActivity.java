package se.lanzen.LunchToday;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


public class PrefAreaActivity extends ListActivity {
	private SharedPreferences mPref = null;
	private ArrayList<PrefResturantItem> mPrefAreaItems = new ArrayList<PrefResturantItem>();

	@Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("PrefAreaActivity:onCreate","key="+(String) getResources().getText(R.string.pref_resturant_names));
        
        // Init pref
        ArrayList<String> resturants = getIntent().getStringArrayListExtra((String) getResources().getText(R.string.pref_resturant_names));
        Log.e("PrefAreaActivity:onCreate","resturants="+resturants);
        String areaName = getIntent().getStringExtra((String) getResources().getText(R.string.pref_current_area_name));
        Log.e("PrefAreaActivity:onCreate","area name="+areaName);
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefAreaItems.clear();
        for(String resturant : resturants) {
        	PrefResturantItem item = new PrefResturantItem(mPref, areaName, resturant);
        	mPrefAreaItems.add(item);
        }
		updateUIFromPref();

		//Prepare UI
        PrefAreaAdapter adapter = new PrefAreaAdapter(this, mPrefAreaItems);
        adapter.setNotifyOnChange(true);
		this.setListAdapter(adapter);
		setTitle("Inställning för " + areaName);
		
    }

	private void updateUIFromPref() {
		
	}


	
}
