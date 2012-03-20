package ch.morefx.xbmc.command;

import java.lang.reflect.Type;

import ch.morefx.xbmc.model.FileSource;

import com.google.gson.InstanceCreator;

public class FileSourceInstanceCreator implements InstanceCreator<FileSource>{
	private String mediaType;
	private FileSource parent;
	
	public FileSourceInstanceCreator(String mediaType) {
		this(mediaType, null);
	}
	
	public FileSourceInstanceCreator(String mediaType, FileSource parent) {
		this.mediaType = mediaType;
		this.parent = parent;
	}
	
	public FileSource createInstance(Type type) {
		return new FileSource(this.mediaType, this.parent);
	}
}