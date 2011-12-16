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
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ReportElement;
import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.master.SaikuElementFormat;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuMessage;
import org.saiku.adhoc.utils.TemplateUtils;

public class SaikuUpdateMessagesTask implements UpdateTask {

private Log log = LogFactory.getLog(SaikuUpdateMessagesTask.class);
private List<SaikuMessage> messages;
private String prefix;
private SaikuMasterModel model;

public SaikuUpdateMessagesTask(List<SaikuMessage> messages,
String prefix, SaikuMasterModel model) {

this.messages = messages;
this.prefix = prefix;
this.model = model;

}

@Override
public void processElement(ReportElement e, int index) {

Element el = (Element) e;

final String uid = prefix + index;

//markup the element
if(el.getElementTypeName().equals("message") ||
el.getElementTypeName().equals("label")){
final String htmlClass = "saiku " + uid;
e.setAttribute(AttributeNames.Html.NAMESPACE, AttributeNames.Html.STYLE_CLASS, htmlClass);


SaikuMessage m = null;

for (SaikuMessage msg : this.messages) {
if(uid.equals(msg.getUid())){
m = msg;
break;
}
}
if(m==null){
m = new SaikuMessage();
m.setElementFormat(new SaikuElementFormat());
m.setUid(uid);
String val =(String) e.getAttribute(AttributeNames.Core.NAMESPACE, AttributeNames.Core.VALUE);
m.setValue(val);
this.messages.add(m);
}

e.setAttribute("http://reporting.pentaho.org/namespaces/engine/attributes/wizard", "allow-metadata-styling", Boolean.FALSE);

/*
* Transfer element style
*/
e.setAttribute(AttributeNames.Core.NAMESPACE, AttributeNames.Core.VALUE, m.getValue());

/*
* Transfer element style
*/

SaikuElementFormat tempFormat = (SaikuElementFormat) m.getElementFormat().clone();

TemplateUtils.mergeElementFormats(e.getStyle(), tempFormat);

model.getDerivedModels().getRptIdToElementFormat().put(uid, tempFormat);

}


}

}

