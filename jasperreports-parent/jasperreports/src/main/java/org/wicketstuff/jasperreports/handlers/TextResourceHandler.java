/*
 * $Id: TextResourceHandler.java 5335 2010-06-14 13:28:39Z cdeal $ $Revision:
 * 1.4 $ $Date: 2010-06-14 09:28:39 -0400 (Mon, 14 Jun 2010) $
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
package org.wicketstuff.jasperreports.handlers;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

/**
 * @author cdeal
 */
public class TextResourceHandler implements IJRResourceHandler, Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * an integer representing the page width in characters.
	 */
	private int pageWidth = 100;

	/**
	 * an integer representing the page height in characters.
	 */
	private int pageHeight = 100;

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
		pageHeight = height;
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
		pageWidth = width;
	}

	/**
	 * @see org.wicketstuff.jasperreports.handlers.IJRResourceHandler#newExporter()
	 */
	public JRAbstractExporter newExporter()
	{
		JRTextExporter exporter = new JRTextExporter();
		exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, Integer.valueOf(pageWidth));
		exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, Integer.valueOf(pageHeight));
		return exporter;
	}

	/**
	 * @see org.wicketstuff.jasperreports.handlers.IJRResourceHandler#getContentType()
	 */
	public String getContentType()
	{
		return "text/plain";
	}

	/**
	 * @see org.wicketstuff.jasperreports.handlers.IJRResourceHandler#getExtension()
	 */
	public String getExtension()
	{
		return "txt";
	}
}
