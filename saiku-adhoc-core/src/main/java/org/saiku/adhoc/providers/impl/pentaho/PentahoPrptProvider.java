/*
 * Copyright (C) 2012 Marius Giepz
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
package org.saiku.adhoc.providers.impl.pentaho;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.platform.plugin.SimpleReportingComponent;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.providers.IPrptProvider;
import org.saiku.adhoc.service.SaikuProperties;

/**
 * @author mg
 *
 */
public class PentahoPrptProvider implements IPrptProvider {

	private static final String TEMPLATE_PATH = "saiku-adhoc/resources/templates";
	private static final String TEMPLATE_SOLUTION = "system";
	private String solution;
	private String path;

	public PentahoPrptProvider() {
		this.solution = SaikuProperties.temporarySolution;
		this.path = SaikuProperties.temporaryPath;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	public ReportTemplate addDatasource(ReportTemplate datasource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportTemplate setDatasource(ReportTemplate datasource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportTemplate> addDatasources(List<ReportTemplate> datasources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeDatasource(String datasourceName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, ReportTemplate> getDatasources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportTemplate getDatasource(String datasourceName) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public String getPath() {
        return "tmp";
    }

    @Override
    public String getSolution() {
        return TEMPLATE_SOLUTION;
    }

	@Override
	public ReportTemplate getTemplate(String path, String solution, String templateName) {
		return new ReportTemplate(TEMPLATE_SOLUTION, TEMPLATE_PATH, templateName);     
	}

	@Override
	public SimpleReportingComponent getReportingComponent() {
		return new SimpleReportingComponent();
	}

	@Override
	public MasterReport getMasterReport(String fullPath, SimpleReportingComponent reportComponent) {
		reportComponent.setReportDefinitionPath(fullPath);
		try {
			return reportComponent.getReport();
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public MasterReport getPrptTemplate(ReportTemplate reportTemplate) {
		return getMasterReport(reportTemplate.getFullPath(), new SimpleReportingComponent());
	}

	@Override
	public String getTemplatePath() {
		// TODO Auto-generated method stub
		return null;
	}


}
