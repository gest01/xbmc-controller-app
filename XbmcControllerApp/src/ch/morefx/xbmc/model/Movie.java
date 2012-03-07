package ch.morefx.xbmc.model;

import java.io.Serializable;

public class Movie extends LibraryItem
	implements Serializable {

	private static final long serialVersionUID = 1L;


	public static String[] getMovieFields(){
		// xbmc/xbmc/interfaces/json-rpc/ServiceDescription.h
		// line 397 "Video.Fields.Movie"
		return new String[] {
				"title", "genre", "year", "rating", "director", "trailer",
                "tagline", "plot", "plotoutline", "originaltitle", "lastplayed",
                "playcount", "writer", "studio", "mpaa", "cast", "country",
                "imdbnumber", "premiered", "productioncode", "runtime", "set",
                "showlink", "streamdetails", "top250", "votes", "fanart",
                "thumbnail", "file", "sorttitle", "resume", "setid" 
		};
	}
	
}
