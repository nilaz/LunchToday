package se.lanzen.LunchToday;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResturantAdapter extends ArrayAdapter<Resturant> {
    /** Re-usable contact image drawable */
    private final Drawable contactImage;

    /**
     * Constructor
     * 
     * @param context The context
     * @param contacts The list of contacts
     */
    public ResturantAdapter(final Context context, final ArrayList<Resturant> resturants) {
        super(context, 0, resturants);
        contactImage = context.getResources().getDrawable(R.drawable.resturant_image);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }

        final TextView name = (TextView)view.findViewById(R.id.resturant_name);
        name.setText((getItem(position)).getName());

        final TextView number = (TextView)view.findViewById(R.id.resturant_menu);
        number.setText((getItem(position)).getMenu());

        final ImageView photo = (ImageView)view.findViewById(R.id.resturant_photo);
        photo.setImageDrawable(contactImage);

        return view;
    }

}
