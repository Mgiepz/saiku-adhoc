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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.pentaho.metadata.model.Domain;
import org.pentaho.platform.api.engine.IFileFilter;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.engine.ISolutionFile;
import org.pentaho.platform.api.engine.ISolutionFilter;
import org.pentaho.platform.api.engine.PentahoAccessControlException;
import org.pentaho.platform.api.repository.ISolutionRepository;
import org.pentaho.platform.engine.core.solution.ActionInfo;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.util.messages.LocaleHelper;
import org.saiku.adhoc.exceptions.QueryException;
import org.saiku.adhoc.model.dto.FileTree;
import org.saiku.adhoc.model.master.ReportTemplate;
import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.service.PluginConfig;


public class PentahoRepositoryHelper implements IRepositoryHelper {

private static final String solution = "system";

private static final String path = "saiku-adhoc/temp";

private static Log log = LogFactory.getLog(PentahoRepositoryHelper.class);

private static final String ENCODING = "UTF-8";

@Override
public boolean writeFile(String solution, String path, String action,
String contents) {

try {
return writeFile(solution, path, action,
contents.getBytes(ENCODING));
} catch (Exception e) {
log.error("Cannot save cda (" + action + ")", e);
return (false);
}

}

@Override
public boolean writeFile(String solution, String path, String artifact,
ByteArrayOutputStream contents) {

try {
return writeFile(solution, path, artifact, contents.toByteArray());
} catch (Exception e) {
log.error("Cannot save cda (" + artifact + ")", e);
return (false);
}

}

@Override
public String loadFile(String solution, String path, String action)
throws IOException {

// do we need to load other files too?
if (!action.endsWith(".adhoc")) {
action += ".adhoc";
}

String fullPath = ActionInfo.buildSolutionPath(solution, path, action);
IPentahoSession userSession = PentahoSessionHolder.getSession();
ISolutionRepository repository = PentahoSystem.get(
ISolutionRepository.class, userSession);

if (repository.resourceExists(fullPath)) {
String doc = repository.getResourceAsString(fullPath,
ISolutionRepository.ACTION_EXECUTE);

if (doc == null) {
log.error("Error retrieving document from solution repository");
throw new NullPointerException(
"Error retrieving document from solution repository");
}

return doc;

}
return null;
}

private static boolean writeFile(String solution, String path,
String artifact, byte[] contents)
throws PentahoAccessControlException, UnsupportedEncodingException,
Exception {

String fullPath = ActionInfo
.buildSolutionPath(solution, path, artifact);
IPentahoSession userSession = PentahoSessionHolder.getSession();
ISolutionRepository repository = PentahoSystem.get(
ISolutionRepository.class, userSession);

if (repository == null) {
log.error("Access to Repository has failed");
throw new NullPointerException("Access to Repository has failed");
}

String base = PentahoSystem.getApplicationContext()
.getSolutionRootPath();
String parentPath = ActionInfo.buildSolutionPath(solution, path, "");
ISolutionFile parentFile = repository.getSolutionFile(parentPath,
ISolutionRepository.ACTION_CREATE);
String filePath = parentPath + ISolutionRepository.SEPARATOR + artifact;
ISolutionFile fileToSave = repository.getSolutionFile(fullPath,
ISolutionRepository.ACTION_UPDATE);

if (fileToSave != null
|| (!repository.resourceExists(filePath) && parentFile != null)) {
repository
.publish(base, '/' + parentPath, artifact, contents, true);
log.debug(PluginConfig.PLUGIN_NAME + " : Published " + solution
+ " / " + path + " / " + artifact);
} else {
throw new Exception(
"Error ocurred while saving to solution repository");
}
return (true);
}

@Override
/**
* Works out what is the most appropriate locale to use given a domain and the user's
* current locale
* @return
*/
public String getLocale(Domain domain) {
String locale = LocaleHelper.getClosestLocale(LocaleHelper.getLocale()
.toString(), domain.getLocaleCodes());
return locale;
}

public ReportTemplate[] getTemplateList(final IPentahoSession userSession) {
log.debug("Getting template list");

ArrayList<ReportTemplate> templates = new ArrayList<ReportTemplate>();

File templateDir = new File(PentahoSystem.getApplicationContext()
.getSolutionPath("system/saiku-adhoc/resources/templates"));
for (String entry : templateDir.list(new PrptFilter())) {
templates.add(new ReportTemplate("system",
"saiku-adhoc/resources/templates", entry));
}

return templates.toArray(new ReportTemplate[templates.size()]);

}

class PrptFilter implements FilenameFilter {
public boolean accept(File f, String s) {
return s.toLowerCase().endsWith(".prpt");
}
}

private class DirectoryFilter implements ISolutionFilter
{

public boolean keepFile(final ISolutionFile iSolutionFile, final int i)
{
if (iSolutionFile.isDirectory())
{
return true;
}
return false;
}
}

/**
* Browse the content of the repository
*
* @param dir
* @param fileExtensions
*/
/*
public Document browse(String dir, final String fileExtensions) {

IPentahoSession userSession = PentahoSessionHolder.getSession();

FileTree tree = new FileTree();

final ISolutionRepository solutionRepository = PentahoSystem.get(ISolutionRepository.class, userSession);

Document dirTree = solutionRepository.getFullSolutionTree(ISolutionRepository.ACTION_EXECUTE, new DirectoryFilter());

return dirTree;

}
*/

@Override
public void cleanTemp() {

IPentahoSession userSession = PentahoSessionHolder.getSession();

final ISolutionRepository solutionRepository = PentahoSystem.get(ISolutionRepository.class, userSession);

final String fileExtensions = "cda";

ISolutionFile[] files = solutionRepository.getFileByPath(this.path).listFiles(new IFileFilter(){
public boolean accept(ISolutionFile file) {
boolean hasAccess = solutionRepository.hasAccess(file,ISolutionRepository.ACTION_DELETE);
boolean visible = !file.isDirectory();
return visible && hasAccess ? !file.isDirectory() && fileExtensions.length() > 0 ? fileExtensions.indexOf(file.getExtension()) != -1 : true : false;
}
});

for (int i = 0; i < files.length; i++) {
ISolutionFile iSolutionFile = files[i];
solutionRepository.removeSolutionFile(iSolutionFile.getFullPath());

}

}

@Override
public Document browse(String dir, String fileExtensions) {
// TODO Auto-generated method stub
return null;
}

@Override
public boolean writeLocalFile(String path, String artifact, byte[] contents) {
    // TODO Empty for server
    return false;
}

}

