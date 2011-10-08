package se.lanzen.LunchToday;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResturantArea {
	public static final String AREA_NAME = "Area_Name";
	public static final String RESTURANT_NAME ="Resturant_%d_Name";
	public static final String RESTURANT_MENUITEM ="Resturant_%d_MenuItem_%d";
	final private ArrayList<Resturant> mResturants = new ArrayList<Resturant>();
	private String mAreaName = null;
	
	public ResturantArea() {
		mAreaName = null;
		mResturants.clear();
	}
	public boolean setArea(URL areaURL) {
		Properties prop = readPropertiesFromFile(areaURL);
		if(prop == null)
			return false;
		
		mAreaName = prop.getProperty(AREA_NAME);
		if(mAreaName == null)
			return false;

		// Read resturants
		return readResturants(prop);
	}
	private boolean readResturants(Properties prop) {
		Resturant resturant;
		for(int resturantIndex = 0; (prop.getProperty(String.format(RESTURANT_NAME,resturantIndex))) != null; resturantIndex++){
			String resturantName = prop.getProperty(String.format(RESTURANT_NAME,resturantIndex));
			resturant = new Resturant(resturantName);
			mResturants.add(resturant);
			// Read menu
			for(int item = 0; (prop.getProperty(String.format(RESTURANT_MENUITEM, resturantIndex,item))) != null; item++) {
				String menuItem = prop.getProperty(String.format(RESTURANT_MENUITEM, resturantIndex,item));
				if(menuItem == null)
					return false;
				resturant.addMenuItem(menuItem);
			}
		}
		return true;
	}

	public String getAreaName() {
		return mAreaName;
	}
	
	public List<String> getResturantNames() {
		List<String> r = new ArrayList<String>();
		Iterator<Resturant> e = mResturants.iterator();
	    while (e.hasNext()) {
	      r.add(e.next().getName());
	    }
		return r;
	}
	private static Properties readPropertiesFromFile(URL url){
		java.util.Properties defaultProps = new Properties();
	    InputStream in = null;

	    try {
			in = url.openStream();
			//BufferedInputStream bis = new BufferedInputStream(in);
			//defaultProps.load(new InputStreamReader(bis,"UTF-8"));
			defaultProps.load(in);
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
		return defaultProps;
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
