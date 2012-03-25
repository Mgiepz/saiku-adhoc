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
package org.saiku.adhoc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.messages.Messages;
import org.saiku.adhoc.model.builder.CdaBuilder;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.providers.ICdaProvider;
import org.saiku.adhoc.providers.IMetadataProvider;
import org.saiku.adhoc.providers.IPrptProvider;
import org.saiku.adhoc.service.EditorService;
import org.saiku.adhoc.service.SaikuProperties;

import pt.webdetails.cda.settings.CdaSettings;

/**
 * This object is a singleton and holds all models. Models are stored under
 * their respective session-id
 * 
 * It is also responsible of setting up a session
 * 
 * @author mgie
 * 
 */
public class WorkspaceSessionHolder {

	private Map<String, SaikuMasterModel> models = new HashMap<String, SaikuMasterModel>();

	private ICdaProvider cdaProvider;

	private IPrptProvider prptProvider;

	private IMetadataProvider metadataProvider;
	
	protected Log log = LogFactory.getLog(WorkspaceSessionHolder.class);
	
	public void setMetadataProvider(IMetadataProvider metadataProvider) {
		this.metadataProvider = metadataProvider;
	}

	public void setPrptProvider(IPrptProvider prptProvider) {
		this.prptProvider = prptProvider;
	}

	public void setCdaProvider(ICdaProvider cdaProvider) {
		this.cdaProvider = cdaProvider;
	}

	public void initSession(SaikuMasterModel masterModel, String sessionId) {

		if (masterModel.getReportTemplate() == null) {
			String name = SaikuProperties.defaultPrptTemplate;
			//TODO: make it a constant;
			String path = null;
			String solution = null;
			
			masterModel.setReportTemplate(prptProvider.getTemplate(path, solution, name));
		}

		models.put(sessionId, masterModel);

	}

	public Map<String, SaikuMasterModel> getModels() {
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
			string.append(saikuColumn.toString() + "\n");
		}

		final List<SaikuGroup> groups = smm.getGroups();

		string.append("GROUPS:\n");
		for (SaikuGroup saikuGroup : groups) {
			string.append(saikuGroup.toString() + "\n");
		}

		string.append("FILTERS:\n");
		final List<SaikuParameter> parameters = smm.getParameters();
		for (SaikuParameter saikuParameter : parameters) {
			string.append(saikuParameter.toString() + "\n");
		}

		return string.toString();
	}

	public void storeCda(String sessionId) throws SaikuAdhocException {

		SaikuMasterModel model = this.getModel(sessionId);

		String action = sessionId + ".cda";

		if (!model.isCdaDirty()) {
			return;
		}

		try {
			final Domain domain = metadataProvider.getDomain(model.getDomainId());
			final LogicalModel logicalModel = metadataProvider.getLogicalModel(model.getDomainId(),model.getLogicalModelId());
		
			final CdaBuilder cdaBuilder = new CdaBuilder();
			CdaSettings cdaSettings = cdaBuilder.build(model, domain, logicalModel);

			if(log.isDebugEnabled()){
				log.debug(logModel(sessionId));
				log.debug(cdaSettings.asXML());
			}
			
			cdaProvider.addDatasource(cdaProvider.getSolution(), cdaProvider.getPath(), action, cdaSettings.asXML());
			model.setCdaDirty(false);
		} catch (Exception e) {
			 e.printStackTrace();
			 throw new SaikuAdhocException(
			 Messages.getErrorString("Repository.ERROR_0001_COULD_NOT_PUBLISH_FILE")
			 );
		}

	}
}
