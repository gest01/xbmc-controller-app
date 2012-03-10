package ch.morefx.xbmc.model;

import android.graphics.drawable.Drawable;

public interface ThumbnailHolder {
	
	String getThumbnailUri();  	
	Drawable getThumbnail();
	void setThumbnail(Drawable thumbail);
	
	int getThumbnailFallbackResourceId();
}