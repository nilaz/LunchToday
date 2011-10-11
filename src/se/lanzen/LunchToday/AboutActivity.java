package se.lanzen.LunchToday;


import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setTitle(getString(R.string.app_name) + " version " + getString(R.string.version));
    }

}
