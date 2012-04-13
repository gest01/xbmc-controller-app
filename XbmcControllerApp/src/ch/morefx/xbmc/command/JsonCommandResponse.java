package ch.morefx.xbmc.command;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.morefx.xbmc.XbmcExceptionHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonCommandResponse {

	private static final String TAG = "CommandResponse";
	
	private JSONArray jsonArrayResult;
	private JSONObject jsonResult;
	private GsonBuilder builder;
	private String rawResult = null;
	
	public JsonCommandResponse(JSONArray array) throws Exception {
		this.rawResult = array.toString();
		this.jsonArrayResult = array;
		this.builder = new GsonBuilder();
	}

	public JsonCommandResponse(String result) {
		 this.rawResult = result;
	}
	
	public JsonCommandResponse(JSONObject jsonResult) {
		this.rawResult = jsonResult.toString();
		this.jsonResult = jsonResult;
		this.builder = new GsonBuilder();
	}
	
	/**
	 * 
	 * @param jsonField The field that contains the array
	 * @param typeOfResultClass
	 * @param type the type definition for the type adapter being registered
	 * @param typeAdapter This object must implement at least one of the TypeAdapter, InstanceCreator, JsonSerializer, and a JsonDeserializer interfaces.
	 * @return
	 */
	public <T> T asArrayResultWithCreator(String jsonField, Class<T> typeOfResultClass, Type type, Object typeAdapter) {
		this.builder.registerTypeAdapter(type, typeAdapter);
		return asArrayResult(jsonField, typeOfResultClass);
	}
	
	public <T> T asArrayResultWithCreator(Class<T> typeOfResultClass, Type type, Object typeAdapter) {
		this.builder.registerTypeAdapter(type, typeAdapter);
		return toArrayResult(this.jsonArrayResult, typeOfResultClass);
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
			
			JSONArray jsonArrayResult = this.jsonResult.getJSONArray(jsonField);
			return toArrayResult(jsonArrayResult, typeOfResultClass);
			
		} catch (JSONException e) {
			XbmcExceptionHandler.handleException(TAG, "json deserialization error", e);
		}
		
		return null;
	}
	
	public <T> T asObjectResult(Class<T> typeOfResultClass) {
		try {
			
			Gson g = this.builder.create();
			return g.fromJson(jsonResult.toString(), typeOfResultClass);
			
		} catch (JsonSyntaxException e) {
			XbmcExceptionHandler.handleException(TAG, "json deserialization error", e);
		}
		
		return null;
	}
	
	private <T> T toArrayResult(JSONArray jsonArrayResult, Class<T> typeOfResultClass) {
		try {
			
			Gson g = this.builder.create();
			return g.fromJson(jsonArrayResult.toString(), typeOfResultClass);
			
		} catch (JsonSyntaxException e) {
			XbmcExceptionHandler.handleException(TAG, "json deserialization error", e);
		}
		
		return null;
	}
	
	public String getRawResult(){
		return this.rawResult;
	}
}
