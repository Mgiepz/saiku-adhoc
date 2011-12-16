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

import java.io.OutputStream;

import org.pentaho.platform.api.engine.IFileFilter;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.ISolutionFile;
import org.pentaho.platform.api.repository.ISolutionRepository;
import org.pentaho.platform.engine.core.system.PentahoSystem;


public class FileExplorer {
	
	private static FileExplorer fileExplorer = null;
	
	public static FileExplorer getInstance() {
		if(fileExplorer == null) {
			fileExplorer = new FileExplorer();
		}
		return fileExplorer;
	}
	
	public void browse(String dir, final String fileExtensions, IPentahoSession userSession, OutputStream outputStream) {
		
		try {
			
			if (dir == null) {
		    	return;
		    }
			
			StringBuilder out = new StringBuilder();
			
			final ISolutionRepository solutionRepository = PentahoSystem.get(ISolutionRepository.class, userSession);
			ISolutionFile[] files =  solutionRepository.getFileByPath(dir).listFiles(new IFileFilter(){
				public boolean accept(ISolutionFile file) {
					boolean hasAccess = solutionRepository.hasAccess(file,ISolutionRepository.ACTION_CREATE);
					String visiblePtoperty = solutionRepository.getLocalizedFileProperty(file, "visible");
					boolean visible = !file.isDirectory() || (visiblePtoperty != null && visiblePtoperty.equals("true"));
					return visible  && hasAccess ? !file.isDirectory() && fileExtensions.length() > 0 ? fileExtensions.indexOf(file.getExtension()) != -1 : true : false;
				}				
			});
			
			
			if(files.length > 0){

				out.append("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
				
				for (ISolutionFile file : files) {
				    if (file.isDirectory()) {
						out.append("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + file.getFileName() + "/\">"+ file.getFileName() + "</a></li>");
				    }
				}
				
				for (ISolutionFile file : files) {
				    if (!file.isDirectory()) {
						int dotIndex = file.getFileName().lastIndexOf('.');
						String ext = dotIndex > 0 ? file.getFileName().substring(dotIndex + 1) : "";
						out.append("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + dir + file.getFileName() + "\">"+ file.getFileName() + "</a></li>");
				    	}
				}
				
				out.append("</ul>");
				
				outputStream.write(out.toString().getBytes("UTF-8"));
		    }
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		
	}

}
