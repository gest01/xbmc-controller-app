package ch.morefx.xbmc.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ch.morefx.xbmc.BuildConfig;
import ch.morefx.xbmc.net.commands.JsonCommand;
import ch.morefx.xbmc.net.commands.JsonCommandResponse;
import ch.morefx.xbmc.net.commands.JsonCommandResponseHandler;
import ch.morefx.xbmc.util.Check;

public final class JsonCommandExecutor 
	implements CommandExecutor {

	private static final String TAG = "JsonCommandExecutor";

	private XbmcConnector connector;

	public JsonCommandExecutor(XbmcConnector connector) {
		Check.argumentNotNull(connector, "connector");
		
		this.connector = connector;
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
		try {
			
			if (BuildConfig.DEBUG)
				Log.d(TAG, String.format("SEND : %s", command.getMethod()));
			
			String jsonCommand = command.prepareCommand();
			String responseString = connector.send(jsonCommand);
			
			if (responseString == null){
				Log.w(TAG, "responseString is null");
				return;
			}
			
//			if (responseString.trim().contains("error")) {
//				Log.e(TAG, "ERROR  : " + responseString);
//				return;
//			}
			
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
			if (resultObject instanceof Integer)
				commandResponse = new JsonCommandResponse(resultObject.toString());
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
}
