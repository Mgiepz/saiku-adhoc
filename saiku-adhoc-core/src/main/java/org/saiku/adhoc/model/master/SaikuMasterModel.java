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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.wizard.model.WizardSpecification;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.messages.Messages;
import org.saiku.adhoc.model.transformation.TransModelToCda;
import org.saiku.adhoc.model.transformation.TransModelToParams;
import org.saiku.adhoc.model.transformation.TransModelToQuery;
import org.saiku.adhoc.model.transformation.TransModelToReport;
import org.saiku.adhoc.model.transformation.TransModelToWizard;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.service.report.ReportGeneratorService;

import pt.webdetails.cda.settings.CdaSettings;

public class SaikuMasterModel {

	public List<SaikuElement> getReportSummaryElements() {
		return reportSummaryElements;
	}

	public void setReportSummaryElements(List<SaikuElement> reportSummaryElements) {
		this.reportSummaryElements = reportSummaryElements;
	}
    
	@JsonIgnore
	protected ICDAManager cdaManager;

	@JsonIgnore
    public void setCDAManager(ICDAManager manager){
        this.cdaManager = manager;
        
    }

	@JsonIgnore
    public ICDAManager getCDAManager(){
        return cdaManager;
    }
    
    @JsonIgnore
    TransModelToReport transReport;
    
    @JsonIgnore
    public void setTransReport(TransModelToReport transReport){
        this.transReport = transReport;
        
    }
    @JsonIgnore
    public TransModelToReport getTransReport(){
        return transReport;
    }

	protected List<SaikuColumn> columns;

	protected List<SaikuElement> reportHeaderElements;

	//These are the elements that are NOT in the Summary Row
	protected List<SaikuElement> reportFooterElements;

	protected List<SaikuElement> reportSummaryElements;
	
	protected List<SaikuElement> pageHeaderElements;

	protected List<SaikuElement> pageFooterElements;

	protected List<SaikuGroup> groups;

	protected List<SaikuParameter> parameters;

	protected List<String> sortColumns;

	//TODO: Remove
	private String reportTitle;

	protected String clientModelSelection;

	protected SaikuReportSettings settings;

	@JsonIgnore
	protected DerivedModelsCollection derivedModels;

    private ReportGeneratorService reportingManager;


	public void init(Domain domain, LogicalModel model, String sessionId, ICDAManager manager, ReportGeneratorService reportGeneratorService) throws SaikuAdhocException{
        this.cdaManager = manager;
        this.reportingManager = reportGeneratorService;
		this.derivedModels = new DerivedModelsCollection(sessionId, domain, model, cdaManager);
		derivedModels.init();

		if(this.settings==null){
			this.settings = new SaikuReportSettings();	
		}


		if(this.clientModelSelection==null){
			//only init these once
			this.columns = new ArrayList<SaikuColumn>();
			this.groups = new ArrayList<SaikuGroup>();
			this.parameters = new ArrayList<SaikuParameter>();
			this.sortColumns = new ArrayList<String>();

			this.reportHeaderElements = new ArrayList<SaikuElement>();
			this.reportFooterElements = new ArrayList<SaikuElement>();
			this.reportSummaryElements = new ArrayList<SaikuElement>();
			this.pageHeaderElements = new ArrayList<SaikuElement>();
			this.pageFooterElements = new ArrayList<SaikuElement>();

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
	 * @throws SaikuAdhocException 
	 * @throws IOException 
	 * @throws ResourceException 
	 */
	public void deriveModels() throws SaikuAdhocException{
		
		if (this.getColumns().isEmpty()){
			throw new SaikuAdhocException(				
        			Messages.getErrorString("MasterModel.ERROR_0002_SELECTION_IS_EMPTY")
        	);
		}

		//Query -> ok!
		TransModelToQuery transQuery = new TransModelToQuery();
		final Query query = transQuery.doIt(this);
		this.derivedModels.setQuery(query);

		//Filter Queries
		TransModelToParams transParams = new TransModelToParams();
		final Map<String, Query> filterQueries = transParams.doIt(this);
		this.derivedModels.setFilterQueries(filterQueries);

		//CDA
		TransModelToCda transCda = new TransModelToCda();
		try {
		final CdaSettings cda = transCda.doIt(this, cdaManager);
		this.derivedModels.setCda(cda);
		} catch (Exception e) {
			//TODO: move that into transformation.doIt
			throw new SaikuAdhocException(				
					Messages.getErrorString("MasterModel.ERROR_0001_TRANSFORMATION_TO_CDA_FAILED")
			);
		}

		//Wizard
		TransModelToWizard transWizard = new TransModelToWizard();
		WizardSpecification wizardSpec;
		try {
			wizardSpec = transWizard.doIt(this);
			this.derivedModels.setWizardSpec(wizardSpec);
		} catch (Exception e1) {
			//TODO: move that into transformation.doIt
			throw new SaikuAdhocException(				
					Messages.getErrorString("MasterModel.ERROR_0001_TRANSFORMATION_TO_WIZARDSPEC_FAILED")
			);
		}

		//Prpt
			MasterReport reportTemplate;
			transReport = new TransModelToReport(reportingManager);
			reportTemplate = transReport.doIt(this);
			this.derivedModels.setReportTemplate(reportTemplate);

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


	public void setReportHeaderElements(List<SaikuElement> reportHeaderMessages) {
		this.reportHeaderElements = reportHeaderMessages;
	}


	public List<SaikuElement> getReportHeaderElements() {
		return reportHeaderElements;
	}


	public void setPageHeaderElements(List<SaikuElement> pageHeaderMessages) {
		this.pageHeaderElements = pageHeaderMessages;
	}


	public List<SaikuElement> getPageHeaderElements() {
		return pageHeaderElements;
	}


	public void setPageFooterElements(List<SaikuElement> pageFooterMessages) {
		this.pageFooterElements = pageFooterMessages;
	}


	public List<SaikuElement> getPageFooterElements() {
		return pageFooterElements;
	}


	public void setReportFooterElements(List<SaikuElement> reportFooterMessages) {
		this.reportFooterElements = reportFooterMessages;
	}


	public List<SaikuElement> getReportFooterElements() {
		return reportFooterElements;
	}

	@JsonIgnore
	public SaikuCDA getCda(){
        String action = this.derivedModels.getSessionId() + ".cda";
        
        return cdaManager.getDatasource(action);	
    }

}
