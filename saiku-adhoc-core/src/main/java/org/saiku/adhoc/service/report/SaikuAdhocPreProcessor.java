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

package org.saiku.adhoc.service.report;

import java.awt.Color;
import java.awt.Image;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.reporting.engine.classic.core.AbstractReportDefinition;
import org.pentaho.reporting.engine.classic.core.AttributeNames;
import org.pentaho.reporting.engine.classic.core.Band;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.Group;
import org.pentaho.reporting.engine.classic.core.GroupBody;
import org.pentaho.reporting.engine.classic.core.GroupFooter;
import org.pentaho.reporting.engine.classic.core.GroupHeader;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.MetaAttributeNames;
import org.pentaho.reporting.engine.classic.core.RelationalGroup;
import org.pentaho.reporting.engine.classic.core.ReportElement;
import org.pentaho.reporting.engine.classic.core.ReportPreProcessor;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.Section;
import org.pentaho.reporting.engine.classic.core.SubGroupBody;
import org.pentaho.reporting.engine.classic.core.SubReport;
import org.pentaho.reporting.engine.classic.core.filter.types.ContentFieldType;
import org.pentaho.reporting.engine.classic.core.filter.types.DateFieldType;
import org.pentaho.reporting.engine.classic.core.filter.types.LabelType;
import org.pentaho.reporting.engine.classic.core.filter.types.MessageType;
import org.pentaho.reporting.engine.classic.core.filter.types.NumberFieldType;
import org.pentaho.reporting.engine.classic.core.filter.types.TextFieldType;
import org.pentaho.reporting.engine.classic.core.function.ProcessingContext;
import org.pentaho.reporting.engine.classic.core.function.StructureFunction;
import org.pentaho.reporting.engine.classic.core.metadata.ElementType;
import org.pentaho.reporting.engine.classic.core.states.datarow.DefaultFlowController;
import org.pentaho.reporting.engine.classic.core.style.BandStyleKeys;
import org.pentaho.reporting.engine.classic.core.style.BorderStyle;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleKeys;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleSheet;
import org.pentaho.reporting.engine.classic.core.wizard.AutoGeneratorUtility;
import org.pentaho.reporting.engine.classic.core.wizard.DataAttributes;
import org.pentaho.reporting.engine.classic.core.wizard.DefaultDataAttributeContext;
import org.pentaho.reporting.engine.classic.core.wizard.RelationalAutoGeneratorPreProcessor;
import org.pentaho.reporting.engine.classic.wizard.WizardOverrideFormattingFunction;
import org.pentaho.reporting.engine.classic.wizard.WizardProcessorUtil;
import org.pentaho.reporting.engine.classic.wizard.model.DetailFieldDefinition;
import org.pentaho.reporting.engine.classic.wizard.model.FieldDefinition;
import org.pentaho.reporting.engine.classic.wizard.model.GroupDefinition;
import org.pentaho.reporting.engine.classic.wizard.model.GroupType;
import org.pentaho.reporting.engine.classic.wizard.model.Length;
import org.pentaho.reporting.engine.classic.wizard.model.RootBandDefinition;
import org.pentaho.reporting.engine.classic.wizard.model.WizardSpecification;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.saiku.adhoc.model.master.SaikuGroup;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.service.report.tasks.SaikuUpdateDetailsHeaderTask;
import org.saiku.adhoc.service.report.tasks.SaikuUpdateDetailsTask;
import org.saiku.adhoc.service.report.tasks.SaikuUpdateFooterTask;
import org.saiku.adhoc.service.report.tasks.SaikuUpdateGroupHeaderTask;
import org.saiku.adhoc.service.report.tasks.SaikuUpdateMessagesTask;
import org.saiku.adhoc.service.report.tasks.UpdateTask;

/**
 * @author mgiepz
 *
 */
public class SaikuAdhocPreProcessor implements ReportPreProcessor {
	private Log log = LogFactory.getLog(SaikuAdhocPreProcessor.class);

	private String RPT_HEADER_MSG = "rpt-rhd-";
	private String PAGE_HEADER_MSG = "rpt-phd-";
	private String RPT_FOOTER_MSG = "rpt-rft-";
	private String PAGE_FOOTER_MSG = "rpt-pft-";

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private SaikuMasterModel model;
	private AbstractReportDefinition definition;
	private DefaultFlowController flowController;
	private WizardSpecification wizardSpecification;

	private DefaultDataAttributeContext attributeContext;

	public SaikuAdhocPreProcessor() {
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void setSaikuMasterModel(SaikuMasterModel model) {
		this.model = model;

	}

	public MasterReport performPreProcessing(final MasterReport definition,
			final DefaultFlowController flowController)
	throws ReportProcessingException {

		MasterReport processedDefinition;
		try {
			processedDefinition = (MasterReport) performCustomWizardPreProcessing(
					definition, flowController, definition.getResourceManager());

			return performSaikuPreProcessing(processedDefinition);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return null;

	}

	/*
	 * Not used in Saiku yet (non-Javadoc)
	 *
	 * @see org.pentaho.reporting.engine.classic.core.ReportPreProcessor#
	 * performPreProcessing(org.pentaho.reporting.engine.classic.core.SubReport,
	 * org
	 * .pentaho.reporting.engine.classic.core.states.datarow.DefaultFlowController
	 * )
	 */
	public SubReport performPreProcessing(final SubReport definition,
			final DefaultFlowController flowController)
	throws ReportProcessingException {
		return definition;
	}

	/**
	 * This does it differently from the usual wizard processor. it does not
	 * refresh but always roll again
	 *
	 * @param definition
	 * @param flowController
	 * @param resourceManager
	 * @return
	 * @throws ReportProcessingException
	 * @throws CloneNotSupportedException
	 */
	protected AbstractReportDefinition performCustomWizardPreProcessing(
			final AbstractReportDefinition definition,
			final DefaultFlowController flowController,
			final ResourceManager resourceManager)
	throws ReportProcessingException, CloneNotSupportedException {
		try {
			this.wizardSpecification = WizardProcessorUtil
			.loadWizardSpecification(definition, resourceManager);
			if (wizardSpecification == null) {
				return definition;
			}

			final StructureFunction[] functions = definition
			.getStructureFunctions();
			boolean hasOverrideFunction = false;
			for (int i = 0; i < functions.length; i++) {
				final StructureFunction function = functions[i];
				if (function instanceof WizardOverrideFormattingFunction) {
					hasOverrideFunction = true;
					break;
				}
			}
			if (hasOverrideFunction == false) {
				definition
				.addStructureFunction(new WizardOverrideFormattingFunction());
			}

			final ProcessingContext reportContext = flowController
			.getReportContext();
			this.definition = definition;
			this.flowController = flowController;
			this.attributeContext = new DefaultDataAttributeContext(
					reportContext.getOutputProcessorMetaData(), reportContext
					.getResourceBundleFactory().getLocale());

			// Always generate, never Refresh ;)
			setupWizardRelationalGroups();
			setupWizardDetails();

			return definition;

		} finally {
			this.wizardSpecification = null;
			this.definition = null;
			this.flowController = null;
			this.attributeContext = null;
		}

	}

	/**
	 * @throws ReportProcessingException
	 * @throws CloneNotSupportedException
	 */
	private void setupWizardRelationalGroups()
	throws ReportProcessingException, CloneNotSupportedException {
		final Group rootgroup = definition.getRootGroup();
		RelationalGroup group;
		if (rootgroup instanceof RelationalGroup == false) {
			group = null;
		} else {
			group = (RelationalGroup) rootgroup;
		}

		final RelationalGroup template = findInnermostRelationalGroup(definition);

		final GroupDefinition[] groupDefinitions = wizardSpecification
		.getGroupDefinitions();
		for (int i = 0; i < groupDefinitions.length; i++) {
			final GroupDefinition groupDefinition = groupDefinitions[i];
			final GroupType type = groupDefinition.getGroupType();
			if (type != null && GroupType.RELATIONAL.equals(type) == false) {
				continue;
			}

			if (group == null) {
				// create a new group and insert it at the end
				final RelationalGroup relationalGroup;
				if (template != null) {
					relationalGroup = (RelationalGroup) template.derive();
				} else {
					relationalGroup = new RelationalGroup();
				}

				if (groupDefinition.getGroupName() != null) {
					relationalGroup.setName(groupDefinition.getGroupName());
				}
				configureRelationalGroup(relationalGroup, groupDefinition, i);
				insertGroup(relationalGroup);
			} else {
				// modify the existing group
				configureRelationalGroup(group, groupDefinition, i);

				final GroupBody body = group.getBody();
				if (body instanceof SubGroupBody) {
					final SubGroupBody sgBody = (SubGroupBody) body;
					if (sgBody.getGroup() instanceof RelationalGroup) {
						group = (RelationalGroup) sgBody.getGroup();
					} else {
						group = null;
					}
				} else {
					group = null;
				}
			}
		}
		// Remove any group bands are not being used ie. groups with no fields
		removedUnusedTemplateGroups(groupDefinitions.length);
	}

	protected void setupWizardDetails() throws ReportProcessingException {
		final DetailFieldDefinition[] detailFieldDefinitions = wizardSpecification
		.getDetailFieldDefinitions();
		if (detailFieldDefinitions.length == 0) {
			if (wizardSpecification.isAutoGenerateDetails()) {
				final RelationalAutoGeneratorPreProcessor generatorPreProcessor = new RelationalAutoGeneratorPreProcessor();
				if (definition instanceof MasterReport) {
					generatorPreProcessor.performPreProcessing(
							(MasterReport) definition, flowController);
				} else if (definition instanceof SubReport) {
					generatorPreProcessor.performPreProcessing(
							(SubReport) definition, flowController);
				}
			}
			return;
		}

		definition.getDetailsHeader().setRepeat(true);
		definition.getDetailsFooter().setRepeat(true);

		final Band detailsHeader = AutoGeneratorUtility
		.findGeneratedContent(definition.getDetailsHeader());
		final Band detailsFooter = AutoGeneratorUtility
		.findGeneratedContent(definition.getDetailsFooter());
		final Band itemBand = AutoGeneratorUtility
		.findGeneratedContent(definition.getItemBand());

		if (itemBand == null) {
			return;
		}

		
		/*
		 * "PERCENTAGE"
		 * 
		 * Hier gehts ab mit der spaltenbreite
		 */
		
		final Float[] widthSpecs = new Float[detailFieldDefinitions.length];
		for (int i = 0; i < detailFieldDefinitions.length; i++) {
			final DetailFieldDefinition fieldDefinition = detailFieldDefinitions[i];
			final Length length = fieldDefinition.getWidth();
			if (length == null) {
				continue;
			}
			widthSpecs[i] = length.getNormalizedValue();
		}
		final float[] computedWidth = AutoGeneratorUtility.computeFieldWidths(
				widthSpecs, definition.getPageDefinition().getWidth());
		
		/*
		 * ---------------------------------------------------------------------
		 */
		

		itemBand.getStyle().setStyleProperty(BandStyleKeys.LAYOUT, "row");
		if (detailsHeader != null) {
			detailsHeader.getStyle().setStyleProperty(BandStyleKeys.LAYOUT,
			"row");
		}
		if (detailsFooter != null) {
			detailsFooter.getStyle().setStyleProperty(BandStyleKeys.LAYOUT,
			"row");
		}

		for (int i = 0; i < detailFieldDefinitions.length; i++) {
			final DetailFieldDefinition detailFieldDefinition = detailFieldDefinitions[i];
			setupField(detailsHeader, detailsFooter, itemBand,
					detailFieldDefinition, computedWidth[i], i);
		}

		if (detailsFooter != null) {
			final Element[] elements = detailsFooter.getElementArray();
			boolean footerEmpty = true;
			for (int i = 0; i < elements.length; i++) {
				final Element element = elements[i];
				if ("label".equals(element.getElementTypeName()) == false) {
					footerEmpty = false;
					break;
				}
			}
			if (footerEmpty) {
				detailsFooter.clear();
			}
		}
	}

	protected void setupField(final Band detailsHeader,
			final Band detailsFooter, final Band itemBand,
			final DetailFieldDefinition field, final float width,
			final int fieldIdx) throws ReportProcessingException {
		if (StringUtils.isEmpty(field.getField())) {
			return;
		}

		final Element detailElement = AutoGeneratorUtility
		.generateDetailsElement(field.getField(),
				computeElementType(field));
		setupDefaultGrid(itemBand, detailElement);

		final String id = "wizard::details-" + field.getField();
		detailElement.setName(id);
		detailElement.getStyle().setStyleProperty(ElementStyleKeys.MIN_WIDTH,
				new Float(width));
		if (Boolean.TRUE.equals(detailElement.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.ALLOW_METADATA_STYLING))) {
			detailElement
			.setAttribute(
					"http://reporting.pentaho.org/namespaces/engine/attributes/wizard",
					"CachedWizardFormatData", field);
			detailElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					"CachedWizardFormatData", field);
		}
		if (Boolean.TRUE.equals(detailElement.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES))) {
			detailElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					"CachedWizardFieldData", field);
		}
		itemBand.addElement(detailElement);

		if (Boolean.TRUE.equals(field.getOnlyShowChangingValues())) {
			detailElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ONLY_SHOW_CHANGING_VALUES,
					Boolean.TRUE);
		}

		if (detailsHeader != null) {
			final Element headerElement = AutoGeneratorUtility
			.generateHeaderElement(field.getField());
			setupDefaultGrid(detailsHeader, headerElement);
			headerElement.getStyle().setStyleProperty(
					ElementStyleKeys.MIN_WIDTH, new Float(width));
			/*
if (Boolean.TRUE.equals(headerElement.getAttribute(
AttributeNames.Wizard.NAMESPACE,
AttributeNames.Wizard.ALLOW_METADATA_STYLING))) {
headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
"CachedWizardFormatData", field);
}
			 */
			if (Boolean.TRUE.equals(headerElement.getAttribute(
					AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES))) {
				headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
						"CachedWizardFieldData", field);
			}
			/*
headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
MetaAttributeNames.Style.HORIZONTAL_ALIGNMENT,
field.getHorizontalAlignment());
			 */
			detailsHeader.addElement(headerElement);
		}

		if (detailsFooter != null) {
			final Class aggFunctionClass = field.getAggregationFunction();
			final Element footerElement = AutoGeneratorUtility
			.generateFooterElement(aggFunctionClass,
					computeElementType(field), null, field.getField());

			setupDefaultGrid(detailsFooter, footerElement);

			footerElement.getStyle().setStyleProperty(
					ElementStyleKeys.MIN_WIDTH, new Float(width));
			if (Boolean.TRUE.equals(footerElement.getAttribute(
					AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ALLOW_METADATA_STYLING))) {
				footerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
						"CachedWizardFormatData", field);
			}
			if (Boolean.TRUE.equals(footerElement.getAttribute(
					AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES))) {
				footerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
						"CachedWizardFieldData", field);
			}

			//details footer looks stupid!
			//detailsFooter.addElement(footerElement);
		}
	}

	protected void setupGroupsummaryField(final Band groupSummaryBand,
			final DetailFieldDefinition field, final float width,
			final int fieldIdx) throws ReportProcessingException {
		if (StringUtils.isEmpty(field.getField())) {
			return;
		}

		final Class aggFunctionClass = field.getAggregationFunction();
		final Element footerElement = AutoGeneratorUtility
		.generateFooterElement(aggFunctionClass,
				computeElementType(field), null, field.getField());

		setupDefaultGrid(groupSummaryBand, footerElement);

		footerElement.getStyle().setStyleProperty(ElementStyleKeys.MIN_WIDTH,
				new Float(width));
		if (Boolean.TRUE.equals(footerElement.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.ALLOW_METADATA_STYLING))) {
			footerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					"CachedWizardFormatData", field);
		}
		if (Boolean.TRUE.equals(footerElement.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES))) {
			footerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					"CachedWizardFieldData", field);
		}

		groupSummaryBand.addElement(footerElement);

	}

	/**
	 * Removes the unusedTemplateGroups based on the assumption that if a group
	 * doesn't have any fields assigned to it that it is empty.
	 */
	private void removedUnusedTemplateGroups(final int groupsDefined) {
		final RelationalGroup[] templateRelationalGroups = getTemplateRelationalGroups();
		final int templateRelationalGroupCount = templateRelationalGroups.length;
		for (int i = groupsDefined; i < templateRelationalGroupCount; i++) {
			final RelationalGroup templateRelationalGroup = templateRelationalGroups[i];
			definition.removeGroup(templateRelationalGroup);
		}
	}

	private RelationalGroup findInnermostRelationalGroup(
			final AbstractReportDefinition definition) {
		RelationalGroup retval = null;
		Group existingGroup = definition.getRootGroup();
		while (existingGroup instanceof RelationalGroup) {
			retval = (RelationalGroup) existingGroup;
			final GroupBody body = existingGroup.getBody();
			if (body instanceof SubGroupBody == false) {
				return retval;
			}
			final SubGroupBody sgb = (SubGroupBody) body;
			existingGroup = sgb.getGroup();
		}

		return retval;
	}

	private void insertGroup(final RelationalGroup group) {
		Group lastGroup = null;
		Group insertGroup = definition.getRootGroup();
		while (true) {
			if (insertGroup instanceof RelationalGroup == false) {
				if (lastGroup == null) {
					definition.setRootGroup(group);
					group.setBody(new SubGroupBody(insertGroup));
					return;
				}

				final GroupBody body = lastGroup.getBody();
				final SubGroupBody sgb = new SubGroupBody(group);
				lastGroup.setBody(sgb);
				group.setBody(body);
				return;
			}

			final GroupBody body = insertGroup.getBody();
			if (body instanceof SubGroupBody == false) {
				final SubGroupBody sgb = new SubGroupBody(group);
				insertGroup.setBody(sgb);
				group.setBody(body);
				return;
			}

			lastGroup = insertGroup;
			final SubGroupBody sgb = (SubGroupBody) body;
			insertGroup = sgb.getGroup();
		}
	}

	protected void configureRelationalGroup(final RelationalGroup group,
			final GroupDefinition groupDefinition, final int index)
	throws ReportProcessingException {
		final String groupField = groupDefinition.getField();
		if (groupField != null) {
			group.setFieldsArray(new String[] { groupField });
		}

		configureRelationalGroupFooter(group, groupDefinition, index);
		configureRelationalGroupHeader(group, groupDefinition, index);
	}

	protected void configureRelationalGroupHeader(final Group group,
			final GroupDefinition groupDefinition, final int index) {
		final RootBandDefinition headerDefinition = groupDefinition.getHeader();
		if (headerDefinition.isVisible()) {
			final GroupHeader header = group.getHeader();
			final Boolean repeat = headerDefinition.getRepeat();
			if (repeat != null) {
				header.setRepeat(repeat.booleanValue());
			}

			final Band content = AutoGeneratorUtility
			.findGeneratedContent(header);
			if (content == null) {
				return;
			}

			final Element headerLabelElement = new Element();
			headerLabelElement.setElementType(new LabelType());
			if (groupDefinition.getDisplayName() != null) {
				headerLabelElement.setAttribute(AttributeNames.Core.NAMESPACE,
						AttributeNames.Core.VALUE,
						groupDefinition.getDisplayName());
			} else {
				headerLabelElement.setAttribute(AttributeNames.Core.NAMESPACE,
						AttributeNames.Core.VALUE, groupDefinition.getField());
				headerLabelElement.setAttribute(
						AttributeNames.Wizard.NAMESPACE,
						AttributeNames.Wizard.LABEL_FOR,
						groupDefinition.getField());
				headerLabelElement.setAttribute(
						AttributeNames.Wizard.NAMESPACE,
						AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES,
						Boolean.TRUE);
			}

			final Element headerValueElement = AutoGeneratorUtility
			.generateDetailsElement(groupDefinition.getField(),
					computeElementType(groupDefinition));
			headerValueElement.setAttribute(AttributeNames.Core.NAMESPACE,
					AttributeNames.Core.NULL_VALUE, "-");

			final Band headerElement = new Band();
			headerElement.getStyle().setStyleProperty(BandStyleKeys.LAYOUT,
					BandStyleKeys.LAYOUT_INLINE);
			headerElement.getStyle().setStyleProperty(
					ElementStyleKeys.MIN_WIDTH, new Float(-100));
			headerElement.getStyle().setStyleProperty(
					ElementStyleKeys.DYNAMIC_HEIGHT, Boolean.TRUE);
			headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ALLOW_METADATA_STYLING, Boolean.TRUE);
			headerElement
			.setAttribute(AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.LABEL_FOR,
					groupDefinition.getField());
			headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					"CachedWizardFormatData", headerDefinition);
			//headerElement.addElement(headerLabelElement);
			//headerElement.addElement(headerValueElement);

			headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ALLOW_METADATA_STYLING, Boolean.FALSE);
			headerElement.setAttribute(AttributeNames.Wizard.NAMESPACE,
					AttributeNames.Wizard.ALLOW_METADATA_ATTRIBUTES, Boolean.FALSE);

			final Element headerMessageElement = new Element();
			headerMessageElement.setElementType(new MessageType());
			headerMessageElement.setAttribute(AttributeNames.Core.NAMESPACE,
					AttributeNames.Core.FIELD, groupDefinition.getField());
			headerMessageElement.setAttribute(AttributeNames.Core.NAMESPACE,
					AttributeNames.Core.NAME, groupDefinition.getDisplayName());

			headerMessageElement.setAttribute(AttributeNames.Core.NAMESPACE,
					AttributeNames.Core.VALUE,
					groupDefinition.getDisplayName() + ": $(" + groupDefinition.getField() + ")"
			);

			//there can be only one!
			headerElement.addElement(headerMessageElement);

			content.clear();
			content.addElement(headerElement);
		}
	}

	protected void configureRelationalGroupFooter(final Group group,
			final GroupDefinition groupDefinition, final int index)
	throws ReportProcessingException {

		final RootBandDefinition footerDefinition = groupDefinition.getFooter();
		final DetailFieldDefinition[] detailFieldDefinitions = wizardSpecification
		.getDetailFieldDefinitions();

		if (footerDefinition.isVisible() == false) {
			return;
		}

		final GroupFooter footer = group.getFooter();
		final Boolean repeat = footerDefinition.getRepeat();
		if (repeat != null) {
			footer.setRepeat(repeat.booleanValue());
		}

		final Band itemBand = AutoGeneratorUtility.findGeneratedContent(footer);
		itemBand.getStyle().setStyleProperty(BandStyleKeys.LAYOUT, "row");

		final Float[] widthSpecs = new Float[detailFieldDefinitions.length];
		for (int i = 0; i < detailFieldDefinitions.length; i++) {
			final DetailFieldDefinition fieldDefinition = detailFieldDefinitions[i];
			final Length length = fieldDefinition.getWidth();
			if (length == null) {
				continue;
			}
			widthSpecs[i] = length.getNormalizedValue();
		}
		final float[] computedWidth = AutoGeneratorUtility.computeFieldWidths(
				widthSpecs, definition.getPageDefinition().getWidth());

		for (int i = 0; i < detailFieldDefinitions.length; i++) {
			final DetailFieldDefinition detailFieldDefinition = detailFieldDefinitions[i];
			setupGroupsummaryField(itemBand, detailFieldDefinition,
					computedWidth[i], i);
		}

	}

	protected void setupDefaultPadding(final Band band,
			final Element detailElement) {
		final Object maybePaddingTop = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.PADDING_TOP);
		final Object maybePaddingLeft = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.PADDING_LEFT);
		final Object maybePaddingBottom = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.PADDING_BOTTOM);
		final Object maybePaddingRight = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.PADDING_RIGHT);

		if (maybePaddingTop instanceof Number == false
				|| maybePaddingLeft instanceof Number == false
				|| maybePaddingBottom instanceof Number == false
				|| maybePaddingRight instanceof Number == false) {
			return;
		}

		final Number paddingTop = (Number) maybePaddingTop;
		final Number paddingLeft = (Number) maybePaddingLeft;
		final Number paddingBottom = (Number) maybePaddingBottom;
		final Number paddingRight = (Number) maybePaddingRight;

		final ElementStyleSheet styleSheet = detailElement.getStyle();
		styleSheet.setStyleProperty(ElementStyleKeys.PADDING_TOP, new Float(
				paddingTop.floatValue()));
		styleSheet.setStyleProperty(ElementStyleKeys.PADDING_LEFT, new Float(
				paddingLeft.floatValue()));
		styleSheet.setStyleProperty(ElementStyleKeys.PADDING_BOTTOM, new Float(
				paddingBottom.floatValue()));
		styleSheet.setStyleProperty(ElementStyleKeys.PADDING_RIGHT, new Float(
				paddingRight.floatValue()));
	}

	protected void setupDefaultGrid(final Band band, final Element detailElement) {
		setupDefaultPadding(band, detailElement);
		final ElementStyleSheet styleSheet = detailElement.getStyle();
		// Always make the height of the detailElement dynamic to the band
		// According to thomas negative numbers equate to percentages
		styleSheet.setStyleProperty(ElementStyleKeys.MIN_HEIGHT,
				new Float(-100));

		final Object maybeBorderStyle = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.GRID_STYLE);
		final Object maybeBorderWidth = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.GRID_WIDTH);
		final Object maybeBorderColor = band.getAttribute(
				AttributeNames.Wizard.NAMESPACE,
				AttributeNames.Wizard.GRID_COLOR);

		if (maybeBorderColor instanceof Color == false
				|| maybeBorderStyle instanceof BorderStyle == false
				|| maybeBorderWidth instanceof Number == false) {
			return;
		}

		final BorderStyle style = (BorderStyle) maybeBorderStyle;
		final Color color = (Color) maybeBorderColor;
		final Number number = (Number) maybeBorderWidth;
		final Float width = new Float(number.floatValue());

		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_TOP_WIDTH, width);
		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_TOP_COLOR, color);
		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_TOP_STYLE, style);

		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_LEFT_WIDTH, width);
		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_LEFT_COLOR, color);
		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_LEFT_STYLE, style);

		styleSheet
		.setStyleProperty(ElementStyleKeys.BORDER_BOTTOM_WIDTH, width);
		styleSheet
		.setStyleProperty(ElementStyleKeys.BORDER_BOTTOM_COLOR, color);
		styleSheet
		.setStyleProperty(ElementStyleKeys.BORDER_BOTTOM_STYLE, style);

		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_RIGHT_WIDTH, width);
		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_RIGHT_COLOR, color);
		styleSheet.setStyleProperty(ElementStyleKeys.BORDER_RIGHT_STYLE, style);
	}

	/**
	 * @return the relational groups in the templates in a flattened array.
	 */
	private RelationalGroup[] getTemplateRelationalGroups() {
		final ArrayList<RelationalGroup> relationalGroups = new ArrayList<RelationalGroup>();
		Group group = definition.getRootGroup();
		while (group != null && group instanceof RelationalGroup) {
			relationalGroups.add((RelationalGroup) group);
			final GroupBody body = group.getBody();
			if (body instanceof SubGroupBody) {
				final SubGroupBody sgBody = (SubGroupBody) body;
				if (sgBody.getGroup() instanceof RelationalGroup) {
					group = sgBody.getGroup();
				} else {
					group = null;
				}
			} else {
				group = null;
			}
		}

		return relationalGroups.toArray(new RelationalGroup[relationalGroups
		                                                    .size()]);
	}

	protected ElementType computeElementType(
			final FieldDefinition fieldDefinition) {
		final String field = fieldDefinition.getField();
		final DataAttributes attributes = flowController.getDataSchema()
		.getAttributes(field);
		if (attributes == null) {
			log.warn("Field '" + field
					+ "' is declared in the wizard-specification, "
					+ "but not present in the data. Assuming defaults.");
			return new TextFieldType();
		}
		final Class fieldType = (Class) attributes.getMetaAttribute(
				MetaAttributeNames.Core.NAMESPACE,
				MetaAttributeNames.Core.TYPE, Class.class, attributeContext);
		if (fieldType == null) {
			return new TextFieldType();
		}

		if (Number.class.isAssignableFrom(fieldType)) {
			return new NumberFieldType();
		}
		if (Date.class.isAssignableFrom(fieldType)) {
			return new DateFieldType();
		}
		if (byte[].class.isAssignableFrom(fieldType)
				|| Blob.class.isAssignableFrom(fieldType)
				|| Image.class.isAssignableFrom(fieldType)) {
			return new ContentFieldType();
		}
		return new TextFieldType();
	}

	/**
	 * That is where the magic happens Im Gegensatz zum WizardProcessor bangen
	 * wir hier auf dem richtigen report rum
	 *
	 * @return
	 * @throws ReportProcessingException
	 */
	private MasterReport performSaikuPreProcessing(MasterReport definition)
	throws ReportProcessingException {

		this.definition = definition;

		setupRelationalGroups();
		setupDetails();

		setupReportHeader();
		setupReportFooter();

		setupPageHeader();
		setupPageFooter();

		final List<SaikuGroup> groupDefinitions = model.getGroups();

		return definition;

	}

	private void setupPageFooter() {
		final Section pageFooter = definition.getPageFooter();
		if (pageFooter == null)
			return;

		iterateSection(pageFooter,
				new SaikuUpdateMessagesTask(model.getPageFooterMessages(), PAGE_FOOTER_MSG, model));

	}

	private void setupPageHeader() {
		final Section pageHeader = definition.getPageHeader();
		if (pageHeader == null)
			return;

		iterateSection(pageHeader,
				new SaikuUpdateMessagesTask(model.getPageHeaderMessages(), PAGE_HEADER_MSG, model));

	}

	private void setupReportFooter() {

		final Section reportFooter = definition.getReportFooter();
		if (reportFooter == null)
			return;

		iterateSection(reportFooter,
				new SaikuUpdateMessagesTask(model.getReportFooterMessages(), RPT_FOOTER_MSG, model));

	}

	private void setupReportHeader() {

		final Section reportHeader = definition.getReportHeader();
		if (reportHeader == null)
			return;

		iterateSection(reportHeader,
				new SaikuUpdateMessagesTask(model.getReportHeaderMessages(), RPT_HEADER_MSG, model));

	}

	/**
	 * Then pass the SaikuColumns and the details Band to the Update Task
	 */
	private void setupDetails() {

		final Band detailsHeader = definition.getDetailsHeader();
		if (detailsHeader == null)
			return;
		iterateSection(detailsHeader,
				new SaikuUpdateDetailsHeaderTask(model));

		final Band itemBand = definition.getItemBand();
		if (itemBand == null)
			return;
		iterateSection(itemBand, new SaikuUpdateDetailsTask(model));

		final Band detailsFooter = definition.getDetailsFooter();
		if (detailsFooter == null)
			return;
		iterateSection(detailsFooter,
				new SaikuUpdateFooterTask(model));

	}

	/**
	 * We need to iterate through the SaikuGroups and find the corresponding
	 * band in the report definition Then pass it to the Group UpdateTask
	 */
	private void setupRelationalGroups() {

		final List<SaikuGroup> groupDefinitions = model.getGroups();

		log.debug("setting up relational group!");


		for (SaikuGroup saikuGroup : groupDefinitions) {
			int groupIndex = groupDefinitions.indexOf(saikuGroup);
			Group group = definition.getGroup(groupIndex);
			configureSaikuGroupHeader(group, saikuGroup, groupIndex);
			configureSaikuGroupFooter(group, saikuGroup, groupIndex);
			//iterateSection(group, new SaikuUpdateGroupTask(saikuGroup));
			// iterateSection(saikuGroup, new UpdateFooterTask(gd));
		}

	}

	private void configureSaikuGroupFooter(Group group, SaikuGroup saikuGroup,
			int groupIndex) {
		//iterateSection(group, new SaikuUpdateGroupFooterTask(saikuGroup));

	}

	private void configureSaikuGroupHeader(Group group, SaikuGroup saikuGroup,
			int groupIndex) {
		iterateSection(group, new SaikuUpdateGroupHeaderTask(model, saikuGroup,groupIndex));
	}

	/**
	 * Find all the other stuff Update Task
	 */
	private void setupOtherFields() {
		// TODO Auto-generated method stub

	}

	/**
	 * We iterate through all Elements of a section. A section is a group-band
	 * or a header...
	 *
	 * @param s
	 * @param task
	 */
	private void iterateSection(final Section s, final UpdateTask task) {
		final int count = s.getElementCount();
		for (int i = 0; i < count; i++) {
			final ReportElement element = s.getElement(i);
			task.processElement(element, i);
			if (element instanceof SubReport) {
				continue;
			}
			if (element instanceof Section) {
				iterateSection((Section) element, task);
			}
		}
	}

}