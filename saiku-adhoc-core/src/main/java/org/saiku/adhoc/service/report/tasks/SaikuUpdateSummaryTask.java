//package org.saiku.adhoc.service.report.tasks;
//
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.pentaho.reporting.engine.classic.core.AttributeNames;
//import org.pentaho.reporting.engine.classic.core.Element;
//import org.pentaho.reporting.engine.classic.core.ReportElement;
//import org.saiku.adhoc.model.master.SaikuElementFormat;
//import org.saiku.adhoc.model.master.SaikuLabel;
//import org.saiku.adhoc.model.master.SaikuMasterModel;
//import org.saiku.adhoc.utils.TemplateUtils;
//
//public class SaikuUpdateSummaryTask implements UpdateTask {
//
//	private Log log = LogFactory.getLog(SaikuUpdateSummaryTask.class);
//	private List<SaikuLabel> messages;
//	private String prefix;
//	private SaikuMasterModel model;
//
//	public SaikuUpdateSummaryTask(List<SaikuLabel> messages,
//			String prefix, SaikuMasterModel model) {
//
//		this.messages = messages;
//		this.prefix = prefix;
//		this.model = model;
//
//	}
//
//	@Override
//	public void processElement(ReportElement e, int index) {
//
//		Element el = (Element) e;
//
//		final String uid = prefix + index;
//
//		//markup the element
//		if(el.getElementTypeName().equals("message") ||
//				el.getElementTypeName().equals("label")||
//				e.getAttribute("http://reporting.pentaho.org/namespaces/engine/attributes/wizard", "aggregation-type")!=null){		
//			final String htmlClass = "saiku " + uid;
//			e.setAttribute(AttributeNames.Html.NAMESPACE, AttributeNames.Html.STYLE_CLASS, htmlClass);
//
//
//			SaikuLabel m = null;
//
//			for (SaikuLabel msg : this.messages) {
//				if(uid.equals(msg.getUid())){
//					m = msg;
//					break;
//				}
//			}
//			if(m==null){
//				m = new SaikuLabel();
//				m.setElementFormat(new SaikuElementFormat());
//				m.setUid(uid);
//				String val =(String) e.getAttribute(AttributeNames.Core.NAMESPACE, AttributeNames.Core.VALUE);
//				m.setValue(val);
//				this.messages.add(m);
//				
//				m.setLayoutId(uid);
//
//			}
//
//			e.setAttribute("http://reporting.pentaho.org/namespaces/engine/attributes/wizard", "allow-metadata-styling", Boolean.FALSE);
//
//			if(e.getAttribute("http://reporting.pentaho.org/namespaces/engine/attributes/wizard", "aggregation-type")==null){
//				e.setAttribute(AttributeNames.Core.NAMESPACE, AttributeNames.Core.VALUE, m.getValue());
//			}
//
//			SaikuElementFormat tempFormat = (SaikuElementFormat) m.getElementFormat().clone();
//
//			TemplateUtils.mergeElementFormats(e.getStyle(), tempFormat);
//
//			//set a transient format
//			m.getElementFormat().setTempFormat(tempFormat);
//		}
//
//
//	}
//
//}
