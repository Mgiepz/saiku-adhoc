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
package org.saiku.adhoc.service.report.tasks;

import org.pentaho.reporting.engine.classic.core.AttributeNames;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ReportElement;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuElementFormat;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.utils.TemplateUtils;

public class SaikuUpdateGroupHeaderTask implements UpdateTask {

	private SaikuGroup groupDefinition;
	private int groupIndex;
	private SaikuMasterModel model;

	public SaikuUpdateGroupHeaderTask(SaikuMasterModel model, SaikuGroup saikuGroup, int groupIndex) {
		this.groupDefinition = saikuGroup;
		this.groupIndex = groupIndex;
		this.model = model;
	}

	@Override
	public void processElement(ReportElement e, int index) {

		/*
		 * TODO: Something terrible might happen if there is more than one message thing
		 * in the group header
		 * 
		 */
		Element el = (Element) e;

		if(el.getElementTypeName().equals("message")){

			final String rptId = "rpt-ghd-" + groupIndex + "-" + index;

			final String htmlClass = "saiku " + rptId;

			model.getDerivedModels().getRptIdToSaikuElement().put(rptId, groupDefinition);

			e.setAttribute(AttributeNames.Html.NAMESPACE, AttributeNames.Html.STYLE_CLASS, htmlClass);

			e.setAttribute("http://reporting.pentaho.org/namespaces/engine/attributes/wizard", "allow-metadata-styling", Boolean.FALSE);

			if(groupDefinition.getGroupName()==null){
				groupDefinition.setGroupName((String) e.getAttribute(AttributeNames.Core.NAMESPACE, AttributeNames.Core.VALUE));
			}
			
			e.setAttribute(AttributeNames.Core.NAMESPACE,
					AttributeNames.Core.VALUE, groupDefinition.getGroupName());	

			/*
			 * Transfer element style
			 */	
			
			SaikuElementFormat tempFormat = (SaikuElementFormat) groupDefinition.getGroupsHeaderFormat().clone();
			
			TemplateUtils.mergeElementFormats(e.getStyle(), tempFormat);
			
			model.getDerivedModels().getRptIdToElementFormat().put(rptId, tempFormat);


		}

	}

}
