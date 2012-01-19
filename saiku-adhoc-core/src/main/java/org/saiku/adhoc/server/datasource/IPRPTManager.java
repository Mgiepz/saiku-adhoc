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
package org.saiku.adhoc.server.datasource;

import java.util.List;
import java.util.Map;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.platform.plugin.SimpleReportingComponent;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.master.ReportTemplate;


public interface IPRPTManager {

public void load();

public ReportTemplate addDatasource(ReportTemplate datasource);

public ReportTemplate setDatasource(ReportTemplate datasource);

public List<ReportTemplate> addDatasources(List<ReportTemplate> datasources);

public boolean removeDatasource(String datasourceName);

public Map<String, ReportTemplate> getDatasources();

public ReportTemplate getDatasource(String datasourceName);

public String getPath();

public String getSolution();

public String getTemplatePath();

public ReportTemplate getTemplate(String path, String solution, String templateName);

public SimpleReportingComponent getReportingComponent();

public MasterReport getMasterReport(String fullPath, SimpleReportingComponent reportComponent) throws SaikuAdhocException;
}