/*
 * $Id$ $Revision$
 * $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.jasperreports.examples;

import java.io.File;

import javax.servlet.ServletContext;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.jasperreports.JRCsvResource;
import wicket.contrib.jasperreports.JRHtmlResource;
import wicket.contrib.jasperreports.JRImageResource;
import wicket.contrib.jasperreports.JRPdfResource;
import wicket.contrib.jasperreports.JRResource;
import wicket.contrib.jasperreports.JRRtfResource;
import wicket.contrib.jasperreports.JRTextResource;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Simple Jasper reports example with PDF output and a jasper reports panel..
 * 
 * @author Eelco Hillenius
 */
public class ReportLinksPage extends WicketExamplePage
{
	/**
	 * Constructor.
	 */
	public ReportLinksPage()
	{
		ServletContext context = ((WebApplication) getApplication()).getServletContext();
		final File reportFile = new File(context.getRealPath("/reports/example.jasper"));

		JRResource pdfResource = new JRPdfResource(reportFile)
				.setReportDataSource(new ExampleDataSource());
		add(new ResourceLink("linkToPdf", pdfResource));

		JRResource rtfResource = new JRRtfResource(reportFile)
				.setReportDataSource(new ExampleDataSource());
		add(new ResourceLink("linkToRtf", rtfResource));

		JRResource htmlResource = new JRHtmlResource(reportFile)
				.setReportDataSource(new ExampleDataSource());
		add(new ResourceLink("linkToHtml", htmlResource));

		JRResource textResource = new JRTextResource(reportFile)
				.setReportDataSource(new ExampleDataSource());
		add(new ResourceLink("linkToText", textResource));

		JRResource imageResource = new JRImageResource(reportFile)
				.setReportDataSource(new ExampleDataSource());
		add(new ResourceLink("linkToImage", imageResource));

		JRResource csvResource = new JRCsvResource(reportFile)
				.setReportDataSource(new ExampleDataSource());
		add(new ResourceLink("linkToCsv", csvResource));
	}

	/**
	 * @see wicket.Component#isVersioned()
	 */
	public boolean isVersioned()
	{
		return false;
	}
}