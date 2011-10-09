package se.lanzen.LunchToday;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class ShowResturantsActivity extends Activity {
	ResturantArea mArea = new ResturantArea();
	
    /** The list view */
    private ResturantListView mResturantListView;
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ArrayList<Resturant> resturants = null;
        boolean foundArea = false;
        try {
        	foundArea = mArea.setArea(new URL((String) getResources().getText(R.string.defaultArea)));
		} catch (MalformedURLException e) {
			// TODO Skapa alertdialog som säger att det inte gick att komma åt servern, avsluta sedan appen 
			e.printStackTrace();
		}
        
		if(foundArea) {
			resturants = mArea.getResturantArray();
	        final ResturantAdapter adapter = new ResturantAdapter(this, resturants);

	        mResturantListView = (ResturantListView)findViewById(R.id.my_list);
	        mResturantListView.setAdapter(adapter);
		} else {
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setMessage("Appen lyckades inte få kontakt med servern. Försök igen senare! " +
					getResources().getText(R.string.app_name) + " kommer att avslutas.");
			alt_bld.setCancelable(false);
			alt_bld.setPositiveButton("Avsluta", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
				}
			});
			AlertDialog alert = alt_bld.create();
			// Title for AlertDialog
			alert.setTitle("Problem");
			// Icon for AlertDialog
			alert.setIcon(R.drawable.icon);
			alert.show();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.about:
        	startActivity(new Intent(this, AboutActivity.class));
            return true;
        case R.id.area:
        	// TODO Hantera preference för current area
            return true;
        case R.id.preference:
        	// TODO Hantera preference för app
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
	private ArrayList<Resturant> createResturantList(int size) {
        final ArrayList<Resturant> resturants = new ArrayList<Resturant>();
        for (int i = 0; i < size; i++) {
        	Resturant r = new Resturant("Resturang "+i);
        	r.addMenuItem("rätt 1");
        	r.addMenuItem("rätt 2");
        	r.addMenuItem("rätt 3");
            resturants.add(r);
        }
        return resturants;
	}
}