package org.saiku.adhoc.model.dto;

import java.util.ArrayList;

public class Directory {
	
	public Directory(String name) {
		super();
		this.name = name;
	}

	private String name;

	private ArrayList<File> files;

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
}
