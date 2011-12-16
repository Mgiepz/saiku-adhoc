/*
 * Copyright (C) 2011 OSBI Ltd
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
    
public class ClassPathResourceCDAManager implements ICDAManager {

    private URL repoURL;

    private Map<String, SaikuCDA> datasources = new HashMap<String, SaikuCDA>();

    public ClassPathResourceCDAManager() {

    }

    public ClassPathResourceCDAManager(String path) {
        try {
            setPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            
                            SaikuCDA ds = new SaikuCDA(file.getCanonicalPath(), file.getName(), bytes);
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

    public SaikuCDA addDatasource(SaikuCDA datasource) {
        
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

    public SaikuCDA setDatasource(SaikuCDA datasource) {
        return addDatasource(datasource);
    }

    public List<SaikuCDA> addDatasources(List<SaikuCDA> datasources) {
        for (SaikuCDA ds : datasources) {
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

    public Map<String, SaikuCDA> getDatasources() {
        return datasources;
    }

    public SaikuCDA getDatasource(String datasourceName) {
        return datasources.get(datasourceName);
    }
}
