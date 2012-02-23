package ch.morefx.xbmc.util;

public final class Check {

	public static void notNull(Object argument, String message){
		if (argument == null){
			throw new NullPointerException(message);
		}
	}
	
	public static void assertion(boolean assertion, String message){
		assert assertion : message;
	}
}
