package se.lanzen.LunchToday;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class PrefAreaActivity extends ListActivity {
	private ArrayList<String> mResturants;
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("PrefAreaActivity:onCreate","key="+(String) getResources().getText(R.string.pref_resturant_names));
        
        mResturants = getIntent().getStringArrayListExtra((String) getResources().getText(R.string.pref_resturant_names));
        Log.e("PrefAreaActivity:onCreate","resturants="+mResturants);
        PrefAreaAdapter adapter = new PrefAreaAdapter(this, mResturants);
        adapter.setNotifyOnChange(true);
		this.setListAdapter(adapter);
    }


	
}
