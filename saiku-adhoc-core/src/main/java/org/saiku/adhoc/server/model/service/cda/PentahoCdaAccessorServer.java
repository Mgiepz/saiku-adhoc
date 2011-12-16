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
package org.saiku.adhoc.server.model.service.cda;

import java.util.HashMap;
import java.util.Map;

import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.server.query.LocalCDA;
import org.saiku.adhoc.service.cda.PentahoCdaAccessor;
import org.saiku.adhoc.utils.ParamUtils;

/**
 * PentahoCdaAccessor accesses CDA Plugin by calling the CDA's
 * ContentGenerator
 * 
 * @author mgie
 *
 */
public class PentahoCdaAccessorServer extends PentahoCdaAccessor {

    /* (non-Javadoc)
     * @see refac.saiku.adhoc.service.cda.ICdaAccessor#doQuery(org.saiku.adhoc.model.AdhocReportModel, java.lang.String, java.lang.String)
     */
    @Override
    public String doQuery(SaikuMasterModel model, String id, String outputType) throws CdaException {

        Map<String, Object> params = new HashMap<String, Object>();

        String path = model.getCda().getPath();
        String daId = id;

        params.put("path", path);
        params.put("dataAccessId", daId);

        if(outputType!=null){
            params.put("outputType",outputType);    
        }
        
        params.putAll(ParamUtils.getReportParameters("param", model));

        
        String result = LocalCDA.localCDAQuery(params);


        
        return result;

    }
    


}
