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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.model.dto.Position;
import org.saiku.adhoc.model.dto.SolutionFileInfo;
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

	//Spring
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	@GET
	@Produces({ "application/vnd.ms-excel" })
	@Path("/{queryname}/xls")
	public StreamingOutput exportXls(
			@PathParam("queryname") final String queryName)
	throws CdaException {

		return new StreamingOutput() {
			public void write(OutputStream output) throws IOException,
			WebApplicationException {
				try {

					if (log.isDebugEnabled()) {
						log.debug("REST:GET "+ queryName + " exportXls");
					}
					
					BufferedWriter bw = new BufferedWriter(new PrintWriter(
							output));

					try {
						bw.write(exportService.exportXls(queryName));
						bw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					throw new WebApplicationException(e);
				}
			}
		};

	}
	
	@GET
	@Produces({ "application/vnd.pdf" })
	@Path("/{queryname}/pdf")
	public StreamingOutput exportPdf(
			@PathParam("queryname") final String queryName)
	throws CdaException {

		return new StreamingOutput() {
			public void write(OutputStream output) throws IOException,
			WebApplicationException {
				try {

					if (log.isDebugEnabled()) {
						log.debug("REST:GET "+ queryName + " exportXls");
					}
					
					exportService.exportPdf(queryName, output);
					
//					BufferedWriter bw = new BufferedWriter(new PrintWriter(
//							output));
//
//					try {
//						bw.write(exportService.exportXls(queryName));
//						bw.flush();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
				} catch (Exception e) {
					throw new WebApplicationException(e);
				}
			}
		};

	}

	@GET
	@Produces({ "text/csv" })
	@Path("/{queryname}/csv")
	public String exportCsv(@PathParam("queryname") String queryName)
	throws CdaException {

		if (log.isDebugEnabled()) {
			log.debug("REST:GET "+ queryName + " exportCsv");
		}
		
		return exportService.exportCsv(queryName);

	}

}
