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
import java.io.IOException;

import org.dom4j.Document;
import org.pentaho.metadata.model.Domain;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.saiku.adhoc.model.dto.FileTree;
import org.saiku.adhoc.model.master.ReportTemplate;

public interface IRepositoryHelper {

public abstract boolean writeFile(String solution, String path,
String action, String contents);

public abstract boolean writeFile(String solution, String path,
String artifact, ByteArrayOutputStream contents);

public abstract String getLocale(Domain domain);

public ReportTemplate[] getTemplateList(final IPentahoSession userSession);

public abstract String loadFile(String solution, String path, String action) throws IOException;

public abstract Document browse(String dir, String fileExtensions);

public abstract void cleanTemp();

boolean writeLocalFile(String path, String artifact, byte[] contents);

}