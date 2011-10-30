package se.lanzen.LunchToday;

import java.util.ArrayList;
import java.util.List;

public class Resturant {
	private String mName;
	private boolean mVisible = true;
	private int mSortOrder = 2;
	
	final private List<String> mMenuItems = new ArrayList<String>();
	public Resturant(String name) {
		mName = name;
		mMenuItems.clear();
	}
	
	public String getName() {
		return mName;
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
}
