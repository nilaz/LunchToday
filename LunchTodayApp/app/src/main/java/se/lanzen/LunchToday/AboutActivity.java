package se.lanzen.LunchToday;


import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class AboutActivity extends Activity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setTitle(getString(R.string.app_name) + " version " + getString(R.string.version));
        TextView tv = (TextView)findViewById( R.id.textView3 );
        Linkify.addLinks( tv, Linkify.WEB_URLS );
        tv = (TextView)findViewById( R.id.iconTextView2 );
        Linkify.addLinks( tv, Linkify.WEB_URLS );
    }

}
