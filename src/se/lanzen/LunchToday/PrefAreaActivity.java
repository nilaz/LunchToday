package se.lanzen.LunchToday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class PrefAreaActivity extends Activity {
	private SharedPreferences mPrefs = null;
	private ArrayList<PrefResturantItem> mPrefAreaItems = new ArrayList<PrefResturantItem>();

	@Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_pref);
        Log.i("PrefAreaActivity:onCreate","key="+(String) getResources().getText(R.string.pref_resturant_names));
        
        // Init pref
        ArrayList<String> resturants = getIntent().getStringArrayListExtra((String) getResources().getText(R.string.pref_resturant_names));
        Log.e("PrefAreaActivity:onCreate","resturants="+resturants);
        String areaName = getIntent().getStringExtra((String) getResources().getText(R.string.pref_current_area_name));
        Log.e("PrefAreaActivity:onCreate","area name="+areaName);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefAreaItems.clear();
        for(String resturant : resturants) {
        	PrefResturantItem item = new PrefResturantItem(mPrefs, areaName, resturant);
        	mPrefAreaItems.add(item);
        }

		// Prepare buttons
		Button okButton =(Button)findViewById(R.id.area_pref_ok_button); 
		okButton.setOnClickListener(new View.OnClickListener() { 
			public void onClick(View view) { 
				savePreferences();
				setResult(RESULT_OK);
				finish(); 
			} 
		}); 
		Button cancelButton = (Button)findViewById(R.id.area_pref_cancel_button); 
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) { 
				setResult(RESULT_CANCELED); 
				finish(); 
			}
		});
		//Prepare UI
        PrefAreaAdapter adapter = new PrefAreaAdapter(this, mPrefAreaItems);
        adapter.setNotifyOnChange(true);
        ListView lv = (ListView)findViewById(R.id.area_pref_listview);
		lv.setAdapter(adapter);
		setTitle("Inställning för " + areaName);
		//updateUIFromPref();
		
    }

	protected void savePreferences() {
		Editor editor=mPrefs.edit(); 
		for(PrefResturantItem resturant : mPrefAreaItems) {
			if(resturant.visibleIsUpdated()) {
				Log.i("PrefAreaActivity:savePreference","Resturang="+resturant.getResturantName()+" visible="+resturant.isVisible());
				editor.putBoolean(resturant.getVisibleTag(),resturant.isVisible()); 
				
			}
			
//			if(resturant.isVisible() != resturant.getVisibleCheckBox().isChecked()) {
//			}
//			if(resturant.getSortOrder() != resturant.getSortOrderSpinner().getSelectedItemPosition()) {
//				editor.putInt(resturant.getSortOrderTag(),resturant.getSortOrderSpinner().getSelectedItemPosition());
//				Log.i("PrefAreaActivity:savePreference","Resturang="+resturant.getResturantName()+" new sortOrder="+resturant.getSortOrderSpinner().getSelectedItemPosition());
//			}

		}
		editor.commit();
		
	}
}
