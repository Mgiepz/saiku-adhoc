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

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.saiku.adhoc.exceptions.CdaException;
import org.saiku.adhoc.exceptions.SaikuAdhocException;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.providers.ICdaProvider;
import org.saiku.adhoc.utils.ParamUtils;

/**
 * CdaAccessor accesses CDA Plugin by calling the CDA's ContentGenerator
 * 
 * @author mgie
 * 
 */
public class CdaAccessor implements ICdaAccessor {

	private ICdaProvider cdaProvider;

	public void setCdaProvider(ICdaProvider provider) {
		this.cdaProvider = provider;

	}

	public ICdaProvider getCdaProvider() {
		return cdaProvider;
	}

	@Override
	public String doQuery(SaikuMasterModel model, String id, String outputType) throws CdaException, SaikuAdhocException {

		Map<String, Object> params = prepareParams(model, id, outputType);
		
		final String result = cdaProvider.callCDA("cda", "doQuery", params);

		return result;

	}

	@Override
	public void doQuery(SaikuMasterModel model, String id, String outputType, OutputStream output) throws SaikuAdhocException {

		Map<String, Object> params = prepareParams(model, id, outputType);

		cdaProvider.callCDA("cda", "doQuery", params, output, null);

	}

	/**
	 * @param model
	 * @param id
	 * @param outputType
	 * @return
	 */
	private Map<String, Object> prepareParams(SaikuMasterModel model, String id, String outputType) {
		Map<String, Object> params = new HashMap<String, Object>();

		String daId = id;

		String solution = cdaProvider.getSolution();
		String path = cdaProvider.getPath();

		String action = model.getSessionId() + ".cda";

		String fullPath = solution + "/" + path + "/" + action;

		params.put("path", fullPath);
		params.put("dataAccessId", daId);

		if (outputType != null) {
			params.put("outputType", outputType);
		}

		params.putAll(ParamUtils.getReportParameters("param", model));
		return params;
	}

}
