package ch.morefx.xbmc.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Movie extends LibraryItem
	implements Serializable, ThumbnailHolder {

	private static final long serialVersionUID = 1L;

	private String title, year, genre, thumbnail;
	private int movieid;
	
	public String getTitle(){
		return this.title;
	}
	
	public String getYear(){
		return this.year;
	}
	
	public String getGenre(){
		return this.genre;
	}
	
	public int getThumbnailFallbackResourceId() {
		return ch.morefx.xbmc.R.drawable.cdicon; // TODO
	}
	
	public String getThumbnailUri() {
		return this.thumbnail;
	}
	
	private transient Drawable drawableThumbnail;
	
	public Drawable getThumbnail() {
		return drawableThumbnail;
	}
	
	public void setThumbnail(Drawable drawable) {
		this.drawableThumbnail = drawable;
	}


	public int getMovieId(){
		return this.movieid;
	}

	public static String[] getMovieFields(){
		// xbmc/xbmc/interfaces/json-rpc/ServiceDescription.h
		// line 397 "Video.Fields.Movie"
		return new String[] {
				"title", "genre", "year", "rating", "director", "trailer",
                "tagline", "plot", "plotoutline", "originaltitle", "lastplayed",
                "playcount", "writer", "studio", "mpaa", "cast", "country",
                "imdbnumber", "premiered", "productioncode", "runtime", "set",
                "showlink", "streamdetails", "top250", "votes", "fanart",
                "thumbnail", "file", /*"sorttitle" Removed..when using PlaylistGetItems, it returns an error ,*/ "resume", "setid"
		};
	}
	
}
