package org.saiku.adhoc.datasource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.platform.plugin.SimpleReportingComponent;
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

    @Override
    public ReportTemplate getTemplate(String path, String solution, String templateName) {
        return new ReportTemplate("system", "saiku-adhoc/resources/templates", templateName);     
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

}
