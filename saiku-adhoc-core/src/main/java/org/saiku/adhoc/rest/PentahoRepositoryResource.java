/*
 * Copyright (C) 2011 Paul Stoellberger
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

package org.saiku.adhoc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.dom4j.Document;
import org.saiku.adhoc.model.WorkspaceSessionHolder;
import org.saiku.adhoc.model.dto.FileTree;
import org.saiku.adhoc.model.dto.SavedQuery;
import org.saiku.adhoc.service.repository.IRepositoryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Path("/saiku-adhoc/rest/repository")
@Scope("request")
@XmlAccessorType(XmlAccessType.NONE)
public class PentahoRepositoryResource {

	private static final Logger log = LoggerFactory.getLogger(PentahoRepositoryResource.class);

	private IRepositoryHelper repository;

	private WorkspaceSessionHolder sessionHolder;

	public void setRepositoryHelper(IRepositoryHelper repository) {
		this.repository = repository;
	}

	public void setSessionHolder(WorkspaceSessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	@POST
	@Produces({"application/json" })
	@Path("/query/{name}")
	public Status saveQuery(
			@PathParam("name") String queryName,
			SavedQuery query	
	) 
	{

		if (log.isDebugEnabled()) {
			log.debug("REST:POST " + queryName + " saveQuery");
		}

		String action = query.getAction();

		if (!action.endsWith(".adhoc")) {
			action += ".adhoc";
		}

		repository.writeFile(query.getSolution(), query.getPath(), action, query.getJson());

		return Status.OK;

	}

	/**
	 * Load a query.
	 * @param queryName - The name of the query to load.
	 * @return A Saiku Query Object.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/query/{name}")
	public SavedQuery loadQuery(
			@PathParam("name") String name,
			@QueryParam("solution") String solution,
			@QueryParam("path") String path,
			@QueryParam("action") String action)	
	{
		if (log.isDebugEnabled()) {
			log.debug("REST:GET loadQuery name=" + name + " solution=" + solution + " path=" + path + " action=" + action);
		}

		try {

			String doc = repository.loadFile(solution, path, action);

			SavedQuery sq = new SavedQuery(action, null, doc);
			return sq;

		} catch (Exception e) {
			log.error("Cannot load query (" + action + ")", e);
		}
		return null;

	}

	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/browse/{dir}/{extensions}")
	public FileTree browse(
			@PathParam("dir") String dir,
			@PathParam("extensions") String fileExtensions
	){
<<<<<<< .merge_file_CGPD2n
		final Document browse = repository.browse(dir, fileExtensions);
		return new FileTree(browse.asXML());

=======
		return repository.browse(dir, fileExtensions);
>>>>>>> .merge_file_vjh3Wx
	}
*/

}
