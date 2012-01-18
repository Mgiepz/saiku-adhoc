/*
 * Copyright (C) 2011 Marius Giepz
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation; either version 2 of the License, or (at your option) 
 * any later version.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 *
 */
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
