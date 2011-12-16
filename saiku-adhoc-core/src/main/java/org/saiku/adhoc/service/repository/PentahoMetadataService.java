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

package org.saiku.adhoc.service.repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.repository.IMetadataDomainRepository;
import org.pentaho.platform.api.engine.ILogger;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.engine.core.system.PentahoBase;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.util.messages.LocaleHelper;
import org.saiku.adhoc.messages.Messages;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.metadata.impl.MetadataModel;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.model.metadata.impl.ModelInfoComparator;



/**
 * This service retrieves information on metadata models to be consumed by the
 * client
 * 
 * <a href="mailto:mgiepz@googlemail.com">Marius Giepz</a>
 * 
 */
public class PentahoMetadataService extends PentahoBase implements IMetadataService {

	private static final long serialVersionUID = 8481450224870463494L;

	private Log logger = LogFactory.getLog(PentahoMetadataService.class);

	protected final IMetadataDomainRepository repo;

	protected IRepositoryHelper repositoryHelper;


	public PentahoMetadataService() {
		setLoggingLevel(ILogger.ERROR);
		//repo = (SecurityAwareMetadataDomainRepository) getMetadataRepository();
		repo = getMetadataRepository();
	}

	/* (non-Javadoc)
	 * @see refac.saiku.adhoc.service.repository.IMetadataService#getBusinessModels(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public MetadataModelInfo[] getBusinessModels(String domainName, String locale) {
		
		Locale loc = new Locale(locale);
		
		LocaleHelper.setLocale(loc);

		List<MetadataModelInfo> models = new ArrayList<MetadataModelInfo>();

		IMetadataDomainRepository repo = getMetadataRepository();

		if (repo == null) {
			error(Messages
					.getErrorString("MetadataService.ERROR_0001_BAD_REPO")); //$NON-NLS-1$
			return null;
		}

		// TODO: what context is appropriate here?
		String context = null;

		try {
			if (StringUtils.isEmpty(domainName)) {
				for (String domain : getMetadataRepository().getDomainIds()) {
					getModelInfos(domain, context, models);
				}
			} else {
				getModelInfos(domainName, context, models);
			}
		} catch (Throwable t) {
			error(Messages
					.getErrorString("MetadataService.ERROR_0002_BAD_MODEL_LIST"), t); //$NON-NLS-1$
		}

		Collections.sort(models, new ModelInfoComparator());
		return models.toArray(new MetadataModelInfo[models.size()]);
	}

	/* (non-Javadoc)
	 * @see refac.saiku.adhoc.service.repository.IMetadataService#getLogicalModel(java.lang.String, java.lang.String)
	 */
	@Override
	public LogicalModel getLogicalModel(String domainId, String modelId) {
		return repo.getDomain(domainId).findLogicalModel(modelId);
	}
	
	/* (non-Javadoc)
	 * @see refac.saiku.adhoc.service.repository.IMetadataService#getDomain(java.lang.String)
	 */
	@Override
	public Domain getDomain(String domainId) {
		return repo.getDomain(domainId);
	}

	/**
	 * Returns a list of ModelInfo objects for the specified domain.
	 * @param domainId
	 * @param context
	 *            Area to check for model visibility
	 * @param models
	 * @throws UnsupportedEncodingException 
	 */
	private void getModelInfos(final String domainId, final String context,
			List<MetadataModelInfo> models) throws UnsupportedEncodingException {

		Domain domainObject = repo.getDomain(domainId);
		if (domainObject == null) {
			return;
		}

		//Some guessing here
		String locale = LocaleHelper.getClosestLocale(LocaleHelper.getLocale()
				.toString(), domainObject.getLocaleCodes());

		// iterate over all of the models in this domain
		for (LogicalModel model : domainObject.getLogicalModels()) {
			String vis = (String) model.getProperty("visible");
			if (vis != null) {
				String[] visibleContexts = vis.split(",");
				boolean visibleToContext = false;
				for (String c : visibleContexts) {
					if (c.equals(context)) {
						visibleToContext = true;
						break;
					}
				}
				if (!visibleToContext) {
					continue;
				}
			}
			// create a new ModelInfo object and give it the envelope
			// information about the model
			MetadataModelInfo modelInfo = new MetadataModelInfo();
			modelInfo.setDomainId(URLEncoder.encode(domainId,"UTF-8"));
			modelInfo.setModelId(model.getId());
			modelInfo.setModelName(model.getName(locale));
			if (model.getDescription() != null) {
				String modelDescription = model.getDescription(locale);
				modelInfo.setModelDescription(modelDescription);
			}
			models.add(modelInfo);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see refac.saiku.adhoc.service.repository.IMetadataService#loadModel(java.lang.String, java.lang.String)
	 */
	@Override
	public MetadataModel loadModel(String domainId, String modelId) {

		if (domainId == null) {
			error(Messages
					.getErrorString("MetadataService.ERROR_0003_NULL_DOMAIN")); //$NON-NLS-1$
			return null;
		}

		if (modelId == null) {
			error(Messages
					.getErrorString("MetadataService.ERROR_0004_NULL_Model")); //$NON-NLS-1$
			return null;
		}

		Domain domain = repo.getDomain(domainId);
		if (domain == null) {
			error(Messages.getErrorString(
					"MetadataService.ERROR_0005_DOMAIN_NOT_FOUND", domainId)); //$NON-NLS-1$
			return null;
		}

		LogicalModel model = domain.findLogicalModel(modelId);

		if (model == null) {
			// the model cannot be found or cannot be loaded
			error(Messages.getErrorString(
					"MetadataService.ERROR_0006_MODEL_NOT_FOUND", modelId)); //$NON-NLS-1$
			return null;
		}

		//TODO: Move to dto util

		PentahoMetadataUtil util = new PentahoMetadataUtil();
		util.setDomain(domain);
		MetadataModel thinModel = util.createThinModel(model, domainId);
		return thinModel;

	}

	/**
	 * Returns a instance of the IMetadataDomainRepository for the current
	 * session
	 * 
	 * @return
	 */
	protected IMetadataDomainRepository getMetadataRepository() {
		IMetadataDomainRepository mdr = PentahoSystem
				.get(IMetadataDomainRepository.class,
						PentahoSessionHolder.getSession());
	     
	    if (mdr instanceof ILogger) {
            ((ILogger) mdr).setLoggingLevel(getLoggingLevel());
        }
	    return mdr;
	}

	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public ReportTemplate[] loadTemplates() {

		IPentahoSession userSession = PentahoSessionHolder.getSession();

		return repositoryHelper.getTemplateList(userSession);

	}

	public IRepositoryHelper getRepositoryHelper() {
		return repositoryHelper;
	}

	public void setRepositoryHelper(IRepositoryHelper repositoryHelper) {
		this.repositoryHelper = repositoryHelper;
	}

}
