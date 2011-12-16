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

package org.saiku.adhoc.server.model.service.cda;

import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.exceptions.QueryException;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.service.cda.CdaQueryService;

public class CdaQueryServiceServer extends CdaQueryService {
	
    private ICDAManager cdaManager;

    public void setCDAManager(ICDAManager manager){
        this.cdaManager = manager;
        
    }

    public ICDAManager getCDAManager(){
        return cdaManager;
    }
    
	/**
	 * Execute the query
	 * 
	 * @param queryName
	 * @param sessionId 
	 * @throws QueryException 
	 * @throws CdaException 
	 * @throws QuerybuilderServiceException 
	 */
	public String runQuery(String queryName, String sessionId) throws QueryException, CdaException {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);

		String action = sessionId + ".cda";

		//Save the cda first
		try {
			model.deriveModels();
			cdaManager.addDatasource(new SaikuCDA(action, model.getCdaSettings().asXML().getBytes("UTF-8")));
		} catch (Exception e) {
			throw new QueryException(e.getMessage());
		}
	
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:CdaQueryService " + sessionId + " runQuery\n" + sessionHolder.logModel(sessionId));
		}
		
		//then let cda generate output Json
		//TODO: We need to remove the group-only columns here
		return cdaAccessor.doQuery(model, queryName, null);

	}

	

}
