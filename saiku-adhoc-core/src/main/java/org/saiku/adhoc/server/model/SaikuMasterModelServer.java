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

package org.saiku.adhoc.server.model;

import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.wizard.model.WizardSpecification;
import org.saiku.adhoc.exceptions.ModelException;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuMessage;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.model.master.SaikuReportSettings;
import org.saiku.adhoc.model.transformation.TransModelToCda;
import org.saiku.adhoc.model.transformation.TransModelToParams;
import org.saiku.adhoc.model.transformation.TransModelToQuery;
import org.saiku.adhoc.model.transformation.TransModelToReport;
import org.saiku.adhoc.model.transformation.TransModelToWizard;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.server.model.transformation.TransModelToCdaServer;
import org.saiku.adhoc.server.model.transformation.TransModelToReportServer;

import pt.webdetails.cda.settings.CdaSettings;

public class SaikuMasterModelServer extends SaikuMasterModel {


    private ICDAManager cdaManager;

    public void setCDAManager(ICDAManager manager){
        this.cdaManager = manager;
        
    }

    public ICDAManager getCDAManager(){
        return cdaManager;
    }
    
    @Override
	public void init(Domain domain, LogicalModel model, String sessionId, ICDAManager manager) throws ModelException{

	    this.cdaManager = manager;
		this.derivedModels = new DerivedModelsCollectionServer(sessionId, domain, model, this.cdaManager);
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

	
	public SaikuMasterModelServer() {
		super();
		
	}

	@Override
	@JsonIgnore
	public SaikuCDA getCda() {
	 
		String action = this.derivedModels.getSessionId() + ".cda";

		
		
		return cdaManager.getDatasource(action);
		}

	@Override
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
	        TransModelToCda transCda = new TransModelToCdaServer();
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
	        TransModelToReport transReport = new TransModelToReportServer();
	        MasterReport reportTemplate;
	        try {
	            reportTemplate = transReport.doIt(this);
	            this.derivedModels.setReportTemplate(reportTemplate);
	        } catch (Exception e) {
	            throw new ModelException();
	        }


	    }

}
