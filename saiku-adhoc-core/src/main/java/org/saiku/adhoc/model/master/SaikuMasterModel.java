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
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.wizard.model.WizardSpecification;
import org.saiku.adhoc.exceptions.ModelException;
import org.saiku.adhoc.model.transformation.TransModelToCda;
import org.saiku.adhoc.model.transformation.TransModelToParams;
import org.saiku.adhoc.model.transformation.TransModelToQuery;
import org.saiku.adhoc.model.transformation.TransModelToReport;
import org.saiku.adhoc.model.transformation.TransModelToWizard;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.service.SaikuProperties;

import pt.webdetails.cda.settings.CdaSettings;

public class SaikuMasterModel {

	protected List<SaikuColumn> columns;

	protected List<SaikuMessage> reportHeaderMessages;

	protected List<SaikuMessage> reportFooterMessages;
	
	protected List<SaikuMessage> pageHeaderMessages;
	
	protected List<SaikuMessage> pageFooterMessages;
	

	protected List<SaikuGroup> groups;

	protected ArrayList<SaikuParameter> parameters;

	protected List<String> sortColumns;

	private String reportTitle;
	
	protected String clientModelSelection;
	
	protected SaikuReportSettings settings;

	@JsonIgnore
    protected DerivedModelsCollection derivedModels;


	public void init(Domain domain, LogicalModel model, String sessionId) throws ModelException{

		this.derivedModels = new DerivedModelsCollection(sessionId, domain, model);
		derivedModels.init();
		
		this.settings = new SaikuReportSettings();

		if(this.clientModelSelection==null){
			//only init these once
			this.columns = new ArrayList<SaikuColumn>();
			this.groups = new ArrayList<SaikuGroup>();
			this.parameters = new ArrayList<SaikuParameter>();
			this.sortColumns = new ArrayList<String>();
			
			this.reportHeaderMessages = new ArrayList<SaikuMessage>();

			this.reportFooterMessages = new ArrayList<SaikuMessage>();
			
			this.pageHeaderMessages = new ArrayList<SaikuMessage>();
			
			this.pageFooterMessages = new ArrayList<SaikuMessage>();
			
		}

	}

	
	public SaikuMasterModel() {
		super();
		
	}

	private Object readResolve() {
		//TODO: refactor to make default possible on deserialization
		derivedModels = null;
		return this;
	}

	@JsonIgnore
	public String getCdaPath() {

		String solution = "system";
		String path = "saiku-adhoc/temp";
	 
		String action = this.derivedModels.getSessionId() + ".cda";

		return solution + "/" + path + "/" + action;
	}


	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.settings.setReportTemplate(reportTemplate);		
	}

	public ReportTemplate getReportTemplate() {
		return this.settings.getReportTemplate();
	}

	@JsonIgnore
	public DerivedModelsCollection getDerivedModels() {
		return derivedModels;
	}
	
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
	public String getReportTitle() {
		return reportTitle;
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


	/**
	 * This method will sync the derived models in the correct order
	 * @throws ModelException 
	 */
	public void deriveModels() throws ModelException{

		//Query -> ok!
		TransModelToQuery transQuery = new TransModelToQuery();
		final Query query = transQuery.doIt(this);
		this.derivedModels.setQuery(query);

		//Filter Queries
		TransModelToParams transParams = new TransModelToParams();
		final Map<String, Query> filterQueries = transParams.doIt(this);
		this.derivedModels.setFilterQueries(filterQueries);

		//CDA -> ok!
		TransModelToCda transCda = new TransModelToCda();
		final CdaSettings cda = transCda.doIt(this);
		this.derivedModels.setCda(cda);

		//Wizard
		TransModelToWizard transWizard = new TransModelToWizard();
		WizardSpecification wizardSpec;
		try {
			wizardSpec = transWizard.doIt(this);
			this.derivedModels.setWizardSpec(wizardSpec);
		} catch (Exception e1) {
			throw new ModelException();
		}

		//Prpt
		TransModelToReport transReport = new TransModelToReport();
		MasterReport reportTemplate;
		try {
			reportTemplate = transReport.doIt(this);
			this.derivedModels.setReportTemplate(reportTemplate);
		} catch (Exception e) {
			throw new ModelException();
		}


	}

	@JsonIgnore
	public Query getQuery(){
		return this.derivedModels.getQuery();		
	}

	@JsonIgnore
	public CdaSettings getCdaSettings(){
		return this.derivedModels.getCda();	
	}

	@JsonIgnore
	public WizardSpecification getWizardSpecification(){
		return this.derivedModels.getWizardSpec();	
	}

	@JsonIgnore
	public MasterReport getMasterReport(){
		return this.derivedModels.getReportTemplate();		
	}

	public void setParameters(ArrayList<SaikuParameter> parameters) {
		this.parameters = parameters;
	}
	
	public ArrayList<SaikuParameter> getParameters() {
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


	public void setReportHeaderMessages(List<SaikuMessage> reportHeaderMessages) {
		this.reportHeaderMessages = reportHeaderMessages;
	}


	public List<SaikuMessage> getReportHeaderMessages() {
		return reportHeaderMessages;
	}


	public void setPageHeaderMessages(List<SaikuMessage> pageHeaderMessages) {
		this.pageHeaderMessages = pageHeaderMessages;
	}


	public List<SaikuMessage> getPageHeaderMessages() {
		return pageHeaderMessages;
	}


	public void setPageFooterMessages(List<SaikuMessage> pageFooterMessages) {
		this.pageFooterMessages = pageFooterMessages;
	}


	public List<SaikuMessage> getPageFooterMessages() {
		return pageFooterMessages;
	}


	public void setReportFooterMessages(List<SaikuMessage> reportFooterMessages) {
		this.reportFooterMessages = reportFooterMessages;
	}


	public List<SaikuMessage> getReportFooterMessages() {
		return reportFooterMessages;
	}

	public SaikuCDA getCda(){
	    return null;
	}


    public void init(Domain domain, LogicalModel model, String sessionId, ICDAManager cdaManager) throws ModelException {
        // Empty class for SaikuMasterModelServer
        
    }
}
