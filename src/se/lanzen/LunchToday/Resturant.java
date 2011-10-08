package se.lanzen.LunchToday;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Resturant {
	private String mName;
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
		Iterator<String> e = mMenuItems.iterator();
		boolean notLast = e.hasNext();
	    while (notLast) {
			menu = menu + e.next();
			notLast = e.hasNext();
			if(notLast)
				menu = menu + "\n";
	    }

		return menu;
	}

	public String getMenu() {
		return menu();
	}
}
