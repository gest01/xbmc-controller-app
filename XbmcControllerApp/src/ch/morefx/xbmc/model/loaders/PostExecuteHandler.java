package ch.morefx.xbmc.model.loaders;

public interface PostExecuteHandler<Result>{
	
	/**
	 * Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}
	 */
	void onPostExecute(Result result);
}