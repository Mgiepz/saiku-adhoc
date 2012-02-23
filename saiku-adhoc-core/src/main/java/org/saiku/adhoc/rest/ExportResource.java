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

import java.io.ByteArrayOutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.service.cda.ExportService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This is the endpoint for exporting all sorts of content, like xls, csv pdf...
 * 
 * @author mgie
 * 
 */
@Component
@Path("/saiku-adhoc/rest/export")
@Scope("request")
public class ExportResource {

	private ExportService exportService;

	private Log log = LogFactory.getLog(ExportResource.class);

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
	@GET
	@Produces({ "application/vnd.ms-excel" })
	@Path("/{queryname}/xls")
	public Response exportXls(
		@PathParam("queryname") String queryName) {

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			exportService.writeXls(queryName, output);
			String name = "export";
			
			byte[] doc = output.toByteArray();
			
			return Response.ok(doc, MediaType.APPLICATION_OCTET_STREAM).header(
					"content-disposition",
					"attachment; filename = " + name + ".xls").header(
							"content-length",doc.length).build();
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Produces({ "application/vnd.pdf" })
	@Path("/{queryname}/pdf")
	public Response exportPdf(
		@PathParam("queryname") String queryName) {

		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			exportService.exportPdf(queryName, output);
			String name = "export";
			
			byte[] doc = output.toByteArray();
			
			return Response.ok(doc, MediaType.APPLICATION_OCTET_STREAM).header(
					"content-disposition",
					"attachment; filename = " + name + ".pdf").header(
							"content-length",doc.length).build();
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	 
	@GET
	@Produces({ "text/csv" })
	@Path("/{queryname}/csv")
	public Response exportCsv(@PathParam("queryname") String queryName) {
		
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			exportService.writeCsv(queryName, output);
			String name = "export";
			
			byte[] doc = output.toByteArray();
			
			return Response.ok(doc, MediaType.APPLICATION_OCTET_STREAM).header(
					"content-disposition",
					"attachment; filename = " + name + ".csv").header(
							"content-length",doc.length).build();
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
		
	}

}
