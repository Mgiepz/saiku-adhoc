//package org.saiku.adhoc.model.transformation;
//
//import java.io.IOException;
//
//import org.pentaho.metadata.query.model.Query;
//import org.saiku.adhoc.TestHelper;
//import org.saiku.adhoc.exceptions.SaikuAdhocException;
//import org.saiku.adhoc.model.master.SaikuMasterModel;
//
//import junit.framework.TestCase;
//
//public class TransModelToQueryTest extends TestCase {
//	
//	TransModelToQuery trans;
//
//	protected void setUp() throws Exception {
//		super.setUp();
//		
//		this.trans = new TransModelToQuery();
//		
//	}
//
//	public void testDoIt() throws SaikuAdhocException, IOException {
//		
//		SaikuMasterModel fixture = TestHelper.loadModel("test1.adhoc");
//		
//		fixture.init(domain, model, sessionId, manager, reportGeneratorService);
//		
//		//fixture.deriveModels();
//	
//		Query result = this.trans.doIt(fixture);
//		
//		assertNotNull(result);
//
//	}
//
//}
