package ch.morefx.xbmc;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ResourceProviderAdapter 
	implements ResourceProvider{

	private Context _context;
	
	public ResourceProviderAdapter(Context context) {
		_context = context;
	}
	
	public Drawable getDrawable(int resourceId) {
		return _context.getResources().getDrawable(resourceId);
	}
}
