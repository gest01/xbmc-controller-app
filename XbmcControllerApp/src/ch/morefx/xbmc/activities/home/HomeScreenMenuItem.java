package ch.morefx.xbmc.activities.home;

import android.content.Intent;

public class HomeScreenMenuItem {
	
    private String title;
    private String description;
    private Intent intent;
    
    public HomeScreenMenuItem(String title, String description, Intent intent) {
        this.title = title;
        this.description = description;
        this.intent = intent;
    }
    
    public String getTitle() {
		return title;
	}

    public Intent getIntent() {
		return intent;
	}
    
    public String getDescription() {
		return description;
	}
    
    @Override
    public String toString() {
    	return getTitle();
    }
}