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

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.IFileInfo;
import org.pentaho.platform.api.engine.ISolutionFile;
import org.pentaho.platform.api.engine.SolutionFileMetaAdapter;
import org.pentaho.platform.engine.core.solution.FileInfo;

/**
 * Retrieve content metadata from the .adhoc content file.
 * 
 * @author Mgiepz
 */
public class AdhocContentTypeMetaProvider extends SolutionFileMetaAdapter {

    private static final Log LOG = LogFactory.getLog(AdhocContentTypeMetaProvider.class);

    public AdhocContentTypeMetaProvider() {};
    
    public IFileInfo getFileInfo(ISolutionFile solutionFile, InputStream in) {
        try {
            String title = solutionFile.getFileName();
            if (title != null && title.endsWith(".adhoc")) {
            	title = title.substring(0,title.indexOf(".adhoc"));
            }

            IFileInfo info = new FileInfo();
            info.setAuthor("");
            info.setDescription("");
            info.setIcon(PluginConfig.ICON);
            info.setTitle(title);
            return info;

        } catch (Exception e) {
            LOG.error(getClass().toString(), e);
        }
        return null;
    }
}
