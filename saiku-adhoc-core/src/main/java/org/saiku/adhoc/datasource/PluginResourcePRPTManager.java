package org.saiku.adhoc.datasource;

import java.util.List;
import java.util.Map;

import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.server.datasource.IPRPTManager;

public class PluginResourcePRPTManager implements IPRPTManager{

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
        return "saiku-adhoc/temp";
    }

    @Override
    public String getSolution() {
        return "system";
    }

    @Override
    public String getTemplatePath() {
        return "saiku-adhoc/resources/templates/";
    }

}
