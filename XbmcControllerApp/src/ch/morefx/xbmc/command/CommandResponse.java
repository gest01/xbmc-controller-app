package ch.morefx.xbmc.command;

import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommandResponse {

	private JSONObject jsonResult;
	private GsonBuilder builder;
	
	public CommandResponse(JSONObject jsonResult) {
		this.jsonResult = jsonResult;
		this.builder = new GsonBuilder();
	}
	
	
	public <T> T asArrayResultWithCreator(String json, Class<T> typeOfClass, Type type, Object typeAdapter) {
		this.builder.registerTypeAdapter(type, typeAdapter);
		return asArrayResult(json, typeOfClass);
	}
	
	public <T> T asArrayResult(String json, Class<T> typeOfClass) {
		try {
			
			Gson g = this.builder.create();
			
			JSONArray jsonArrayResult = this.jsonResult.getJSONArray(json);
			T typedArray = g.fromJson(jsonArrayResult.toString(), typeOfClass);
			return typedArray;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
