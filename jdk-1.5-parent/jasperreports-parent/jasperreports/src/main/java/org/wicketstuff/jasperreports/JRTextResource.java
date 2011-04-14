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

import org.wicketstuff.jasperreports.handlers.TextResourceHandler;

/**
 * Resource class for jasper reports text resources.
 * 
 * @author Eelco Hillenius
 * @author cdeal
 * @deprecated Use JRConcreteResource(*, new TextResourceHandler())
 */
@Deprecated
public final class JRTextResource extends JRConcreteResource<TextResourceHandler>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct without a report. You must provide a report before you can use this resource.
	 */
	public JRTextResource()
	{
		super(new TextResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRTextResource(InputStream report)
	{
		super(report, new TextResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRTextResource(URL report)
	{
		super(report, new TextResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRTextResource(File report)
	{
		super(report, new TextResourceHandler());
	}

	/**
	 * Gets page height.
	 * 
	 * @return an integer representing the page height in characters
	 */
	public int getPageHeight()
	{
		return getHandler().getPageHeight();
	}

	/**
	 * Sets page height.
	 * 
	 * @param height
	 *            an integer representing the page height in characters
	 */
	public void setPageHeight(int height)
	{
		getHandler().setPageHeight(height);
	}

	/**
	 * Gets page width.
	 * 
	 * @return an integer representing the page width in characters
	 */
	public int getPageWidth()
	{
		return getHandler().getPageWidth();
	}

	/**
	 * Sets page width.
	 * 
	 * @param width
	 *            an integer representing the page width in characters
	 */
	public void setPageWidth(int width)
	{
		getHandler().setPageWidth(width);
	}
}
