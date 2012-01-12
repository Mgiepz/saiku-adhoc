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
package org.saiku.adhoc.server.reporting;

import java.io.File;

import org.pentaho.platform.api.engine.IApplicationContext;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.libraries.repository.ContentIOException;
import org.pentaho.reporting.libraries.repository.ContentLocation;
import org.pentaho.reporting.libraries.repository.DefaultNameGenerator;
import org.pentaho.reporting.libraries.repository.file.FileRepository;
import org.pentaho.reporting.libraries.repository.stream.StreamRepository;
import org.pentaho.reporting.platform.plugin.messages.Messages;
import org.pentaho.reporting.platform.plugin.output.PageableHTMLOutput;
import org.pentaho.reporting.platform.plugin.repository.PentahoNameGenerator;

public class PageableHTMLOutputOver extends PageableHTMLOutput{

    public PageableHTMLOutputOver(String contentHandlerPattern) {
        super(contentHandlerPattern);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void reinitOutputTarget() throws ReportProcessingException, ContentIOException {
        final IApplicationContext ctx = PentahoSystem.getApplicationContext();


        final ContentLocation dataLocation;
        final PentahoNameGenerator dataNameGenerator;
        if (ctx != null)
        {
          File dataDirectory = new File(ctx.getFileOutputPath("system/tmp/"));//$NON-NLS-1$
          if (dataDirectory.exists() && (dataDirectory.isDirectory() == false))
          {
            dataDirectory = dataDirectory.getParentFile();
            if (dataDirectory.isDirectory() == false)
            {
              throw new ReportProcessingException("Dead " + dataDirectory.getPath()); //$NON-NLS-1$
            }
          }
          else if (dataDirectory.exists() == false)
          {
            dataDirectory.mkdirs();
          }

          final FileRepository dataRepository = new FileRepository(dataDirectory);
          dataLocation = dataRepository.getRoot();
          dataNameGenerator = PentahoSystem.get(PentahoNameGenerator.class);
          if (dataNameGenerator == null)
          {
            throw new IllegalStateException
                (Messages.getInstance().getString("ReportPlugin.errorNameGeneratorMissingConfiguration"));
          }
          dataNameGenerator.initialize(dataLocation, true);
        }
        else
        {
          dataLocation = null;
          dataNameGenerator = null;
        }

        final StreamRepository targetRepository = new StreamRepository(null, getProxyOutputStream(), "report"); //$NON-NLS-1$
        final ContentLocation targetRoot = targetRepository.getRoot();

        getPrinter().setContentWriter(targetRoot, new DefaultNameGenerator(targetRoot, "index", "html"));//$NON-NLS-1$//$NON-NLS-2$
        getPrinter().setDataWriter(dataLocation, dataNameGenerator);
    }
}
