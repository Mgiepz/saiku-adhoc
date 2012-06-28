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

package org.saiku.adhoc.model.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.pentaho.metadata.model.Category;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.model.concept.types.AggregationType;
import org.pentaho.metadata.model.concept.types.DataType;
import org.pentaho.metadata.query.model.CombinationType;
import org.pentaho.metadata.query.model.Constraint;
import org.pentaho.metadata.query.model.Order;
import org.pentaho.metadata.query.model.Order.Type;
import org.pentaho.metadata.query.model.Parameter;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.metadata.query.model.Selection;
import org.pentaho.metadata.query.model.util.QueryXmlHelper;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.utils.XmlUtils;

import pt.webdetails.cda.connections.Connection;
import pt.webdetails.cda.connections.UnsupportedConnectionException;
import pt.webdetails.cda.connections.metadata.MetadataConnection;
import pt.webdetails.cda.dataaccess.AbstractDataAccess;
import pt.webdetails.cda.dataaccess.ColumnDefinition;
import pt.webdetails.cda.dataaccess.MqlDataAccess;
import pt.webdetails.cda.dataaccess.UnsupportedDataAccessException;
import pt.webdetails.cda.settings.CdaSettings;
import pt.webdetails.cda.settings.UnknownDataAccessException;

public class CdaBuilder {


	public CdaSettings build(SaikuMasterModel masterModel, Domain domain, LogicalModel logicalModel) throws UnsupportedConnectionException,
			UnsupportedDataAccessException, UnknownDataAccessException, SaikuAdhocException {

		final Collection<ColumnDefinition> cdaColumns = new ArrayList<ColumnDefinition>();

		// The MQL-Query to build
		Query query = new Query(domain, logicalModel);

		int index = 0;

		// The Group Columns
		for (SaikuGroup saikuGroup : masterModel.getGroups()) {
			Category category = logicalModel.findCategory(saikuGroup.getCategory());
			LogicalColumn column = logicalModel.findLogicalColumn(saikuGroup.getColumnId());
			final AggregationType selectedAggType = AggregationType.NONE;
			Selection selection = new Selection(category, column, selectedAggType);
			query.getSelections().add(selection);

			if (saikuGroup.getSort().equals("ASC")) {
				Order order = new Order(selection, Type.ASC);
				query.getOrders().add(order);
			} else if (saikuGroup.getSort().equals("DESC")) {
				Order order = new Order(selection, Type.DESC);
				query.getOrders().add(order);
			}

			ColumnDefinition columnDef = new ColumnDefinition();
			columnDef.setIndex(index);

			columnDef.setName(saikuGroup.getDisplayName());
			columnDef.setType(ColumnDefinition.TYPE.COLUMN);
			cdaColumns.add(columnDef);

			index++;
		}

		// while the query is being assembled we also create the cda columns
		// that are based on original columns
		for (SaikuColumn saikuColumn : masterModel.getColumns()) {
			if (saikuColumn.getFormula() == null) {

				Category category = logicalModel.findCategory(saikuColumn.getCategory());
				LogicalColumn column = logicalModel.findLogicalColumn(saikuColumn.getId());
				final AggregationType selectedAggType = saikuColumn.getSelectedAggType() != null ? AggregationType
						.valueOf(saikuColumn.getSelectedAggType()) : AggregationType.NONE;
				Selection selection = new Selection(category, column, selectedAggType);
				query.getSelections().add(selection);

				if (saikuColumn.getSort().equals("ASC")) {
					Order order = new Order(selection, Type.ASC);
					query.getOrders().add(order);
				} else if (saikuColumn.getSort().equals("DESC")) {
					Order order = new Order(selection, Type.DESC);
					query.getOrders().add(order);
				}

				ColumnDefinition columnDef = new ColumnDefinition();
				columnDef.setIndex(index);

				columnDef.setName(saikuColumn.getName());
				columnDef.setType(ColumnDefinition.TYPE.COLUMN);
				cdaColumns.add(columnDef);

				index++;
			}

		}

		// and then the calculated columns
		for (SaikuColumn saikuColumn : masterModel.getColumns()) {
			if (saikuColumn.getFormula() != null) {
				ColumnDefinition columnDef = new ColumnDefinition();
				columnDef.setName(saikuColumn.getName());
				columnDef.setType(ColumnDefinition.TYPE.CALCULATED_COLUMN);
				columnDef.setFormula("=" + saikuColumn.getFormula());
				cdaColumns.add(columnDef);
			}
		}

		// The CDA to build
		String sessionId = masterModel.getSessionId();
		String domainId = masterModel.getDomainId();
		CdaSettings cda = initCda(sessionId, domainId);

		// Remove all old filters from query
		query.getConstraints().clear();
		query.getParameters().clear();

		// Build the MQL-Paramters
		buildMqlParameters(masterModel, query);

		Integer limit = masterModel.getSettings().getLimit();
		if (limit != null && limit != -1)
			query.setLimit(limit);
		query.setDisableDistinct(masterModel.getSettings().isDisableDistinct()); // TODO:Client

		final QueryXmlHelper xmlHelper = new QueryXmlHelper();

		cda.getDataAccess(sessionId).setQuery(XmlUtils.prettyPrint(xmlHelper.toXML(query)));

		// Create a CDA for each Filter Query
		final Map<String, Query> filterQueries = buildFilterQueries(masterModel, domain, logicalModel);
		for (Entry<String, Query> filterQueryEntry : filterQueries.entrySet()) {
			String filterKey = filterQueryEntry.getKey();
			Query filterQuery = filterQueryEntry.getValue();
			MqlDataAccess fCda = new MqlDataAccess(filterKey, filterKey, "1",
					XmlUtils.prettyPrint(xmlHelper.toXML(filterQuery)));
			fCda.setCacheEnabled(true);
			cda.addDataAccess(fCda);
		}

		final List<pt.webdetails.cda.dataaccess.Parameter> parameters = ((AbstractDataAccess) cda.getDataAccess(sessionId))
				.getParameters();

		parameters.addAll(buildCdaParameters(masterModel, logicalModel));

		cda.getDataAccess(sessionId).getColumnDefinitions().clear();
		cda.getDataAccess(sessionId).getColumnDefinitions().addAll(cdaColumns);

		return cda;

	}

	private void buildMqlParameters(SaikuMasterModel smm, Query query) {

		final List<SaikuParameter> params = smm.getParameters();

		for (SaikuParameter param : params) {
			final String filterName = "F_" + param.getCategory() + "_" + param.getId();
			String columnId = param.getCategory() + "." + param.getId();

			if (param.getType().equals(DataType.STRING.getName())) {
				// string parameters
				String formula = "OR(" + "IN([" + columnId + "]; [param:" + filterName + "]);" + "EQUALS(\"\"; [param:"
						+ filterName + "]))";
				Constraint cst = new Constraint(CombinationType.AND, formula);
				query.getConstraints().add(cst);

				Parameter paramMql = new Parameter(filterName, DataType.STRING, "");
				query.getParameters().add(paramMql);
			}

			if (param.getType().equals(DataType.DATE.getName()) 
					//&& !(param.getParameterValues().size() < 2)
					) {

				String formulaFrom = "[" + columnId + "] >= " + "[param:" + filterName + "_FROM]";
				String formulaTo = "[" + columnId + "] <= " + "[param:" + filterName + "_TO]";

				Constraint from = new Constraint(CombinationType.AND, formulaFrom);
				query.getConstraints().add(from);
				Constraint to = new Constraint(CombinationType.AND, formulaTo);
				query.getConstraints().add(to);

				Parameter paramMqlFrom = new Parameter(filterName + "_FROM", DataType.DATE, "");
				query.getParameters().add(paramMqlFrom);

				Parameter paramMqlTo = new Parameter(filterName + "_TO", DataType.DATE, "");
				query.getParameters().add(paramMqlTo);
			}

		}

	}

	private List<pt.webdetails.cda.dataaccess.Parameter> buildCdaParameters(SaikuMasterModel smm, LogicalModel logicalModel) {

		final List<SaikuParameter> params = smm.getParameters();

		List<pt.webdetails.cda.dataaccess.Parameter> parameters = new ArrayList<pt.webdetails.cda.dataaccess.Parameter>();

		for (SaikuParameter saikuParameter : params) {

			String columnId = saikuParameter.getId();
			
			LogicalColumn column = logicalModel.findLogicalColumn(columnId);

			final String filterName = "F_" + saikuParameter.getCategory() + "_" + saikuParameter.getId();

			if (column.getDataType().getName().equals("String")) {

				String type = "String";
				String pattern = "";
				type = pt.webdetails.cda.dataaccess.Parameter.Type.STRING_ARRAY.getName();
				pt.webdetails.cda.dataaccess.Parameter paramCda = new pt.webdetails.cda.dataaccess.Parameter(filterName,
						type, "", pattern, pt.webdetails.cda.dataaccess.Parameter.Access.PUBLIC.name());

				parameters.add(paramCda);

			} else if (column.getDataType().getName().equals("Date")
					//&& !(saikuParameter.getParameterValues().size() < 2)
			) {

				String nameFrom = filterName + "_FROM";
				String nameTo = filterName + "_TO";
				String type = pt.webdetails.cda.dataaccess.Parameter.Type.DATE.getName();
				String pattern = "dd.MM.yyyy";

				pt.webdetails.cda.dataaccess.Parameter paramCdaFrom = new pt.webdetails.cda.dataaccess.Parameter(nameFrom,
						type, "${TODAY()}", pattern, pt.webdetails.cda.dataaccess.Parameter.Access.PUBLIC.name());

				pt.webdetails.cda.dataaccess.Parameter paramCdaTo = new pt.webdetails.cda.dataaccess.Parameter(nameTo, type,
						"${TODAY()}", pattern, pt.webdetails.cda.dataaccess.Parameter.Access.PUBLIC.name());

				
				
				parameters.add(paramCdaFrom);
				parameters.add(paramCdaTo);

			}

		}

		return parameters;

	}

	/**
	 * This method builds mql-queries to provide the parameter values
	 * 
	 * @param smm
	 * @return
	 */
	private Map<String, Query> buildFilterQueries(SaikuMasterModel smm, Domain domain, LogicalModel logicalModel) {

		List<SaikuParameter> parameters = smm.getParameters();

		Map<String, Query> filterQueries = new HashMap<String, Query>();

		for (SaikuParameter saikuParameter : parameters) {
			final String categoryId = saikuParameter.getCategory();
			final String columnId = saikuParameter.getId();

			String filterKey = categoryId + "." + columnId;

			Category category = logicalModel.findCategory(categoryId);
			LogicalColumn column = logicalModel.findLogicalColumn(columnId);

			// Dates do not need their own query
			if (!column.getDataType().equals(DataType.DATE)) {
				Query filterQuery = new Query(domain, logicalModel);

				Selection selection = new Selection(category, column, AggregationType.NONE);
				
				filterQuery.getSelections().add(selection);
				filterQuery.getOrders().add(new Order(selection, Type.ASC ));
				filterQueries.put(filterKey, filterQuery);
			}
		}

		return filterQueries;

	}

	private CdaSettings initCda(String sessionId, String domain) throws UnsupportedConnectionException,
			UnsupportedDataAccessException {
		CdaSettings cda = new CdaSettings("cda" + sessionId, null);

		String[] domainInfo = domain.split("/");
		Connection connection = new MetadataConnection("1", domainInfo[0] + "/" + domainInfo[1], domainInfo[1]);
		MqlDataAccess dataAccess = new MqlDataAccess(sessionId, sessionId, "1", "");
		//dataAccess.setCacheEnabled(true);
		cda.addConnection(connection);
		cda.addDataAccess(dataAccess);
		return cda;
	}

}
