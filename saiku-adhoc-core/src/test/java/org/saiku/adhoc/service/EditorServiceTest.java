package org.saiku.adhoc.service;

import java.util.ArrayList;
import org.apache.commons.discovery.log.SimpleLog;
import org.apache.commons.logging.Log;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.dto.DisplayName;
import org.saiku.adhoc.model.dto.ElementFormat;
import org.saiku.adhoc.model.dto.FilterValue;
import org.saiku.adhoc.model.dto.Position;
import org.saiku.adhoc.model.master.SaikuColumn;
import org.saiku.adhoc.model.master.SaikuElementFormat;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.server.datasource.ClassPathResourceCDAManager;
import org.saiku.adhoc.server.datasource.ICDAManager;
import org.saiku.adhoc.service.report.ReportGeneratorService;
import org.saiku.adhoc.service.repository.IMetadataService;
import org.saiku.adhoc.service.repository.PentahoMetadataService;
import junit.framework.*;

/**
 * The class <code>EditorServiceTest</code> contains tests for the class <code>{@link EditorService}</code>.
 * 
 * @author mg
 * @version $Revision: 1.0 $
 */
public class EditorServiceTest extends TestCase {
	/**
	 * Run the EditorService() constructor test.
	 */
	public void testEditorService_1()
		throws Exception {
		EditorService result = new EditorService();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the DisplayName addColumn(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddColumn_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		DisplayName result = fixture.addColumn(sessionId, category, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName addColumn(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddColumn_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		DisplayName result = fixture.addColumn(sessionId, category, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the void addFilter(String,String,String,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddFilter_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		int position = 1;

		fixture.addFilter(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addFilter(String,String,String,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddFilter_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		int position = 1;

		fixture.addFilter(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_4()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_5()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_6()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_7()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_8()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_9()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_10()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_11()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void addGroup(String,String,String,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testAddGroup_12()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String categoryId = "";
		String columnId = "";
		Position position = new Position();
		position.setPosition(new Integer(1));
		position.setUid("");

		fixture.addGroup(sessionId, categoryId, columnId, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void createNewModel(String,MetadataModelInfo) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testCreateNewModel_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		MetadataModelInfo modelInfo = new MetadataModelInfo();
		modelInfo.setModelId("");
		modelInfo.setJson((String) null);
		modelInfo.setDomainId("");

		fixture.createNewModel(sessionId, modelInfo);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void createNewModel(String,MetadataModelInfo) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testCreateNewModel_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		MetadataModelInfo modelInfo = new MetadataModelInfo();
		modelInfo.setModelId("");
		modelInfo.setJson((String) null);
		modelInfo.setDomainId("");

		fixture.createNewModel(sessionId, modelInfo);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the ICDAManager getCDAManager() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetCDAManager_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");

		ICDAManager result = fixture.getCDAManager();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the SaikuColumn getColumnConfig(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetColumnConfig_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		SaikuColumn result = fixture.getColumnConfig(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the SaikuColumn getColumnConfig(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetColumnConfig_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		SaikuColumn result = fixture.getColumnConfig(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the SaikuColumn getColumnConfig(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetColumnConfig_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		SaikuColumn result = fixture.getColumnConfig(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the SaikuColumn getColumnConfig(String,String,String,Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetColumnConfig_4()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String column = "";
		Integer position = new Integer(1);

		SaikuColumn result = fixture.getColumnConfig(sessionId, category, column, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_4()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_5()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_6()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_7()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_8()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_9()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_10()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_11()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_12()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_13()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_14()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_15()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the ElementFormat getElementFormat(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetElementFormat_16()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String id = "";

		ElementFormat result = fixture.getElementFormat(sessionId, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the String getModelJson(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetModelJson_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";

		String result = fixture.getModelJson(sessionId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the String getModelJson(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetModelJson_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";

		String result = fixture.getModelJson(sessionId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the String getModelJson(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetModelJson_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";

		String result = fixture.getModelJson(sessionId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the String getModelJson(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testGetModelJson_4()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";

		String result = fixture.getModelJson(sessionId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the void removeColumn(String,String,String,Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testRemoveColumn_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		Integer position = new Integer(1);

		fixture.removeColumn(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void removeColumn(String,String,String,Integer) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testRemoveColumn_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		Integer position = new Integer(1);

		fixture.removeColumn(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void removeFilter(String,String,String,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testRemoveFilter_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		int position = 1;

		fixture.removeFilter(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void removeFilter(String,String,String,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testRemoveFilter_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		int position = 1;

		fixture.removeFilter(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void removeGroup(String,String,String,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testRemoveGroup_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		int position = 1;

		fixture.removeGroup(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void removeGroup(String,String,String,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testRemoveGroup_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		int position = 1;

		fixture.removeGroup(sessionId, category, businessColumn, position);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setCDAManager(ICDAManager) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetCDAManager_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		ICDAManager manager = new ClassPathResourceCDAManager();

		fixture.setCDAManager(manager);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the DisplayName setColumnConfig(String,String,String,Integer,SaikuColumn) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetColumnConfig_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "CALCULATED";
		String businessColumn = "";
		Integer position = new Integer(1);
		SaikuColumn config = new SaikuColumn();
		config.setName("");
		config.setUid("");
		config.setId("");

		DisplayName result = fixture.setColumnConfig(sessionId, category, businessColumn, position, config);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setColumnConfig(String,String,String,Integer,SaikuColumn) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetColumnConfig_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		Integer position = new Integer(1);
		SaikuColumn config = new SaikuColumn();
		config.setName("");
		config.setUid("");

		DisplayName result = fixture.setColumnConfig(sessionId, category, businessColumn, position, config);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setColumnConfig(String,String,String,Integer,SaikuColumn) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetColumnConfig_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "CALCULATED";
		String businessColumn = "";
		Integer position = new Integer(1);
		SaikuColumn config = new SaikuColumn();
		config.setName("");
		config.setUid("");
		config.setId("NEW");

		DisplayName result = fixture.setColumnConfig(sessionId, category, businessColumn, position, config);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the void setColumnSort(String,String,String,Integer,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetColumnSort_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String column = "";
		Integer position = new Integer(1);
		String order = "";

		fixture.setColumnSort(sessionId, category, column, position, order);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_4()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_5()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_6()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_7()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_8()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_9()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the DisplayName setElementFormat(String,ElementFormat,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetElementFormat_10()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		ElementFormat format = new ElementFormat(new SaikuElementFormat(), "");
		String id = "";

		DisplayName result = fixture.setElementFormat(sessionId, format, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
		assertNotNull(result);
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_3()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_4()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_5()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_6()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_7()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setFilterValues(String,String,String,ArrayList<FilterValue>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetFilterValues_8()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String businessColumn = "";
		ArrayList<FilterValue> selection = new ArrayList();

		fixture.setFilterValues(sessionId, category, businessColumn, selection);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setGroupSort(String,String,String,Integer,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetGroupSort_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String category = "";
		String column = "";
		Integer position = new Integer(1);
		String order = "";

		fixture.setGroupSort(sessionId, category, column, position, order);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setMetadataService(IMetadataService) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetMetadataService_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		IMetadataService metadataService = new PentahoMetadataService();

		fixture.setMetadataService(metadataService);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setReportGeneratorService(ReportGeneratorService) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetReportGeneratorService_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		ReportGeneratorService reportGeneratorService = new ReportGeneratorService();

		fixture.setReportGeneratorService(reportGeneratorService);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setRowlimit(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetRowlimit_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "";
		String rowlimit = "";

		fixture.setRowlimit(sessionId, rowlimit);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setRowlimit(String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetRowlimit_2()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		String sessionId = "a";
		String rowlimit = "0";

		fixture.setRowlimit(sessionId, rowlimit);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Run the void setSessionHolder(WorkspaceSessionHolder) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public void testSetSessionHolder_1()
		throws Exception {
		EditorService fixture = new EditorService();
		fixture.setMetadataService(new PentahoMetadataService());
		fixture.setSessionHolder(new WorkspaceSessionHolder());
		fixture.setReportGeneratorService(new ReportGeneratorService());
		fixture.setCDAManager(new ClassPathResourceCDAManager());
		fixture.log = new SimpleLog("");
		WorkspaceSessionHolder sessionHolder = new WorkspaceSessionHolder();

		fixture.setSessionHolder(sessionHolder);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:539)
		//       at org.pentaho.platform.engine.core.system.PentahoSystem.get(PentahoSystem.java:521)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.getMetadataRepository(PentahoMetadataService.java:233)
		//       at org.saiku.adhoc.service.repository.PentahoMetadataService.<init>(PentahoMetadataService.java:71)
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @see TestCase#setUp()
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	protected void setUp()
		throws Exception {
		super.setUp();
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @see TestCase#tearDown()
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	protected void tearDown()
		throws Exception {
		super.tearDown();
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 31.01.12 01:39
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			// Run all of the tests
			junit.textui.TestRunner.run(EditorServiceTest.class);
		} else {
			// Run only the named tests
			TestSuite suite = new TestSuite("Selected tests");
			for (int i = 0; i < args.length; i++) {
				TestCase test = new EditorServiceTest();
				test.setName(args[i]);
				suite.addTest(test);
			}
			junit.textui.TestRunner.run(suite);
		}
	}
}