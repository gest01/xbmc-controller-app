package ch.morefx.xbmc;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ContextResourceProvider 
	implements ResourceProvider {

	private Context _context;
	
	public ContextResourceProvider(Context context) {
		_context = context;
	}
	
	public Drawable getDrawable(int resourceId) {
		return _context.getResources().getDrawable(resourceId);
	}
}
