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
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;


public class TransModelToQuery {

	public Query doIt(SaikuMasterModel smm) {

		Domain domain = smm.getDerivedModels().getDomain();
		LogicalModel model = smm.getDerivedModels().getLogicalModel();

		Query query = new Query(domain, model);

		//We also need the Group Fields to be in the query
		for (SaikuGroup saikuGroup : smm.getGroups()) {
			Category category = model.findCategory(saikuGroup.getCategory());
			LogicalColumn column = model.findLogicalColumn(saikuGroup.getColumnId());
			final AggregationType selectedAggType = AggregationType.NONE;
			Selection selection = new Selection(category, column, selectedAggType);
			query.getSelections().add(selection);

			if(saikuGroup.getSort().equals("ASC")){
				Order order =  new Order(selection, Type.ASC);
				query.getOrders().add(order);
			}else if(saikuGroup.getSort().equals("DESC")){
				Order order =  new Order(selection, Type.DESC);
				query.getOrders().add(order);
			}

		}		
		
		for (SaikuColumn saikuColumn : smm.getColumns()) {
			if(saikuColumn.getFormula()==null){
				Category category = model.findCategory(saikuColumn.getCategory());
				LogicalColumn column = model.findLogicalColumn(saikuColumn.getId());
				final AggregationType selectedAggType = 
					saikuColumn.getSelectedAggType()!=null?
							AggregationType.valueOf(saikuColumn.getSelectedAggType()): AggregationType.NONE;
							Selection selection = new Selection(category, column, selectedAggType);
							query.getSelections().add(selection);

							if(saikuColumn.getSort().equals("ASC")){
								Order order =  new Order(selection, Type.ASC);
								query.getOrders().add(order);
							}else if(saikuColumn.getSort().equals("DESC")){
								Order order =  new Order(selection, Type.DESC);
								query.getOrders().add(order);
							}
			}

		}

		//We also need the Group Fields to be in the query
		for (SaikuGroup saikuGroup : smm.getGroups()) {
			Category category = model.findCategory(saikuGroup.getCategory());
			LogicalColumn column = model.findLogicalColumn(saikuGroup.getColumnId());
			final AggregationType selectedAggType = AggregationType.NONE;
			Selection selection = new Selection(category, column, selectedAggType);
			query.getSelections().add(selection);

			/*
			Order order =  new Order(selection, Type.ASC);
			query.getOrders().add(order);
			 */

		}

		//Remove all old filters from query
		query.getConstraints().clear();
		query.getParameters().clear();

		//add params
		//		for (String filter : smm.getDerivedModels().getFilterQueries().keySet()) {
		//			final String filterName = "F_" + filter.replace(".", "_");
		//			//add filter to mql
		//			String formula = "OR(" +
		//			"IN([" + filter + "]; [param:" + filterName + "]);" +
		//			"EQUALS(\"\"; [param:" + filterName + "]))";
		//			Constraint cst = new Constraint(CombinationType.AND , formula);
		//			query.getConstraints().add(cst);
		//			//TODO: Dateparams
		//			Parameter paramMql = new Parameter(filterName, DataType.STRING, "");
		//			query.getParameters().add(paramMql);
		//		}

		final ArrayList<SaikuParameter> parameters = smm.getParameters();

		for (SaikuParameter param : parameters) {
			final String filterName = "F_" + param.getCategory() + "_" + param.getId();
			String columnId = param.getCategory() + "." + param.getId();

			if(param.getType().equals(DataType.STRING.getName())){
				//string parameters
				String formula = "OR(" +
				"IN([" + columnId  + "]; [param:" + filterName + "]);" +
				"EQUALS(\"\"; [param:" + filterName + "]))";
				Constraint cst = new Constraint(CombinationType.AND , formula);
				query.getConstraints().add(cst);

				Parameter paramMql = new Parameter(filterName, DataType.STRING, "");
				query.getParameters().add(paramMql);
			}

			if(param.getType().equals(DataType.DATE.getName())){

				String formulaFrom = "["+columnId+"] > " + "[param:" + filterName + "_FROM]";
				String formulaTo = "["+columnId+"] < " + "[param:" + filterName + "_TO]";

				Constraint from = new Constraint(CombinationType.AND , formulaFrom);
				query.getConstraints().add(from);
				Constraint to = new Constraint(CombinationType.AND , formulaTo);
				query.getConstraints().add(to);

				Parameter paramMqlFrom = new Parameter(filterName + "_FROM", DataType.DATE, "");
				query.getParameters().add(paramMqlFrom);

				Parameter paramMqlTo = new Parameter(filterName + "_TO", DataType.DATE, "");
				query.getParameters().add(paramMqlTo);
			}


		}


		query.setDisableDistinct(smm.getSettings().isDisableDistinct());
		query.setLimit(smm.getSettings().getLimit());

		return query;
	}

}
