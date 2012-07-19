package ch.morefx.xbmc.activities.home;

import ch.morefx.xbmc.model.ThumbnailHolder;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class HomeScreenMenuItem {
	
    private String title;
    private String description;
    private Intent intent;
    
    private Drawable iconDrawable;
    private ThumbnailHolder thumbnailholder;
    
    public HomeScreenMenuItem(Drawable iconDrawable, String title, String description, Intent intent) {
        this.title = title;
        this.description = description;
        this.intent = intent;
        this.iconDrawable = iconDrawable;
    }
    
    public HomeScreenMenuItem(ThumbnailHolder thumbnailholder, String title, String description, Intent intent) {
        this.title = title;
        this.description = description;
        this.intent = intent;
        this.iconDrawable = null;
        this.thumbnailholder = thumbnailholder;
    }
    
    public Drawable getIconDrawable(){
    	return this.iconDrawable;
    }
    
    public ThumbnailHolder getThumbnailHolder(){
    	return this.thumbnailholder;
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