package se.lanzen.LunchToday;

import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResturantArea {
	public static final String AREA_NAME = "Area_Name";
	public static final String RESTURANT_NAME ="Resturant_%d_Name";
	public static final String RESTURANT_MENUITEM ="Resturant_%d_MenuItem_%d";
	public static final String CURRENT_DATE = "CurrentDate";
	public static final String LATEST_VERSION = "LatestVersion";
	public static final String RESTURANT_LIST = "ResturantList";
	public static final String RESTURANT = "Resturant";
	public static final String MENU = "Menu";
	
	final private ArrayList<Resturant> mResturants = new ArrayList<Resturant>();
	private String mAreaName = null;
	private String mCurrentDate = null;
	
	public ResturantArea() {
		mAreaName = null;
		mResturants.clear();
	}
	public boolean setArea(JSONObject object) {
		mAreaName = "Test";
		mCurrentDate = "FooBar";
		
		try {
			mAreaName = object.getString(AREA_NAME);
		
			// Read date
			mCurrentDate = object.getString(CURRENT_DATE);
			
			JSONArray resturants = object.getJSONArray(RESTURANT_LIST);
			for(int i = 0; i < resturants.length(); i++) {
				JSONObject jsonRes = resturants.getJSONObject(i);
				Resturant resturant = new Resturant(jsonRes.getString(RESTURANT));
				mResturants.add(resturant);
				JSONArray menu = jsonRes.getJSONArray(MENU);
				for(int j = 0; j < menu.length(); j++) {
					resturant.addMenuItem(menu.getString(j));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getAreaName() {
		return mAreaName;
	}
	
	public String getCurrentDate() {
		return mCurrentDate;
	}
	
	public List<String> getResturantNames() {
		List<String> r = new ArrayList<String>();
		Iterator<Resturant> e = mResturants.iterator();
	    while (e.hasNext()) {
	      r.add(e.next().getName());
	    }
		return r;
	}

	public Resturant getResturant(String name) {
		Resturant r;
		Iterator<Resturant> e = mResturants.iterator();
	    while (e.hasNext()) {
	      r = e.next();
	      if(name.compareTo(r.getName()) == 0) {
	    	  return r;
	      }
	    }
	    return null;
	}
	public ArrayList<Resturant> getResturantArray() {
        return mResturants;
	}
	
}
