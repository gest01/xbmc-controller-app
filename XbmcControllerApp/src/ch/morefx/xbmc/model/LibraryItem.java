package ch.morefx.xbmc.model;

import java.io.Serializable;

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
}
