package se.lanzen.LunchToday;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;

public class ShowResturantsActivity extends Activity {
	ResturantArea mArea = new ResturantArea();
	
    /** The list view */
    private ResturantListView mResturantListView;
	
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ArrayList<Resturant> resturants = null;
        boolean foundArea = false;
        try {
        	foundArea = mArea.setArea(new URL("http://www.lanzen.se/LunchToday/MjardeviArea.properties"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if(foundArea) {
			resturants = mArea.getResturantArray();
		} else {
	        resturants = createResturantList(20);
		}

        final ResturantAdapter adapter = new ResturantAdapter(this, resturants);

        mResturantListView = (ResturantListView)findViewById(R.id.my_list);
        mResturantListView.setAdapter(adapter);

    }

	private ArrayList<Resturant> createResturantList(int size) {
        final ArrayList<Resturant> resturants = new ArrayList<Resturant>();
        for (int i = 0; i < size; i++) {
        	Resturant r = new Resturant("Resturang "+i);
        	r.addMenuItem("rätt 1");
        	r.addMenuItem("rätt 2");
        	r.addMenuItem("rätt 3");
            resturants.add(r);
        }
        return resturants;
	}

	/* Old
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  mArea = new ResturantArea();
	  mArea.setArea(getDefaultArea());
	  
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, mArea.getResturantNames()));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
//		      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//			          Toast.LENGTH_SHORT).show();
		      Toast.makeText(getApplicationContext(), mArea.getResturant((String)((TextView) view).getText()).getMenu(),
		    		  Toast.LENGTH_LONG).show();
	    }
	  });
	}
*/
	/*
	private URL getDefaultArea() {
		// TODO Hardcoded...
		URL url;
		try {
			url = new URL("http://www.lanzen.se/LunchToday/MjardeviArea.properties");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		return url;
	}	
	*/
}