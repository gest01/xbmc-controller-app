package ch.morefx.xbmc.command;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class CommandBuilder{
	
	private String jsonrpc = "2.0";
	private int id = 1;
	private String method;
	
	private Map<String, Object> params;
	
	public CommandBuilder(String jsonMethod) {
		method = jsonMethod;
	}
	
	public void setSortMethod(String value){
		
		if (this.params == null)
			this.params = new LinkedHashMap<String, Object>();
		
		this.params.put("sort", new Sort(value));
	}
	
	public void addParams(String key, int value)
	{
		if (this.params == null)
			this.params = new LinkedHashMap<String, Object>();
		
		this.params.put(key, value);
	}
	
	public void addParams(String key, String value)
	{
		if (this.params == null)
			this.params = new LinkedHashMap<String, Object>();
		
		this.params.put(key, value);
	}
	
	public void addParams(String key, String[] values)
	{
		if (this.params == null)
			this.params = new LinkedHashMap<String, Object>();
		
		this.params.put(key, values);
	}
	
	public void addParams(CommandItemSet itemSet)
	{
		if (this.params == null)
			this.params = new LinkedHashMap<String, Object>();
		
		this.params.put("item", itemSet.getItems());
	}
	
	private static class Sort{
		private final String method;
		public Sort(String method) {
			this.method = method;
		}
	}
}