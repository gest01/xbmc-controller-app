package ch.morefx.xbmc.command;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import ch.morefx.xbmc.XbmcExceptionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommandResponse {

	private static final String TAG = "CommandResponse";
	
	private JSONObject jsonResult;
	private GsonBuilder builder;
	
	CommandResponse(JSONObject jsonResult) {
		this.jsonResult = jsonResult;
		this.builder = new GsonBuilder();
	}
	
	/**
	 * 
	 * @param json
	 * @param typeOfResultClass
	 * @param type the type definition for the type adapter being registered
	 * @param typeAdapter This object must implement at least one of the TypeAdapter, InstanceCreator, JsonSerializer, and a JsonDeserializer interfaces.
	 * @return
	 */
	public <T> T asArrayResultWithCreator(String json, Class<T> typeOfResultClass, Type type, Object typeAdapter) {
		this.builder.registerTypeAdapter(type, typeAdapter);
		return asArrayResult(json, typeOfResultClass);
	}
	
	/**
	 * Gets a typed array from a json response. When response identified by param json is null, an empty array will be returned. 
	 * @param jsonField The field that contains the array
	 * @param typeOfResultClass Type of result array.
	 * @return An array of type typeOfClass
	 */
	@SuppressWarnings("unchecked")
	public <T> T asArrayResult(String jsonField, Class<T> typeOfResultClass) {
		try {
			
			if (this.jsonResult.isNull(jsonField)){
				return (T)Array.newInstance(typeOfResultClass.getComponentType(), 0);
			}
			
			Gson g = this.builder.create();
			JSONArray jsonArrayResult = this.jsonResult.getJSONArray(jsonField);
			T typedArray = g.fromJson(jsonArrayResult.toString(), typeOfResultClass);
			return typedArray;
			
		} catch (Exception e) {
			XbmcExceptionHandler.handleException(TAG, "json deserialization error", e);
		}
		
		return null;
	}
}
