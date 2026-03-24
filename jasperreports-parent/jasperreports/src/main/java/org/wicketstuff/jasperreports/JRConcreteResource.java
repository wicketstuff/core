/*
 * $Id: JRGenericResource.java 5335 2010-06-14 13:28:39Z cdeal $ $Revision:
 * 1.3 $ $Date: 2010-06-14 09:28:39 -0400 (Mon, 14 Jun 2010) $
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

import org.wicketstuff.jasperreports.handlers.IJRResourceHandler;

/**
 * A concrete implementation of a JRResource that delegates the common methods to a handler
 * 
 * @author cdeal
 */
public class JRConcreteResource<H extends IJRResourceHandler> extends JRResource
{
	private static final long serialVersionUID = 1L;
	private H handler = null;

	/**
	 * Constructor for JRConcreteResource
	 * 
	 * @param handler
	 */
	public JRConcreteResource(H handler)
	{
		super();
		setHandler(handler);
	}

	/**
	 * Constructor for JRConcreteResource
	 * 
	 * @param report
	 * @param handler
	 */
	public JRConcreteResource(InputStream report, H handler)
	{
		super(report);
		setHandler(handler);
	}

	/**
	 * Constructor for JRConcreteResource
	 * 
	 * @param report
	 * @param handler
	 */
	public JRConcreteResource(URL report, H handler)
	{
		super(report);
		setHandler(handler);
	}

	/**
	 * Constructor for JRConcreteResource
	 * 
	 * @param report
	 * @param handler
	 */
	public JRConcreteResource(File report, H handler)
	{
		super(report);
		setHandler(handler);
	}

	/**
	 * Constructor for JRConcreteResource
	 * 
	 * @param factory
	 * @param handler
	 */
	public JRConcreteResource(IJasperReportFactory factory, H handler)
	{
		super(factory);
		setHandler(handler);
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getContentType()
	 */
	@Override
	public final String getContentType()
	{
		return handler.getContentType();
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getExtension()
	 */
	@Override
	public final String getExtension()
	{
		return handler.getExtension();
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#newExporter()
	 */
	@Override
	public final JRAbstractExporter newExporter()
	{
		return handler.newExporter();
	}

	/**
	 * @return Returns the handler.
	 */
	public final H getHandler()
	{
		return handler;
	}

	/**
	 * @param handler
	 *            The handler to set.
	 */
	public final void setHandler(H handler)
	{
		if (handler == null)
		{
			throw new IllegalArgumentException("handler is reuqired!");
		}
		this.handler = handler;
	}
}
