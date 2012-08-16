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

package org.saiku.adhoc.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.reporting.libraries.formula.Formula;
import org.pentaho.reporting.libraries.formula.parser.ParseException;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.dto.DisplayName;
import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.dto.FilterValue;
import org.saiku.adhoc.model.dto.Position;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuLabel;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.model.master.SaikuReportSettings;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.providers.IMetadataProvider;
import org.saiku.adhoc.utils.StringUtils;
import org.saiku.adhoc.utils.TemplateUtils;

/**
 * @author mgiepz
 * 
 */
public class EditorService {

	public IMetadataProvider getMetadataProvider() {
		return metadataProvider;
	}

	public void setMetadataProvider(IMetadataProvider metadataProvider) {
		this.metadataProvider = metadataProvider;
	}

	protected Log log = LogFactory.getLog(EditorService.class);

	protected WorkspaceSessionHolder sessionHolder;
	protected IMetadataProvider metadataProvider;

	public void setSessionHolder(WorkspaceSessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;

	}

//	private ReportGeneratorService reportGeneratorService;
//
//	public void setReportGeneratorService(ReportGeneratorService reportGeneratorService) {
//		this.reportGeneratorService = reportGeneratorService;
//	}

	public void createNewModel(String sessionId, MetadataModelInfo modelInfo) throws SaikuAdhocException {

		SaikuMasterModel masterModel = null;

		try {

			if (modelInfo.getJson() == null) {
				String domainId;

				domainId = URLDecoder.decode(modelInfo.getDomainId(), "UTF-8");

				Domain domain = metadataProvider.getDomain(domainId);
				LogicalModel logicalModel = metadataProvider.getLogicalModel(domainId, modelInfo.getModelId());

				masterModel = new SaikuMasterModel();
				
				ModelHelper.init(masterModel, domain, logicalModel, sessionId);
				
			} else {
				ObjectMapper mapper = new ObjectMapper();
				masterModel = mapper.readValue(modelInfo.getJson(), SaikuMasterModel.class);

				String[] split = masterModel.getClientModelSelection().split("/");

				String domainId = URLDecoder.decode(split[0], "UTF-8");
				Domain domain = metadataProvider.getDomain(domainId);
				LogicalModel logicalModel = metadataProvider.getLogicalModel(domainId, split[1]);

				ModelHelper.init(masterModel, domain, logicalModel, sessionId);
				
				masterModel.setCdaDirty(true);

			}

			sessionHolder.initSession(masterModel, sessionId);

			sessionHolder.getModel(sessionId).setClientModelSelection(
					URLEncoder.encode(masterModel.getDomainId(), "UTF-8") + "/"
							+ masterModel.getLogicalModelId());

		} catch (UnsupportedEncodingException e) {
			final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - "
					+ e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
			log.error(message, e);
			throw new SaikuAdhocException(message, e);
		} catch (JsonParseException e) {
			final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - "
					+ e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
			log.error(message, e);
			throw new SaikuAdhocException(message, e);
		} catch (JsonMappingException e) {
			final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - "
					+ e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
			log.error(message, e);
			throw new SaikuAdhocException(message, e);
		} catch (IOException e) {
			final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - "
					+ e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
			log.error(message, e);
			throw new SaikuAdhocException(message, e);
		}

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " createNewModel\n" + sessionHolder.logModel(sessionId));
		}

	}

	public DisplayName addColumn(String sessionId, String category, String columnId, Position position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		SaikuColumn column = findAndRemoveByUid(model, position.getUid());

		List<SaikuColumn> columns = model.getColumns();

		final LogicalModel logicalModel = metadataProvider.getLogicalModel(model.getDomainId(),model.getLogicalModelId());
		LogicalColumn logicalColumn = logicalModel.findLogicalColumn(columnId);

		if (column == null) {
			column = new SaikuColumn(logicalColumn);
			column.setName(StringUtils.getUniqueColumnName(column.getName(), columns));
			column.setCategory(category);
			column.setId(columnId);
			column.setSelectedAggType(column.getDefaultAggType());
			column.setSelectedSummaryType("NONE");
			column.setUid(position.getUid());
		}

		columns.add(position.getPosition(), column);
		
		model.setCdaDirty(true);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " addColumn\n" + sessionHolder.logModel(sessionId));
		}

		return new DisplayName(column.getName(), position.getUid());

	}

	/**
	 * This method is ambigeous. it removes all types of elements but only finds columns
	 * 
	 * @param model
	 * @param uid
	 * @return
	 */
	private SaikuColumn findAndRemoveByUid(SaikuMasterModel model, String uid) {

		final List<SaikuColumn> columns = model.getColumns();

		SaikuColumn saikuColumn = null;
		for (Iterator<SaikuColumn> iterator = columns.iterator(); iterator.hasNext();) {
			SaikuColumn col = (SaikuColumn) iterator.next();
			if (col.getUid().equals(uid)) {
				saikuColumn = col;
				break;
			}
		}
		if (saikuColumn != null) {
			columns.remove(saikuColumn);
			return saikuColumn;
		}

		final List<SaikuGroup> groups = model.getGroups();

		SaikuGroup saikuGroup = null;
		for (Iterator<SaikuGroup> iterator2 = groups.iterator(); iterator2.hasNext();) {
			SaikuGroup group = (SaikuGroup) iterator2.next();
			if (group.getUid().equals(uid)) {
				saikuGroup = group;
				break;
			}
		}
		if (saikuGroup != null) {
			groups.remove(saikuGroup);
		}


		final List<SaikuParameter> params = model.getParameters();

		SaikuParameter saikuParam = null;
		for (Iterator<SaikuParameter> iterator3 = params.iterator(); iterator3.hasNext();) {
			SaikuParameter param = (SaikuParameter) iterator3.next();
			if (param.getUid().equals(uid)) {
				saikuParam = param;
				break;
			}
		}
		if (saikuParam != null) {
			params.remove(saikuParam);
		}
		
		return null;
		
		

		/*
		 * Doesnt work with parameters yet
		 * 
		 * final ArrayList<SaikuParameter> parameters = model.getParameters();
		 * for (SaikuParameter saikuParam : parameters) {
		 * if(saikuParam.g.equals(uid)){ groups.remove(saikuParam); } }
		 */

	}

	public void removeColumn(String sessionId, String category, String businessColumn, Integer position) {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		List<SaikuColumn> columns = model.getColumns();

		int index = position;

		columns.remove(index);

		model.setCdaDirty(true);
		
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " removeColumn\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void addFilter(String sessionId, String category, String businessColumn, Position position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		findAndRemoveByUid(model, position.getUid());

		List<SaikuParameter> parameters = model.getParameters();

		final LogicalModel logicalModel = metadataProvider.getLogicalModel(model.getDomainId(), model.getLogicalModelId());
		LogicalColumn logicalColumn = logicalModel.findLogicalColumn(businessColumn);

		SaikuParameter parameter = new SaikuParameter(logicalColumn);
		parameter.setCategory(category);
		parameter.setId(businessColumn);
		parameter.setUid(position.getUid());
		parameter.setType(logicalColumn.getDataType().getName());

		parameters.add(position.getPosition(), parameter);

		model.setCdaDirty(true);
		
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " addFilter\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void removeFilter(String sessionId, String category, String businessColumn, int position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		List<SaikuParameter> parameters = model.getParameters();

		parameters.remove(position);

		//String filterKey = category + "." + businessColumn;

		//model.getDerivedModels().getFilterQueries().remove(filterKey);

		model.setCdaDirty(true);
		
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " removeFilter\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void setFilterValues(String sessionId, String category, String businessColumn, ArrayList<FilterValue> selection) {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);

		ArrayList<String> values = new ArrayList<String>();
		for (FilterValue filterValue : selection) {
			values.add(filterValue.getValue());
		}

		final List<SaikuParameter> parameters = model.getParameters();
		for (SaikuParameter saikuParameter : parameters) {
			if (saikuParameter.getCategory().equals(category) && saikuParameter.getId().equals(businessColumn)) {
				saikuParameter.setParameterValues(values);
			}

		}

	}

	public void addGroup(String sessionId, String categoryId, String columnId, Position position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		findAndRemoveByUid(model, position.getUid());

		String name = null;

		List<SaikuGroup> groups = model.getGroups();

		final LogicalModel logicalModel = metadataProvider.getLogicalModel(model.getDomainId(),model.getLogicalModelId());
		LogicalColumn logicalColumn = logicalModel.findLogicalColumn(columnId);
		if(logicalColumn!=null){
			String locale = "en_En";
			name = logicalColumn.getName(locale);
			
		}
		//if its not a logicalColumn it must be a calculated one
		else{
			final SaikuColumn column = ModelHelper.findColumnByUid(model, position.getUid()); 
			name = column.getName();
		}
		
		SaikuGroup group = null;

		// see if group is allready in the selection
		for (SaikuGroup saikuGroup : groups) {
			if (saikuGroup.getUid().equals(position.getUid())) {
				group = saikuGroup;
			}
		}

		if (group != null) {
			groups.remove(group);
		} else {
			group = new SaikuGroup();
			group.setColumnId(columnId);
			group.setCategory(categoryId);


			group.setColumnName(name);
			group.setDisplayName(name);

			group.setUid(position.getUid());

			group.setGroupTotalsLabel("Total " + name);
		}

		groups.add(position.getPosition(), group);

		model.setCdaDirty(true);
		
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " addGroup\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void removeGroup(String sessionId, String category, String businessColumn, int position) {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		List<SaikuGroup> groups = model.getGroups();

		groups.remove(position);

		model.setCdaDirty(true);
		
		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " removeGroup\n" + sessionHolder.logModel(sessionId));
		}

	}

	public SaikuColumn getColumnConfig(String sessionId, String category, String column, Integer position) {

		return sessionHolder.getModel(sessionId).getColumns().get(position);

	}

	public SaikuColumn getColumnConfig(String sessionId, String id) {

		final List<SaikuColumn> columns = sessionHolder.getModel(sessionId).getColumns();

		for (SaikuColumn saikuColumn : columns) {
			if (id.equals(saikuColumn.getUid())) {
				return saikuColumn;
			}
		}

		return null;
	}

	public ElementFormat getElementFormat(String sessionId, String id) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		if (id.contains("dtl")) {
			final SaikuColumn column = ModelHelper.findColumnByLayoutId(model, id); 
			return new ElementFormat(column.getElementFormat().getTempFormat(), column.getName());

		} else if (id.contains("dth")) {
			final SaikuColumn column = ModelHelper.findColumnByLayoutId(model, id.replace("dth", "dtl")); 
			return new ElementFormat(column.getColumnHeaderFormat().getTempFormat(), column.getName());

		} else if (id.contains("ghd")) {
			final SaikuGroup saikuGroup = ModelHelper.findGroupById(model, id);
			return new ElementFormat(saikuGroup.getGroupHeaderFormat().getTempFormat(), saikuGroup.getGroupName());
		}

		else if (id.contains("gft")) {

			String[] splits = id.split("-");
			Integer index = Integer.valueOf(splits[2]);
			// get the correct group by index
			SaikuGroup saikuGroup = model.getGroups().get(index);

			final List<SaikuLabel> msgs = saikuGroup.getGroupFooterElements();
			for (SaikuLabel msg : msgs) {
				if (id.equals(msg.getUid())) {
					return new ElementFormat(msg.getElementFormat().getTempFormat(), msg.getValue());
				}
			}
		}

		else if (id.contains("rhd")) {
			final List<SaikuLabel> msgs = model.getReportHeaderElements();
			for (SaikuLabel msg : msgs) {
				if (id.equals(msg.getUid())) {
					return new ElementFormat(msg.getElementFormat().getTempFormat(), msg.getValue());

				}
			}
		} else if (id.contains("rft")) {
			final List<SaikuLabel> msgs = model.getReportFooterElements();
			for (SaikuLabel msg : msgs) {
				if (id.equals(msg.getUid())) {
					return new ElementFormat(msg.getElementFormat().getTempFormat(), msg.getValue());
				}
			}
		} else if (id.contains("sum")) {
			final List<SaikuLabel> msgs = model.getReportSummaryElements();
			for (SaikuLabel msg : msgs) {
				if (id.equals(msg.getUid())) {
					return new ElementFormat(msg.getElementFormat().getTempFormat(), msg.getValue());
				}
			}
		} else if (id.contains("phd")) {
			final List<SaikuLabel> msgs = model.getPageHeaderElements();
			for (SaikuLabel msg : msgs) {
				if (id.equals(msg.getUid())) {
					return new ElementFormat(msg.getElementFormat().getTempFormat(), msg.getValue());
				}
			}
		} else if (id.contains("pft")) {
			final List<SaikuLabel> msgs = model.getPageFooterElements();
			for (SaikuLabel msg : msgs) {
				if (id.equals(msg.getUid())) {
					return new ElementFormat(msg.getElementFormat().getTempFormat(), msg.getValue());
				}
			}
		}

		return null;
	}

	public DisplayName setElementFormat(String sessionId, ElementFormat format, String id) throws Exception {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		String displayName = format.getValue();

		if (id.contains("dtl")) {
			final SaikuColumn saikuColumn = ModelHelper.findColumnByLayoutId(model, id);	
			TemplateUtils.mergeElementFormats(format.getFormat(), saikuColumn.getElementFormat());

		}

		else if (id.contains("dth")) {
			
			final SaikuColumn m = ModelHelper.findColumnByLayoutId(model, id.replace("dth", "dtl"));	

 			List<SaikuColumn> columns = model.getColumns();
		
			if(format.getFormat().getWidth()==null){
				format.getFormat().setWidth(m.getColumnHeaderFormat().getWidth());
			}
	
			TemplateUtils.mergeElementFormats(format.getFormat(), m.getColumnHeaderFormat());
			if (!m.getName().equals(displayName)) {
				m.setName(StringUtils.getUniqueColumnName(displayName, columns));
				model.setCdaDirty(true);
			}
			return new DisplayName(m.getName(), m.getUid());

		} else if (id.contains("ghd")) {
			final SaikuGroup m = ModelHelper.findGroupById(model, id);
			TemplateUtils.mergeElementFormats(format.getFormat(), m.getGroupHeaderFormat());
			m.setGroupName(displayName);
		} else if (id.contains("gft") || id.contains("rhd")) {
			final SaikuLabel m = ModelHelper.findLabelById(model, id);
			TemplateUtils.mergeElementFormats(format.getFormat(), m.getElementFormat());
			m.setValue(displayName);
		} else if (id.contains("sum")) {
			final List<SaikuLabel> msgs = model.getReportSummaryElements();
			setFormat(format, id, msgs);
		} else if (id.contains("rft")) {
			final List<SaikuLabel> msgs = model.getReportFooterElements();
			setFormat(format, id, msgs);
		} else if (id.contains("phd")) {
			final List<SaikuLabel> msgs = model.getPageHeaderElements();
			setFormat(format, id, msgs);
		} else if (id.contains("pft")) {
			final List<SaikuLabel> msgs = model.getPageFooterElements();
			setFormat(format, id, msgs);
		}

		return null;

	}

	private void setFormat(ElementFormat format, String id, final List<SaikuLabel> msgs) throws Exception {
		for (SaikuLabel msg : msgs) {
			if (id.equals(msg.getUid())) {
				TemplateUtils.mergeElementFormats(format.getFormat(), msg.getElementFormat());
				msg.setValue(format.getValue());
			}
		}
	}

	public DisplayName setColumnConfig(String sessionId, String category, String businessColumn, Integer position,
			SaikuColumn config) throws ParseException {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		List<SaikuColumn> columns = model.getColumns();
		
		if(config.getFormula()!=null){
				final Formula f = new Formula(config.getFormula());		
		}

		// The new name must be unique among the columns (excluding itself);
		List<String> colNames = new ArrayList<String>();

		for (SaikuColumn col : columns) {
			if (columns.indexOf(col) != position) {
				colNames.add(col.getName());
			}
		}

		String newName = StringUtils.getUniqueName(config.getName(), colNames);
		config.setName(newName);

		model.setCdaDirty(true);
		
		if (category.equals("CALCULATED") && config.getId().equals("NEW")) {
			config.setId(UUID.randomUUID().toString());
			columns.add(config);
		} else {
			SaikuColumn oldCol = columns.set(position, config);
			// if we changed the name we need to replace it in all formulas
			String oldName = oldCol.getName();
			if (!oldName.equals(newName)) {
				replaceInFormulas(oldName, newName, columns);
			}
		}

		return new DisplayName(config.getName(), config.getUid());

	}

	private void replaceInFormulas(String oldName, String newName, List<SaikuColumn> columns) {

		String tokenOld = "\\[" + oldName + "\\]";
		String tokenNew = "[" + newName + "]";

		for (SaikuColumn saikuColumn : columns) {
			String formula = saikuColumn.getFormula();
			if (formula != null)
				saikuColumn.setFormula(formula.replaceAll(tokenOld, tokenNew));
		}

	}

	public void setColumnSort(String sessionId, String category, String column, Integer position, String order) {
		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		model.getColumns().get(position).setSort(order);
		model.setCdaDirty(true);
	}

	public void setGroupSort(String sessionId, String category, String column, Integer position, String order) {
		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		model.getGroups().get(position).setSort(order);
		model.setCdaDirty(true);
	}

	public String getModelJson(String sessionId) throws JsonGenerationException, JsonMappingException, IOException {

		String value = null;
		SaikuMasterModel model = sessionHolder.getModel(sessionId);

		ObjectMapper mapper = new ObjectMapper();
		value = mapper.writeValueAsString(model);
		log.debug(value);
		return value;
	}

	public void setRowlimit(String sessionId, String rowlimit) {
		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		model.getSettings().setLimit(Integer.parseInt(rowlimit));
		model.setCdaDirty(true);
	}

	public void setDistinct(String sessionId, Boolean distinct) {
		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		model.getSettings().setDisableDistinct(distinct);
		model.setCdaDirty(true);
	}
	
	public SaikuReportSettings getReportSettings(String sessionId) {
		return sessionHolder.getModel(sessionId).getSettings();
	}

	public void setReportSettings(String sessionId, SaikuReportSettings settings) {
		sessionHolder.getModel(sessionId).setSettings(settings);
	}

}
