package org.saiku.adhoc.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class SavedQuery implements Comparable<SavedQuery> {
	private String name;
	private String newname;
	private String lastModified;
	private String json;
	private String solution;
	private String path;
	private String action;
	private String overwrite;

	public SavedQuery() {		
	}
	
	public SavedQuery(String name, String lastModified, String json) {
		this.name = name;
		this.lastModified = lastModified;
		this.json = json;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLastModified() {
		return lastModified;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public int compareTo(SavedQuery o) {
		return name.compareTo(o.getName());
	}

	public String getNewname() {
		return newname;
	}

	public void setNewname(String newname) {
		this.newname = newname;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOverwrite() {
		return overwrite;
	}

	public void setOverwrite(String overwrite) {
		this.overwrite = overwrite;
	}
}

