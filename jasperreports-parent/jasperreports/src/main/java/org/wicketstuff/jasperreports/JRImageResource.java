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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration;

import org.apache.wicket.WicketRuntimeException;

/**
 * Resource class for jasper reports PDF resources.
 *
 * @author Eelco Hillenius
 */
public final class JRImageResource extends JRResource
{
	private static final long serialVersionUID = 1L;

	/**
	 * Type of image (one of BufferedImage.TYPE_*).
	 */
	private int type = BufferedImage.TYPE_INT_RGB;

	/**
	 * The zoom ratio used for the export. The default value is 1.
	 */
	private float zoomRatio = 1;

	/**
	 * the image type. The default value is 'png'.
	 */
	private String format = "png";

	/**
	 * Construct without a report. You must provide a report before you can use this resource.
	 */
	public JRImageResource()
	{
		super();
	}

	/**
	 * Construct.
	 *
	 * @param report
	 *            the report input stream
	 */
	public JRImageResource(InputStream report)
	{
		super(report);
	}

	/**
	 * Construct.
	 *
	 * @param report
	 *            the report input stream
	 */
	public JRImageResource(URL report)
	{
		super(report);
	}

	/**
	 * Construct.
	 *
	 * @param report
	 *            the report input stream
	 */
	public JRImageResource(File report)
	{
		super(report);
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#newExporter()
	 */
	@Override
	public final JRAbstractExporter newExporter()
	{
		try
		{
			return new JRGraphics2DExporter();
		}
		catch (JRException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public ExporterOutput newExporterOutput(OutputStream os) {
		return null; // not used
	}

	@Override
	protected byte[] getExporterData(JasperPrint print, JRAbstractExporter exporter)
		throws JRException
	{
		// prepare a stream to trap the exporter's output
		exporter.setExporterInput(new SimpleExporterInput(print));

		// create an image object
		int width = (int)(print.getPageWidth() * getZoomRatio());
		int height = (int)(print.getPageHeight() * getZoomRatio());
		BufferedImage image = new BufferedImage(width, height, type);
		SimpleGraphics2DExporterOutput out = new SimpleGraphics2DExporterOutput();
		out.setGraphics2D(image.createGraphics());
		exporter.setExporterOutput(out);
		SimpleGraphics2DReportConfiguration cfg = new SimpleGraphics2DReportConfiguration();
		cfg.setZoomRatio(zoomRatio);
		exporter.setConfiguration(cfg);

		// execute the export and return the trapped result
		exporter.exportReport();
		return toImageData(image);
	}

	/**
	 * @param image
	 *            The image to turn into data
	 *
	 * @return The image data for this dynamic image
	 */
	protected byte[] toImageData(final BufferedImage image)
	{
		try
		{
			// Create output stream
			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			// Get image writer for format
			final ImageWriter writer = ImageIO.getImageWritersByFormatName(format).next();

			// Write out image
			writer.setOutput(ImageIO.createImageOutputStream(out));
			writer.write(image);

			// Return the image data
			return out.toByteArray();
		}
		catch (IOException e)
		{
			throw new WicketRuntimeException("Unable to convert dynamic image to stream", e);
		}
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getContentType()
	 */
	@Override
	public String getContentType()
	{
		return "image/" + format;
	}

	/**
	 * Gets the zoom ratio.
	 *
	 * @return the zoom ratio used for the export. The default value is 1
	 */
	public float getZoomRatio()
	{
		return zoomRatio;
	}

	/**
	 * Sets the zoom ratio.
	 *
	 * @param ratio
	 *            the zoom ratio used for the export. The default value is 1
	 */
	public void setZoomRatio(float ratio)
	{
		zoomRatio = ratio;
	}

	/**
	 * Gets the image type.
	 *
	 * @return the image type. The default value is 'png'
	 */
	public String getFormat()
	{
		return format;
	}

	/**
	 * Sets the image type.
	 *
	 * @param format
	 *            the image type. The default value is 'png'
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}

	/**
	 * Gets type of image (one of BufferedImage.TYPE_*).
	 *
	 * @return type of image
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * Sets type of image (one of BufferedImage.TYPE_*).
	 *
	 * @param type
	 *            type of image
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @see org.wicketstuff.jasperreports.JRResource#getExtension()
	 */
	@Override
	public String getExtension()
	{
		return getFormat();
	}
}