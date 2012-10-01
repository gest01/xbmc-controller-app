package ch.morefx.xbmc.util;

import java.io.Serializable;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

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
	
	/**
	 *  Extracts extras from an Fragment
	 * @param fragment The Fragment
	 * @param key Key
	 * @param typeOfResultClass  Type of Result
	 * @return
	 */
	public static <T> T getExtra(Fragment fragment, String key, Class<T> typeOfResultClass) {
		
		Check.argumentNotNull(fragment, "fragment");
		Check.argumentNotNull(key, "key");
		
		Bundle bundle = fragment.getArguments();
		
		Check.assertion(bundle.containsKey(key),  key + " not found in " + bundle + " extras!");
		
		Serializable serializable = bundle.getSerializable(key);
		Check.notNull(serializable, "Serializable " + key + " not found in " + bundle + " extras!" );
		Check.assertion(typeOfResultClass.getClass() == serializable.getClass(), "Serializable is of type " + serializable.getClass() + " instead of " + typeOfResultClass);
		
		return typeOfResultClass.cast(serializable);
	}
}
