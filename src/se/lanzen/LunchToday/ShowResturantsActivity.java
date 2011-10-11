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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class ShowResturantsActivity extends Activity {
	ResturantArea mArea = new ResturantArea();
	
    /** The list view */
    private ResturantListView mResturantListView;
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ArrayList<Resturant> resturants = null;
        URL url = null;
        try {
        	url = new URL((String) getResources().getText(R.string.defaultArea));
		} catch (MalformedURLException e) {
			showAlertDialogBadUrlToArea();
		}
        
		if(mArea.setArea(url)) {
			resturants = mArea.getResturantArray();
	        final ResturantAdapter adapter = new ResturantAdapter(this, resturants);

	        mResturantListView = (ResturantListView)findViewById(R.id.my_list);
	        mResturantListView.setAdapter(adapter);
	        
		} else {
			showAlertDialogNoServer();
		}
		setTitle(getString(R.string.app_name) + " " + mArea.getCurrentDate());
		checkForNewVersion();
    }

	private void checkForNewVersion() {
		int currentVersion = getCurrentVersion();
		int latestVersion = mArea.getLatestVersion();
		if(currentVersion < latestVersion) {
			showAlertDialogNewVersionAvailable();
		}
		
	}

	private void showAlertDialogNewVersionAvailable() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Det finns en nyare version av " + getString(R.string.app_name) + ". G� till sidan http://blogg.lanzen.se/tag/dagens-lunch f�r att uppdatera!");
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Do nothing
			}
		});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("Ny version");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();

		
	}

	private int getCurrentVersion() {
		PackageInfo pinfo;
		try {
			pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			return pinfo.versionCode;
		} catch (NameNotFoundException e) {
			// Can not happen
		}
		return 999999;
	}

	private void showAlertDialogBadUrlToArea() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Trasig url till restuarant area. �ndra i inst�llningarna");
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Do nothing
			}
		});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("Problem");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}

	private void showAlertDialogNoServer() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Appen lyckades inte f� kontakt med servern. F�rs�k igen senare! " +
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
        	// TODO Hantera preference f�r current area
            return true;
        case R.id.preference:
        	// TODO Hantera preference f�r app
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
//	private ArrayList<Resturant> createResturantList(int size) {
//        final ArrayList<Resturant> resturants = new ArrayList<Resturant>();
//        for (int i = 0; i < size; i++) {
//        	Resturant r = new Resturant("Resturang "+i);
//        	r.addMenuItem("r�tt 1");
//        	r.addMenuItem("r�tt 2");
//        	r.addMenuItem("r�tt 3");
//            resturants.add(r);
//        }
//        return resturants;
//	}
}