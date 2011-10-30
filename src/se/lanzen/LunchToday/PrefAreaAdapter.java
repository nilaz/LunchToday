package se.lanzen.LunchToday;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;  
import android.widget.AdapterView;
public class PrefAreaAdapter extends ArrayAdapter<PrefResturantItem> {
	private class CheckBoxListener implements View.OnClickListener {
		private PrefResturantItem mResturant;
		public CheckBoxListener(PrefResturantItem rest) {
			mResturant = rest;
		}
		public void onClick(View v) {
			CheckBox cb = (CheckBox)v;
			Log.i("CheckBoxListener","Resturant "+mResturant.getResturantName()+" synlig="+cb.isChecked());
			mResturant.setVisible(cb.isChecked());
		}
	}
	
	private class SpinnerListener implements AdapterView.OnItemSelectedListener {
		private PrefResturantItem mResturant;
		public SpinnerListener(PrefResturantItem rest) {
			mResturant = rest;
		}
		public void onItemSelected(AdapterView<?> parent,
                View v, int position, long id) {
			Log.i("SpinnerListener","Resturant "+mResturant.getResturantName()+" position="+position+" id="+id);
			mResturant.setSortOrder(position);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing
		}	
	}
	
	public PrefAreaAdapter(Context context, final ArrayList<PrefResturantItem> items) {
		super(context, 0, items);
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View view = convertView;
		PrefResturantItem resturant = getItem(position);
		boolean creating = false;
		
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.pref_resturant_item, null);
			view.setTag(resturant);
			creating = true;
		}
		
		final TextView name = (TextView)view.findViewById(R.id.pref_resturant_name);
		name.setText(resturant.getResturantName());

		final CheckBox resturantVisible = (CheckBox)view.findViewById(R.id.pref_resturant_visible);
		resturantVisible.setChecked(resturant.isVisible());
		Log.i("PrefAreaAdapter:getView","visible="+resturant.isVisible());
		if(creating) {
			resturantVisible.setOnClickListener(new CheckBoxListener(resturant));
		}

		final Spinner sortOrderSpinner = (Spinner)view.findViewById(R.id.pref_resturant_sortorder);
		ArrayAdapter<CharSequence> mAdapter;
		mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.pref_resturant_sortorder, android.R.layout.simple_spinner_item); 
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sortOrderSpinner.setAdapter(mAdapter);
		sortOrderSpinner.setSelection(resturant.getSortOrder());
		Log.i("PrefAreaAdapter:getView","sortorder="+resturant.getSortOrder());
		if(creating) {
			sortOrderSpinner.setOnItemSelectedListener(new SpinnerListener(resturant));
		}
		
		return view;
	}
}
