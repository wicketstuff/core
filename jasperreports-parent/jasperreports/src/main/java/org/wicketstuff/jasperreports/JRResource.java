/*
 * $Id$ $Revision:
 * 1.4 $ $Date$
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
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.io.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for jasper reports resources.
 * 
 * @author Eelco Hillenius
 * @author Matej Knopp
 * @author Luciano Montebove
 */
@SuppressWarnings("serial")
public abstract class JRResource extends AbstractResource
{
	/**
	 * logger.
	 */
	private static Logger log = LoggerFactory.getLogger(JRResource.class);

	/**
	 * the connection provider if any for filling this report.
	 */
	private IDatabaseConnectionProvider connectionProvider;

	/**
	 * factory for delayed report creation.
	 */
	private IJasperReportFactory jasperReportFactory;

	/**
	 * The compiled report this resource references. Made transient as we don't want our report to
	 * be serialized while we can recreate it at other servers at will using the factory.
	 */
	private transient JasperReport jasperReport;

	/**
	 * the report parameters.
	 */
	private Map<String, Object> reportParameters;

	/**
	 * the datasource if any for filling this report.
	 */
	private JRDataSource reportDataSource;

	/**
	 * When set, a header 'Content-Disposition: attachment; filename="${fileName}"' will be added to
	 * the response, resulting in a download dialog. No magical extensions are added, so you should
	 * make sure the file has the extension you want yourself.
	 */
	private String fileName;

	/**
	 * Construct without a report. You must provide a report before you can use this resource.
	 */
	public JRResource()
	{
		super();
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRResource(final InputStream report)
	{
		this(new IJasperReportFactory()
		{
			public JasperReport newJasperReport() throws JRException
			{
				return (JasperReport)JRLoader.loadObject(report);
			}

			;
		});
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRResource(final URL report)
	{
		this(new IJasperReportFactory()
		{
			public JasperReport newJasperReport() throws JRException
			{
				return (JasperReport)JRLoader.loadObject(report);
			}

			;
		});
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JRResource(final File report)
	{
		this(new IJasperReportFactory()
		{
			public JasperReport newJasperReport() throws JRException
			{
				return (JasperReport)JRLoader.loadObject(report);
			}

			;
		});
	}

	/**
	 * Construct.
	 * 
	 * @param factory
	 *            report factory for lazy initialization
	 */
	public JRResource(IJasperReportFactory factory)
	{
		super();
		jasperReportFactory = factory;
	}

	/**
	 * Gets jasperReport. This implementation uses an internal factory to lazily create the report.
	 * After creation the report is cached (set as the jasperReport property). Override this method
	 * in case you want to provide some alternative creation/ caching scheme.
	 * 
	 * @return jasperReport
	 */
	public JasperReport getJasperReport()
	{
		// if the report has not yet been initialized and can be, initialize it
		if (jasperReport == null && jasperReportFactory != null)
		{
			try
			{
				setJasperReport(jasperReportFactory.newJasperReport());
			}
			catch (JRException e)
			{
				throw new WicketRuntimeException(e);
			}
		}
		return jasperReport;
	}

	/**
	 * Sets {bjasperReport.
	 * 
	 * @param report
	 *            report
	 */
	public final void setJasperReport(JasperReport report)
	{
		jasperReport = report;
	}

	/**
	 * Gets the report parameters. Returns a new copy of the reportParameters Map as JasperReports
	 * modifies it with not serializable objects
	 * 
	 * @return report parameters
	 */
	public Map<String, Object> getReportParameters()
	{
		return new HashMap<String, Object>(reportParameters);
	}

	/**
	 * Sets the report parameters.
	 * 
	 * @param params
	 *            report parameters
	 * 
	 * @return This
	 */
	public final JRResource setReportParameters(Map<String, Object> params)
	{
		reportParameters = params;
		return this;
	}

	/**
	 * Gets the datasource if any for filling this report.
	 * 
	 * @return the datasource if any for filling this report
	 */
	public JRDataSource getReportDataSource()
	{
		return reportDataSource;
	}

	/**
	 * Sets the datasource if any for filling this report.
	 * 
	 * @param dataSource
	 *            the datasource if any for filling this report
	 * 
	 * @return This
	 */
	public JRResource setReportDataSource(JRDataSource dataSource)
	{
		reportDataSource = dataSource;
		return this;
	}

	/**
	 * Gets the connection provider if any for filling this report.
	 * 
	 * @return the connection provider if any for filling this report
	 */
	public IDatabaseConnectionProvider getConnectionProvider()
	{
		return connectionProvider;
	}

	/**
	 * Sets the connection provider if any for filling this report.
	 * 
	 * @param provider
	 *            the connection provider if any for filling this report
	 * 
	 * @return This
	 */
	public final JRResource setConnectionProvider(IDatabaseConnectionProvider provider)
	{
		connectionProvider = provider;
		return this;
	}

	/**
	 * Gets the file name. When set, a header 'Content-Disposition: attachment;
	 * filename="${fileName}"' will be added to the response, resulting in a download dialog. No
	 * magical extensions are added, so you should make sure the file has the extension you want
	 * yourself.
	 * 
	 * @return the file name
	 */
	public String getFileName()
	{
		if (fileName == null)
		{
			fileName = getJasperReport().getName() + "." + getExtension();
		}
		return fileName;
	}

	/**
	 * Sets the file name. When set, a header 'Content-Disposition: attachment; filename="${name}"'
	 * will be added to the response, resulting in a download dialog. No magical extensions are
	 * added, so you should make sure the file has the extension you want yourself.
	 * 
	 * @param name
	 *            the file name
	 * 
	 * @return This
	 */
	public final JRResource setFileName(String name)
	{
		fileName = name;
		return this;
	}

	/**
	 * Called by getData to obtain an exporter instance.
	 * 
	 * @return an exporter instance
	 */
	public abstract JRAbstractExporter newExporter();


	/**
	 * @return The content type of the reports
	 */
	public abstract String getContentType();


	/**
	 * The default is to return the content as an attachment.
	 * 
	 * @return The content disposition of the reports
	 */
	public ContentDisposition getContentDisposition()
	{
		return ContentDisposition.ATTACHMENT;
	}


	/**
	 * Returns the extension for the resource's file. This string should not contain the leading "."
	 * 
	 * @return The extension for the resource's file.
	 */
	public abstract String getExtension();

	/**
	 * Creates a new {@link JasperPrint} instance. This instance is specific for this render, but it
	 * not yet designated for one output format only.
	 * 
	 * @return a new {@link JasperPrint} instance.
	 * 
	 * @throws JRException
	 */
	protected JasperPrint newJasperPrint() throws JRException
	{
		final JasperPrint jasperPrint;
		JasperReport report = getJasperReport();
		Map<String, Object> params = getReportParameters();
		JRDataSource dataSource = getReportDataSource();
		if (dataSource != null)
		{
			jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
		}
		else
		{
			IDatabaseConnectionProvider provider = null;
			try
			{
				provider = getConnectionProvider();
				if (provider == null)
				{
					throw new IllegalStateException(
						"JasperReportsResources must either have a JRDataSource, "
							+ "or a JDBC Connection provided");
				}
				jasperPrint = JasperFillManager.fillReport(report, params, provider.get());
			}
			finally
			{
				if (provider != null)
				{
					provider.release();
				}
			}
		}
		return jasperPrint;
	}

	@Override
	protected ResourceResponse newResourceResponse(Attributes attributes)
	{
		ResourceResponse resp = new ResourceResponse();
		resp.disableCaching();
		resp.setContentType(getContentType());
		resp.setFileName(getFileName());
		resp.setContentDisposition(getContentDisposition());
		if (resp.dataNeedsToBeWritten(attributes))
		{
			resp.setWriteCallback(new WriteCallback()
			{

				@Override
				public void writeData(Attributes attributes)
				{
					try
					{
						long t1 = System.currentTimeMillis();
						// get a print instance for exporting
						JasperPrint print = newJasperPrint();

						// get a fresh instance of an exporter for this report
						JRAbstractExporter exporter = newExporter();


						final byte[] data = getExporterData(print, exporter);

						attributes.getResponse().write(data);
						if (log.isDebugEnabled())
						{
							long t2 = System.currentTimeMillis();
							log.debug("loaded report data; bytes: " + data.length + " in " +
								(t2 - t1) + " miliseconds");
						}
					}
					catch (JRException e)
					{
						throw new WicketRuntimeException(e);
					}
				}
			});
		}
		return resp;
	}

	protected byte[] getExporterData(JasperPrint print, JRAbstractExporter exporter)
		throws JRException
	{
		// prepare a stream to trap the exporter's output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		// execute the export and return the trapped result
		exporter.exportReport();

		return baos.toByteArray();
	}

}