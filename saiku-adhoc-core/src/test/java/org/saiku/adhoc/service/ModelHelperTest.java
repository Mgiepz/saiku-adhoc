package org.saiku.adhoc.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuLabel;
import org.saiku.adhoc.model.master.SaikuMasterModel;

public class ModelHelperTest extends TestCase {
	/**
	 * Run the SaikuColumn findColumnById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindColumnById()
		throws Exception {
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		List<SaikuColumn> cols = new ArrayList<SaikuColumn>();
		SaikuColumn col = new SaikuColumn();
		String id = "test";
		col.setLayoutId(id);
		cols.add(col);
		model.setColumns(cols);

		SaikuColumn result = ModelHelper.findColumnById(model, id);

		assertNotNull(result);
	}
	
	/**
	 * Run the SaikuGroup findGroupById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindGroupById()
		throws Exception {
		
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		List<SaikuGroup> groups = new ArrayList<SaikuGroup>();
		SaikuGroup group = new SaikuGroup();
		String id = "test";
		group.setLayoutId(id);
		groups.add(group);
		model.setGroups(groups);

		SaikuGroup result = ModelHelper.findGroupById(model, id);

		assertNotNull(result);

	}
	
	/**
	 * Run the SaikuLabel findLabelById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindLabelById1()
		throws Exception {
		
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		List<SaikuGroup> groups = new ArrayList<SaikuGroup>();
		SaikuGroup group = new SaikuGroup();
		SaikuLabel label = new SaikuLabel();
		String id = "test";
		label.setLayoutId(id);
		group.getGroupFooterElements().add(label);
		groups.add(group);
		model.setGroups(groups);

		SaikuLabel result = ModelHelper.findLabelById(model, id);

		assertNotNull(result);
		
	}

	/**
	 * Run the SaikuLabel findLabelById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindLabelById2()
		throws Exception {
		
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		SaikuLabel label = new SaikuLabel();
		String id = "test";
		label.setLayoutId(id);
		model.getPageHeaderElements().add(label);

		SaikuLabel result = ModelHelper.findLabelById(model, id);

		assertNotNull(result);
		
	}

	/**
	 * Run the SaikuLabel findLabelById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindLabelById3()
		throws Exception {
		
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		SaikuLabel label = new SaikuLabel();
		String id = "test";
		label.setLayoutId(id);
		model.getPageFooterElements().add(label);

		SaikuLabel result = ModelHelper.findLabelById(model, id);

		assertNotNull(result);
		
	}
	
	/**
	 * Run the SaikuLabel findLabelById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindLabelById4()
		throws Exception {
		
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		SaikuLabel label = new SaikuLabel();
		String id = "test";
		label.setLayoutId(id);
		model.getReportHeaderElements().add(label);

		SaikuLabel result = ModelHelper.findLabelById(model, id);

		assertNotNull(result);
		
	}

	/**
	 * Run the SaikuLabel findLabelById(SaikuMasterModel,String) method test.
	 *
	 * @throws Exception
	 */
	public void testFindLabelById5()
		throws Exception {
		
		SaikuMasterModel model = new SaikuMasterModel();
		ModelHelper.init(model, null, null, null);
		
		SaikuLabel label = new SaikuLabel();
		String id = "test";
		label.setLayoutId(id);
		model.getReportFooterElements().add(label);

		SaikuLabel result = ModelHelper.findLabelById(model, id);

		assertNotNull(result);
		
	}

}