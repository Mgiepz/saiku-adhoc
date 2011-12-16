///*
// * Copyright (C) 2011 Marius Giepz
// *
// * This program is free software; you can redistribute it and/or modify it 
// * under the terms of the GNU General Public License as published by the Free 
// * Software Foundation; either version 2 of the License, or (at your option) 
// * any later version.
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * 
// * See the GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License along 
// * with this program; if not, write to the Free Software Foundation, Inc., 
// * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
// *
// */
//package org.saiku.adhoc.model.dto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.pentaho.metadata.model.Category;
//import org.pentaho.metadata.model.Domain;
//import org.pentaho.metadata.model.LogicalColumn;
//import org.pentaho.metadata.model.LogicalModel;
//import org.pentaho.metadata.model.concept.types.AggregationType;
//import org.pentaho.metadata.model.concept.types.Alignment;
//import org.pentaho.metadata.model.concept.types.DataType;
//import org.pentaho.metadata.model.concept.types.FieldType;
//import org.pentaho.platform.util.messages.LocaleHelper;
//
//public class DtoAssembler {
//	
//	private Domain domain;
//	
//	public ColumnConfig getColumnConfig(LogicalModel m, LogicalColumn c, Category category) {
//		
//		String locale = LocaleHelper.getClosestLocale(LocaleHelper.getLocale().toString(), domain.getLocaleCodes());
//		
//		ColumnConfig col = new ColumnConfig();
//		col.setName(c.getName(locale));
//		col.setId(c.getId());
//		col.setDescription(c.getDescription(locale));
//		if(col.getId().equals(col.getDescription())) {
//			col.setDescription(null);
//		}
//		if( c.getFieldType() != null ) {      
//			col.setFieldType(c.getFieldType().name());
//		} else {
//			col.setFieldType( "UNKNOWN" );
//		}
//
//		col.setType(c.getDataType().getName().toUpperCase());
//		col.setCategory(category.getId());
//
//		List<AggregationType> possibleAggs = c.getAggregationList();
//		List<String> aggTypes = new ArrayList<String>();
//		if (possibleAggs != null) {
//			for (AggregationType agg : possibleAggs) {
//				aggTypes.add(agg.name());
//			}
//		}
//
//		AggregationType defaultAggType = AggregationType.NONE;
//		if (c.getAggregationType() != null) {
//			defaultAggType = c.getAggregationType();
//		}
//		if (!aggTypes.contains(defaultAggType)) {
//			aggTypes.add(defaultAggType.name());
//		}
//		col.setAggTypes(aggTypes);
//		col.setDefaultAggType(defaultAggType.name());
//		col.setSelectedAggType(defaultAggType.name());
//
//		DataType dataType = c.getDataType();
//		FieldType fieldType = c.getFieldType();
//		Object obj = c.getProperty("alignment"); 
//		if(obj instanceof Alignment) {
//			if(obj == Alignment.LEFT) {
//				col.setHorizontalAlignment(Alignment.LEFT.toString());
//			}
//			else if(obj == Alignment.RIGHT) {
//				col.setHorizontalAlignment(Alignment.RIGHT.toString());
//			}
//			else if(obj == Alignment.CENTERED) {
//				col.setHorizontalAlignment(Alignment.CENTERED.toString());
//			}
//		}
//		else if(fieldType == FieldType.FACT) {
//			col.setHorizontalAlignment(Alignment.RIGHT.toString());
//		}
//		else if(fieldType == FieldType.OTHER && dataType == DataType.NUMERIC) {
//			col.setHorizontalAlignment(Alignment.RIGHT.toString());
//		}
//		else {
//			col.setHorizontalAlignment(Alignment.LEFT.toString());
//		}
//
//		obj = c.getProperty("mask");
//		if(obj != null) {
//			col.setFormatMask((String)obj);
//		}
//		return col;
//	}
//
//	public void setDomain(Domain domain) {
//		this.domain = domain;
//	}
//
//}
