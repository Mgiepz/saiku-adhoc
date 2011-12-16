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
//@Deprecated
//public class ColumnConfig {
//
//  private static final long serialVersionUID = 3751750093446278893L;
//  private String id, name, description;
//  private String type;
//  private List<String> aggTypes = new ArrayList<String>();
//  private String defaultAggType;
//  private String selectedAggType;
//  private String fieldType;
//  private String category;
//  private String getHorizontalAlignment;
//  private String formatMask;
//  private String formula;
//  
//  private String[] alignments = new String[]{"left","center","right"};
//  
//  public String getHorizontalAlignment() {
//    return getHorizontalAlignment;
//  }
//
//  public void setHorizontalAlignment(String getHorizontalAlignment) {
//    this.getHorizontalAlignment = getHorizontalAlignment;
//  }
//
//  public String getFormatMask() {
//    return formatMask;
//  }
//
//  public void setFormatMask(String formatMask) {
//    this.formatMask = formatMask;
//  }
//
//  public String getCategory() {
//    return category;
//  }
//
//  public void setCategory(String category) {
//    this.category = category;
//  }
//
//  public String getFieldType() {
//    return fieldType;
//  }
//
//  public void setFieldType(String fieldType) {
//    this.fieldType = fieldType;
//  }
//
//  public String getId() {
//    return this.id;
//  }
//
//  public String getName() {
//    return this.name;   
//  }
//
//  public String getType() {
//    return this.type;
//  }
//
//  public void setId(String id) {
//    this.id = id;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public void setType(String type) {
//    this.type = type;
//  }
//
//  public String getDefaultAggType() {
//    return defaultAggType;
//  }
//
//  public String[] getAggTypes() {
//    return aggTypes.toArray( new String[aggTypes.size()] );
//  }
//
//  public void setAggTypes(List<String> aggTypes) {
//    this.aggTypes = aggTypes;
//  }
//
//  public void setDefaultAggType(String defaultAggType) {
//    this.defaultAggType = defaultAggType;
//  }
//
//  public void setSelectedAggType(String aggType){
//    this.selectedAggType = aggType;
//  }
//
//  public String getSelectedAggType(){
//    return this.selectedAggType;
//  }
//
//  public void setDescription(String description) {
//    this.description = description;
//  }
//
//  public String getDescription() {
//    return description;
//  }
//
//public void setAlignments(String[] alignments) {
//	this.alignments = alignments;
//}
//
//public String[] getAlignments() {
//	return alignments;
//}
//
//public void setFormula(String formula) {
//	this.formula = formula;
//}
//
//public String getFormula() {
//	return formula;
//}
//
//}
