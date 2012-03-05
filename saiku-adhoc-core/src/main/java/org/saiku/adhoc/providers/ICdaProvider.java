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
package org.saiku.adhoc.providers;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaDataFactory;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.dto.SaikuCda;

public interface ICdaProvider {
	
	public void load();

	public SaikuCda addDatasource(SaikuCda datasource);

	public SaikuCda setDatasource(SaikuCda datasource);

	public List<SaikuCda> addDatasources(List<SaikuCda> datasources);

	public boolean removeDatasource(String datasourceName);

	public Map<String, SaikuCda> getDatasources();

	public SaikuCda getDatasource(String datasourceName);

	public String getPath();

	//public String getSolution();

	public void addDatasource(String solution, String path, String action, String asXML);

	public CdaDataFactory getDataFactory(String dsId);
	
	public void callCDA(String pluginName, String method, Map<String, Object> params, OutputStream outputStream, String foo) throws SaikuAdhocException;

	public String callCDA(String pluginName, String method, Map<String, Object> params) throws SaikuAdhocException;

	public String getSolution();

}
