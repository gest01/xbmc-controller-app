package ch.morefx.xbmc.command;

import java.lang.reflect.Type;

import ch.morefx.xbmc.model.FileSource;

import com.google.gson.InstanceCreator;

public class FileSourceInstanceCreator implements InstanceCreator<FileSource>{
	private String mediaType;
	public FileSourceInstanceCreator(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public FileSource createInstance(Type type) {
		return new FileSource(this.mediaType);
	}
}