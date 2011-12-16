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
import org.pentaho.reporting.engine.classic.core.ReportElement;
import org.saiku.adhoc.model.master.SaikuGroup;

public class SaikuUpdateHeaderTask implements UpdateTask {


	private SaikuGroup groupDefinition;

	public SaikuUpdateHeaderTask(final SaikuGroup  saikuGroup)
	{
		this.groupDefinition = saikuGroup;
	}

	@Override

	public void processElement(final ReportElement e, int index)
	{
		if (Boolean.TRUE.equals
				(e.getAttribute(AttributeNames.Wizard.NAMESPACE, AttributeNames.Wizard.ALLOW_METADATA_STYLING)))
		{
	//		e.setAttribute(AttributeNames.Wizard.NAMESPACE, AttributeNames.Wizard.CACHED_WIZARD_FORMAT_DATA, groupDefinition.getHeader());
		}
		if (Boolean.TRUE.equals
				(e.getAttribute(AttributeNames.Wizard.NAMESPACE, AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES)))
		{
	//		e.setAttribute(AttributeNames.Wizard.NAMESPACE, AttributeNames.Wizard.c, groupDefinition);
		}
	}

}
