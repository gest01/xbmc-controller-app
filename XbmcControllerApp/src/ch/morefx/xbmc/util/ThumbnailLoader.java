package ch.morefx.xbmc.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import ch.morefx.xbmc.ContextResourceProvider;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.ThumbnailHolder;

/**
 * Loads Thumbnails for ThumbnailHolder objects async.
 */
public final class ThumbnailLoader {
	
	private final ThumbnailHolder holder;
	private final Context context;
	
	/**
	 * Creates and initializes a new instance of the <code>ThumbnailLoader</code> class.
	 * @param holder The thumbnailholder instance
	 * @param context The calling context
	 */
	public ThumbnailLoader(ThumbnailHolder holder, Context context) {
		Check.argumentNotNull(holder, "holder");
		Check.argumentNotNull(context, "context");
		
		this.holder = holder;
		this.context = context;
	}

	/**
	 * Load thumbnail and set the result into an ImageView 
	 * @param view The ImageView that holds the Thumbnail.
	 */
	public void loadIntoView(final ImageView view){
		Check.argumentNotNull(view, "view");
		
		Handler handler = new Handler(){
			@Override public void handleMessage(Message msg) {
				view.setImageDrawable(holder.getThumbnail());
			}
		};
		
		loadThumbnailAsync(handler);
	}
	
	
	/**
	 * Loads a thumbnail and set the result into the View as background 
	 * @param view The View that holds the Thumbnailas background
	 */
	public void loadIntoView(final View view){
		Check.argumentNotNull(view, "view");
		
		Handler handler = new Handler(){
			@Override public void handleMessage(Message msg) {
				view.setBackgroundDrawable(holder.getThumbnail());
			}
		};
		
		loadThumbnailAsync(handler);
	}
	
	private void loadThumbnailAsync(final Handler handler){
		Runnable runnable = new Runnable() {
			public void run() {
				XbmcConnection app = XbmcRemoteControlApplication.getInstance().getCurrentConnection();
				app.loadThumbnail(holder, new ContextResourceProvider(context));
				handler.sendEmptyMessage(0);
			}
		};
		
		new Thread(runnable).start();
	}
}
