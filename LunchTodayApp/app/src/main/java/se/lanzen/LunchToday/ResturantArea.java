package se.lanzen.LunchToday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.content.SharedPreferences;

public class ResturantArea {
	public static final String AREA_NAME = "Area_Name";
	public static final String RESTURANT_NAME ="Resturant_%d_Name";
	public static final String RESTURANT_MENUITEM ="Resturant_%d_MenuItem_%d";
	public static final String CURRENT_DATE = "CurrentDate";
	public static final String LATEST_VERSION = "LatestVersion";
	public static final String RESTURANT_LIST = "ResturantList";
	public static final String RESTURANT = "Resturant";
	public static final String MENU = "Menu";
	public static final String POLYGON = "Polygon";
	
	final private ArrayList<Resturant> mResturants = new ArrayList<Resturant>();
	private String mAreaName = null;
	private String mCurrentDate = null;
	private SharedPreferences mPrefs;
	
	public ResturantArea(SharedPreferences prefs) {
		mAreaName = null;
		mResturants.clear();
		mPrefs = prefs;
	}

	public boolean setArea(JSONObject object) {
		mAreaName = null; 
		mCurrentDate = null;
		mResturants.clear();

		try {
			mAreaName = object.getString(AREA_NAME);
		
			// Read date
			mCurrentDate = object.getString(CURRENT_DATE);
			
			JSONArray resturants = object.getJSONArray(RESTURANT_LIST);
			for(int i = 0; i < resturants.length(); i++) {
				JSONObject jsonRes = resturants.getJSONObject(i);
				Resturant resturant = new Resturant(jsonRes.getString(RESTURANT));
				resturant.setVisible(mPrefs.getBoolean(String.format(PrefResturantItem.VISIBLE_TAG,mAreaName, resturant.getName()), true)); 
				resturant.setSortOrder(mPrefs.getInt(String.format(PrefResturantItem.SORTORDER_TAG,mAreaName, resturant.getName()), 2)); 
				mResturants.add(resturant);
				JSONArray menu = jsonRes.getJSONArray(MENU);
				for(int j = 0; j < menu.length(); j++) {
					resturant.addMenuItem(menu.getString(j));
				}
				List<GeoPoint> polygon = new ArrayList<GeoPoint>(); 
				try {
					JSONArray jsonPolygon = jsonRes.getJSONArray(POLYGON);
					for(int c = 0; c < jsonPolygon.length(); c++) {
						JSONArray jsonCoord = jsonPolygon.getJSONArray(c);
						polygon.add(new GeoPoint((int)(jsonCoord.getDouble(1)*1E6),
												  (int)(jsonCoord.getDouble(0)*1E6)));
					}
				} catch(JSONException e) {}
				resturant.setPolygon(polygon);
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
	public ArrayList<String> getArrayListOfResturantNames() {
		ArrayList<String> list = new ArrayList<String>();
		for(Resturant resturant : mResturants) {
			list.add(resturant.getName());
		}
		return list;
	}

	public ArrayList<Resturant> getFilteredResturantArray() {
		ArrayList<Resturant> filteredResturants = new ArrayList<Resturant>();
		for(Resturant r : mResturants) {
			if(r.isVisible()) {
				filteredResturants.add(r);
			}
		}
		Collections.sort(filteredResturants, new SortOrder());
		return filteredResturants;
	}

	private class SortOrder implements Comparator<Resturant> {

	    @Override
	    public int compare(Resturant r1, Resturant r2) {

	        int rank1 = r1.getSortOrder();
	        int rank2 = r2.getSortOrder();

	        if (rank1 > rank2){
	            return +1;
	        }else if (rank1 < rank2){
	            return -1;
	        }else{
	            return 0;
	        }
	    }
	}
}
