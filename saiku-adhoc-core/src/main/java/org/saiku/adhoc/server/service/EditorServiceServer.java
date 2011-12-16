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

package org.saiku.adhoc.server.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.saiku.adhoc.exceptions.ModelException;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.model.SaikuMasterModelServer;
import org.saiku.adhoc.service.EditorService;

/**
 * @author mgiepz
 *
 */
public class EditorServiceServer extends EditorService {
	
    private ICDAManager cdaManager;

    public void setCDAManager(ICDAManager manager){
        this.cdaManager = manager;
        
    }

    public ICDAManager getCDAManager(){
        return cdaManager;
    }
	
	@Override
	public void createNewModel(String sessionId, MetadataModelInfo modelInfo)
	throws ModelException, JsonParseException, JsonMappingException, IOException {

	    SaikuMasterModel masterModel = null;
		
		if(modelInfo.getJson()==null){
			
			String domainId = URLDecoder.decode(modelInfo.getDomainId(), "UTF-8");
			Domain domain = metadataService.getDomain(domainId);
			LogicalModel model = metadataService.getLogicalModel(domainId,
					modelInfo.getModelId());
						
			masterModel = new SaikuMasterModelServer();
			masterModel.init(domain, model, sessionId, cdaManager);
		}else{
			ObjectMapper mapper = new ObjectMapper();
			masterModel = mapper.readValue(modelInfo.getJson(), SaikuMasterModelServer.class);
			
			String[] split = masterModel.getClientModelSelection().split("/");
			
			String domainId = URLDecoder.decode(split[0], "UTF-8");
			Domain domain = metadataService.getDomain(domainId);
			LogicalModel model = metadataService.getLogicalModel(domainId, split[1]);
			
			masterModel.init(domain, model, sessionId, cdaManager);
			masterModel.deriveModels();
		}
		
		
		
		sessionHolder.initSession(masterModel, sessionId);
		sessionHolder.getModel(sessionId).setClientModelSelection(
				URLEncoder.encode(masterModel.getDerivedModels().getDomain().getId(), "UTF-8")
				+ "/" + masterModel.getDerivedModels().getLogicalModel().getId());
		
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " createNewModel\n" + sessionHolder.logModel(sessionId));
		}

	}
}
