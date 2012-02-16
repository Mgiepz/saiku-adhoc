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
package org.saiku.adhoc.server.query;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.pentaho.reporting.libraries.base.util.CSVTokenizer;

import pt.webdetails.cda.CdaEngine;
import pt.webdetails.cda.connections.UnsupportedConnectionException;
import pt.webdetails.cda.dataaccess.QueryException;
import pt.webdetails.cda.dataaccess.UnsupportedDataAccessException;
import pt.webdetails.cda.exporter.ExporterException;
import pt.webdetails.cda.exporter.UnsupportedExporterException;
import pt.webdetails.cda.query.QueryOptions;
import pt.webdetails.cda.settings.CdaSettings;
import pt.webdetails.cda.settings.SettingsManager;
import pt.webdetails.cda.settings.UnknownDataAccessException;

public class LocalCDA {

    final static int DEFAULT_PAGE_SIZE = 20;
    final static int DEFAULT_START_PAGE = 0;
	
    private LocalCDA(){
        
    }
    
    public static String localCDAQuery(final Map<String, Object> inputs){
    	
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	
    	localCDAQuery(inputs, out);
    
    	return out.toString();
		
	}
    
    public static void localCDAQuery(Map<String, Object> inputs, OutputStream out) {
    	// TODO Auto-generated method stub
        
        CdaEngine engine = CdaEngine.getInstance();
        
        final QueryOptions queryOptions = new QueryOptions();

        CdaSettings cdaSettings=null;
        try {
            cdaSettings = SettingsManager.getInstance().parseSettingsFile(inputsGetString(inputs, "path", "empty"));
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedDataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        final String CDA_PARAMS = "cdaParameterString";
        final String CDA_PARAM_SEPARATOR = ";";

        // page info
        
        final long pageSize = inputsGetLong(inputs, "pageSize", 0);
        final long pageStart = inputsGetLong(inputs, "pageStart", 0);
        final boolean paginate = "true".equals(inputsGetString(inputs, "paginateQuery", "false"));
        if (pageSize > 0 || pageStart > 0 || paginate) {
          if (pageSize > Integer.MAX_VALUE || pageStart > Integer.MAX_VALUE) {
            throw new ArithmeticException("Paging values too large");
          }
          queryOptions.setPaginate(true);
          queryOptions.setPageSize(pageSize > 0 ? (int) pageSize : paginate ? DEFAULT_PAGE_SIZE : 0);
          queryOptions.setPageStart(pageStart > 0 ? (int) pageStart : paginate ? DEFAULT_START_PAGE : 0);
        }

        // query info 
        
        queryOptions.setOutputType(inputsGetString(inputs, "outputType", "json"));
        queryOptions.setDataAccessId(inputsGetString(inputs, "dataAccessId", "<blank>"));
        queryOptions.setOutputIndexId(inputsGetInteger(inputs, "outputIndexId", 1));
        
        // params and settings
        
        //process parameter string "name1=value1;name2=value2"
        String cdaParamString = inputsGetString(inputs, CDA_PARAMS, null);
        if (cdaParamString != null && cdaParamString.trim().length() > 0) {
          
          List<String> cdaParams = new ArrayList<String>();
          //split to 'name=val' tokens
          CSVTokenizer tokenizer = new CSVTokenizer(cdaParamString, CDA_PARAM_SEPARATOR);
          while(tokenizer.hasMoreTokens()){
            cdaParams.add(tokenizer.nextToken());
          }
          
          //split '='
          for(String nameValue : cdaParams){
            int i = 0;
            CSVTokenizer nameValSeparator = new CSVTokenizer(nameValue, "=");
            String name=null, value=null;
            while(nameValSeparator.hasMoreTokens()){
              if(i++ == 0){
                name = nameValSeparator.nextToken();
              }
              else {
                value = nameValSeparator.nextToken();
                break;
              }
            }
            if(name != null) queryOptions.addParameter(name, value);
          }
        }
        
        for (String param : inputs.keySet()) {
          if (param.startsWith("param")) {
            queryOptions.addParameter(param.substring(5), inputsGetString(inputs, param, ""));
          } else if (param.startsWith("setting")) {
            queryOptions.addSetting(param.substring(7), inputsGetString(inputs, param, ""));
          }
          //queryOptions.setOutputType("json");
        }
        try {
            engine.doQuery(out, cdaSettings, queryOptions);
        } catch (UnknownDataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (QueryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedExporterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExporterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        }
    
     private static int inputsGetInteger(Map<String,Object>inputs, String name, int defaultVal)
      {
        Object obj = inputs.get(name);
        
        // pojo component forces all strings to upper case :-(
        if (obj == null)
        {
          obj = inputs.get(name.toUpperCase());
        }
        
        if (obj == null)
        {
          return defaultVal;
        }
        
        return new Integer(obj.toString());
      }

      private static long inputsGetLong(Map<String,Object>inputs,String name, long defaultVal) {
          Object obj = inputs.get(name);
          // pojo component forces all strings to upper case :-(
          if (obj == null) {
            obj = inputs.get(name.toUpperCase());
          }
          if (obj == null) {
            return defaultVal;
          }
          return new Long(obj.toString());
      }
      
      private static String inputsGetString(Map<String,Object>inputs,String name, String defaultVal) {
        Object obj = inputs.get(name);
        // pojo component forces all strings to upper case :-(
        if (obj == null) {
          obj = inputs.get(name.toUpperCase());
        }
        if (obj == null) {
          return defaultVal;
        }
        return obj.toString();
      }
      
}
