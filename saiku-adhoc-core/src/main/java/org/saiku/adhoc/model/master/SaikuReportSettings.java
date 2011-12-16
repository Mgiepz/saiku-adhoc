package org.saiku.adhoc.model.master;

public class SaikuReportSettings {
	
	private boolean disableDistinct;
	private int limit;
	
	private ReportTemplate reportTemplate;
	
	public SaikuReportSettings() {
		this.disableDistinct = false;
		this.limit = 100;
	}

	public void setDisableDistinct(boolean disableDistinct) {
		this.disableDistinct = disableDistinct;
	}
	public boolean isDisableDistinct() {
		return disableDistinct;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getLimit() {
		return limit;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

}
