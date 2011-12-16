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
package org.saiku.adhoc.utils;

import java.util.TimerTask;

import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.ISolutionFile;
import org.pentaho.platform.api.repository.ISolutionRepository;
import org.pentaho.platform.api.util.ITempFileDeleter;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;

public class TempCleanerTask extends TimerTask {

private static final String path = "system/saiku-adhoc/temp";

public void run() {



ITempFileDeleter temp;

// IPentahoSession userSession = PentahoSessionHolder.getSession();
//
// final ISolutionRepository solutionRepository = PentahoSystem.get(ISolutionRepository.class, userSession);
//
// final String fileExtensions = "cda";
//
// String name = solutionRepository.getRepositoryName();
//
// ISolutionFile[] files = solutionRepository.getFileByPath(this.path).listFiles(
// /*
// new IFileFilter(){
// public boolean accept(ISolutionFile file) {
// boolean hasAccess = solutionRepository.hasAccess(file,ISolutionRepository.ACTION_DELETE);
// boolean visible = !file.isDirectory();
// return visible && hasAccess ? !file.isDirectory() && fileExtensions.length() > 0 ? fileExtensions.indexOf(file.getExtension()) != -1 : true : false;
//
// }} */
// );
//
// for (int i = 0; i < files.length; i++) {
// ISolutionFile iSolutionFile = files[i];
// solutionRepository.removeSolutionFile(iSolutionFile.getFullPath());
//
// }

}

}