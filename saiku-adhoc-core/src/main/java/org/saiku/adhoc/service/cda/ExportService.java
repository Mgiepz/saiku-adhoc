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

package org.saiku.adhoc.service.cda;

import java.io.OutputStream;
import java.util.Map;

import org.pentaho.platform.api.engine.IContentGenerator;
import org.pentaho.platform.api.engine.IParameterProvider;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.service.report.ReportGeneratorService;

public class ExportService {
	
	private WorkspaceSessionHolder sessionHolder;
	
	private ReportGeneratorService reportGeneratorService;
	
	private ICdaAccessor cdaAccessor;

	public void setSessionHolder(WorkspaceSessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public void setCdaAccessor(ICdaAccessor cda) {
		this.cdaAccessor = cda;
	}

	public String exportXls(String sessionId) throws CdaException {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);
		
		return cdaAccessor.doQuery(model, sessionId, "xls");
		
	}
	
	public String exportCsv(String sessionId) throws CdaException {

		SaikuMasterModel query = sessionHolder.getModel(sessionId);

		return cdaAccessor.doQuery(query, sessionId, "csv");
		
	}
	
	public void exportPdf(String sessionId, OutputStream output) throws Exception{
		this.reportGeneratorService.renderReportPdf(sessionId, output);
	}

	
	public void setReportGeneratorService(ReportGeneratorService reportGeneratorService) {
		this.reportGeneratorService = reportGeneratorService;
	}

	public ReportGeneratorService getReportGeneratorService() {
		return reportGeneratorService;
	}


}
