/*
 * $Id$ $Revision$
 * $Date$
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.wicketstuff.jasperreports.examples;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jasperreports.*;

/**
 * Simple Jasper reports example with PDF output and a jasper reports panel..
 * 
 * @author Eelco Hillenius
 */
public class ReportLinksPage extends WebPage {
	/**
	 * Constructor.
	 */
	public ReportLinksPage() {
		ServletContext context = ((WebApplication) getApplication()).getServletContext();
		final File reportFile = new File(context.getRealPath("/reports/WebappReport.jasper"));

		final Map parameters = new HashMap();
		parameters.put("BaseDir", new File(context.getRealPath("/reports")));

		JRResource pdfResource = new JRPdfResource(reportFile).setReportParameters(parameters).setReportDataSource(new WebappDataSource());
		add(new ResourceLink("linkToPdf", pdfResource));

		JRResource rtfResource = new JRRtfResource(reportFile).setReportParameters(parameters).setReportDataSource(new WebappDataSource());
		add(new ResourceLink("linkToRtf", rtfResource));

		JRResource htmlResource = new JRHtmlResource(reportFile).setReportParameters(parameters).setReportDataSource(new WebappDataSource());
		add(new ResourceLink("linkToHtml", htmlResource));

		JRResource textResource = new JRTextResource(reportFile).setReportParameters(parameters).setReportDataSource(new WebappDataSource());
		add(new ResourceLink("linkToText", textResource));

		JRResource imageResource = new JRImageResource(reportFile).setReportParameters(parameters).setReportDataSource(new WebappDataSource());
		add(new ResourceLink("linkToImage", imageResource));

		JRResource csvResource = new JRCsvResource(reportFile).setReportParameters(parameters).setReportDataSource(new WebappDataSource());
		add(new ResourceLink("linkToCsv", csvResource));
	}

	/**
	 * @see wicket.Component#isVersioned()
	 */
	public boolean isVersioned() {
		return false;
	}
}