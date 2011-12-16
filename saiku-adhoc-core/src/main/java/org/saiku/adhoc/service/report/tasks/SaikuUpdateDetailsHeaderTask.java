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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.reporting.engine.classic.core.AttributeNames;
import org.pentaho.reporting.engine.classic.core.ReportElement;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuElementFormat;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.utils.TemplateUtils;

public class SaikuUpdateDetailsHeaderTask implements UpdateTask {

public SaikuUpdateDetailsHeaderTask(SaikuMasterModel model) {
super();
this.columns = model.getColumns();
this.model = model;
}

private Log log = LogFactory.getLog(SaikuUpdateDetailsHeaderTask.class);

private SaikuMasterModel model;

private List<SaikuColumn> columns;

@Override
public void processElement(ReportElement e, int index) {

log.debug(e.getClass() + " " + e.getName());

/*
* Markup for Client
*/
final SaikuColumn saikuColumn = columns.get(index);

final String rptId = "rpt-dth-" + index;

//final String htmlClass = "saiku " + columns.get(index).getUid().replace("rpt-dtl-", "rpt-dth-");;

final String htmlClass = "saiku " + rptId;

model.getDerivedModels().getRptIdToSaikuElement().put(rptId, saikuColumn);

e.setAttribute(AttributeNames.Html.NAMESPACE, AttributeNames.Html.STYLE_CLASS, htmlClass);

e.setAttribute("http://reporting.pentaho.org/namespaces/engine/attributes/wizard", "allow-metadata-styling", Boolean.FALSE);

/*
* Transfer element style
*/

SaikuElementFormat tempFormat = (SaikuElementFormat) saikuColumn.getColumnHeaderFormat().clone();

TemplateUtils.mergeElementFormats(e.getStyle(), tempFormat);

model.getDerivedModels().getRptIdToElementFormat().put(rptId, tempFormat);

}

}

