package ch.morefx.xbmc.net.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import ch.morefx.xbmc.ResourceProvider;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.util.Check;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class XbmcNotification
	implements Notification {

	private static final String TAG = XbmcNotification.class.getName();
	
	private String jsonrpc;
	private String method;
	private JSONObject jsonObject, dataSection, paramsSection;
	
	private String type;
	
	public final void setJSONObject(JSONObject jsonObject) throws JSONException{
		Check.argumentNotNull(jsonObject, "jsonObject");
		
		this.jsonObject = jsonObject;
		this.method = jsonObject.getString("method");
		this.jsonrpc = jsonObject.getString("jsonrpc");
		
		this.paramsSection = (JSONObject)jsonObject.get("params");
		this.dataSection = (JSONObject)this.paramsSection.get("data");
		
		JSONObject item = (JSONObject)this.dataSection.get("item");
		type = item.getString("type");
	}
	
	protected int getIdFromItem(){
		try {
			return this.jsonObject.getJSONObject("params").getJSONObject("data").getJSONObject("item").getInt("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	protected PlayerInfo getPlayerInfo(){
		if (this.dataSection.isNull("player"))
			return null;
		
		try {
			JSONObject jsonPlayer = this.dataSection.getJSONObject("player");
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(jsonPlayer.toString(), PlayerInfo.class);
		}  catch (JSONException e) {
			XbmcExceptionHandler.handleException(TAG, e);
		}
		
		return null;
	}
	
	protected boolean isSong(){
		return this.type.equals("song");
	}
	
	protected boolean isMovie(){
		return this.type.equals("movie");
	}
	
	public JSONObject getJSONObject(){
		return this.jsonObject;
	}
	
	public String getJsonRpc(){
		return jsonrpc;
	}
	
	public String getMethod(){
		return this.method;
	}

		
	public abstract String handle(XbmcConnection connection, ResourceProvider resourceprovider);
	
	@Override
	public String toString() {
		return String.format("jsonrpc=%s;method=%s", jsonrpc, method );
	}
	
	protected static class PlayerInfo {
		private int playerid, speed;
		
		public int getPlayerId() { return this.playerid; }
		public int getSpeed() { return this.speed; }
	}
}
