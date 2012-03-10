package ch.morefx.xbmc;

import java.lang.Thread.UncaughtExceptionHandler;

import android.util.Log;

public class XbmcExceptionHandler implements UncaughtExceptionHandler {

	private static final String TAG = "XbmcExceptionHandler";
	
	public void uncaughtException(Thread thread, Throwable ex) {
		handleException(thread, ex);
	}

	
	public static void handleException(final String message, Throwable ex){
		Log.e(TAG, message, ex);
	}
	
	public static void handleException(final String tag, final String message, Throwable ex){
		Log.e(tag, message, ex);
	}
	
	public static void handleException(Thread thread, Throwable ex){
		Log.e(TAG, "**** Unhandled Exception Occured ****", ex);
	}
}
