/*
 * $Id$ $Revision:
 * 1.3 $ $Date$
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

import org.wicketstuff.jasperreports.handlers.CsvResourceHandler;

/**
 * Resource class for jasper reports CSV resources.
 * 
 * @author Eelco Hillenius
 * @author cdeal
 * @deprecated Use JRConcreteResource(*, new CsvResourceHandler())
 */
@Deprecated
public final class JRCsvResource extends JRConcreteResource<CsvResourceHandler>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct without a report. You must provide a report before you can use this resource.
	 */
	public JRCsvResource()
	{
		super(new CsvResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRCsvResource(InputStream report)
	{
		super(report, new CsvResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRCsvResource(URL report)
	{
		super(report, new CsvResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRCsvResource(File report)
	{
		super(report, new CsvResourceHandler());
	}
}
