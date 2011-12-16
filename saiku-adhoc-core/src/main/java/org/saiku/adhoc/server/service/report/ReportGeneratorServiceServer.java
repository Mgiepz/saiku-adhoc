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

package org.saiku.adhoc.server.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.pentaho.reporting.engine.classic.core.AttributeNames;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportPreProcessor;
import org.pentaho.reporting.engine.classic.core.cache.CachingDataFactory;
import org.pentaho.reporting.engine.classic.core.function.ProcessingContext;
import org.pentaho.reporting.engine.classic.core.layout.output.DefaultProcessingContext;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlTableModule;
import org.pentaho.reporting.engine.classic.core.modules.parser.bundle.writer.BundleWriter;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterDefinitionEntry;
import org.pentaho.reporting.engine.classic.core.states.StateUtilities;
import org.pentaho.reporting.engine.classic.core.states.datarow.DefaultFlowController;
import org.pentaho.reporting.engine.classic.core.util.ReportParameterValues;
import org.pentaho.reporting.engine.classic.core.wizard.DataSchemaDefinition;
import org.pentaho.reporting.engine.classic.wizard.model.WizardSpecification;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.platform.plugin.SimpleReportingComponent;
import org.saiku.adhoc.exceptions.ModelException;
import org.saiku.adhoc.exceptions.ReportException;
import org.saiku.adhoc.model.dto.HtmlReport;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.server.datasource.IPRPTManager;
import org.saiku.adhoc.server.reporting.SaikuReportingComponent;
import org.saiku.adhoc.service.SaikuProperties;
import org.saiku.adhoc.service.report.ReportGeneratorService;
import org.saiku.adhoc.service.report.SaikuAdhocPreProcessor;
import org.saiku.adhoc.utils.ParamUtils;

public class ReportGeneratorServiceServer extends ReportGeneratorService {

	private IPRPTManager prptManager;

    /**
	 * Renders the report for a given query to html
	 * 
	 * @param sessionId
	 * @param report 
	 * @param acceptedPage 
	 * @param template 
	 * @return
	 * @throws ReportException
	 * @throws ModelException
	 * @throws IOException
	 * @throws ResourceException
	 */
    @Override
	public void renderReportHtml(String sessionId, String templateName, HtmlReport report, Integer acceptedPage) throws Exception {

		SaikuMasterModel model = sessionHolder.getModel(sessionId);

		templateName = templateName.equals("default")? SaikuProperties.defaultPrptTemplate : templateName + ".prpt";
		
		ReportTemplate template =  this.prptManager.getDatasource(templateName);
		
		model.setReportTemplate(template);
		
		MasterReport output = null;

        output = processReport(model, output);          

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        generateHtmlReport(output, stream, ParamUtils.getReportParameters("", model), report, acceptedPage);

        report.setData(stream.toString());      

		
	}
	
	private void generateHtmlReport(MasterReport output, OutputStream stream,
            Map<String, Object> reportParameters, HtmlReport report, Integer acceptedPage) throws Exception {

        final SaikuReportingComponent reportComponent = new SaikuReportingComponent();
        reportComponent.setReport(output);
        reportComponent.setPaginateOutput(true);      
        reportComponent.setInputs(reportParameters);
        reportComponent.setDefaultOutputTarget(HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE);
        reportComponent.setOutputTarget(HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE);
        reportComponent.setDashboardMode(true);  
        reportComponent.setOutputStream(stream);
        reportComponent.setAcceptedPage(acceptedPage);  
    
       // reportComponent.validate();  
        reportComponent.execute();

        report.setCurrentPage(reportComponent.getAcceptedPage());
        report.setPageCount(reportComponent.getPageCount());
     

         
    }
	  
    public void setPRPTManager(IPRPTManager manager){
        this.prptManager = manager;
        
    }

    public IPRPTManager getPRPTManager(){
        return prptManager;
    }

}
