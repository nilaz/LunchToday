package se.lanzen.LunchToday;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
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
			Log.i("onCreate", "Bad url");
			showAlertDialogBadUrlToArea();
		}

        // Read json file with default area
        JSONObject jsonDefaultArea = getJSONfromURL((String) getResources().getText(R.string.defaultArea));
		if(mArea.setArea(jsonDefaultArea)) {
			resturants = mArea.getResturantArray();
	        final ResturantAdapter adapter = new ResturantAdapter(this, resturants);

	        mResturantListView = (ResturantListView)findViewById(R.id.my_list);
	        mResturantListView.setAdapter(adapter);
	        
		} else {
			Log.e("no server", "Bad url");
			showAlertDialogNoServer();
		}
		setTitle(getString(R.string.app_name) + " " + mArea.getCurrentDate());
		//checkForNewVersion();
    }

	private void checkForNewVersion() {
		int currentVersion = getCurrentVersion();
		int latestVersion = getLatestVersion();
		if(currentVersion < latestVersion) {
			showAlertDialogNewVersionAvailable();
		}
		
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

	private int getLatestVersion() {
		JSONObject json = getJSONfromURL((String) getResources().getText(R.string.latestVersionURL));
		try {
			return json.getInt((String) getResources().getText(R.string.latestVersion));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
    
	private  JSONObject getJSONfromURL(String url){
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		System.out.println("First");

		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(builder.toString());
		JSONObject object;
		try {
			object = new JSONObject(builder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return object;
	}
}