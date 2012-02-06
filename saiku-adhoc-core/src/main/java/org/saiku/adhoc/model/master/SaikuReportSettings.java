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
	
	private static final int INITIAL_LIMIT = 100;
	
	private boolean disableDistinct;
	private int limit;
	private int orientation;
	
	private ReportTemplate reportTemplate;
	
	public SaikuReportSettings() {
		this.disableDistinct = false;
		this.limit = INITIAL_LIMIT;
		this.orientation = 0;
	}

	public void setDisableDistinct(boolean disableDistinct) {
		this.disableDistinct = disableDistinct;
	}
	public boolean isDisableDistinct() {
		return disableDistinct;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getLimit() {
		return limit;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getOrientation() {
		return orientation;
	}

}
