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
package org.saiku.adhoc.providers.impl.standalone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaDataFactory;
import org.saiku.adhoc.model.dto.SaikuCda;
import org.saiku.adhoc.providers.ICdaProvider;
import org.saiku.adhoc.service.SaikuProperties;
import org.saiku.adhoc.service.cda.LocalCDA;

/**
 * @author mg
 *
 */
public class StandaloneCdaProvider implements ICdaProvider {

	private URL repoURL;
	
	private String solution;
	private String path;
	
	public StandaloneCdaProvider() {
		//TODO: read from properties
		this.solution = "";
		this.path = "";
	}

	public StandaloneCdaProvider(String path) {
        try {
            setPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
    private Map<String, SaikuCda> datasources = new HashMap<String, SaikuCda>();
	
	
	/* (non-Javadoc)
	 * @see org.saiku.adhoc.providers.CdaProvider#getDataFactory(java.lang.String)
	 */
	/**
	 * This method returns the CDA-Datafactory for the report
	 * 
	 */
	@Override
	public CdaDataFactory getDataFactory(ArrayList<String> dsIds) {
		
		CdaDataFactory f = new CdaDataFactory();        
		String baseUrlField = null;
		f.setBaseUrlField(baseUrlField);

		String baseUrl = SaikuProperties.baseURL;
		f.setUsername(SaikuProperties.cdaUser);
		f.setPassword(SaikuProperties.cdaPassword);
		f.setBaseUrl(baseUrl);
		f.setSolution(this.getSolution());
		f.setPath(this.getPath());
		String file =  dsIds.get(0) + ".cda";
		f.setFile(file);      
		
		for (String id : dsIds) {
			f.setQuery(id, id);	
		}

		return f;

	}

	   public void setPath(String path) {

	        FileSystemManager fileSystemManager;
	        try {
	            fileSystemManager = VFS.getManager();

	            FileObject fileObject;
	            fileObject = fileSystemManager.resolveFile(path);
	            if (fileObject == null) {
	                throw new IOException("File cannot be resolved: " + path);
	            }
	            if (!fileObject.exists()) {
	                throw new IOException("File does not exist: " + path);
	            }
	            repoURL = fileObject.getURL();
	            if (repoURL == null) {
	                throw new Exception("Cannot load connection repository from path: " + path);
	            } else {
	                load();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }

	    public String getPath(){
	        return repoURL.getPath();
	    }
	    
	    public void load() {
	        datasources.clear();
	        try {
	            if (repoURL != null) {
	                File[] files = new File(repoURL.getFile()).listFiles();

	                for (File file : files) {
	                    if (!file.isHidden()) {
	                        if(getFileExtension(file.getAbsolutePath())!=null && getFileExtension(file.getAbsolutePath()).equalsIgnoreCase("cda")){
	                            
	                            InputStream is = new FileInputStream(file);
	                            
	                            long length = file.length();
	                            if (length > Integer.MAX_VALUE) {
	                                // File is too large
	                            }
	                            
	                            byte[] bytes = new byte[(int)length];
	                            
	                            int offset = 0;
	                            int numRead = 0;
	                            while (offset < bytes.length
	                                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	                                offset += numRead;
	                            }
	                            if (offset < bytes.length) {
	                                throw new IOException("Could not completely read file "+file.getName());
	                            }
	                            is.close();
	                            
	                            SaikuCda ds = new SaikuCda(file.getCanonicalPath(), file.getName(), bytes);
	                            datasources.put(file.getName(), ds);
	                        //}
	                        }
	                    }
	                }
	            } else {
	                throw new Exception("repo URL is null");
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    private static String getFileExtension(String filePath)
	    {
	        File f = new File(filePath);
	        String name = f.getName();
	        int k = name.lastIndexOf(".");
	        String ext = null;
	        if(k != -1)
	            ext = name.substring(k + 1, name.length());
	        return ext;
	    }

	    public SaikuCda addDatasource(SaikuCda datasource) {
	        
	        try {
	            String uri = repoURL.toURI().toString()+"/";
	            if (uri != null && datasource != null) {
	                uri += datasource.getName().replace(" ", "_");
	                File dsFile = new File(new URI(uri));
	                if (dsFile.exists()) {
	                    dsFile.delete();
	                } else {
	                    dsFile.createNewFile();
	                }
	                FileWriter fw = new FileWriter(dsFile);
	                byte[] props = datasource.getContents();
	                fw.write(new String(props));
	                //props.store(fw, null);
	                fw.close();
	                datasources.put(datasource.getName(), datasource);
	                datasource.setPath(repoURL.getPath()+"/"+datasource.getName().replace(" ", "_"));
	                return datasource;

	            } else {
	                throw new Exception("Cannot save datasource because uri or datasource is null uri("
	                        + (uri == null) + ")");
	            }
	        } catch (Exception e) {
	            try {
	                throw new Exception(e);
	            } catch (Exception e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	        }
	        return datasource;
	    }

	    public SaikuCda setDatasource(SaikuCda datasource) {
	        return addDatasource(datasource);
	    }

	    public List<SaikuCda> addDatasources(List<SaikuCda> datasources) {
	        for (SaikuCda ds : datasources) {
	            addDatasource(ds);
	        }
	        return datasources;
	    }

	    public boolean removeDatasource(String datasourceName) {
	        try {
	            String uri = repoURL.toURI().toString();
	            if (uri != null) {
	                // seems like we don't have to do this anymore
	                // uri.toString().endsWith(String.valueOf(File.separatorChar))) {
	                uri += datasourceName;
	                File dsFile = new File(new URI(uri));
	                if (dsFile.delete()) {
	                    datasources.remove(datasourceName);
	                }

	            }
	            else {
	                throw new Exception("Cannot delete datasource file uri:" + uri);
	            }
	        } catch (Exception e) {
	            try {
	                throw new Exception("Cannot delete datasource", e);
	            } catch (Exception e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	        }
	        return false;
	    }

	    public Map<String, SaikuCda> getDatasources() {
	        return datasources;
	    }

	    public SaikuCda getDatasource(String datasourceName) {
	        return datasources.get(datasourceName);
	    }
	    
//	    public String getSolution(){
//	        return "";
//	    }

	    public void callCDA(String pluginName, String method, Map<String, Object> params, OutputStream outputStream,
	            String foo) {
	    	LocalCDA.localCDAQuery(params, outputStream);
	        
	    }

	    public String callCDA(String pluginName, String method, Map<String, Object> params) {
	        return LocalCDA.localCDAQuery(params);
	    }

	    @Override
	    public void addDatasource(String solution, String path, String action, String asXML) {
	        try {
	            this.addDatasource(new SaikuCda(action, asXML.getBytes("UTF-8")));
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	    }

		@Override
		public String getSolution() {
			// TODO Auto-generated method stub
			return null;
		}


}
