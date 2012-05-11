package org.saiku.adhoc.providers.impl.standalone;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.pentaho.metadata.repository.IMetadataDomainRepository;
import org.pentaho.platform.api.engine.ILogger;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.saiku.adhoc.messages.Messages;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.metadata.impl.MetadataModel;
import org.saiku.adhoc.providers.AbstractMetadataProvider;
import org.saiku.adhoc.providers.impl.pentaho.PentahoMetadataUtil;
import org.saiku.adhoc.service.repository.IRepositoryHelper;
import org.saiku.adhoc.service.repository.MemoryBasedRepository;

public class StandaloneMetadataProvider extends AbstractMetadataProvider {

	    private static final long serialVersionUID = 8481450224870463494L;

	    private Log logger = LogFactory.getLog(StandaloneMetadataProvider.class);

	    public IMetadataDomainRepository repo;
	    
	    protected IRepositoryHelper repositoryHelper;
		
	    MemoryBasedRepository mbr;
		
		public StandaloneMetadataProvider() {
		    super();
			setLoggingLevel(ILogger.ERROR);
			mbr = new MemoryBasedRepository();
			repo = getMetadataRepository();
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
		public IMetadataDomainRepository getMetadataRepository() {

		    IMetadataDomainRepository mdr = mbr.getImmdr();
		    if (mdr instanceof ILogger) {
	            ((ILogger) mdr).setLoggingLevel(getLoggingLevel());
	        }
			return mdr;
		}

	    
	    public Log getLogger() {
	        return logger;
	    }

	    
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

