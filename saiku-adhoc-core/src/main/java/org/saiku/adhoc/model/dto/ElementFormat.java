package org.saiku.adhoc.model.dto;

import org.saiku.adhoc.model.master.SaikuElementFormat;

public class ElementFormat {
	
	public ElementFormat() {
	}

	public ElementFormat(SaikuElementFormat format, String value) {
		super();
		this.format = format;
		this.value = value;
	}

	private SaikuElementFormat format;
	
	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setFormat(SaikuElementFormat format) {
		this.format = format;
	}

	public SaikuElementFormat getFormat() {
		return format;
	}

}
