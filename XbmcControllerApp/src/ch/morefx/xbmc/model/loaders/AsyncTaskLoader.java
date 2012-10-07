package ch.morefx.xbmc.model.loaders;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import ch.morefx.xbmc.ContextResourceProvider;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.ThumbnailHolder;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.util.Check;

public abstract class AsyncTaskLoader<Params, Progress, Result>  
	extends AsyncTask<Params, Progress, Result> {

	private Context context;
	private PostExecuteHandler<Result> handler;
	
	private AsyncTaskCompleteListener<Result> callback;
	
	AsyncTaskLoader(Context context) {
		this.context = context;
	}
	
	/**
	 * Gets the current Context
	 * @return an instance of a Context
	 */
	protected Context getContext(){
		return this.context;
	}
	
	public AsyncTaskLoader<Params, Progress, Result> setPostExecuteHandler(PostExecuteHandler<Result> handler){
		this.handler = handler;
		return this;
	}
	
	public AsyncTaskLoader<Params, Progress, Result> setOnTaskCompleteListener(AsyncTaskCompleteListener<Result> callback){
		this.callback = callback;
		return this;
	}
	
	protected void fireOnTaskComplete(Result result){
		if (this.callback != null){
			this.callback.onTaskComplete(result);
		}
	}
	
	protected void onPostExecute(Result result) {
		if (this.handler != null){
			this.handler.onPostExecute(result);	
		}
	}
	
	protected void loadThumbnails(List<? extends ThumbnailHolder> holders){
		
		if (holders == null) {
			Log.e("AsyncTaskLoader", "holders is NULL");
			return;
		}
		
		for(ThumbnailHolder holder : holders){
			loadThumbnail(holder);
		}
	}
	
	protected void loadThumbnail(ThumbnailHolder holder){
		XbmcConnection connection = getConnection();
		connection.loadThumbnail(holder, new ContextResourceProvider(context));
	}
	
	protected XbmcConnection getConnection(){
		XbmcRemoteControlApplication application = (XbmcRemoteControlApplication)this.context.getApplicationContext();
		return application.getCurrentConnection();
	}
	
	protected AudioLibrary getAudioLibrary(){
		XbmcConnection connection = getConnection();
		
		Check.notNull(connection, "Current connection is not set!");
		return connection.getAudioLibrary();
	}
	
	protected VideoLibrary getVideoLibrary(){
		XbmcConnection connection = getConnection();
		
		Check.notNull(connection, "Current connection is not set!");
		return connection.getVideoLibrary();
	}
	
}
