package ch.morefx.xbmc.util;

import java.io.Serializable;

import android.app.Activity;

public final class ExtrasHelper {
	
	/**
	 * Extracts extras from an Intent message
	 * @param a The activity
	 * @param key Key
	 * @param typeOfResultClass Type of Result
	 * @return result
	 */
	public static <T> T getExtra(Activity a, String key, Class<T> typeOfResultClass) {
		Serializable serializable = a.getIntent().getExtras().getSerializable(key);
		Check.notNull(serializable, "Serializable " + key + " not found in " + a.getClass().getCanonicalName() + " extras!" );
		Check.assertion(typeOfResultClass.getClass() == serializable.getClass(), "Serializable is of type " + serializable.getClass() + " instead of " + typeOfResultClass);
		
		return typeOfResultClass.cast(serializable);
	}
}
