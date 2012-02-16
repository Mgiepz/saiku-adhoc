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

public class SaikuReportSettings {
	
	public void setPageFormat(String pageFormat) {
		this.pageFormat = pageFormat;
	}

	private static final int INITIAL_LIMIT = 100;
	
	private boolean disableDistinct;
	private Integer limit;
	private Integer orientation;
	
	private Integer marginLeft;
	private Integer marginRight;
	private Integer marginBottom;
	private Integer marginTop;
	
	private ReportTemplate reportTemplate;

	private String pageFormat;
	
	public SaikuReportSettings() {
		this.disableDistinct = false;
		//this.limit = INITIAL_LIMIT;
		this.orientation = null;
		this.marginBottom = null;
		this.marginLeft = null;
		this.marginRight = null;
		this.marginTop = null;
		
	}

	public void setDisableDistinct(boolean disableDistinct) {
		this.disableDistinct = disableDistinct;
	}
	public boolean isDisableDistinct() {
		return disableDistinct;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getLimit() {
		return limit;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	public Integer getOrientation() {
		return orientation;
	}

	public void setMarginLeft(Integer marginLeft) {
		this.marginLeft = marginLeft;
	}

	public Integer getMarginLeft() {
		return marginLeft;
	}

	public void setMarginRight(Integer marginRight) {
		this.marginRight = marginRight;
	}

	public Integer getMarginRight() {
		return marginRight;
	}

	public void setMarginBottom(Integer marginBottom) {
		this.marginBottom = marginBottom;
	}

	public Integer getMarginBottom() {
		return marginBottom;
	}

	public void setMarginTop(Integer marginTop) {
		this.marginTop = marginTop;
	}

	public Integer getMarginTop() {
		return marginTop;
	}

	public String getPageFormat() {
		return this.pageFormat;
	}

}
