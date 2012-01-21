package se.lanzen.LunchToday;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.util.Log;

import com.google.android.maps.GeoPoint;

public class Resturant {
	private String mName;
	private boolean mVisible = true;
	private int mSortOrder = 2;
	private List<GeoPoint> mPolygon = null;
	
	final private List<String> mMenuItems = new ArrayList<String>();
	public Resturant(String name) {
		mName = name;
		mMenuItems.clear();
	}
	
	public String getName() {
		return mName;
	}
	
	public void setPolygon(List<GeoPoint> polygon) {
		mPolygon = polygon;
	}
	
	public void addMenuItem(String item) {
		mMenuItems.add(item);
	}

	public String menu() {
		String menu = "";
		boolean notFirst = false;
		for(String dish : mMenuItems) {
			if(notFirst) {
				menu += "\n";
			}
			menu += "- " + dish;
			notFirst = true;
		}
		return menu;
	}

	public String getMenu() {
		return menu();
	}

	public boolean isVisible() {
		return mVisible;
	}

	public void setVisible(boolean mVisible) {
		this.mVisible = mVisible;
	}

	public int getSortOrder() {
		return mSortOrder;
	}

	public void setSortOrder(int mSortOrder) {
		this.mSortOrder = mSortOrder;
	}

	public String getPolygonAsJSON() {
		if(mPolygon == null) {
			return null;
		}
		JSONArray jp = new JSONArray();
		try {
			for(GeoPoint coord : mPolygon) {
				JSONArray point = new JSONArray();
				point.put(coord.getLatitudeE6());
				point.put(coord.getLongitudeE6());
				jp.put(point);
			}
		} catch(Exception e) {
			return null;
		}
		Log.i("getPolygonAsJSON",jp.toString());
		return jp.toString();
	}
}
