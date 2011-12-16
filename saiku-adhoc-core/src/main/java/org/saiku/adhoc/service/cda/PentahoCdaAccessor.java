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
package org.saiku.adhoc.service.cda;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.utils.ParamUtils;

/**
* PentahoCdaAccessor accesses CDA Plugin by calling the CDA's
* ContentGenerator
*
* @author mgie
*
*/
public class PentahoCdaAccessor implements ICdaAccessor {

/* (non-Javadoc)
* @see refac.saiku.adhoc.service.cda.ICdaAccessor#doQuery(org.saiku.adhoc.model.AdhocReportModel, java.lang.String, java.lang.String)
*/
@Override
public String doQuery(SaikuMasterModel model, String id, String outputType) throws CdaException {

Map<String, Object> params = new HashMap<String, Object>();

String path = model.getCdaPath();
String daId = id;

params.put("path", path);
params.put("dataAccessId", daId);

if(outputType!=null){
params.put("outputType",outputType);
}

params.putAll(ParamUtils.getReportParameters("param", model));

// HashMap<String,ArrayList<String>> valuesMap = new HashMap<String,ArrayList<String>>();
//
// final ArrayList<SaikuParameter> parameters = model.getParameters();
// for (SaikuParameter saikuParameter : parameters) {
// String filterKey = saikuParameter.getCategory() + "." + saikuParameter.getId();
// valuesMap.put(filterKey, saikuParameter.getParameterValues());
// }
//
//
// if(!valuesMap.isEmpty()){
// for (String filter : valuesMap.keySet()) {
// String filterName = "paramF_" + filter.replace(".", "_");
// final ArrayList<String> valueList = valuesMap.get(filter);
// String[] values = null;
// if(!valueList.isEmpty()){
// values = valueList.toArray(new String[valueList.size()]);
// }else{
// values = new String[]{""};
// }
//
// params.put(filterName, values);
// }
// }

final String result = PluginUtils.callPlugin("cda", "doQuery", params);

try{
FileWriter fstream = new FileWriter("c:/tmp/cda.xls");
BufferedWriter out = new BufferedWriter(fstream);
out.write(result);
out.close();
}catch (Exception e){
throw new CdaException(e.getMessage());
}


return result;

}

}

