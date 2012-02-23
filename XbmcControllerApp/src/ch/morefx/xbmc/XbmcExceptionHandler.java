package ch.morefx.xbmc;

import java.lang.Thread.UncaughtExceptionHandler;

import android.util.Log;

public class XbmcExceptionHandler implements UncaughtExceptionHandler {

	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e("XbmcExceptionHandler", "**** Unhandled Exception Occured ****", ex);
	}

	
	public static void handleException(Throwable ex){
		handleException(Thread.currentThread(), ex);
	}
	
	public static void handleException(Thread thread, Throwable ex){
		Log.e("XbmcExceptionHandler", "FUCK!!!!");
		ex.printStackTrace();
		//Toast.makeText(null, ex.toString(), Toast.LENGTH_SHORT);
		//DialogUtil.showError(null, ex.toString(), "FUCK");
	}
}
