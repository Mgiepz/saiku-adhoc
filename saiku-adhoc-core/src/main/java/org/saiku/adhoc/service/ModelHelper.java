package org.saiku.adhoc.service;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuElement;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuLabel;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;
import org.saiku.adhoc.model.master.SaikuReportSettings;

/**
 * Helps find model elements
 * 
 * @author mg
 * 
 */
public class ModelHelper {

	public static SaikuGroup findGroupById(SaikuMasterModel model, String id) {

		List<SaikuGroup> groups = model.getGroups();
		for (SaikuGroup saikuGroup : groups) {
			if (saikuGroup.getLayoutId().equals(id)) {
				return saikuGroup;
			}
		}
		return null;
	}

	public static SaikuColumn findColumnById(SaikuMasterModel model, String id) {

		List<SaikuColumn> columns = model.getColumns();
		for (SaikuColumn saikuColumn : columns) {
			if (saikuColumn.getLayoutId().equals(id)) {
				return saikuColumn;
			}
		}
		return null;

	}

	public static SaikuLabel findLabelById(SaikuMasterModel model, String id) {

		List<SaikuGroup> groups = model.getGroups();
		for (SaikuGroup saikuGroup : groups) {
			List<SaikuLabel> groupFooterElements = saikuGroup.getGroupFooterElements();
			for (SaikuLabel saikuLabel : groupFooterElements) {
				if (saikuLabel.getLayoutId().equals(id)) {
					return saikuLabel;
				}
			}

		}

		List<SaikuLabel> pageHeaderElements = model.getPageHeaderElements();
		for (SaikuLabel saikuLabel : pageHeaderElements) {
			if (saikuLabel.getLayoutId().equals(id)) {
				return saikuLabel;
			}
		}

		List<SaikuLabel> pageFooterElements = model.getPageFooterElements();
		for (SaikuLabel saikuLabel : pageFooterElements) {
			if (saikuLabel.getLayoutId().equals(id)) {
				return saikuLabel;
			}
		}

		List<SaikuLabel> reportHeaderElements = model.getReportHeaderElements();
		for (SaikuLabel saikuLabel : reportHeaderElements) {
			if (saikuLabel.getLayoutId().equals(id)) {
				return saikuLabel;
			}
		}

		List<SaikuLabel> reportFooterElements = model.getReportFooterElements();
		for (SaikuLabel saikuLabel : reportFooterElements) {
			if (saikuLabel.getLayoutId().equals(id)) {
				return saikuLabel;
			}
		}

		List<SaikuLabel> reportSummaryElements = model.getReportSummaryElements();
		for (SaikuLabel saikuLabel : reportSummaryElements) {
			if (saikuLabel.getLayoutId().equals(id)) {
				return saikuLabel;
			}
		}

		return null;

	}

	public static void init(SaikuMasterModel masterModel, Domain domain, LogicalModel logicalModel, String sessionId) {

		masterModel.setSessionId(sessionId);
		masterModel.setDomainId(domain.getId());
		masterModel.setLogicalModelId(logicalModel.getId());
		
		if (masterModel.getSettings() == null) {
			masterModel.setSettings(new SaikuReportSettings());
		}

		if (masterModel.getClientModelSelection() == null) {
			masterModel.setColumns(new ArrayList<SaikuColumn>());
			masterModel.setGroups(new ArrayList<SaikuGroup>());
			masterModel.setParameters(new ArrayList<SaikuParameter>());
			masterModel.setSortColumns(new ArrayList<String>());
			masterModel.setReportHeaderElements(new ArrayList<SaikuLabel>());
			masterModel.setReportFooterElements(new ArrayList<SaikuLabel>());
			masterModel.setReportSummaryElements(new ArrayList<SaikuLabel>());
			masterModel.setPageHeaderElements(new ArrayList<SaikuLabel>());
			masterModel.setPageFooterElements(new ArrayList<SaikuLabel>());
		}
	}
}
