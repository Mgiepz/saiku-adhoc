package org.saiku.adhoc.model.dto;


public class File {
	
	public File(String name, String extension) {
		super();
		this.name = name;
		this.extension = extension;
	}

	private String name;
	
	private String extension;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

}
