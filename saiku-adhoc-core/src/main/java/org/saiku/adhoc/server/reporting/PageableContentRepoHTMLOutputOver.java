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

import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.api.repository.IContentLocation;
import org.pentaho.platform.api.repository.IContentRepository;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlPrinter;
import org.pentaho.reporting.libraries.repository.ContentIOException;
import org.pentaho.reporting.libraries.repository.ContentLocation;
import org.pentaho.reporting.libraries.repository.DefaultNameGenerator;
import org.pentaho.reporting.libraries.repository.stream.StreamRepository;
import org.pentaho.reporting.platform.plugin.messages.Messages;
import org.pentaho.reporting.platform.plugin.output.PageableContentRepoHtmlOutput;
import org.pentaho.reporting.platform.plugin.repository.PentahoNameGenerator;
import org.pentaho.reporting.platform.plugin.repository.ReportContentRepository;

public class PageableContentRepoHTMLOutputOver extends PageableContentRepoHtmlOutput{
    private String reportName;

    public PageableContentRepoHTMLOutputOver(String contentHandlerPattern) {
        super(contentHandlerPattern);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void reinitOutputTarget() throws ReportProcessingException, ContentIOException
    {
      final IPentahoSession session = PentahoSessionHolder.getSession();
      final String solutionPath = "report-content" + "/" + reportName + "/"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      final String thePath = solutionPath + session.getId() + "-" + System.currentTimeMillis();//$NON-NLS-1$//$NON-NLS-2$
      final IContentRepository contentRepository = PentahoSystem.get(IContentRepository.class, session);
      final IContentLocation pentahoContentLocation = contentRepository.newContentLocation(thePath, reportName, reportName, solutionPath, true);

      final ReportContentRepository repository = new ReportContentRepository(pentahoContentLocation, reportName);
      final ContentLocation dataLocation = repository.getRoot();
      final PentahoNameGenerator dataNameGenerator = PentahoSystem.get(PentahoNameGenerator.class);
      if (dataNameGenerator == null)
      {
        throw new IllegalStateException
            (Messages.getInstance().getString("ReportPlugin.errorNameGeneratorMissingConfiguration"));
      }
      dataNameGenerator.initialize(dataLocation, true);


      final StreamRepository targetRepository = new StreamRepository(null, getProxyOutputStream(), "report"); //$NON-NLS-1$
      final ContentLocation targetRoot = targetRepository.getRoot();

      final HtmlPrinter printer = getPrinter();
      printer.setContentWriter(targetRoot, new DefaultNameGenerator(targetRoot, "index", "html"));//$NON-NLS-1$//$NON-NLS-2$
      printer.setDataWriter(dataLocation, dataNameGenerator);
    }
}
