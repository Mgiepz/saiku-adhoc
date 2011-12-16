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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.metadata.model.Category;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.model.concept.types.AggregationType;
import org.pentaho.metadata.model.concept.types.DataType;
import org.pentaho.metadata.query.model.Query;
import org.pentaho.metadata.query.model.Selection;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;

public class TransModelToParams {

	public Map<String, Query> doIt(SaikuMasterModel smm) {

		List<SaikuParameter> parameters = smm.getParameters();

		Map<String, Query> filterQueries = new HashMap<String, Query>();

		for (SaikuParameter saikuParameter : parameters) {
			final String categoryId = saikuParameter.getCategory();
			final String columnId = saikuParameter.getId();
			
			//
			String filterKey = categoryId + "." + columnId;	

			final LogicalModel logicalModel = smm.getDerivedModels().getLogicalModel();
			Category category = logicalModel.findCategory(categoryId);
			LogicalColumn column = logicalModel.findLogicalColumn(columnId);

			//Dates do not need their own query
			if(!column.getDataType().equals(DataType.DATE)){
				Query filterQuery = new Query(smm.getDerivedModels().getDomain(), 
						logicalModel);

				Selection selection = new Selection(category, column, AggregationType.NONE);
				filterQuery.getSelections().add(selection);	
				filterQueries.put(filterKey, filterQuery);
			}
		}

		return filterQueries;
	}

}
