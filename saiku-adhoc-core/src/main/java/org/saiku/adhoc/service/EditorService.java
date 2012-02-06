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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalColumn;
import org.pentaho.metadata.model.LogicalModel;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.dto.DisplayName;
import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.dto.FilterValue;
import org.saiku.adhoc.model.dto.Position;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuElement;
import org.saiku.adhoc.model.master.SaikuElementFormat;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.model.master.SaikuReportSettings;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.service.report.ReportGeneratorService;
import org.saiku.adhoc.service.repository.IMetadataService;
import org.saiku.adhoc.utils.StringUtils;

/**
 * @author mgiepz
 *
 */
public class EditorService {

	protected Log log = LogFactory.getLog(EditorService.class);

	protected WorkspaceSessionHolder sessionHolder;
	protected IMetadataService metadataService;

	public void setSessionHolder(WorkspaceSessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;

	}

	private ICDAManager cdaManager;

	public void setCDAManager(ICDAManager manager){
		this.cdaManager = manager;

	}

	public ICDAManager getCDAManager(){
		return cdaManager;
	}

	private ReportGeneratorService reportGeneratorService;

	public void setReportGeneratorService(
			ReportGeneratorService reportGeneratorService) {
		this.reportGeneratorService = reportGeneratorService;
	}



	public void createNewModel(String sessionId, MetadataModelInfo modelInfo) throws SaikuAdhocException{

		SaikuMasterModel masterModel = null;

		try {

			if(modelInfo.getJson()==null){
				String domainId;

				domainId = URLDecoder.decode(modelInfo.getDomainId(), "UTF-8");

				Domain domain = metadataService.getDomain(domainId);
				LogicalModel model = metadataService.getLogicalModel(domainId,
						modelInfo.getModelId());

				masterModel = new SaikuMasterModel();
				masterModel.init(domain, model, sessionId, cdaManager, reportGeneratorService);
			}else{
				ObjectMapper mapper = new ObjectMapper();
				masterModel = mapper.readValue(modelInfo.getJson(), SaikuMasterModel.class);

				String[] split = masterModel.getClientModelSelection().split("/");

				String domainId = URLDecoder.decode(split[0], "UTF-8");
				Domain domain = metadataService.getDomain(domainId);
				LogicalModel model = metadataService.getLogicalModel(domainId, split[1]);

				masterModel.init(domain, model, sessionId, cdaManager, reportGeneratorService);

				masterModel.deriveModels();
			}

			sessionHolder.initSession(masterModel, sessionId);

			//masterModel.deriveModels();

			sessionHolder.getModel(sessionId).setClientModelSelection(
					URLEncoder.encode(masterModel.getDerivedModels().getDomain().getId(), "UTF-8")
					+ "/" + masterModel.getDerivedModels().getLogicalModel().getId());

		} catch (Exception e) {
			final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - " + e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
			log.error(message, e);
			throw new SaikuAdhocException("Encoding not supported", e);
		}

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " createNewModel\n" + sessionHolder.logModel(sessionId));
		}

	}


	/*add column/group/filter need to call a method that removes the element with that uid-from any of the
	 * other dropzones first
	 * 
	 * We could do this by putting the object into a hashmap uid2SaikuElement after creation
	 * and then removing it like
	 * 
	 * columns.remove(hasmap.get(uid));
	 * 
	 * filters.add(element)
	 * hashmap.put(uid,element)
	 * 
	 * by removing the elements the indexes should be corrected automatically
	 * 
	 * the rpt-xxx ids should only be asigned by the report-processor tasks
	 * here we also need to create a hashmap (rpt-xxx,saikuelement); 
	 * so that when ElementFormat is requested from the client
	 * we can get the correct element format by casting depending on rpt-xxx's type.
	 * we have to see if the hashmap needs to be cleared on every render the hashmap  (rpt-xxx,saikuelement); 
	 * 
	 */

	public DisplayName addColumn(String sessionId, String category, String columnId,
			Position position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		SaikuColumn column = findAndRemoveByUid(model,position.getUid());

		List<SaikuColumn> columns = model.getColumns();

		final LogicalModel logicalModel = model.getDerivedModels().getLogicalModel();
		LogicalColumn logicalColumn = logicalModel.findLogicalColumn(columnId);

		if(column==null){
			column = new SaikuColumn(logicalColumn);
			column.setName(StringUtils.getUniqueColumnName(column.getName(),columns));
			column.setCategory(category);
			column.setId(columnId);
			column.setSelectedAggType(column.getDefaultAggType());
			column.setSelectedSummaryType("NONE");
			column.setUid(position.getUid());
		}

		columns.add(position.getPosition(), column);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " addColumn\n" + sessionHolder.logModel(sessionId));
		}

		return new DisplayName(column.getName(),position.getUid());

	}

	private SaikuColumn findAndRemoveByUid(SaikuMasterModel model, String uid) {

		final List<SaikuColumn> columns = model.getColumns();

		SaikuColumn saikuColumn = null;
		for (Iterator<SaikuColumn> iterator = columns.iterator(); iterator.hasNext();) {
			SaikuColumn col = (SaikuColumn) iterator.next();
			if(col.getUid().equals(uid)){
				saikuColumn = col;
				break;
			}
		}
		if(saikuColumn!=null){
			columns.remove(saikuColumn);
			return saikuColumn;
		}

		final List<SaikuGroup> groups = model.getGroups();

		SaikuGroup saikuGroup = null;
		for (Iterator<SaikuGroup> iterator2 = groups.iterator(); iterator2.hasNext();) {
			SaikuGroup group = (SaikuGroup) iterator2.next();
			if(group.getUid().equals(uid)){			
				saikuGroup = group;
				break;
			}
		}
		if(saikuGroup!=null){
			groups.remove(saikuGroup);
		}

		return null;

		/*
		 * Doesnt work with parameters yet
		 * 
		final ArrayList<SaikuParameter> parameters = model.getParameters();
		for (SaikuParameter saikuParam : parameters) {
			if(saikuParam.g.equals(uid)){
				groups.remove(saikuParam);
			}
		}
		 */


	}

/*
	public DisplayName addCalulatedColumn(String sessionId, Integer position, SaikuColumn config) {
		final SaikuMasterModel model = sessionHolder.getModel(sessionId);
		List<SaikuColumn> columns = model.getColumns();	
		columns.add(config);
		
		return new DisplayName(config.getUid(),config.getName());
	}

*/

	public void removeColumn(String sessionId, String category,
			String businessColumn, Integer position) {

		List<SaikuColumn> columns = sessionHolder.getModel(sessionId)
		.getColumns();

		int index = position;

		columns.remove(index);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " removeColumn\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void addFilter(String sessionId, String category,
			String businessColumn, int position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		//TODO: We need remove by uid here too

		List<SaikuParameter> parameters = model.getParameters();

		final LogicalModel logicalModel = model.getDerivedModels().getLogicalModel();
		LogicalColumn logicalColumn = logicalModel.findLogicalColumn(businessColumn);

		SaikuParameter parameter = new SaikuParameter(logicalColumn);
		parameter.setCategory(category);
		parameter.setId(businessColumn);
		parameter.setType(logicalColumn.getDataType().getName());

		parameters.add(position, parameter);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " addFilter\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void removeFilter(String sessionId, String category,
			String businessColumn, int position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		List<SaikuParameter> parameters = model
		.getParameters();

		String filterKey = category + "." + businessColumn;

		parameters.remove(position);

		model.getDerivedModels().getFilterQueries().remove(filterKey);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " removeFilter\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void setFilterValues(String sessionId, String category,
			String businessColumn, ArrayList<FilterValue> selection) {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);

		ArrayList<String> values = new ArrayList<String>();
		for (FilterValue filterValue : selection) {
			values.add(filterValue.getValue());
		}

		final List<SaikuParameter> parameters = model.getParameters();
		for (SaikuParameter saikuParameter : parameters) {
			if(saikuParameter.getCategory().equals(category)&&
					saikuParameter.getId().equals(businessColumn)){
				saikuParameter.setParameterValues(values);
			}

		}

	}

	public void addGroup(String sessionId, String categoryId, String columnId,
			Position position) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		findAndRemoveByUid(model,position.getUid());

		List<SaikuGroup> groups = model.getGroups();

		final LogicalModel logicalModel = model.getDerivedModels()
		.getLogicalModel();
		LogicalColumn column = logicalModel.findLogicalColumn(columnId);

		SaikuGroup group = null;

		// see if group is allready in the selection
		for (SaikuGroup saikuGroup : groups) {
			if (saikuGroup.getUid().equals(position.getUid())) {
				group = saikuGroup;				
			}
		}

		if(group!=null){
			groups.remove(group);
		}else {
			group = new SaikuGroup();
			group.setColumnId(columnId);
			group.setCategory(categoryId);

			String locale = "en_En";
			group.setColumnName(column.getName(locale));
			group.setDisplayName(column.getName(locale));

			group.setUid(position.getUid());

			// Group name gets overwritten with the message format
			//group.setGroupName(column.getName(locale));


			group.setGroupTotalsLabel("Total " + column.getName(locale));
		}

		groups.add(position.getPosition(), group);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " addGroup\n" + sessionHolder.logModel(sessionId));
		}

	}

	public void removeGroup(String sessionId, String category,
			String businessColumn, int position) {

		List<SaikuGroup> groups = sessionHolder.getModel(sessionId).getGroups();

		groups.remove(position);

		if (log.isDebugEnabled()) {
			log.debug("SERVICE:EditorService " + sessionId + " removeGroup\n" + sessionHolder.logModel(sessionId));
		}

	}

	public SaikuColumn getColumnConfig(String sessionId, String category,
			String column, Integer position) {

		return sessionHolder.getModel(sessionId).getColumns().get(position);

	}

	public SaikuColumn getColumnConfig(String sessionId, String id) {

		final List<SaikuColumn> columns = sessionHolder.getModel(sessionId).getColumns();


		for (SaikuColumn saikuColumn : columns) {
			if(id.equals(saikuColumn.getUid())){
				return saikuColumn;
			}
		}

		return null;
	}

/*
	public void setColumnConfig(String sessionId, SaikuColumn config) {

		final List<SaikuColumn> columns = sessionHolder.getModel(sessionId).getColumns();

		for (SaikuColumn saikuColumn : columns) {
			if(config.getUid().equals(saikuColumn.getUid())){
				columns.set(columns.indexOf(saikuColumn), config);
			}
		}

	}
*/
	public ElementFormat getElementFormat(String sessionId, String id) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		final Map<String, SaikuElementFormat> rptIdToElementFormat = model.getDerivedModels().getRptIdToElementFormat();

		if(id.contains("dtl")){
			return getFormat(id, model, rptIdToElementFormat);

		}else if(id.contains("dth")){
			return getFormat(id, model, rptIdToElementFormat);

		}else if(id.contains("ghd")){
			final SaikuGroup saikuGroup = (SaikuGroup) model.getDerivedModels().getRptIdToSaikuElement().get(id);
			return new ElementFormat(
					rptIdToElementFormat.get(id),saikuGroup.getGroupName());
		}

		else if(id.contains("gft")){

			String[] splits = id.split("-");	
			Integer index = Integer.valueOf(splits[2]);
			//get the correct group by index
			SaikuGroup saikuGroup = model.getGroups().get(index);

			final List<SaikuElement> msgs = saikuGroup.getGroupFooterElements();			
			for (SaikuElement msg : msgs) {
				if(id.equals(msg.getUid())){
					return new ElementFormat(
							rptIdToElementFormat.get(id),msg.getValue());
				}
			}	
		}

		else if(id.contains("rhd")){
			final List<SaikuElement> msgs = model.getReportHeaderElements();			
			for (SaikuElement msg : msgs) {
				if(id.equals(msg.getUid())){
					return new ElementFormat(
							rptIdToElementFormat.get(id),msg.getValue());

				}
			}	
		}else if(id.contains("rft")){
			final List<SaikuElement> msgs = model.getReportFooterElements();			
			for (SaikuElement msg : msgs) {
				if(id.equals(msg.getUid())){
					return new ElementFormat(
							rptIdToElementFormat.get(id),msg.getValue());
				}
			}			
		}else if(id.contains("phd")){
			final List<SaikuElement> msgs = model.getPageHeaderElements();			
			for (SaikuElement msg : msgs) {
				if(id.equals(msg.getUid())){
					return new ElementFormat(
							rptIdToElementFormat.get(id),msg.getValue());
				}
			}		
		}else if(id.contains("pft")){
			final List<SaikuElement> msgs = model.getPageFooterElements();			
			for (SaikuElement msg : msgs) {
				if(id.equals(msg.getUid())){
					return new ElementFormat(
							rptIdToElementFormat.get(id),msg.getValue());
				}
			}				
		}



		return null;
	}

	private ElementFormat getFormat(String id, final SaikuMasterModel model,
			final Map<String, SaikuElementFormat> rptIdToElementFormat) {
		final SaikuColumn saikuColumn = (SaikuColumn) model.getDerivedModels().getRptIdToSaikuElement().get(id);
		return new ElementFormat(
				rptIdToElementFormat.get(id),saikuColumn.getName());
	}

	public DisplayName setElementFormat(String sessionId, ElementFormat format, String id) {

		final SaikuMasterModel model = sessionHolder.getModel(sessionId);

		String displayName = format.getValue();

		if (id.contains("dtl")) {
			final SaikuColumn saikuColumn = (SaikuColumn) model.getDerivedModels().getRptIdToSaikuElement().get(id);
			saikuColumn.setElementFormat(format.getFormat());
			//saikuColumn.setName(displayName);

		}

		else if (id.contains("dth")) {
			final SaikuColumn m = (SaikuColumn) model.getDerivedModels().getRptIdToSaikuElement().get(id);
			
			List<SaikuColumn> columns = model.getColumns();
			/*
			Float oldWidth = m.getColumnHeaderFormat().getWidth();
			Float newWidth = format.getFormat().getWidth();
			Float widthChange = oldWidth + newWidth;

			SaikuColumn nextCol = columns.get(columns.indexOf(m)+1);
			Float nextColWidth = nextCol.getColumnHeaderFormat().getWidth();
			nextCol.getColumnHeaderFormat().setWidth(nextColWidth - widthChange);
			*/
			m.setColumnHeaderFormat(format.getFormat());
			if(!m.getName().equals(displayName)){
				m.setName(StringUtils.getUniqueColumnName(displayName, columns));
			}
			return new DisplayName(m.getName(), m.getUid());

		} else if (id.contains("ghd")) {
			final SaikuGroup m = (SaikuGroup) model.getDerivedModels().getRptIdToSaikuElement().get(id);
			m.setGroupsHeaderFormat(format.getFormat());
			m.setGroupName(displayName);

		} else if (id.contains("gft") || id.contains("rhd")) {
			final SaikuElement m = (SaikuElement) model.getDerivedModels().getRptIdToSaikuElement().get(id);
			m.setElementFormat(format.getFormat());
			m.setValue(displayName);

		}

		else if (id.contains("rft")) {
			final List<SaikuElement> msgs = model.getReportFooterElements();
			setFormat(format, id, msgs);
		} else if (id.contains("phd")) {
			final List<SaikuElement> msgs = model.getPageHeaderElements();
			setFormat(format, id, msgs);
		} else if (id.contains("pft")) {
			final List<SaikuElement> msgs = model.getPageFooterElements();
			setFormat(format, id, msgs);
		}

		return null;

	}

	private void setFormat(ElementFormat format, String id, final List<SaikuElement> msgs) {
		for (SaikuElement msg : msgs) {
			if(id.equals(msg.getUid())){
				msg.setElementFormat(format.getFormat());
				msg.setValue(format.getValue());
			}
		}
	}

	public DisplayName setColumnConfig(String sessionId, String category,
			String businessColumn, Integer position, SaikuColumn config) {

		List<SaikuColumn> columns = sessionHolder.getModel(sessionId).getColumns();
		
		//The new name must be unique among the columns (excluding itself);
		List<String> colNames = new ArrayList<String>();
		
		for (SaikuColumn col : columns) {
			if(columns.indexOf(col)!=position){
				colNames.add(col.getName());
			}	
		}
		

		String newName = StringUtils.getUniqueName(config.getName(), colNames);
		config.setName(newName);

		if(category.equals("CALCULATED") && config.getId().equals("NEW")){		
			config.setId(UUID.randomUUID().toString());	
			columns.add(config);
		}else{
			SaikuColumn oldCol = columns.set(position, config);	
			//if we changed the name we need to replace it in all formulas
			String oldName = oldCol.getName();
			if(!oldName.equals(newName)){
				replaceInFormulas(oldName,newName,columns);	
			}
		}

		return new DisplayName(config.getName(), config.getUid());
		
	}

	private void replaceInFormulas(String oldName, String newName, List<SaikuColumn> columns) {
		
		String tokenOld = "\\[" + oldName + "\\]";
		String tokenNew = "[" + newName + "]";
		
		for (SaikuColumn saikuColumn : columns) {
			String formula = saikuColumn.getFormula();
			if(formula!=null) saikuColumn.setFormula(formula.replaceAll(tokenOld, tokenNew));
		}
		
	}

	public void setColumnSort(String sessionId, String category, String column,
			Integer position, String order) {
		sessionHolder.getModel(sessionId).getColumns().get(position).setSort(order);
	}

	public void setGroupSort(String sessionId, String category, String column,
			Integer position, String order) {
		sessionHolder.getModel(sessionId).getGroups().get(position).setSort(order);
	}

	public void setMetadataService(IMetadataService metadataService) {
		this.metadataService = metadataService;
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
	}

	public SaikuReportSettings getReportSettings(String sessionId) {
		return sessionHolder.getModel(sessionId).getSettings();
	}

	public void setReportSettings(String sessionId, SaikuReportSettings settings) {
		sessionHolder.getModel(sessionId).setSettings(settings);
	}
	
}
