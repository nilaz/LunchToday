package se.lanzen.LunchToday;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResturantAdapter extends ArrayAdapter<Resturant> {
    /** Re-usable contact image drawable */
    //private final Drawable contactImage;
    
    /**
     * Constructor
     * 
     * @param context The context
     * @param contacts The list of contacts
     */
    public ResturantAdapter(final Context context, final ArrayList<Resturant> resturants) {
        super(context, 0, resturants);
        //contactImage = context.getResources().getDrawable(R.drawable.resturant_image);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }
        final TextView name = (TextView)view.findViewById(R.id.resturant_name);
        name.setText((getItem(position)).getName());
    	//Log.e("getView:","Resturant="+(getItem(position)).getName());

        // Create menu for the resturant
        final LinearLayout menu = (LinearLayout)view.findViewById(R.id.menu_layout);
        menu.removeAllViews();
        for(String dish: (getItem(position)).getMenuList()) {
        	final RelativeLayout dishRL = (RelativeLayout)(LayoutInflater.from(getContext()).inflate(R.layout.dish_item, null));
        	final TextView dishTV = (TextView)dishRL.findViewById(R.id.menu_dish);
        	dishTV.setText(dish);
        	menu.addView(dishRL);
        }

        return view;
    }

}
