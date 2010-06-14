package wicket.contrib.jasperreports;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 * Resource class for jasper reports Excell resources.
 * 
 * @author Justin Lee
 */
public final class JRXlsResource extends JRResource
{
	/**
	 * Construct without a report. You must provide a report before you can use
	 * this resource.
	 */
	public JRXlsResource()
	{
		super();
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRXlsResource(InputStream report)
	{
		super(report);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRXlsResource(URL report)
	{
		super(report);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRXlsResource(File report)
	{
		super(report);
	}

	/**
	 * @see JRResource#newExporter()
	 */
	public JRAbstractExporter newExporter()
	{
		return new JRXlsExporter();
	}

	/**
	 * @see JRResource#getContentType()
	 */
	public String getContentType()
	{
		return "application/excel";
	}

	/**
	 * @see wicket.contrib.jasperreports.JRResource#getExtension()
	 */
	public String getExtension()
	{
		return "xls";
	}
}