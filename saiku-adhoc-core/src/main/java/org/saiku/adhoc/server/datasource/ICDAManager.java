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

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import pt.webdetails.cda.settings.CdaSettings;


public interface ICDAManager {

public void load();

public SaikuCDA addDatasource(SaikuCDA datasource);

public SaikuCDA setDatasource(SaikuCDA datasource);

public List<SaikuCDA> addDatasources(List<SaikuCDA> datasources);

public boolean removeDatasource(String datasourceName);

public Map<String, SaikuCDA> getDatasources();

public SaikuCDA getDatasource(String datasourceName);

public String getPath();

public String getSolution();

public void callCDA(String pluginName, String method, Map<String, Object> params, OutputStream outputStream, String foo);

public String callCDA(String pluginName, String method, Map<String, Object> params);

public void addDatasource(String solution, String path, String action, String asXML);

CdaSettings initCDA(String sessionId, String domain);

}