package ch.morefx.xbmc.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import android.util.Log;

public abstract class LibraryItem 
	implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String label;
	protected int id;

	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
		
	public void printDebugString(){
		printDebugString(getClass());
	}
	
	
	private void printDebugString(Class<?> clazz){
		if (clazz == null) return;
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			try {
				field.setAccessible(true);
				Object value = field.get(this);
				Log.d(getClass().getCanonicalName(), field.getName() + " = " + value);
				
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		printDebugString(clazz.getSuperclass());
	}
}
