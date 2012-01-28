package org.saiku.adhoc.model.dto;

public class DisplayName {
	
	public DisplayName(String displayName, String uid) {
		super();
		this.displayName = displayName;
		this.uid = uid;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	String displayName;
	
	String uid;

}
