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
	public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        // retry a max of 10 times
        if(executionCount >= 10){
        	Log.w(getClass().getName(), "executionCount reached! " + executionCount);
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
