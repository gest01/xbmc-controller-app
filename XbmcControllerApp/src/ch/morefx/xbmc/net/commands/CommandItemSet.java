package ch.morefx.xbmc.net.commands;

import java.util.LinkedHashMap;
import java.util.Map;

class CommandItemSet {
	LinkedHashMap<String, Object> items =  new LinkedHashMap<String, Object>();
	
	public void add(String key, Object value){
		items.put(key, value);	
	}
	
	public Map<String, Object> getItems(){
		return this.items;
	}
}