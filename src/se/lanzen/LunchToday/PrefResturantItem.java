package se.lanzen.LunchToday;

import android.content.SharedPreferences;

public class PrefResturantItem {
	private String mResturantName;

	private String mVisibleTag;
	private boolean mVisible;
	private String mSortOrderTag;
	private int mSortOrder;

	public static final String VISIBLE_TAG="%s_%s_VISIBLE"; // area + resturant
	public static final String SORTORDER_TAG="%s_%s_SORTORDER"; // area + resturant

	public PrefResturantItem(SharedPreferences prefs, String areaName, String resturantName) {
		mResturantName = resturantName;
		mVisibleTag = String.format(VISIBLE_TAG,areaName, mResturantName);
		setVisible(prefs.getBoolean(mVisibleTag, true));
		mSortOrderTag = String.format(SORTORDER_TAG,areaName, mResturantName);
		setSortOrder(prefs.getInt(mSortOrderTag, 4));
		
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

	public String getResturantName() {
		return mResturantName;
	}

	public String getVisibleTab() {
		return mVisibleTag;
	}

	public String getSortOrderTag() {
		return mSortOrderTag;
	}

}
