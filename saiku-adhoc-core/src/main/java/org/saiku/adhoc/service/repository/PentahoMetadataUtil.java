/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2011 Pentaho Corporation.  All rights reserved.
 * 
 * Created Jan, 2011
 * @author jdixon
 */
package org.saiku.adhoc.service.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.model.concept.types.AggregationType;
import org.pentaho.metadata.model.concept.types.Alignment;
import org.pentaho.metadata.model.concept.types.DataType;
import org.pentaho.metadata.model.concept.types.FieldType;
import org.pentaho.platform.engine.core.system.PentahoBase;
import org.pentaho.platform.util.messages.LocaleHelper;
import org.saiku.adhoc.model.metadata.ICategory;
import org.saiku.adhoc.model.metadata.IColumn;
import org.saiku.adhoc.model.metadata.impl.Category;
import org.saiku.adhoc.model.metadata.impl.Column;
import org.saiku.adhoc.model.metadata.impl.MetadataModel;


public class PentahoMetadataUtil extends PentahoBase {

	public org.pentaho.metadata.model.Domain getDomain() {
		return domain;
	}

	public void setDomain(org.pentaho.metadata.model.Domain domain) {
		this.domain = domain;
	}

	private static final long serialVersionUID = -123835493828427853L;

	private Log logger = LogFactory.getLog(PentahoMetadataUtil.class);

	private org.pentaho.metadata.model.Domain domain;

	@Override
	public Log getLogger() {
		return logger;
	}

	/**
	 * Works out what is the most appropriate locale to use given a domain and the user's
	 * current locale
	 * @return
	 */
	protected String getLocale(  ) {
		String locale = LocaleHelper.getClosestLocale(LocaleHelper.getLocale().toString(), domain.getLocaleCodes());
		return locale;
	}

	/**
	 * Creates a lightweight, serializable model object from a logical model
	 * @param m
	 * @param domainId
	 * @return
	 */
	public MetadataModel createThinModel(LogicalModel m, String domainId) {
		// create the model object
		MetadataModel model = new MetadataModel();
		model.setName(m.getName(getLocale()));
		model.setId(m.getId());
		model.setDomainId(domainId);
		model.setDescription(m.getDescription(getLocale()));
		// add the categories to the model
		List<ICategory> categories = new ArrayList<ICategory>();
		for (org.pentaho.metadata.model.Category cat : m.getCategories()) {
			categories.add(createCategory(m, cat));
		}
		model.setCategories(categories.toArray(new Category[categories.size()]));

		return model;

	}

	/**
	 * Creates a lightweight, serializable category objects from a logical model category
	 * @param m
	 * @param c
	 * @return
	 */
	private Category createCategory(LogicalModel m, org.pentaho.metadata.model.Category c) {
		// create a thin category object
		Category cat = new Category();
		cat.setName(c.getName(getLocale()));
		cat.setId(c.getId());
		cat.setDescription(c.getDescription(getLocale()));
		if(cat.getId().equals(cat.getDescription())) {
			cat.setDescription(null);
		}
		List<IColumn> columns = new ArrayList<IColumn>();
		for (LogicalColumn col : c.getLogicalColumns()) {
			columns.add(createColumn(m, col, c));
		}
		cat.setColumns(columns.toArray(new Column[columns.size()]));

		return cat;
	}

	/**
	 * Creates a lightweight, serializable Column object from a logical model column
	 * @param m
	 * @param c
	 * @return
	 */
	private Column createColumn(LogicalModel m, LogicalColumn c, org.pentaho.metadata.model.Category category) {
		Column col = new Column();
		col.setName(c.getName(getLocale()));
		col.setId(c.getId());
		col.setDescription(c.getDescription(getLocale()));
		if(col.getId().equals(col.getDescription())) {
			col.setDescription(null);
		}
		if( c.getFieldType() != null ) {      
			col.setFieldType(c.getFieldType().name());
		} else {
			col.setFieldType( "UNKNOWN" ); //$NON-NLS-1$
		}

		col.setType(c.getDataType().getName().toUpperCase());
		col.setCategory(category.getId());
		// set the aggregation fields for the column
		List<AggregationType> possibleAggs = c.getAggregationList();
		List<String> aggTypes = new ArrayList<String>();
		if (possibleAggs != null) {
			for (AggregationType agg : possibleAggs) {
				aggTypes.add(agg.name());
			}
		}

		// There might be a default agg, but no agg list. If so, add it to the list.

		AggregationType defaultAggType = AggregationType.NONE;
		if (c.getAggregationType() != null) {
			defaultAggType = c.getAggregationType();
		}
		if (!aggTypes.contains(defaultAggType)) {
			aggTypes.add(defaultAggType.name());
		}
		col.setAggTypes(aggTypes);
		col.setDefaultAggType(defaultAggType.name());
		col.setSelectedAggType(defaultAggType.name());

		// set the alignment
		DataType dataType = c.getDataType();
		FieldType fieldType = c.getFieldType();
		Object obj = c.getProperty("alignment"); //$NON-NLS-1$
		if(obj instanceof Alignment) {
			if(obj == Alignment.LEFT) {
				col.setHorizontalAlignment(Alignment.LEFT.toString());
			}
			else if(obj == Alignment.RIGHT) {
				col.setHorizontalAlignment(Alignment.RIGHT.toString());
			}
			else if(obj == Alignment.CENTERED) {
				col.setHorizontalAlignment(Alignment.CENTERED.toString());
			}
		}
		else if(fieldType == FieldType.FACT) {
			col.setHorizontalAlignment(Alignment.RIGHT.toString());
		}
		else if(fieldType == FieldType.OTHER && dataType == DataType.NUMERIC) {
			col.setHorizontalAlignment(Alignment.RIGHT.toString());
		}
		else {
			col.setHorizontalAlignment(Alignment.LEFT.toString());
		}
		// set the format mask
		obj = c.getProperty("mask"); //$NON-NLS-1$
		if(obj != null) {
			col.setFormatMask((String)obj);
		}
		return col;
	}

}
