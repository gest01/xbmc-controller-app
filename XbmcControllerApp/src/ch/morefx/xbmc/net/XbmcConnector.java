package ch.morefx.xbmc.net;

import java.io.IOException;

import android.graphics.drawable.Drawable;

/**
 *
 */
public interface XbmcConnector {

	/**
	 * 
	 * @param jsonCommand
	 * @return
	 * @throws Exception
	 */
	String send(String jsonCommand) throws Exception;
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	Drawable downloadImage(String url) throws IOException;
}
