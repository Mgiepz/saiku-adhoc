package org.saiku.adhoc.service.report.tasks;

import org.pentaho.reporting.engine.classic.core.ReportElement;

public interface UpdateTask {

	public void processElement(ReportElement e, int index);

}
