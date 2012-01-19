package org.saiku.adhoc.datasource;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.server.datasource.SaikuCDA;
import org.saiku.adhoc.service.cda.PluginUtils;
import org.saiku.adhoc.service.repository.IRepositoryHelper;

import pt.webdetails.cda.connections.Connection;
import pt.webdetails.cda.connections.UnsupportedConnectionException;
import pt.webdetails.cda.connections.metadata.MetadataConnection;
import pt.webdetails.cda.dataaccess.DataAccess;
import pt.webdetails.cda.dataaccess.MqlDataAccess;
import pt.webdetails.cda.dataaccess.UnsupportedDataAccessException;
import pt.webdetails.cda.settings.CdaSettings;

public class PluginResourceCDAManager implements ICDAManager{

    private IRepositoryHelper repository;
    
    @Override
    public void load() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public SaikuCDA addDatasource(SaikuCDA datasource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SaikuCDA setDatasource(SaikuCDA datasource) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SaikuCDA> addDatasources(List<SaikuCDA> datasources) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeDatasource(String datasourceName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, SaikuCDA> getDatasources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SaikuCDA getDatasource(String datasourceName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPath() {
        return "saiku-adhoc/temp";
    }

    @Override
    public String getSolution() {
        return "system";

    }

    public void callCDA(String pluginName, String method, Map<String, Object> params, OutputStream outputStream,
            String foo) {
        PluginUtils.callPlugin("cda", "doQuery", params, outputStream, null);

        
    }

    public String callCDA(String pluginName, String method, Map<String, Object> params) {
        return PluginUtils.callPlugin("cda", "doQuery", params);
    }

    @Override
    public void addDatasource(String solution, String path, String action, String asXML) {

        repository.writeFile(solution,path, action, asXML);        
    }

    @Override
    public CdaSettings initCDA(String sessionId, String domain) {
        CdaSettings cda = null;
        try {
            cda = new CdaSettings("cda" + sessionId, null);
        } catch (UnsupportedConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedDataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] domainInfo = domain.split("/");
        Connection connection = new MetadataConnection("1", domainInfo[0], domainInfo[1]);
        DataAccess dataAccess = new MqlDataAccess(sessionId, sessionId, "1", "") ;
        cda.addConnection(connection);
        cda.addDataAccess(dataAccess);
        return cda;
        
    }

}
