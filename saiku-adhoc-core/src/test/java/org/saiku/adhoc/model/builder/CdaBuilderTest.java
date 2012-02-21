package org.saiku.adhoc.model.builder;

import junit.framework.TestCase;

import org.saiku.adhoc.model.master.SaikuMasterModel;

public class CdaBuilderTest extends TestCase {
	
	private CdaBuilder builder;

	protected void setUp() throws Exception {
		super.setUp();
		
		builder = new CdaBuilder();
		
	}

	public void testBuild() {
		
		SaikuMasterModel smm = null;

		try {
			//builder.build(smm);
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

}
