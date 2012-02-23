package ch.morefx.xbmc.command;

import java.io.IOException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

// http://stackoverflow.com/questions/2052299/httpclient-on-android-nohttpresponseexception-through-umts-3g
// Nur ein Emulator Problem ??

public class HttpRequestRetryHandlerImpl implements HttpRequestRetryHandler {

	private static final String TAG = "HttpRequestRetryHandlerImpl";
	
	public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
    	Log.w(TAG, "retryRequest " + executionCount);
    	
        // retry a max of 10 times
        if(executionCount >= 10){
            return false;
        }
        if(exception instanceof NoHttpResponseException){
            return true;
        } else if (exception instanceof ClientProtocolException){
            return true;
        } 
        return false;
	}
}
