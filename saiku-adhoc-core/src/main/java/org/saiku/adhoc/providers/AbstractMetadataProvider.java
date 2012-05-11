/**
 * 
 */
package org.saiku.adhoc.providers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.repository.IMetadataDomainRepository;
import org.pentaho.platform.engine.core.system.PentahoBase;
import org.pentaho.platform.util.messages.LocaleHelper;
import org.saiku.adhoc.messages.Messages;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;
import org.saiku.adhoc.model.metadata.impl.ModelInfoComparator;

/**
 * @author mg
 *
 */
public abstract class AbstractMetadataProvider extends PentahoBase implements IMetadataProvider{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns a list of ModelInfo objects for the specified domain.
	 * @param domainId
	 * @param context
	 *            Area to check for model visibility
	 * @param models
	 * @throws UnsupportedEncodingException 
	 */
	protected void getModelInfos(final String domainId, final String context, List<MetadataModelInfo> models) throws UnsupportedEncodingException {
	
	    Domain domainObject = getDomain(domainId);
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



}
