package se.lanzen.LunchToday;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class ShowResturantsActivity extends ListActivity {
	private ResturantArea mArea;
	private SharedPreferences mPrefs;
	private String mActiveLunchAreaName;
	public static final String PREF_LUNCH_AREA = "PREF_LUNCH_AREA";
	private static final int SHOW_PREFERENCES = 1;
	private static final int AREA_PREFERENCES = 2;
	private static final int SHOW_MAP= 3;
	private SharedPreferences.OnSharedPreferenceChangeListener mPrefsListener = 
			new SharedPreferences.OnSharedPreferenceChangeListener() { 
				@Override
				public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		    		// Log.i("PrefsListener:initPrefs","onSharedPreferenceChanged");
		    		// Log.i("PrefsListener:initPrefs","key="+key);
					if(key.equals(PREF_LUNCH_AREA)) {
			    		// Log.i("PrefsListener:initPrefs","Lunch area updated");
			            mActiveLunchAreaName = mPrefs.getString(PREF_LUNCH_AREA, (String) getResources().getText(R.string.defaultArea));
						refreshResturantArea();
					}
					
				} 
			}; 
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPrefs();
        mArea = new ResturantArea(mPrefs); 
        refreshResturantArea();
        checkForNewVersion();
        checkForUpdatedMenu();
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                     //i couldn't reach here
             Resturant r = (Resturant)(parent.getItemAtPosition(position));
             // Log.v("Onclicklistener","Clicked at "+r.getName());
             String polygonJSON = r.getPolygonAsJSON();
             // Log.v("Onclicklistener","Clicked at "+r.getName() + " Polygon="+polygonJSON);
             Intent intent = new Intent(getApplicationContext(), ResturantMap.class);
         	 intent.putExtra((String) getResources().getText(R.string.map_resturant_name), r.getName());
         	 intent.putExtra((String) getResources().getText(R.string.map_polygon), polygonJSON);
         	 startActivityForResult(intent, SHOW_MAP);
        }
      }); 
    }

    private void initPrefs() {
    	mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mActiveLunchAreaName = mPrefs.getString(PREF_LUNCH_AREA, (String) getResources().getText(R.string.defaultArea));
        // Register callback to be called when app preference updated
        mPrefs.registerOnSharedPreferenceChangeListener(mPrefsListener); 
      
	}

	@Override public void onActivityResult(int requestCode,int resultCode,Intent data) { 
    	super.onActivityResult(requestCode,resultCode,data); 
    	if(requestCode == SHOW_PREFERENCES) { 
    		// Log.i("ShowResturantsActivity:onActivityResult","Back from preference");
    	}
    	if(requestCode == AREA_PREFERENCES) { 
    		if(resultCode == RESULT_OK) {
        		// Log.i("ShowResturantsActivity:onActivityResult","Area uppdaterad");
        		refreshResturantArea();
    		}
    	}
    }
	private void refreshResturantArea() {
        // Read json file with default area
        JSONObject jsonDefaultArea = getJSONfromURL(mActiveLunchAreaName);
		if(mArea.setArea(jsonDefaultArea)) {
			ArrayList<Resturant> resturants = mArea.getFilteredResturantArray();
			final ResturantAdapter adapter = new ResturantAdapter(this, resturants);
	        adapter.setNotifyOnChange(false);
			this.setListAdapter(adapter);
			adapter.notifyDataSetChanged();
			setTitle(getString(R.string.app_name) + " " + mArea.getCurrentDate());
		} else {
			// Log.e("no server", "Bad url");
			showAlertDialogNoServer();
		}
	}

	private void checkForUpdatedMenu() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(new Date());
		String genDate = mArea.getCurrentDate();
		currDate = genDate;
		if( ! currDate.equals(genDate)) {
			showAlertDialogNoMenuforToday(currDate, genDate);
		}
	}

	private void showAlertDialogNoMenuforToday(String currDate, String genDate) {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Notera att menyn är aktuell för " + genDate +
				" och visar inte dagens meny. Det genereras bara menyer för vardagar.");
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Do nothing
			}
		});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("Menyn ej aktuell");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.ic_launcher);
		alert.show();
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
			e.printStackTrace();
		}
		return 0;
	}

	private void showAlertDialogNewVersionAvailable() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Det finns en nyare version av " + getString(R.string.app_name) + ". Gå till sidan http://blogg.lanzen.se/tag/dagens-lunch för att uppdatera!");
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
		alert.setIcon(R.drawable.ic_launcher);
		alert.show();
	}

	private void showAlertDialogNoServer() {
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
		alert.setIcon(R.drawable.ic_launcher);
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
    	// Log.e("ShowResturantsActivity:onOptionsItemSelected","item = " + item.getItemId());
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.about:
        	startActivity(new Intent(this, AboutActivity.class));
            return true;
        case R.id.area:
        	// Log.e("Menu area","Preference for area");
        	Intent intent = new Intent(this, PrefAreaActivity.class);
        	ArrayList<String> listOfResturantNames = mArea.getArrayListOfResturantNames(); 
        	// Log.e("Menu area","Array of names = " + listOfResturantNames);
        	intent.putExtra((String) getResources().getText(R.string.pref_resturant_names), listOfResturantNames);
        	intent.putExtra((String) getResources().getText(R.string.pref_current_area_name), mArea.getAreaName());
        	startActivityForResult(intent, AREA_PREFERENCES);
            return true;
//        case R.id.preference:
//        	startActivityForResult(new Intent(this, AppPreference.class), SHOW_PREFERENCES);            
//        	return true;
            //TODO Lägg tillbaka preference för att välja area
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
	private JSONObject getJSONfromURL(String url){
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content,"UTF-8"));
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
		JSONObject object;
		try {
			object = new JSONObject(builder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return object;
	}
}