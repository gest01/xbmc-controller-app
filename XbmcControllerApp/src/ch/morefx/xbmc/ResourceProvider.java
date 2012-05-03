package ch.morefx.xbmc;

import android.graphics.drawable.Drawable;

/**
 * 
 */
public interface ResourceProvider {
		
	/**
	 * 
	 * @param resourceId
	 * @return
	 */
	Drawable getDrawable(int resourceId);
}
