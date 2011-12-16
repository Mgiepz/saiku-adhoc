package org.saiku.adhoc.server.reporting;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.reporting.engine.classic.core.AttributeNames;
import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.metadata.ReportProcessTaskRegistry;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfPageableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.plaintext.PlainTextPageableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.xml.XmlPageableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.csv.CSVTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.rtf.RTFTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelTableModule;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xml.XmlTableModule;
import org.pentaho.reporting.engine.classic.core.parameters.DefaultParameterContext;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterContext;
import org.pentaho.reporting.engine.classic.core.parameters.ParameterDefinitionEntry;
import org.pentaho.reporting.engine.classic.core.parameters.ValidationMessage;
import org.pentaho.reporting.engine.classic.core.parameters.ValidationResult;
import org.pentaho.reporting.engine.classic.core.util.ReportParameterValues;
import org.pentaho.reporting.engine.classic.extensions.modules.java14print.Java14PrintUtil;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.xmlns.common.ParserUtil;
import org.pentaho.reporting.platform.plugin.ParameterXmlContentHandler;
import org.pentaho.reporting.platform.plugin.ReportContentUtil;
import org.pentaho.reporting.platform.plugin.cache.NullReportCache;
import org.pentaho.reporting.platform.plugin.cache.ReportCache;
import org.pentaho.reporting.platform.plugin.cache.ReportCacheKey;
import org.pentaho.reporting.platform.plugin.messages.Messages;
import org.pentaho.reporting.platform.plugin.output.ReportOutputHandler;
import org.saiku.adhoc.service.report.ReportGeneratorService;

public class SaikuReportingComponent {
    private static final Log log = LogFactory
    .getLog(ReportGeneratorService.class);
    
    boolean paginateOutput = false;

    private String outputTarget;

    private String defaultOutputTarget;

    private String outputType;

    private MasterReport report;
    
    private int acceptedPage;

    private boolean print;

    private String printer;

    private OutputStream outputStream;

    private int pageCount;

    public static final String OUTPUT_TYPE = "output-type"; //$NON-NLS-1$

    public static final String MIME_TYPE_HTML = "text/html"; //$NON-NLS-1$

    public static final String MIME_TYPE_EMAIL = "mime-message/text/html"; //$NON-NLS-1$

    public static final String MIME_TYPE_PDF = "application/pdf"; //$NON-NLS-1$

    public static final String MIME_TYPE_XLS = "application/vnd.ms-excel"; //$NON-NLS-1$

    public static final String MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; //$NON-NLS-1$

    public static final String MIME_TYPE_RTF = "application/rtf"; //$NON-NLS-1$

    public static final String MIME_TYPE_CSV = "text/csv"; //$NON-NLS-1$

    public static final String MIME_TYPE_TXT = "text/plain"; //$NON-NLS-1$

    public static final String MIME_TYPE_XML = "application/xml"; //$NON-NLS-1$

    public static final String MIME_TYPE_PNG = "image/png"; //$NON-NLS-1$

    public static final String PNG_EXPORT_TYPE = "pageable/X-AWT-Graphics;image-type=png";

    public static final String XLS_WORKBOOK_PARAM = "workbook"; //$NON-NLS-1$

    private Map<String, Object> inputs = Collections.emptyMap();

    private boolean dashboardMode;

    private Boolean useContentRepository = false;

    public static final String REPORTHTML_CONTENTHANDLER_PATTERN = "content-handler-pattern"; //$NON-NLS-1$

    public static final String OUTPUT_TARGET = "output-target"; //$NON-NLS-1$

    public static final String REPORT_DEFINITION_INPUT = "report-definition"; //$NON-NLS-1$

    public static final String USE_CONTENT_REPOSITORY = "useContentRepository"; //$NON-NLS-1$

    public static final String PAGINATE_OUTPUT = "paginate"; //$NON-NLS-1$

    public static final String ACCEPTED_PAGE = "accepted-page"; //$NON-NLS-1$

    public static final String PRINT = "print"; //$NON-NLS-1$

    public static final String PRINTER_NAME = "printer-name"; //$NON-NLS-1$

    public static final String DASHBOARD_MODE = "dashboard-mode"; //$NON-NLS-1$

    public static final String REPORTGENERATE_YIELDRATE = "yield-rate"; //$NON-NLS-1$

    public SaikuReportingComponent(){
        pageCount = -1;
        print = false;
    }
    
    
    
    public int getAcceptedPage() {
        return acceptedPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean execute() throws Exception {
        final MasterReport report = getReport();

        try {
            final DefaultParameterContext parameterContext = new DefaultParameterContext(report);
            // open parameter context
            final ValidationResult vr = applyInputsToReportParameters(parameterContext, null);
            if (vr.isEmpty() == false) {
                return false;
            }
            parameterContext.close();

            if (isPrint()) {
                // handle printing
                // basic logic here is: get the default printer, attempt to resolve the user specified printer, default
                // back as needed
                PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
                if (StringUtils.isEmpty(getPrinter()) == false) {
                    final PrintService[] services = PrintServiceLookup.lookupPrintServices(
                            DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
                    for (final PrintService service : services) {
                        if (service.getName().equals(printer)) {
                            printService = service;
                        }
                    }
                    if ((printer == null) && (services.length > 0)) {
                        printService = services[0];
                    }
                }
                Java14PrintUtil.printDirectly(report, printService);
                return true;
            }

            final String outputType = computeEffectiveOutputTarget();
            final ReportOutputHandler reportOutputHandler = createOutputHandlerForOutputType(outputType);
            if (reportOutputHandler == null) {
                log.warn(Messages.getInstance().getString("ReportPlugin.warnUnprocessableRequest", outputType));
                return false;
            }
            synchronized (reportOutputHandler.getReportLock()) {
                try {
                    pageCount = reportOutputHandler.generate(report, acceptedPage, outputStream, getYieldRate());
                    return pageCount != -1;
                    } finally {
                    reportOutputHandler.close();
                }
            }
        } catch (Throwable t) {
            log.error(Messages.getInstance().getString("ReportPlugin.executionFailed"), t); //$NON-NLS-1$
        }
        // lets not pretend we were successfull, if the export type was not a valid one.
        return false;
    }

    public ValidationResult applyInputsToReportParameters(final ParameterContext context,
            ValidationResult validationResult) throws IOException, ResourceException {
        if (validationResult == null) {
            validationResult = new ValidationResult();
        }
        // apply inputs to report
        if (inputs != null) {
            final MasterReport report = getReport();
            final ParameterDefinitionEntry[] params = report.getParameterDefinition().getParameterDefinitions();
            final ReportParameterValues parameterValues = report.getParameterValues();
            for (final ParameterDefinitionEntry param : params) {
                final String paramName = param.getName();
                try {
                    final Object computedParameter = ReportContentUtil.computeParameterValue(context, param,
                            inputs.get(paramName));
                    parameterValues.put(param.getName(), computedParameter);
                    if (log.isInfoEnabled()) {
                        log.info(Messages.getInstance().getString("ReportPlugin.infoParameterValues", paramName,
                                String.valueOf(inputs.get(paramName)), String.valueOf(computedParameter)));
                    }
                } catch (Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn(Messages.getInstance().getString("ReportPlugin.logErrorParametrization"), e);
                    }
                    validationResult.addError(paramName, new ValidationMessage(e.getMessage()));
                }
            }
        }
        return validationResult;
    }

    protected int getYieldRate() {
        final Object yieldRate = getInput(REPORTGENERATE_YIELDRATE, null);
        if (yieldRate instanceof Number) {
            final Number n = (Number) yieldRate;
            if (n.intValue() < 1) {
                return 0;
            }
            return n.intValue();
        }
        return 0;
    }

    public boolean isPrint() {
        return print;
    }

    public String getPrinter() {
        return printer;
    }

    public void setAcceptedPage(final int acceptedPage) {
        this.acceptedPage = acceptedPage;
    }

    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setReportDefinitionInputStream(final InputStream reportDefinitionInputStream) {
    }

    public Map<String, Object> getInputs() {
        if (inputs != null) {
            return Collections.unmodifiableMap(inputs);
        }
        return Collections.emptyMap();
    }

    public void setInputs(final Map<String, Object> inputs) {
        if (inputs == null) {
            this.inputs = Collections.emptyMap();
            return;
        }

        this.inputs = inputs;
        if (inputs.containsKey(OUTPUT_TYPE)) {
            setOutputType(String.valueOf(inputs.get(OUTPUT_TYPE)));
        }
        if (inputs.containsKey(OUTPUT_TARGET)) {
            setOutputTarget(String.valueOf(inputs.get(OUTPUT_TARGET)));
        }
        if (inputs.containsKey(REPORT_DEFINITION_INPUT)) {
            setReportDefinitionInputStream((InputStream) inputs.get(REPORT_DEFINITION_INPUT));
        }
        if (inputs.containsKey(USE_CONTENT_REPOSITORY)) {
            setUseContentRepository((Boolean) inputs.get(USE_CONTENT_REPOSITORY));
        }
        if (inputs.containsKey(PAGINATE_OUTPUT)) {
            paginateOutput = "true".equals(String.valueOf(inputs.get(PAGINATE_OUTPUT))); //$NON-NLS-1$
        }
        if (inputs.containsKey(ACCEPTED_PAGE)) {
            acceptedPage = ParserUtil.parseInt(String.valueOf(inputs.get(ACCEPTED_PAGE)), -1); //$NON-NLS-1$
        }
        if (inputs.containsKey(PRINT)) {
            print = "true".equals(String.valueOf(inputs.get(PRINT))); //$NON-NLS-1$
        }
        if (inputs.containsKey(PRINTER_NAME)) {
            printer = String.valueOf(inputs.get(PRINTER_NAME));
        }
        if (inputs.containsKey(DASHBOARD_MODE)) {
            dashboardMode = "true".equals(String.valueOf(inputs.get(DASHBOARD_MODE))); //$NON-NLS-1$
        }
    }

    public void setOutputType(final String outputType) {
        this.outputType = outputType;
    }

    protected Object getInput(final String key, final Object defaultValue) {
        if (inputs != null) {
            final Object input = inputs.get(key);
            if (input != null) {
                return input;
            }
        }
        return defaultValue;
    }

    public boolean getUseContentRepository() {
        return useContentRepository;
    }

    public void setUseContentRepository(final Boolean useContentRepository) {
        this.useContentRepository = useContentRepository;
    }

    public boolean isDashboardMode() {
        return dashboardMode;
    }

    public void setDashboardMode(final boolean dashboardMode) {
        this.dashboardMode = dashboardMode;
    }

    public boolean isPaginateOutput() {
        return paginateOutput;
    }

    public void setPaginateOutput(final boolean paginateOutput) {
        this.paginateOutput = paginateOutput;
    }

    public String getOutputType() {
        return outputType;
    }

    public MasterReport getReport() {
        return report;
    }

    public void setReport(MasterReport report) {
        this.report = report;
    }

    private boolean isValidOutputType(final String outputType) {
        if (PNG_EXPORT_TYPE.equals(outputType)) {
            return true;
        }
        return ReportProcessTaskRegistry.getInstance().isExportTypeRegistered(outputType);
    }

    private String mapOutputTypeToOutputTarget(final String outputType) {
        // if the user has given a mime-type instead of a output-target, lets map it to the "best" choice. If the
        // user wanted full control, he would have used the output-target property instead.
        if (MIME_TYPE_CSV.equals(outputType)) {
            return CSVTableModule.TABLE_CSV_STREAM_EXPORT_TYPE;
        }
        if (MIME_TYPE_HTML.equals(outputType)) {
            if (isPaginateOutput()) {
                return HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE;
            }
            return HtmlTableModule.TABLE_HTML_STREAM_EXPORT_TYPE;
        }
        if (MIME_TYPE_XML.equals(outputType)) {
            if (isPaginateOutput()) {
                return XmlTableModule.TABLE_XML_EXPORT_TYPE;
            }
            return XmlPageableModule.PAGEABLE_XML_EXPORT_TYPE;
        }
        if (MIME_TYPE_PDF.equals(outputType)) {
            return PdfPageableModule.PDF_EXPORT_TYPE;
        }
        if (MIME_TYPE_RTF.equals(outputType)) {
            return RTFTableModule.TABLE_RTF_FLOW_EXPORT_TYPE;
        }
        if (MIME_TYPE_XLS.equals(outputType)) {
            return ExcelTableModule.EXCEL_FLOW_EXPORT_TYPE;
        }
        if (MIME_TYPE_XLSX.equals(outputType)) {
            return ExcelTableModule.XLSX_FLOW_EXPORT_TYPE;
        }
        if (MIME_TYPE_EMAIL.equals(outputType)) {
            return MIME_TYPE_EMAIL;
        }
        if (MIME_TYPE_TXT.equals(outputType)) {
            return PlainTextPageableModule.PLAINTEXT_EXPORT_TYPE;
        }

        if ("pdf".equalsIgnoreCase(outputType)) //$NON-NLS-1$
        {
            log.warn(Messages.getInstance().getString("ReportPlugin.warnDeprecatedPDF"));
            return PdfPageableModule.PDF_EXPORT_TYPE;
        } else if ("html".equalsIgnoreCase(outputType)) //$NON-NLS-1$
        {
            log.warn(Messages.getInstance().getString("ReportPlugin.warnDeprecatedHTML"));
            if (isPaginateOutput()) {
                return HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE;
            }
            return HtmlTableModule.TABLE_HTML_STREAM_EXPORT_TYPE;
        } else if ("csv".equalsIgnoreCase(outputType)) //$NON-NLS-1$
        {
            log.warn(Messages.getInstance().getString("ReportPlugin.warnDeprecatedCSV"));
            return CSVTableModule.TABLE_CSV_STREAM_EXPORT_TYPE;
        } else if ("rtf".equalsIgnoreCase(outputType)) //$NON-NLS-1$
        {
            log.warn(Messages.getInstance().getString("ReportPlugin.warnDeprecatedRTF"));
            return RTFTableModule.TABLE_RTF_FLOW_EXPORT_TYPE;
        } else if ("xls".equalsIgnoreCase(outputType)) //$NON-NLS-1$
        {
            log.warn(Messages.getInstance().getString("ReportPlugin.warnDeprecatedXLS"));
            return ExcelTableModule.EXCEL_FLOW_EXPORT_TYPE;
        } else if ("txt".equalsIgnoreCase(outputType)) //$NON-NLS-1$
        {
            log.warn(Messages.getInstance().getString("ReportPlugin.warnDeprecatedTXT"));
            return PlainTextPageableModule.PLAINTEXT_EXPORT_TYPE;
        }
        return null;
    }

    private String computeEffectiveOutputTarget() throws IOException, ResourceException {
        final MasterReport report = getReport();
        if (Boolean.TRUE.equals(report.getAttribute(AttributeNames.Core.NAMESPACE,
                AttributeNames.Core.LOCK_PREFERRED_OUTPUT_TYPE))) {
            // preferred output type is one of the engine's output-target identifiers. It is not a mime-type string.
            // The engine supports multiple subformats per mime-type (example HTML: zipped/streaming/flow/pageable)
            // The mime-mapping would be inaccurate.
            final Object preferredOutputType = report.getAttribute(AttributeNames.Core.NAMESPACE,
                    AttributeNames.Core.PREFERRED_OUTPUT_TYPE);
            if (preferredOutputType != null) {
                final String preferredOutputTypeString = String.valueOf(preferredOutputType);
                if (isValidOutputType(preferredOutputTypeString)) {
                    // if it is a recognized process-type, then fine, return it.
                    return preferredOutputTypeString;
                }

                final String mappedLegacyType = mapOutputTypeToOutputTarget(preferredOutputTypeString);
                if (mappedLegacyType != null) {
                    log.warn(Messages.getInstance().getString("ReportPlugin.warnLegacyLockedOutput",
                            preferredOutputTypeString));
                    return mappedLegacyType;
                }

                log.warn(Messages.getInstance().getString("ReportPlugin.warnInvalidLockedOutput",
                        preferredOutputTypeString));
            }
        }

        final String outputTarget = getOutputTarget();
        if (outputTarget != null) {
            if (isValidOutputType(outputTarget) == false) {
                log.warn(Messages.getInstance().getString("ReportPlugin.warnInvalidOutputTarget", outputTarget));
            }
            // if a engine-level output target is given, use it as it is. We can assume that the user knows how to
            // map from that to a real mime-type.
            return outputTarget;
        }

        final String mappingFromParams = mapOutputTypeToOutputTarget(getOutputType());
        if (mappingFromParams != null) {
            return mappingFromParams;
        }

        // if nothing is specified explicity, we may as well ask the report what it prefers..
        final Object preferredOutputType = report.getAttribute(AttributeNames.Core.NAMESPACE,
                AttributeNames.Core.PREFERRED_OUTPUT_TYPE);
        if (preferredOutputType != null) {
            final String preferredOutputTypeString = String.valueOf(preferredOutputType);
            if (isValidOutputType(preferredOutputTypeString)) {
                return preferredOutputTypeString;
            }

            final String mappedLegacyType = mapOutputTypeToOutputTarget(preferredOutputTypeString);
            if (mappedLegacyType != null) {
                log.warn(Messages.getInstance().getString("ReportPlugin.warnLegacyPreferredOutput",
                        preferredOutputTypeString));
                return mappedLegacyType;
            }

            log.warn(Messages.getInstance().getString("ReportPlugin.warnInvalidPreferredOutput",
                    preferredOutputTypeString, getDefaultOutputTarget()));
            return getDefaultOutputTarget();
        }

        if (StringUtils.isEmpty(getOutputTarget()) == false || StringUtils.isEmpty(getOutputType()) == false) {
            // if you have come that far, it means you really messed up. Sorry, this error is not a error caused
            // by our legacy code - it is more likely that you just entered values that are totally wrong.
            log.error(Messages.getInstance().getString("ReportPlugin.warnInvalidOutputType", getOutputType(),
                    getDefaultOutputTarget()));
        }
        return getDefaultOutputTarget();
    }

    public String getDefaultOutputTarget() {
        return defaultOutputTarget;
    }

    public void setDefaultOutputTarget(final String defaultOutputTarget) {
        if (defaultOutputTarget == null) {
            throw new NullPointerException();
        }
        this.defaultOutputTarget = defaultOutputTarget;
    }

    public String getOutputTarget() {
        return outputTarget;
    }

    public void setOutputTarget(final String outputTarget) {
        this.outputTarget = outputTarget;
    }

    protected String getViewerSessionId() {
        if (inputs == null) {
            return null;
        }
        final Object o = inputs.get(ParameterXmlContentHandler.SYS_PARAM_SESSION_ID);
        if (o instanceof String) {
            return String.valueOf(o);
        }
        return null;
    }

    protected ReportOutputHandler createOutputHandlerForOutputType(final String outputType) throws IOException {
        if (inputs == null) {
            throw new IllegalStateException("Inputs are null, this component did not validate properly");
        }

        final ReportCacheKey reportCacheKey = new ReportCacheKey(getViewerSessionId(), inputs);
        ReportCache cache = new NullReportCache();
        // if (Boolean.FALSE.equals(attribute))
        // {
        // cache = new NullReportCache();
        // }
        // else
        // {
        // cache = PentahoSystem.get(ReportCache.class);
        // if (cache == null)
        // {
        // cache = new DefaultReportCache();
        // }
        // final ReportOutputHandler outputHandler = cache.get(reportCacheKey);
        // if (outputHandler != null)
        // {
        // return outputHandler;
        // }
        // }

        final ReportOutputHandler reportOutputHandler;

        if (HtmlTableModule.TABLE_HTML_PAGE_EXPORT_TYPE.equals(outputType)) {
            if (dashboardMode) {
                report.getReportConfiguration().setConfigProperty(HtmlTableModule.BODY_FRAGMENT, "true");
            }
            if (useContentRepository) {
                // use the content repository
                final Configuration globalConfig = ClassicEngineBoot.getInstance().getGlobalConfig();
                final String contentHandlerPattern = (String) getInput(REPORTHTML_CONTENTHANDLER_PATTERN,
                        globalConfig.getConfigProperty("org.pentaho.web.resource.ContentHandler")); //$NON-NLS-1$
                reportOutputHandler = new PageableContentRepoHTMLOutputOver(contentHandlerPattern);
            } else {
                // don't use the content repository
                //final Configuration globalConfig = ClassicEngineBoot.getInstance().getGlobalConfig();
                /*
                 * String contentHandlerPattern = PentahoRequestContextHolder.getRequestContext().getContextPath();
                 * contentHandlerPattern += (String) getInput(REPORTHTML_CONTENTHANDLER_PATTERN,
                 * globalConfig.getConfigProperty("org.pentaho.web.ContentHandler"));
                 *///$NON-NLS-1$
                String contentHandlerPattern = "/saiku-adhoc-webapp";
                reportOutputHandler = new PageableHTMLOutputOver(contentHandlerPattern);
            }
        }
        // else if (HtmlTableModule.TABLE_HTML_STREAM_EXPORT_TYPE.equals(outputType))
        // {
        // if (dashboardMode)
        // {
        // report.getReportConfiguration().setConfigProperty(HtmlTableModule.BODY_FRAGMENT, "true");
        // }
        // if (useContentRepository)
        // {
        // // use the content repository
        // final Configuration globalConfig = ClassicEngineBoot.getInstance().getGlobalConfig();
        // final String contentHandlerPattern = (String) getInput(REPORTHTML_CONTENTHANDLER_PATTERN,
        //            globalConfig.getConfigProperty("org.pentaho.web.resource.ContentHandler")); //$NON-NLS-1$
        // reportOutputHandler = new StreamContentRepoHtmlOutput(contentHandlerPattern);
        // }
        // else
        // {
        // final Configuration globalConfig = ClassicEngineBoot.getInstance().getGlobalConfig();
        // String contentHandlerPattern = PentahoRequestContextHolder.getRequestContext().getContextPath();
        // contentHandlerPattern += (String) getInput(REPORTHTML_CONTENTHANDLER_PATTERN,
        //            globalConfig.getConfigProperty("org.pentaho.web.ContentHandler")); //$NON-NLS-1$
        // // don't use the content repository
        // reportOutputHandler = new StreamHtmlOutput(contentHandlerPattern);
        // }
        // }
        // else if (PNG_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new PNGOutput();
        // }
        // else if (XmlPageableModule.PAGEABLE_XML_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new XmlPageableOutput();
        // }
        // else if (XmlTableModule.TABLE_XML_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new XmlTableOutput();
        // }
        // else if (PdfPageableModule.PDF_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new PDFOutput();
        // }
        // else if (ExcelTableModule.EXCEL_FLOW_EXPORT_TYPE.equals(outputType))
        // {
        // final InputStream templateInputStream = (InputStream) getInput(XLS_WORKBOOK_PARAM, null);
        // reportOutputHandler = new XLSOutput(templateInputStream);
        // }
        // else if (ExcelTableModule.XLSX_FLOW_EXPORT_TYPE.equals(outputType))
        // {
        // final InputStream templateInputStream = (InputStream) getInput(XLS_WORKBOOK_PARAM, null);
        // reportOutputHandler = new XLSXOutput(templateInputStream);
        // }
        // else if (CSVTableModule.TABLE_CSV_STREAM_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new CSVOutput();
        // }
        // else if (RTFTableModule.TABLE_RTF_FLOW_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new RTFOutput();
        // }
        // else if (MIME_TYPE_EMAIL.equals(outputType))
        // {
        // reportOutputHandler = new EmailOutput();
        // }
        // else if (PlainTextPageableModule.PLAINTEXT_EXPORT_TYPE.equals(outputType))
        // {
        // reportOutputHandler = new PlainTextOutput();
        // }
        else {
            return null;
        }
        org.pentaho.reporting.platform.plugin.output.ReportOutputHandler roh;
        roh = (org.pentaho.reporting.platform.plugin.output.ReportOutputHandler) reportOutputHandler;
        return cache.put(reportCacheKey, roh);
    }
}
