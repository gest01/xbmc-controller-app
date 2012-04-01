package ch.morefx.xbmc.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ch.morefx.xbmc.XbmcConnection;

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
	
	public void executeAsync(JsonCommand... commands){
		AsyncJsonCommandExecutor asyncExecutor = new AsyncJsonCommandExecutor(this);
		asyncExecutor.execute(commands);		
	}

	public final void execute(JsonCommand command) throws CommandExecutorException{

		DefaultHttpClient client = initializeHttpClient(this.connection);

		try {
			
			String jsonCommand = command.prepareCommand();
			String responseString = executeJsonCommand(jsonCommand, client);
			
			if (responseString == null){
				Log.w(TAG, "responseString is null");
				return;
			}
			
			if (responseString.contains("error")) {
				Log.e(TAG, "ERROR  : " + responseString);
				return;
			}
			
			JSONObject jsonResponse = new JSONObject(responseString);		
			if (!jsonResponse.isNull("result")) {
				handleJsonResult(command, jsonResponse);
			} else {
				Log.e(TAG, "ERROR : RESULT IS NULL OR EMPTY ");
			}
			
		} catch (Exception e) {
			throw new CommandExecutorException(e);
		}
	}

	private final void handleJsonResult(JsonCommand command, JSONObject jsonResponse) throws JSONException, Exception{
		
		if (command instanceof JsonCommandResponseHandler){
			JsonCommandResponse commandResponse = null;
			
			Object resultObject = jsonResponse.get("result");
			if (resultObject instanceof String)
				commandResponse = new JsonCommandResponse((String)resultObject);
			if (resultObject instanceof JSONObject)
				commandResponse = new JsonCommandResponse((JSONObject)resultObject);
			if (resultObject instanceof JSONArray)
				commandResponse = new JsonCommandResponse((JSONArray)resultObject);
								
			if (commandResponse == null)
				throw new Exception("unable to create a CommandResponse object from json result : " + resultObject.getClass()); // This should never happen!
			
			((JsonCommandResponseHandler)command).handleResponse(commandResponse);
				
		}
	}

	/**
	 * 
	 * @param jsonCommand
	 * @param httpClient
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private String executeJsonCommand(String jsonCommand, DefaultHttpClient httpClient) throws ClientProtocolException, IOException{
		
		String uri = this.connection.getXbmcConnectionUri();
		HttpPost post = new HttpPost(uri);
		
		StringEntity entity = new StringEntity(jsonCommand);
		entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		post.setEntity(entity);
		
		HttpResponse response = httpClient.execute(post);
		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String responseString = reader.readLine();
		
		Log.d(TAG, "RESPONSE: " + responseString);
		return responseString;
			
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
