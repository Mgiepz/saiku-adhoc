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
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.LogFactory;
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
import org.saiku.adhoc.rest.ExportResource;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.service.report.ReportGeneratorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pt.webdetails.cda.settings.CdaSettings;

public class SaikuMasterModel {

	public List<SaikuLabel> getReportSummaryElements() {
		return reportSummaryElements;
	}

	public void setReportSummaryElements(List<SaikuLabel> reportSummaryElements) {
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

	protected List<SaikuLabel> reportHeaderElements;

	//These are the elements that are NOT in the Summary Row
	protected List<SaikuLabel> reportFooterElements;

	protected List<SaikuLabel> reportSummaryElements;
	
	protected List<SaikuLabel> pageHeaderElements;

	protected List<SaikuLabel> pageFooterElements;

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

    @JsonIgnore
	private Log log = LogFactory.getLog(SaikuMasterModel.class);
    

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
		derivedModels = null;
		return this;
	}

	@JsonIgnore
	public String getCdaPath() {
		
		String solution = cdaManager.getSolution();
		String path = cdaManager.getPath();

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
		
		if(log.isDebugEnabled()){
			logModel();
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

	@JsonIgnore
	public SaikuCDA getCda(){
        String action = this.derivedModels.getSessionId() + ".cda";
        
        return cdaManager.getDatasource(action);	
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdaManager == null) ? 0 : cdaManager.hashCode());
		result = prime * result + ((clientModelSelection == null) ? 0 : clientModelSelection.hashCode());
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((derivedModels == null) ? 0 : derivedModels.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((pageFooterElements == null) ? 0 : pageFooterElements.hashCode());
		result = prime * result + ((pageHeaderElements == null) ? 0 : pageHeaderElements.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((reportFooterElements == null) ? 0 : reportFooterElements.hashCode());
		result = prime * result + ((reportHeaderElements == null) ? 0 : reportHeaderElements.hashCode());
		result = prime * result + ((reportSummaryElements == null) ? 0 : reportSummaryElements.hashCode());
		result = prime * result + ((reportTitle == null) ? 0 : reportTitle.hashCode());
		result = prime * result + ((reportingManager == null) ? 0 : reportingManager.hashCode());
		result = prime * result + ((settings == null) ? 0 : settings.hashCode());
		result = prime * result + ((sortColumns == null) ? 0 : sortColumns.hashCode());
		result = prime * result + ((transReport == null) ? 0 : transReport.hashCode());
		return result;
	}


	private void logModel() {

		Set<Entry<String,SaikuElement>> rptIdToElementFormat = this.getDerivedModels().getRptIdToSaikuElement().entrySet();
		
		log.debug("Model description: ");
		
		for (Entry<String, SaikuElement> entry : rptIdToElementFormat) {
			String object = "Element: " + entry.getKey() + "->" + entry.getValue().getUid() + ":";
			if(entry.getValue() instanceof SaikuLabel){
				object += ((SaikuLabel) entry.getValue()).getValue();	
			}
			
			log.debug(object);
		}

	}
	
}
