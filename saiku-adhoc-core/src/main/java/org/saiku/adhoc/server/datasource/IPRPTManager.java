package org.saiku.adhoc.server.datasource;

import java.util.List;
import java.util.Map;

import org.saiku.adhoc.model.master.ReportTemplate;


public interface IPRPTManager {

public void load();

public ReportTemplate addDatasource(ReportTemplate datasource);

public ReportTemplate setDatasource(ReportTemplate datasource);

public List<ReportTemplate> addDatasources(List<ReportTemplate> datasources);

public boolean removeDatasource(String datasourceName);

public Map<String, ReportTemplate> getDatasources();

public ReportTemplate getDatasource(String datasourceName);

}