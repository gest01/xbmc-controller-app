package ch.morefx.xbmc.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ch.morefx.xbmc.CommandExecutor;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;

public final class JsonCommandExecutor 
	implements CommandExecutor {

	private static final String TAG = "JsonCommandExecutor";

	private XbmcConnection connection;

	public JsonCommandExecutor(XbmcConnection connection) {
		this.connection = connection;
	}
	
	public void executeAsync(JsonCommand command) {
		AsyncJsonCommandExecutor asyncExecutor = new AsyncJsonCommandExecutor(this);
		asyncExecutor.execute(command);
	}	
	
	public void executeAsync(JsonCommand... commands) {
		AsyncJsonCommandExecutor asyncExecutor = new AsyncJsonCommandExecutor(this);
		asyncExecutor.execute(commands);		
	}

	public final void execute(JsonCommand command) {
		DefaultHttpClient client = initializeHttpClient(this.connection);
		
		String jsonCommand = command.prepareCommand();
		String responseString = executeJsonCommand(jsonCommand, client);
		
		try {
			
			if (responseString == null){
				Log.w(TAG, "responseString is null");
				return;
			}
				
			
			if (responseString.contains("error")) {
				Log.e(TAG, "ERROR  : " + responseString);
			} else {
				
				JSONObject jsonResponse = new JSONObject(responseString);		
				if (!jsonResponse.isNull("result")) {
					
					Object resultObject = jsonResponse.get("result");
					if (resultObject instanceof JSONObject){
						if (command instanceof CommandResponseHandler){
							JSONObject jsonResult = (JSONObject)resultObject;
							CommandResponse commandResponse = new CommandResponse(jsonResult);
							((CommandResponseHandler)command).handleResponse(commandResponse);
						}
					}
					else if (resultObject instanceof String){
						// Nothing todo?!
					} else {
						Log.w(TAG, "Unhandled Result Object " + resultObject);
					}

				} else {
					Log.e(TAG, "ERROR : RESULT IS NULL OR EMPTY ");
				}
			}
			
		} catch (JSONException e) {
			Log.e(TAG, "JSON DESERIALIZATION ERROR");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public final String executeDirect(String command) {
		DefaultHttpClient client = initializeHttpClient(this.connection);
		return executeJsonCommand(command, client);
	}
	
	/**
	 * 
	 * @param jsonCommand
	 * @param httpClient
	 * @return
	 */
	private String executeJsonCommand(String jsonCommand, DefaultHttpClient httpClient){
		
		try {
			
			String uri = this.connection.getXbmcConnectionUri();
			HttpPost post = new HttpPost(uri);
			
			StringEntity entity = new StringEntity(jsonCommand);
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(entity);
			
			HttpResponse response = httpClient.execute(post);
			InputStream in = response.getEntity().getContent();
			java.io.BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String responseString = reader.readLine();
			Log.d(TAG, "RESPONSE: " + responseString);
			
			return responseString;
			
		} catch (UnsupportedEncodingException e) {
			XbmcExceptionHandler.handleException(TAG, "*** UnsupportedEncodingException ***", e);
		} catch (ClientProtocolException e) {
			XbmcExceptionHandler.handleException(TAG, "*** ClientProtocolException ***", e);
		} catch (IllegalStateException e) {
			XbmcExceptionHandler.handleException(TAG, "*** IllegalStateException ***", e);
		} catch (IOException e) {
			XbmcExceptionHandler.handleException(TAG, "*** IOException ***", e);
		}catch (Exception e) {
			XbmcExceptionHandler.handleException(TAG, "*** Exception ***", e);
		}
		
		return null;
	}
	
	
	private DefaultHttpClient _client;
	
	/**
	 * 
	 * @param connection
	 * @return
	 */
	private DefaultHttpClient initializeHttpClient(XbmcConnection connection){
		
		if (_client == null)
			_client = connection.createHttpClient();
		
		return _client;
	}
}
