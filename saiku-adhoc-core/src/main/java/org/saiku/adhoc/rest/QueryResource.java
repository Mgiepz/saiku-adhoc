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
package org.saiku.adhoc.rest;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.pentaho.platform.engine.core.system.PentahoBase;
import org.pentaho.reporting.libraries.formula.parser.ParseException;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.exceptions.SaikuClientException;
import org.saiku.adhoc.messages.Messages;
import org.saiku.adhoc.model.dto.DisplayName;
import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.dto.FilterResult;
import org.saiku.adhoc.model.dto.FilterValue;
import org.saiku.adhoc.model.dto.HtmlReport;
import org.saiku.adhoc.model.dto.Position;
import org.saiku.adhoc.model.dto.PrptSolutionFileInfo;
import org.saiku.adhoc.model.dto.SavedQuery;
import org.saiku.adhoc.model.dto.SolutionFileInfo;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuReportSettings;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.service.EditorService;
import org.saiku.adhoc.service.cda.CdaQueryService;
import org.saiku.adhoc.service.report.ReportGeneratorService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Path("/saiku-adhoc/rest/query")
@Scope("request")
@XmlAccessorType(XmlAccessType.NONE)
public class QueryResource extends PentahoBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(QueryResource.class);

	private CdaQueryService queryService;

	private EditorService editorService;

	private ReportGeneratorService reportGeneratorService;

	@GET
	@Produces({"application/json" })
	@Path("/{queryname}/json")
	public SavedQuery getModelJson(@PathParam("queryname") String queryName){

		if (log.isDebugEnabled()) {
			log.debug("REST:GET " + queryName + " saveQuery");
		}



		if (log.isDebugEnabled()) {
			log.debug("REST:GET " + queryName + " saveQuery");
		}

		try {
			String json = editorService.getModelJson(queryName);

			return new SavedQuery(queryName, null, json);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/result")
	public String executeQuery(@PathParam("queryname") String sessionId) {

		String result;

		try{
			result = queryService.runQuery(sessionId, sessionId);

		}catch (Exception e) {
			log.error("Cannot generate report (" + sessionId + ")",e);
			throw new SaikuClientException(e.getMessage());
		}

		if(!result.equals("")){
			return result;
		}else{
			throw new SaikuClientException(				
					Messages.getErrorString("CdaQueryService.ERROR_0001_QUERY_RETURNED_NO_DATA")
			);
		}

	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}")
	public Status createQuery(MetadataModelInfo modelInfo,
			@PathParam("queryname") String sessionId) {

		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " createQuery");
		}

		try {

			if (log.isDebugEnabled()) {
				log.debug("REST:POST " + sessionId + " createQuery");
			}

			editorService.createNewModel(sessionId, modelInfo);
			return Status.OK;
		} catch (Exception e) {
			e.printStackTrace();
			//log.error(e.getCause());
			return Status.INTERNAL_SERVER_ERROR;
		}
	}


	@GET
	@Produces({"application/json" })
	@Path("/{queryname}/properties")
	public Properties getProperties(@PathParam("queryname") String queryName) {

		if (log.isDebugEnabled()) {
			log.debug("REST:GET " + queryName + " getProperties");
		}

		return queryService.getProperties(queryName);
	}


	@POST
	@Produces({"application/json" })
	@Path("/{queryname}/properties")
	public Properties setProperties(
			@PathParam("queryname") String queryName, 
			String properties) 
	{
		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + queryName + " setProperties\t" + properties);
		}

		try {
			Properties props = new Properties();
			StringReader sr = new StringReader(properties);
			props.load(sr);
			return queryService.setProperties(queryName, props);
		} catch(Exception e) {
			log.error("Cannot set properties for query (" + queryName + ")",e);
			//return something
			return  queryService.getProperties(queryName);
		}

	}

	@POST
	@Produces({"application/json" })
	@Path("/{queryname}/properties/{propertyKey}")
	public Properties setProperties(
			@PathParam("queryname") String queryName, 
			@PathParam("propertyKey") String propertyKey, 
			@FormParam("propertyValue") String propertyValue) 
	{
		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + queryName + " setProperties "+ propertyKey + "=" + propertyValue);
		}

		try{
			Properties props = new Properties();
			props.put(propertyKey, propertyValue);
			return queryService.setProperties(queryName, props);
		}catch(Exception e){
			log.error("Cannot set property (" + propertyKey + " ) for query (" + queryName + ")",e);
			return null;
		}

	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/FILTER/CATEGORY/{category}/COLUMN/{column}/result")
	public FilterResult getFilterValues(
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String column) throws SaikuAdhocException {

		if (log.isDebugEnabled()) {
			log.debug("REST:GET " + sessionId + " getFilterValues category="+ category + " column=" + column);
		}

		try {

			return queryService.getFilterResult(sessionId, category, column);

		} catch (Exception e) {
			final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - " + e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
			log.error(message, e);
			throw new SaikuAdhocException("Encoding not supported", e);
		}

	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/report/{page}")
	public HtmlReport generateReport(
			@PathParam("queryname") String sessionId,
			//@PathParam("template") String template,
			@PathParam("page") String page){

		if (log.isDebugEnabled()) {
			log.debug("REST:GET " + sessionId + " generate");
		}

		try {
			HtmlReport report = new HtmlReport();
			Integer acceptedPage = Integer.parseInt(page) - 1;
			reportGeneratorService.renderReportHtml(sessionId, null, report, acceptedPage);

			return report;

		}catch (Exception e) {
			log.error("Cannot generate report (" + sessionId + ")",e);
			throw new SaikuClientException(e.getMessage());
		}

	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/ROWLIMIT/{rowlimit}")
	public Status setRowlimit(
			@PathParam("queryname") String sessionId,
			@PathParam("rowlimit") String rowlimit) {

		try {
			editorService.setRowlimit(sessionId, rowlimit);
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot set rowlimit", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/DISTINCT/{distinct}")
	public Status setDistinct(
			@PathParam("queryname") String sessionId,
			@PathParam("distinct") String distinct) {

		try {	
			editorService.setDistinct(sessionId, Boolean.valueOf(distinct));
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot set distinct", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@POST
	@Path("/{queryname}/COLUMNS/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}")
	public DisplayName addColumn(
			Position position,
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn,
			@PathParam("position") Integer pos) {

		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " addColumn uid=" + position.getUid() + " position=" + position.getPosition() 
					+ " category=" + category + " column=" + businessColumn);
		}

		try {
			return editorService.addColumn(sessionId, category, businessColumn, position);
		} catch (Exception e) {
			log.error("Cannot add column " + businessColumn + " to query ("
					+ sessionId + ")", e);
			String error = ExceptionUtils.getRootCauseMessage(e);
			String clientMessage = Messages.getErrorString(error);
			throw new SaikuClientException(clientMessage);		
		}
	}

	@DELETE
	@Path("/{queryname}/COLUMNS/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}")
	public Status removeColumn(			
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn,
			@PathParam("position") Integer position) {


		if (log.isDebugEnabled()) {
			log.debug("REST:DELETE " + sessionId + " removeColumn position=" + position
					+ " category=" + category + " column=" + businessColumn);
		}

		try {
			editorService.removeColumn(sessionId, category, businessColumn, position);
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot remove column " + businessColumn
					+ " from query (" + sessionId + ")", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@POST
	@Path("/{queryname}/FILTER/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}")
	public Status addFilter(
			Position position,
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn) {


		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " addFilter position=" + position
					+ " category=" + category + " column=" + businessColumn);
		}

		try {
			editorService.addFilter(sessionId, category, businessColumn, position);
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot add Filter " + businessColumn + " to query ("
					+ sessionId + ")", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@DELETE
	@Path("/{queryname}/FILTER/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}")
	public Status removeFilter(
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn,
			@PathParam("position") Integer position) {


		if (log.isDebugEnabled()) {
			log.debug("REST:DELETE " + sessionId + " removeFilter position=" + position
					+ " category=" + category + " column=" + businessColumn);
		}

		try {
			editorService.removeFilter(sessionId, category, businessColumn,
					position);
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot remove Filter " + businessColumn
					+ " from query (" + sessionId + ")", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}/FILTER/CATEGORY/{category}/COLUMN/{column}/VALUES")
	public Status setFilterValues(
			ArrayList<FilterValue> selection,
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn) {


		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " setFilterValues " + 
					"category=" + category + " column=" + businessColumn);
		}

		try {
			editorService.setFilterValues(sessionId, category, businessColumn,
					selection);
			return Status.OK;
		} catch (Exception e) {
			// log.error("Cannot remove column "+ businessColumn+
			// " from query (" + queryName + ")",e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@POST
	@Path("/{queryname}/GROUP/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}")
	public Status addGroup(
			Position position,
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn,
			@PathParam("position") Integer pos) {


		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " addGroup uid=" + position.getUid() + " position=" + position.getPosition() 
					+ " category=" + category + " column=" + businessColumn);
		}

		try {
			editorService.addGroup(sessionId, category, businessColumn, position);
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot add Group " + businessColumn + " to query ("
					+ sessionId + ")", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@DELETE
	@Path("/{queryname}/GROUP/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}")
	public Status removeGroup(
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String businessColumn,
			@PathParam("position") Integer position) {


		if (log.isDebugEnabled()) {
			log.debug("REST:DELETE " + sessionId + " removeGroup position=" + position
					+ " category=" + category + " column=" + businessColumn);
		}

		try {
			editorService.removeGroup(sessionId, category, businessColumn, position);
			return Status.OK;
		} catch (Exception e) {
			log.error("Cannot remove Group " + businessColumn
					+ " from query (" + sessionId + ")", e);
			return Status.INTERNAL_SERVER_ERROR;
		}
	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/COLUMNS/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}/config")
	public SaikuColumn getColumnConfig(
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String column,
			@PathParam("position") Integer position) {

		if (log.isDebugEnabled()) {
			log.debug("REST:GET " + sessionId + " getColumnConfig position=" + position
					+ " category=" + category + " column=" + column);
		}

		try {

			return editorService.getColumnConfig(sessionId, category, column, position);

		} catch (Exception e) {
			log.error("Cannot execute Query", e);
		}

		return null;
	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}/COLUMNS/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}/config")
	public DisplayName setColumnConfig(SaikuColumn config,
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String column,
			@PathParam("position") Integer position) {

		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " setColumnConfig position=" + position
					+ " category=" + category + " column=" + column);
		}

		try {
			return editorService.setColumnConfig(sessionId, category, column, position, config);	
		}catch(ParseException e){
			log.error("Formula is not valid", e);
			String error = Messages.getErrorString("ReportGeneratorService.ERROR_0007_INVALID_FORMULA");
			throw new SaikuClientException(error);	

		} catch (Exception e1) {
			log.error("Cannot config column", e1);
			String error = ExceptionUtils.getRootCauseMessage(e1);
			throw new SaikuClientException(error);		
		}
	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}/COLUMNS/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}/SORT/{order}")
	public Status setColumnSort(
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String column,
			@PathParam("position") Integer position,
			@PathParam("order") String order) {

		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " sort position=" + position
					+ " category=" + category + " column=" + column);
		}

		try {
			editorService.setColumnSort(sessionId, category, column, position, order);
			return Status.OK;
		} catch (Exception e) {
			e.printStackTrace();

			return Status.INTERNAL_SERVER_ERROR;
		}

	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}/GROUP/CATEGORY/{category}/COLUMN/{column}/POSITION/{position}/SORT/{order}")
	public Status setGroupSort(
			@PathParam("queryname") String sessionId,
			@PathParam("category") String category,
			@PathParam("column") String column,
			@PathParam("position") Integer position,
			@PathParam("order") String order) {

		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + sessionId + " sort position=" + position
					+ " category=" + category + " column=" + column);
		}

		try {
			editorService.setGroupSort(sessionId, category, column, position, order);
			return Status.OK;
		} catch (Exception e) {
			e.printStackTrace();

			return Status.INTERNAL_SERVER_ERROR;
		}

	}


	@Override
	public Log getLogger() {
		return log;
	}

	public void setQueryService(CdaQueryService queryService) {
		this.queryService = queryService;
	}

	public void setEditorService(EditorService editorService) {
		this.editorService = editorService;
	}

	public void setReportGeneratorService(
			ReportGeneratorService reportGeneratorService) {
		this.reportGeneratorService = reportGeneratorService;
	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/SETTINGS")
	public SaikuReportSettings getReportSettings(
			@PathParam("queryname") String sessionId) {

		try {
			return editorService.getReportSettings(sessionId);

		} catch (Exception e) {
			log.error("Cannot get settings", e);
			String error = ExceptionUtils.getRootCauseMessage(e);
			throw new SaikuClientException(error);
		}

	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}/SETTINGS")
	public void setReportSettings(
			SaikuReportSettings settings,
			@PathParam("queryname") String sessionId) {

		try {
			editorService.setReportSettings(sessionId,settings);

		} catch (Exception e) {
			log.error("Cannot set settings", e);
			String error = ExceptionUtils.getRootCauseMessage(e);
			throw new SaikuClientException(error);
		}

	}

	@GET
	@Produces({ "application/json" })
	@Path("/{queryname}/FORMAT/ELEMENT/{id}")
	public ElementFormat getElementFormat(
			@PathParam("queryname") String sessionId,
			@PathParam("id") String id) {

		try {

			//return editorService.getColumnConfig(sessionId,id);
			return editorService.getElementFormat(sessionId, id);

		} catch (Exception e) {
			log.error("Cannot execute Query", e);
		}

		return null;
	}

	@POST
	@Consumes({ "application/json" })
	@Path("/{queryname}/FORMAT/ELEMENT/{id}")
	public DisplayName setElementFormat(ElementFormat format,
			@PathParam("queryname") String sessionId,
			@PathParam("id") String id) {

		try {
			return editorService.setElementFormat(sessionId, format, id);
		} catch (Exception e) {
			log.error("Cannot set element format", e);
			String error = ExceptionUtils.getRootCauseMessage(e);
			throw new SaikuClientException(error);		
		}
	}


	@POST
	@Path("/{queryname}/EXPORT/PRPT")
	public void exportPrpt(
			PrptSolutionFileInfo file,
			@PathParam("queryname") String sessionId) {

		try {
			reportGeneratorService.savePrpt(sessionId, file.getPath(),file.getFile(),
					file.getUser(),file.getPassword());
		} catch (Exception e) {
			log.error("Cannot export prpt (" + sessionId + ")",e);
			String clientMessage = Messages.getErrorString(e.getMessage());
			throw new SaikuClientException(clientMessage);
		}
	}

	@POST
	@Path("/{queryname}/EXPORT/CDA")
	public Status exportCda(
			SolutionFileInfo file,
			@PathParam("queryname") String sessionId) {

		try {
			reportGeneratorService.saveCda(sessionId, file.getPath(),file.getFile());

			return Status.OK;
		} catch (Exception e) {

			return Status.INTERNAL_SERVER_ERROR;
		}
	}

}
