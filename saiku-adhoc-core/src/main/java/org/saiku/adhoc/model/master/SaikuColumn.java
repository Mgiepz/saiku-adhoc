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
import java.util.List;

import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.concept.types.AggregationType;

/**
 * @author mgie
 * 
 *         org.pentaho.reporting.engine.classic.wizard.model.
 *         DetailFieldDefinition.class
 * 
 */
public class SaikuColumn {

	private SaikuElementFormat columnHeaderFormat;

	private SaikuElementFormat columnFormat;
	
	private String id, name, description;
	
	private List<String> aggTypes = new ArrayList<String>();
	
	private String defaultAggType;
	
	private String selectedAggType;

	private String selectedSummaryType;
	
	private String fieldType;
	
	private String formula;
	
	private String category;
	
	private String formatMask;
	
	private String uid;

	private String sort;
	
	private boolean forGroupOnly;

	private boolean hideRepeating;
	
	public SaikuColumn() {
		this.forGroupOnly = false;
		this.hideRepeating = false;
		this.sort = "NONE";
	}

	public SaikuColumn(LogicalColumn logicalColumn) {

		final List<AggregationType> aggregationList = logicalColumn.getAggregationList();
	
		this.aggTypes = new ArrayList<String>();

		for (AggregationType aggregationType : aggregationList) {
			this.aggTypes.add(aggregationType.name());
		}
		if(!this.aggTypes.contains(AggregationType.NONE.name())){
			this.aggTypes.add(AggregationType.NONE.name());
		}

		final String none = AggregationType.NONE.name();
		
		if(this.aggTypes.isEmpty()){
			this.aggTypes.add(none);
		}

		String defAggType = logicalColumn.getAggregationType().name()!=null?
				logicalColumn.getAggregationType().name(): none;
					
		this.defaultAggType = defAggType;		
		final String locale = "en_En";	
		this.description = logicalColumn.getDescription(locale);	
		this.name = logicalColumn.getName(locale);
		this.columnFormat = new SaikuElementFormat();		
		this.columnHeaderFormat = new SaikuElementFormat();		
		this.fieldType = logicalColumn.getDataType().getName();		
		this.forGroupOnly = false;

		this.sort = "NONE";
		
	}

	public SaikuElementFormat getElementFormat() {
		return columnFormat;
	}

	public void setElementFormat(SaikuElementFormat elementFormat) {
		this.columnFormat = elementFormat;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAggTypes() {
		return aggTypes;
	}

	public void setAggTypes(List<String> aggTypes) {
		this.aggTypes = aggTypes;
	}

	public String getDefaultAggType() {
		return defaultAggType;
	}

	public void setDefaultAggType(String defaultAggType) {
		this.defaultAggType = defaultAggType;
	}

	public String getSelectedAggType() {
		return selectedAggType;
	}

	public void setSelectedAggType(String selectedAggType) {
		this.selectedAggType = selectedAggType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFormatMask() {
		return formatMask;
	}

	public void setFormatMask(String formatMask) {
		this.formatMask = formatMask;
	}

	public void setForGroupOnly(boolean forGroupOnly) {
		this.forGroupOnly = forGroupOnly;
	}

	public boolean isForGroupOnly() {
		return forGroupOnly;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	@Override
	public String toString() {
		return "SaikuColumn [id=" + id + ", name=" + name + ", description="
				+ description + ", selectedAggType=" + selectedAggType
				+ ", fieldType=" + fieldType + ", uid=" + uid + "]";
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSort() {
		return sort;
	}

	public void setColumnHeaderFormat(SaikuElementFormat columnHeaderFormat) {
		this.columnHeaderFormat = columnHeaderFormat;
	}

	public SaikuElementFormat getColumnHeaderFormat() {
		return columnHeaderFormat;
	}

	public void setSelectedSummaryType(String selectedSummaryType) {
		this.selectedSummaryType = selectedSummaryType;
	}

	public String getSelectedSummaryType() {
		return selectedSummaryType;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFormula() {
		return formula;
	}

	public void setHideRepeating(boolean hideRepeating) {
		this.hideRepeating = hideRepeating;
	}

	public boolean isHideRepeating() {
		return hideRepeating;
	}

}
