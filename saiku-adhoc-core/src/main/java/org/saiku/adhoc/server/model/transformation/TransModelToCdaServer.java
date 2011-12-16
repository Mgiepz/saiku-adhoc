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

package org.saiku.adhoc.server.model.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.metadata.query.model.util.QueryXmlHelper;
import org.saiku.adhoc.exceptions.ModelException;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.model.transformation.TransModelToCda;
import org.saiku.adhoc.utils.XmlUtils;

import pt.webdetails.cda.connections.Connection;
import pt.webdetails.cda.connections.metadata.MetadataConnection;
import pt.webdetails.cda.dataaccess.AbstractDataAccess;
import pt.webdetails.cda.dataaccess.DataAccess;
import pt.webdetails.cda.dataaccess.MqlDataAccess;
import pt.webdetails.cda.settings.CdaSettings;

public class TransModelToCdaServer extends TransModelToCda{

	public CdaSettings doIt(SaikuMasterModel smm) throws ModelException {

		CdaSettings cda = null;

		String sessionId = smm.getDerivedModels().getSessionId();

		try {
			cda = new CdaSettings("cda" + sessionId, null);

			String[] domainInfo = smm.getDerivedModels().getDomain().getId()
					.split("/");

			Connection connection = new MetadataConnection("1", domainInfo[0]+"/"+domainInfo[1],
					domainInfo[1]);
			DataAccess dataAccess = new MqlDataAccess(sessionId, sessionId,
					"1", "");

			cda.addConnection(connection);
			cda.addDataAccess(dataAccess);
			
			final QueryXmlHelper xmlHelper = smm.getDerivedModels().getXmlHelper();
			
			cda.getDataAccess(sessionId).setQuery(
					XmlUtils.prettyPrint(xmlHelper
							.toXML(smm.getDerivedModels().getQuery())));


			final Map<String, Query> filterQueries = smm.getDerivedModels().getFilterQueries();
			
			//Create a CDA for each Filter Query
			for (Entry<String, Query> filterQueryEntry : filterQueries.entrySet()){
				String filterKey = filterQueryEntry.getKey();
				Query filterQuery = filterQueryEntry.getValue();
				DataAccess fCda = new MqlDataAccess(filterKey, filterKey, "1", XmlUtils.prettyPrint(xmlHelper.toXML(filterQuery))) ;
				cda.addDataAccess(fCda);
			}
			
			List<SaikuParameter> params = smm.getParameters();
					
			for (SaikuParameter saikuParameter : params) {
				
				String columnId = saikuParameter.getId();
				LogicalColumn column = smm.getDerivedModels().getQuery().getLogicalModel().findLogicalColumn(columnId);

				final String filterName = "F_" + saikuParameter.getCategory() + "_" + saikuParameter.getId();
				
				
				final ArrayList<pt.webdetails.cda.dataaccess.Parameter> parameters = ((AbstractDataAccess) cda.getDataAccess(sessionId))
				.getParameters();
				if(column.getDataType().getName().equals("String")){
				

				String type = "String";
				String pattern = ""; 
				type = pt.webdetails.cda.dataaccess.Parameter.Type.STRING_ARRAY.getName(); 
				pt.webdetails.cda.dataaccess.Parameter paramCda = new pt.webdetails.cda.dataaccess.Parameter(
						filterName,
						type,
						"", pattern,
						pt.webdetails.cda.dataaccess.Parameter.Access.PUBLIC.name()
						);

				parameters.add(paramCda);				
				
				}else if(column.getDataType().getName().equals("Date")){
					
					String nameFrom = filterName + "_FROM";
					String nameTo = filterName + "_TO";
					String type = pt.webdetails.cda.dataaccess.Parameter.Type.DATE.getName(); 
					String pattern = "dd.mm.yyyy"; 
					
					pt.webdetails.cda.dataaccess.Parameter paramCdaFrom = new pt.webdetails.cda.dataaccess.Parameter(
							nameFrom,
							type,
							"", pattern,
							pt.webdetails.cda.dataaccess.Parameter.Access.PUBLIC.name()
							);

					pt.webdetails.cda.dataaccess.Parameter paramCdaTo = new pt.webdetails.cda.dataaccess.Parameter(
							nameTo,
							type,
							"", pattern,
							pt.webdetails.cda.dataaccess.Parameter.Access.PUBLIC.name()
							);

					parameters.add(paramCdaFrom);	
					parameters.add(paramCdaTo);	
					
				}
									

			}
			
			cda.getDataAccess(sessionId).getColumnDefinitions().clear();
			cda.getDataAccess(sessionId).getColumnDefinitions()
					.addAll(getCdaColumns(smm));

		} catch (Exception e) {
			throw new ModelException("could not derive CDA");
		}

		return cda;
	}

}
