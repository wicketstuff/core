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

import org.wicketstuff.jasperreports.handlers.HtmlResourceHandler;

/**
 * Resource class for jasper reports HTML resources.
 * 
 * @author Eelco Hillenius
 * @author cdeal
 * @deprecated Use JRConcreteResource(*, new HtmlResourceHandler())
 */
@Deprecated
public final class JRHtmlResource extends JRConcreteResource<HtmlResourceHandler>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct without a report. You must provide a report before you can use this resource.
	 */
	public JRHtmlResource()
	{
		super(new HtmlResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRHtmlResource(InputStream report)
	{
		super(report, new HtmlResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRHtmlResource(URL report)
	{
		super(report, new HtmlResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRHtmlResource(File report)
	{
		super(report, new HtmlResourceHandler());
	}
}
