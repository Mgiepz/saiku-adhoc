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

package org.saiku.adhoc.service;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IParameterProvider;
import org.pentaho.platform.api.repository.IContentItem;
import org.pentaho.platform.engine.services.solution.SimpleContentGenerator;
import org.saiku.adhoc.service.repository.FileExplorer;



/**
* @author mgiepz
*
*/
public class SaikuAdhocContentGenerator extends SimpleContentGenerator {

private static final long serialVersionUID = -9180003935693305152L;
private static final Log logger = LogFactory
.getLog(SaikuAdhocContentGenerator.class);

public String getMimeType() {
return "text/html";
}

public Log getLogger() {
return logger;
}

@Override
public void createContent(OutputStream out) throws Exception {
{

final IParameterProvider pathParams = parameterProviders.get("path");
final IParameterProvider requestParams = parameterProviders.get("request");

try
{

final Class[] params =
{
IParameterProvider.class, OutputStream.class
};

final String method = pathParams.getStringParameter("path", null).split("/")[1].toLowerCase();

try
{
final Method mthd = this.getClass().getMethod(method, params);
mthd.invoke(this, requestParams, out);
}
catch (NoSuchMethodException e)
{
logger.error("could not invoke " + method);
}
catch (InvocationTargetException e)
{
//get to the cause and rethrow properly
Throwable target = e.getTargetException();
if (!e.equals(target))
{//just in case
//get to the real cause
while (target != null && target instanceof InvocationTargetException)
{
target = ((InvocationTargetException) target).getTargetException();
}
}
if (target instanceof Exception)
{
throw (Exception) target;
}
else
{
throw new Exception(target);
}
}
}
catch (Exception e)
{
final String message = e.getCause() != null ? e.getCause().getClass().getName() + " - " + e.getCause().getMessage() : e.getClass().getName() + " - " + e.getMessage();
logger.error(message, e);
}
}
}

public void explorefolder(final IParameterProvider pathParams, final OutputStream out)
{
final String folder = pathParams.getStringParameter("dir", null);
final String fileExtensions = pathParams.getStringParameter("fileExtensions", null);
FileExplorer.getInstance().browse(folder, fileExtensions, userSession, out);
}

}

