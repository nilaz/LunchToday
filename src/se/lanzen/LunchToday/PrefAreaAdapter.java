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

public class PrefAreaAdapter extends ArrayAdapter<PrefResturantItem> {
	

	public PrefAreaAdapter(Context context, final ArrayList<PrefResturantItem> items) {
		super(context, 0, items);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.pref_resturant_item, null);
    	}

		final TextView name = (TextView)view.findViewById(R.id.pref_resturant_name);
		name.setText(getItem(position).getResturantName());

		final CheckBox resturantVisible = (CheckBox)view.findViewById(R.id.pref_resturant_visible);
		resturantVisible.setChecked(getItem(position).isVisible());

		final Spinner sortOrderSpinner = (Spinner)view.findViewById(R.id.pref_resturant_sortorder);
		ArrayAdapter<CharSequence> mAdapter;
		mAdapter = ArrayAdapter.createFromResource(getContext(), R.array.pref_resturant_sortorder, android.R.layout.simple_spinner_item); 
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		sortOrderSpinner.setAdapter(mAdapter);
		sortOrderSpinner.setSelection(getItem(position).getSortOrder());
		
		return view;
	}


	
}
