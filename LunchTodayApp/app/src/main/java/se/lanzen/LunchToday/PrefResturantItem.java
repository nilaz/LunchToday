package se.lanzen.LunchToday;

import android.content.SharedPreferences;

public class PrefResturantItem {
	private String mResturantName;

	private String mVisibleTag;
	private boolean mVisible;
	private boolean mVisibleUpdated = true; // Will be toggled in constructor
	private String mSortOrderTag;
	private boolean mSortOrderUpdated = false; // Will be toggled in constructor
	private int mSortOrder;

	public static final String VISIBLE_TAG="%s_%s_VISIBLE"; // area + resturant
	public static final String SORTORDER_TAG="%s_%s_SORTORDER"; // area + resturant

	public PrefResturantItem(SharedPreferences prefs, String areaName, String resturantName) {
		mResturantName = resturantName;
		mVisibleTag = String.format(VISIBLE_TAG,areaName, mResturantName);
		setVisible(prefs.getBoolean(mVisibleTag, true));
		mSortOrderTag = String.format(SORTORDER_TAG,areaName, mResturantName);
		setSortOrder(prefs.getInt(mSortOrderTag, 2));
		
	}

	public boolean isVisible() {
		return mVisible;
	}

	public void setVisible(boolean mVisible) {
		mVisibleUpdated = ! mVisibleUpdated;
		this.mVisible = mVisible;
	}

	public int getSortOrder() {
		return mSortOrder;
	}

	public void setSortOrder(int mSortOrder) {
		mSortOrderUpdated = ! mSortOrderUpdated;
		this.mSortOrder = mSortOrder;
	}

	public String getResturantName() {
		return mResturantName;
	}

	public String getVisibleTag() {
		return mVisibleTag;
	}

	public String getSortOrderTag() {
		return mSortOrderTag;
	}
	public boolean visibleIsUpdated() {
		return mVisibleUpdated;
	}
	
	public boolean sortOrderIsUpdated() {
		return mSortOrderUpdated;
	}

}
