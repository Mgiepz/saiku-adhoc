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

package org.saiku.adhoc.service.cda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.concept.types.DataType;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.exceptions.QueryException;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.dto.FilterResult;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.service.repository.IRepositoryHelper;

public class CdaQueryService {
	
	protected Log log = LogFactory.getLog(CdaQueryService.class);
	
	protected ICdaAccessor cdaAccessor;
	
	protected IRepositoryHelper repository;
	
	protected WorkspaceSessionHolder sessionHolder;

	public void setSessionHolder(WorkspaceSessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public void setRepositoryHelper(IRepositoryHelper repository) {
		this.repository = repository;
	}

	public void setCdaAccessor(ICdaAccessor cdaAccessor) {
		this.cdaAccessor = cdaAccessor;
	}
	

	/**
	 * Execute the query
	 * 
	 * @param queryName
	 * @param sessionId 
	 * @throws SaikuAdhocException 
	 * @throws QueryException 
	 * @throws CdaException 
	 * @throws QuerybuilderServiceException 
	 */
	public String runQuery(String queryName, String sessionId) throws SaikuAdhocException, CdaException {

		
		sessionHolder.materializeModel(sessionId);
		
		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		
		//then let cda generate output Json
		//TODO: We need to remove the group-only columns here
		return cdaAccessor.doQuery(model, queryName, null);

	}

	public FilterResult getFilterResult(String sessionId, String categoryId,
			String columnId) throws CdaException, QueryException, SaikuAdhocException, ResourceException, IOException{ 
	
		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		
		model.deriveModels();
		LogicalColumn column = model.getDerivedModels().getQuery().getLogicalModel().findLogicalColumn(columnId);

		//
		String filterKey = categoryId + "." + columnId; 
		
		List<String> selectedValues = null;
		
		final List<SaikuParameter> parameters = model.getParameters();
		for (SaikuParameter saikuParameter : parameters) {
			if(saikuParameter.getCategory().equals(categoryId)&&
				saikuParameter.getId().equals(columnId)){
					selectedValues = saikuParameter.getParameterValues();
				}
		}

		if(column.getDataType().equals(DataType.DATE)){
			//second argument should be the currently selected date
			return new FilterResult(null,null, column.getDataType().getName());	
		}else{
			final String filterResultJson = this.runQuery(filterKey, sessionId);
			return new FilterResult(filterResultJson,
					selectedValues, 
					column.getDataType().getName());	
		}

	}

	public Properties getProperties(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties setProperties(String queryName, Properties props) {
		// TODO Auto-generated method stub
		return null;
	}

}
