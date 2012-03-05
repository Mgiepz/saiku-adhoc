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

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaDataFactory;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.dto.SaikuCda;
import org.saiku.adhoc.providers.ICdaProvider;
import org.saiku.adhoc.service.SaikuProperties;
import org.saiku.adhoc.service.cda.PluginUtils;
import org.saiku.adhoc.service.repository.IRepositoryHelper;

/**
 * @author mg
 *
 */
public class PentahoCdaProvider implements ICdaProvider {
	
    private IRepositoryHelper repository;
    
    public void setRepositoryHelper(IRepositoryHelper repository) {
        this.repository = repository;
    }

	public PentahoCdaProvider() {

	}


	/**
	 * This method returns the CDA-Datafactory for the report
	 * 
	 */
	@Override
	public CdaDataFactory getDataFactory(String dsId) {
		
		CdaDataFactory f = new CdaDataFactory();        
		String baseUrlField = null;
		f.setBaseUrlField(baseUrlField);
		String name = dsId;
		String queryString = dsId;
//		CdaQueryEntry entry = new CdaQueryEntry(queryString);
//		entry.setId(dsId);
//		f.setQuery(name, entry);     
//		f.setQuery(name, queryString); 
		f.setQuery(name, queryString);
		String baseUrl = SaikuProperties.baseURL;
		
		//Use this for the login
		//PentahoSessionHolder.getSession().getId();          
		f.setBaseUrl(baseUrl);
		f.setSolution(this.getSolution());
        f.setPath(this.getPath());
		String file =  dsId + ".cda";
		f.setFile(file);      
		f.setUsername(SaikuProperties.cdaUser);
		f.setPassword(SaikuProperties.cdaPassword);

		return f;
		
	}
	
    @Override
    public void load() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public SaikuCda addDatasource(SaikuCda datasource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SaikuCda setDatasource(SaikuCda datasource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SaikuCda> addDatasources(List<SaikuCda> datasources) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeDatasource(String datasourceName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, SaikuCda> getDatasources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SaikuCda getDatasource(String datasourceName) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public String getSolution(){
    	return "system";
    }
    

    @Override
    public String getPath() {
        return "saiku-adhoc/temp";
  
    }

//    @Override
//    public String getSolution() {
//        return "system";
//
//    }

    @Override
    public void addDatasource(String solution, String path, String action, String asXML) {
    	
    	repository.writeFile(solution,path, action, asXML);        
    }
    
    public void callCDA(String pluginName, String method, Map<String, Object> params, OutputStream outputStream,
            String foo) throws SaikuAdhocException {
        PluginUtils.callPlugin("cda", "doQuery", params, outputStream, null);
      
    }

    public String callCDA(String pluginName, String method, Map<String, Object> params) throws SaikuAdhocException {
        return PluginUtils.callPlugin("cda", "doQuery", params);
    }

}
