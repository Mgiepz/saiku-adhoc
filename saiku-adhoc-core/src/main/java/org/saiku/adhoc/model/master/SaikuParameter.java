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
package org.saiku.adhoc.model.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.platform.util.messages.LocaleHelper;


public class SaikuParameter {
	
	public SaikuParameter() {
		super();
		this.setParameterValues(new ArrayList<String>());	
	}

	private String name;

	private String id;

	private String category;
	
	private String type;
	
	private ArrayList<String> parameterValues;	

	public SaikuParameter(LogicalColumn logicalColumn) {
		
		LocaleHelper.getLocale().toString();

		final String locale = LocaleHelper.getLocale().toString();

		this.setParameterValues(new ArrayList<String>());	
		
		this.name = logicalColumn.getName(locale);
		
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "SaikuParameter [id=" + id + ", category=" + category + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setParameterValues(ArrayList<String> parameterValues) {
		this.parameterValues = parameterValues;
	}

	public ArrayList<String> getParameterValues() {
		return parameterValues;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}


}
