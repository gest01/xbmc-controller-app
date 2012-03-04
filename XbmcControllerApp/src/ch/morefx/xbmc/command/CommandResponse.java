package ch.morefx.xbmc.command;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import org.json.JSONArray;
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
	
	/**
	 * Gets a typed array from a json response. When response identified by param json is null, an empty array will be returned. 
	 * @param jsonField The field that contains the array
	 * @param typeOfClass Type of array.
	 * @return An array of type typeOfClass
	 */
	@SuppressWarnings("unchecked")
	public <T> T asArrayResult(String jsonField, Class<T> typeOfClass) {
		try {
			
			if (this.jsonResult.isNull(jsonField)){
				return (T)Array.newInstance(typeOfClass.getComponentType(), 0);
			}
			
			Gson g = this.builder.create();
			JSONArray jsonArrayResult = this.jsonResult.getJSONArray(jsonField);
			T typedArray = g.fromJson(jsonArrayResult.toString(), typeOfClass);
			return typedArray;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
