package ch.morefx.xbmc.util;



public class CastHelper {

	public static <T> T cast(Object source, Class<T> destination) {
		Check.argumentNotNull(source, "source");
		
		if (destination.isAssignableFrom(source.getClass())){
			return destination.cast(source);
		} 
		
		throw new IllegalStateException("Unable to cast " + source.getClass() + " to " + destination.getName());		
	}
}
