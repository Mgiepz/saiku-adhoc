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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.IPRPTManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.server.model.master.ReportTemplateServer;
import org.saiku.adhoc.server.model.service.repository.ServerRepositoryHelper;


/**
 * This object is a singleton and holds all models. Models are stored under
 * their respective session-id
 * 
 * It is also responsible of setting up a session
 * 
 * @author mgie
 * 
 */
public class WorkspaceSessionHolderServer extends WorkspaceSessionHolder{

    
	private Map<String, SaikuMasterModel> models = new HashMap<String, SaikuMasterModel>();
    private IPRPTManager prptManager;
    private ICDAManager cdaManager;

    @Override
	public void initSession(SaikuMasterModel masterModel, String sessionId) {

		// TODO: Move and make configurable && plugin
		ReportTemplate template = prptManager.getDatasources().values().toArray(new ReportTemplateServer[]{})[0];
		
		masterModel.setReportTemplate(template);

		models.put(sessionId, masterModel);
		
	}

    
    public void setPRPTManager(IPRPTManager manager){
        this.prptManager = manager;
        
    }

    public IPRPTManager getPRPTManager(){
        return prptManager;
    }
    
    public void setCDAManager(ICDAManager manager){
        this.cdaManager = manager;
        
    }

    public ICDAManager getCDAManager(){
        return cdaManager;
    }
    
	public Map getModels() {
		return models;
	}

	public SaikuMasterModel getModel(String sessionId) {
		return models.get(sessionId);
	}

	public String logModel(String sessionId) {

		StringBuffer string = new StringBuffer();

		final SaikuMasterModel smm = models.get(sessionId);
		
		string.append("\nMASTER-MODEL\nCOLUMNS:\n");
		
		final List<SaikuColumn> columns = smm.getColumns();
		for (SaikuColumn saikuColumn : columns) {
			string.append(saikuColumn.toString()+ "\n");
		}
		
		final List<SaikuGroup> groups = smm.getGroups();
		
		string.append("GROUPS:\n");
		for (SaikuGroup saikuGroup : groups) {
			string.append(saikuGroup.toString()+ "\n");
		}
		
		string.append("FILTERS:\n");
		final List<SaikuParameter> parameters = smm.getParameters();
		for (SaikuParameter saikuParameter : parameters) {
			string.append(saikuParameter.toString()+ "\n");
		}
		
		return string.toString();
	}

	   public void materializeModel(String sessionId) {

	        SaikuMasterModel model = this.getModel(sessionId);

	        String action = sessionId + ".cda";

	        //Save the cda first
	        try {
	            model.deriveModels();
	            cdaManager.addDatasource(new SaikuCDA(action, model.getCdaSettings().asXML().getBytes("UTF-8")));
	        } catch (SaikuAdhocException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (OperationNotSupportedException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (TransformerFactoryConfigurationError e) {
	            e.printStackTrace();
	        } catch (TransformerException e) {
	            e.printStackTrace();
	        } catch (ResourceException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	    }
}
