package org.saiku.adhoc.server.datasource;

import java.util.List;
import java.util.Map;

import org.saiku.adhoc.model.master.ReportTemplate;


public interface ICDAManager {

public void load();

public SaikuCDA addDatasource(SaikuCDA datasource);

public SaikuCDA setDatasource(SaikuCDA datasource);

public List<SaikuCDA> addDatasources(List<SaikuCDA> datasources);

public boolean removeDatasource(String datasourceName);

public Map<String, SaikuCDA> getDatasources();

public SaikuCDA getDatasource(String datasourceName);

public String getPath();
}