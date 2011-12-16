package org.saiku.adhoc.model.dto;

public class SolutionFileInfo {
	
	private String path;
	
	private String file;

	public void setPath(String path) {
		
		if(path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

}
