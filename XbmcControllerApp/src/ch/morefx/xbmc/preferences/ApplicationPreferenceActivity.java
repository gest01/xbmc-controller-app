package ch.morefx.xbmc.preferences;

import android.app.Activity;
import android.os.Bundle;
import ch.morefx.xbmc.R;

public class ApplicationPreferenceActivity extends Activity{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_preferences_layout);
    }
	
}
