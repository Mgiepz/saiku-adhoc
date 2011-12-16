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

import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.model.LogicalModel;
import org.saiku.adhoc.exceptions.MetadataException;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.metadata.impl.MetadataModel;
import org.saiku.adhoc.model.metadata.impl.MetadataModelInfo;

public interface IMetadataService {

	/**
	 * Get a thin representation of all available metadata models
	 * 
	 * @param domainName
	 * @param locale 
	 * @return
	 * @throws QuerybuilderServiceException
	 */
	@SuppressWarnings("unchecked")
	public abstract MetadataModelInfo[] getBusinessModels(String domainName, String locale)
			throws MetadataException;

	/**
	 * Get the real logical model for a given ID
	 * 
	 * @param modelId
	 * @param domainId 
	 * @return
	 */
	public abstract LogicalModel getLogicalModel(String domainId, String modelId);

	/**
	 * Get the real domain object for a given id
	 * @param domain
	 * @return
	 */
	public abstract Domain getDomain(String domainId);

	/**
	 * Returns a Model object for the requested model. The model will include
	 * the basic metadata - categories and columns.
	 * 
	 * @param domainId
	 * @param modelId
	 * @return
	 */
	public abstract MetadataModel loadModel(String domainId, String modelId);

	public abstract ReportTemplate[] loadTemplates();

}