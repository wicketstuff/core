package org.wicketstuff.jasperreports;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.wicketstuff.jasperreports.handlers.XlsResourceHandler;

/**
 * Resource class for jasper reports Excel resources.
 * 
 * @author Justin Lee
 * @author cdeal
 * @deprecated Use JRConcreteResource(*, new XlsResourceHandler())
 */
@Deprecated
public final class JRXlsResource extends JRConcreteResource<XlsResourceHandler>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct without a report. You must provide a report before you can use this resource.
	 */
	public JRXlsResource()
	{
		super(new XlsResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRXlsResource(InputStream report)
	{
		super(report, new XlsResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRXlsResource(URL report)
	{
		super(report, new XlsResourceHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRXlsResource(File report)
	{
		super(report, new XlsResourceHandler());
	}
}