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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.service.report.ReportGeneratorService;

@JsonPropertyOrder(
		{
			"version",
			"domainId",
			"logicalModelId",
			"reportHeaderElements",
			"pageHeaderElements",
			"pageFooterElements",
			"reportFooterElements",
			"columns",
			"sortColumns",
			"groups",
			"reportSummaryElements",
			"parameters",
			"settings",
			"clientModelSelection",
			"maxClientSeq"
		}
)

public class SaikuMasterModel {

	private final static String VERSION_INFO = "V_1_0";

	@JsonIgnore
	public Boolean getCdaDirty() {
		return cdaDirty;
	}
	
	public String getVersion(){
		return VERSION_INFO;
	}

	public void setVersion(String version){

	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public void setLogicalModelId(String logicalModelId) {
		this.logicalModelId = logicalModelId;
	}

	public List<SaikuLabel> getReportSummaryElements() {
		return reportSummaryElements;
	}

	public void setReportSummaryElements(List<SaikuLabel> reportSummaryElements) {
		this.reportSummaryElements = reportSummaryElements;
	}

	protected List<SaikuColumn> columns;

	protected List<SaikuLabel> reportHeaderElements;

	//These are the elements that are NOT in the Summary Row
	protected List<SaikuLabel> reportFooterElements;

	protected List<SaikuLabel> reportSummaryElements;
	
	protected List<SaikuLabel> pageHeaderElements;

	protected List<SaikuLabel> pageFooterElements;

	protected List<SaikuGroup> groups;

	protected List<SaikuParameter> parameters;

	protected List<String> sortColumns;


	protected String clientModelSelection;

	protected SaikuReportSettings settings;

	@JsonIgnore
	private Log log = LogFactory.getLog(SaikuMasterModel.class);
    
    @JsonIgnore
    private Boolean cdaDirty = true;

	private String domainId;

	private String logicalModelId;

	private String sessionId;
	
	private Integer maxClientSeq;
    

	public void init(String sessionId, ReportGeneratorService reportGeneratorService) throws SaikuAdhocException{

		this.sessionId = sessionId;
		
		this.setMaxClientSeq(null);

		if(this.settings==null){
			this.settings = new SaikuReportSettings();	
		}

		if(this.clientModelSelection==null){
			//only init these once
			this.columns = new ArrayList<SaikuColumn>();
			this.groups = new ArrayList<SaikuGroup>();
			this.parameters = new ArrayList<SaikuParameter>();
			this.sortColumns = new ArrayList<String>();

			this.reportHeaderElements = new ArrayList<SaikuLabel>();
			this.reportFooterElements = new ArrayList<SaikuLabel>();
			this.reportSummaryElements = new ArrayList<SaikuLabel>();
			this.pageHeaderElements = new ArrayList<SaikuLabel>();
			this.pageFooterElements = new ArrayList<SaikuLabel>();

		}

	}

	public SaikuMasterModel() {
		super();

	}

	private Object readResolve() {
		//TODO: refactor to make default possible on deserialization
		return this;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.settings.setReportTemplate(reportTemplate);		
	}

	@JsonIgnore
	public ReportTemplate getReportTemplate() {
		return this.settings.getReportTemplate();
	}

	public List<SaikuColumn> getColumns() {
		return columns;
	}

	public List<SaikuGroup> getGroups() {
		return groups;
	}

	public List<String> getSortColumns() {
		return sortColumns;
	}

	public void setParameters(List<SaikuParameter> parameters) {
		this.parameters = parameters;
	}

	public List<SaikuParameter> getParameters() {
		return parameters;
	}

	public void setClientModelSelection(String clientModelSelection) {
		this.clientModelSelection = clientModelSelection;
	}

	public String getClientModelSelection() {
		return clientModelSelection;
	}


	public void setColumns(List<SaikuColumn> columns) {
		this.columns = columns;
	}


	public void setGroups(List<SaikuGroup> groups) {
		this.groups = groups;
	}


	public void setSortColumns(List<String> sortColumns) {
		this.sortColumns = sortColumns;
	}


	public void setSettings(SaikuReportSettings settings) {
		this.settings = settings;
	}


	public SaikuReportSettings getSettings() {
		return settings;
	}


	public void setReportHeaderElements(List<SaikuLabel> reportHeaderMessages) {
		this.reportHeaderElements = reportHeaderMessages;
	}


	public List<SaikuLabel> getReportHeaderElements() {
		return reportHeaderElements;
	}


	public void setPageHeaderElements(List<SaikuLabel> pageHeaderMessages) {
		this.pageHeaderElements = pageHeaderMessages;
	}


	public List<SaikuLabel> getPageHeaderElements() {
		return pageHeaderElements;
	}


	public void setPageFooterElements(List<SaikuLabel> pageFooterMessages) {
		this.pageFooterElements = pageFooterMessages;
	}


	public List<SaikuLabel> getPageFooterElements() {
		return pageFooterElements;
	}


	public void setReportFooterElements(List<SaikuLabel> reportFooterMessages) {
		this.reportFooterElements = reportFooterMessages;
	}


	public List<SaikuLabel> getReportFooterElements() {
		return reportFooterElements;
	}

	public void setCdaDirty(Boolean cdaDirty) {
		this.cdaDirty = cdaDirty;
	}

	@JsonIgnore
	public Boolean isCdaDirty() {
		return cdaDirty;
	}

	public String getDomainId() {
		return this.domainId;
	}

	public String getLogicalModelId() {
		return this.logicalModelId;
	}

	@JsonIgnore
	public String getSessionId() {
		return sessionId;
	}

	public void setMaxClientSeq(Integer maxClientSeq) {
		this.maxClientSeq = maxClientSeq;
	}

	public Integer getMaxClientSeq() {
		return maxClientSeq;
	}
	
}
