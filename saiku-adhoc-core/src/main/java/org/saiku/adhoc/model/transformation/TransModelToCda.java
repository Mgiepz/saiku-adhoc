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

package org.saiku.adhoc.model.transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.metadata.query.model.Selection;
import org.pentaho.metadata.query.model.util.QueryXmlHelper;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.utils.XmlUtils;

import pt.webdetails.cda.connections.UnsupportedConnectionException;
import pt.webdetails.cda.dataaccess.AbstractDataAccess;
import pt.webdetails.cda.dataaccess.ColumnDefinition;
import pt.webdetails.cda.dataaccess.DataAccess;
import pt.webdetails.cda.dataaccess.MqlDataAccess;
import pt.webdetails.cda.dataaccess.UnsupportedDataAccessException;
import pt.webdetails.cda.settings.CdaSettings;
import pt.webdetails.cda.settings.UnknownDataAccessException;

public class TransModelToCda {

	public CdaSettings doIt(SaikuMasterModel smm, ICDAManager cdaManager) throws UnsupportedConnectionException, UnsupportedDataAccessException, UnknownDataAccessException, SaikuAdhocException {

		CdaSettings cda = null;

		String sessionId = smm.getDerivedModels().getSessionId();
	    String domainInfo = smm.getDerivedModels().getDomain().getId();
			cda = cdaManager.initCDA(sessionId, domainInfo);
			
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
				
				
				final List<pt.webdetails.cda.dataaccess.Parameter> parameters = ((AbstractDataAccess) cda.getDataAccess(sessionId))
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

		return cda;
	}

	protected Collection<ColumnDefinition> getCdaColumns(SaikuMasterModel smm) {

		// TODO: This should work without using the mql columns
		// should be created directly from the MasterModel

		final Query query = smm.getDerivedModels().getQuery();
		final int columnCount = query.getSelections().size();

		String locale = "en_En";
		
		final Collection<ColumnDefinition> cdaColumns = new ArrayList<ColumnDefinition>();

		for (int i = 0; i < columnCount; i++) {
			
			Selection sel = query.getSelections().get(i);
			ColumnDefinition columnDef = new ColumnDefinition();
			columnDef.setIndex(i);
			columnDef.setName(sel.getLogicalColumn().getName(locale));
			columnDef.setType(ColumnDefinition.TYPE.COLUMN);
			cdaColumns.add(columnDef);
			
		}
		
		for (SaikuColumn saikuColumn : smm.getColumns()) {
			if(!(saikuColumn.getFormula()==null)){
				ColumnDefinition columnDef = new ColumnDefinition();
				columnDef.setName(saikuColumn.getName());
				columnDef.setType(ColumnDefinition.TYPE.CALCULATED_COLUMN);
				columnDef.setFormula(saikuColumn.getFormula());
				cdaColumns.add(columnDef);
			}
		}
		
		//add the calculated columns
		

		return cdaColumns;
	}

}
