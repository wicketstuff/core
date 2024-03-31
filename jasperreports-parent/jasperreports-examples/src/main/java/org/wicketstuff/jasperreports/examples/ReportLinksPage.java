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

import jakarta.servlet.ServletContext;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jasperreports.JRConcreteResource;
import org.wicketstuff.jasperreports.JRImageResource;
import org.wicketstuff.jasperreports.JRResource;
import org.wicketstuff.jasperreports.handlers.CsvResourceHandler;
import org.wicketstuff.jasperreports.handlers.HtmlResourceHandler;
import org.wicketstuff.jasperreports.handlers.PdfResourceHandler;
import org.wicketstuff.jasperreports.handlers.RtfResourceHandler;
import org.wicketstuff.jasperreports.handlers.TextResourceHandler;

/**
 * Simple Jasper reports example with PDF output and a jasper reports panel..
 * 
 * @author Eelco Hillenius
 */
public class ReportLinksPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ReportLinksPage()
	{
		ServletContext context = ((WebApplication)getApplication()).getServletContext();
		final File reportFile = new File(context.getRealPath("/reports/WebappReport.jasper"));

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("BaseDir", new File(context.getRealPath("/reports")));

		JRResource pdfResource = new JRConcreteResource<PdfResourceHandler>(reportFile,
			new PdfResourceHandler()).setReportParameters(parameters).setReportDataSource(
			new WebappDataSource());
		add(new ResourceLink<Void>("linkToPdf", pdfResource));

		JRResource rtfResource = new JRConcreteResource<RtfResourceHandler>(reportFile,
			new RtfResourceHandler()).setReportParameters(parameters).setReportDataSource(
			new WebappDataSource());
		add(new ResourceLink<Void>("linkToRtf", rtfResource));

		JRResource htmlResource = new JRConcreteResource<HtmlResourceHandler>(reportFile,
			new HtmlResourceHandler()).setReportParameters(parameters).setReportDataSource(
			new WebappDataSource());
		add(new ResourceLink<Void>("linkToHtml", htmlResource));

		JRResource textResource = new JRConcreteResource<TextResourceHandler>(reportFile,
			new TextResourceHandler()).setReportParameters(parameters).setReportDataSource(
			new WebappDataSource());
		add(new ResourceLink<Void>("linkToText", textResource));

		JRResource imageResource = new JRImageResource(reportFile).setReportParameters(parameters)
			.setReportDataSource(new WebappDataSource());
		add(new ResourceLink<Void>("linkToImage", imageResource));

		JRResource csvResource = new JRConcreteResource<CsvResourceHandler>(reportFile,
			new CsvResourceHandler()).setReportParameters(parameters).setReportDataSource(
			new WebappDataSource());
		add(new ResourceLink<Void>("linkToCsv", csvResource));
	}

	/**
	 * @see org.apache.wicket.Component#isVersioned()
	 */
	@Override
	public boolean isVersioned()
	{
		return false;
	}
}