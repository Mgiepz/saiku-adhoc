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

package org.saiku.adhoc.model.transformation;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.parameters.DefaultParameterDefinition;
import org.pentaho.reporting.engine.classic.core.parameters.PlainParameter;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.platform.plugin.SimpleReportingComponent;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;


public class TransModelToReport {

	public MasterReport doIt(SaikuMasterModel smm) throws ResourceException, IOException {

		String fullPath = smm.getReportTemplate().getFullPath();

		final SimpleReportingComponent reportComponent = new SimpleReportingComponent();
		reportComponent.setReportDefinitionPath(fullPath);
		final MasterReport reportTemplate = reportComponent.getReport();

		DefaultParameterDefinition paramDef = new DefaultParameterDefinition();

		List<SaikuParameter> parameters = smm.getParameters();

		//Generate Filter Parameters

		for (SaikuParameter saikuParameter : parameters) {

			final String categoryId = saikuParameter.getCategory();
			final String columnId = saikuParameter.getId();	
			final String parameterName = "F_" + categoryId + "_" + columnId;

			if(saikuParameter.getType().equals("String")){
				final PlainParameter plainParameter = new PlainParameter(parameterName, String[].class);
				paramDef.addParameterDefinition(plainParameter);
			}
			if(saikuParameter.getType().equals("Date")){
				String nameFrom = parameterName + "_FROM";
				String nameTo = parameterName + "_TO";

				final PlainParameter plainParameterFrom = new PlainParameter(nameFrom, java.util.Date.class);
				paramDef.addParameterDefinition(plainParameterFrom);

				final PlainParameter plainParameterTo = new PlainParameter(nameTo, java.util.Date.class);
				paramDef.addParameterDefinition(plainParameterTo);

			}
		}

		reportTemplate.setParameterDefinition(paramDef);

		return reportTemplate;
	}

}
