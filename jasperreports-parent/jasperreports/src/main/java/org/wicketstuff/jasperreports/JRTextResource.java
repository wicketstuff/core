/*
 * $Id$
 * $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.jasperreports;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

/**
 * Resource class for jasper reports text resources.
 * 
 * @author Eelco Hillenius
 */
public final class JRTextResource extends JRResource
{
	/**
	 * an integer representing the page width in characters.
	 */
	private int pageWidth = 100;

	/**
	 * an integer representing the page height in characters.
	 */
	private int pageHeight = 100;

	/**
	 * Construct without a report. You must provide a report before you can use
	 * this resource.
	 */
	public JRTextResource()
	{
		super();
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRTextResource(InputStream report)
	{
		super(report);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRTextResource(URL report)
	{
		super(report);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRTextResource(File report)
	{
		super(report);
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#newExporter()
	 */
	public JRAbstractExporter newExporter()
	{
		JRTextExporter exporter = new JRTextExporter();
		exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, new Integer(pageWidth));
		exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT,
				new Integer(pageHeight));
		return exporter;
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getContentType()
	 */
	public String getContentType()
	{
		return "text/plain";
	}

	/**
	 * Gets page height.
	 * 
	 * @return an integer representing the page height in characters
	 */
	public int getPageHeight()
	{
		return pageHeight;
	}

	/**
	 * Sets page height.
	 * 
	 * @param height
	 *            an integer representing the page height in characters
	 */
	public void setPageHeight(int height)
	{
		this.pageHeight = height;
	}

	/**
	 * Gets page width.
	 * 
	 * @return an integer representing the page width in characters
	 */
	public int getPageWidth()
	{
		return pageWidth;
	}

	/**
	 * Sets page width.
	 * 
	 * @param width
	 *            an integer representing the page width in characters
	 */
	public void setPageWidth(int width)
	{
		this.pageWidth = width;
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getExtension()
	 */
	public String getExtension()
	{
		return "txt";
	}
}
