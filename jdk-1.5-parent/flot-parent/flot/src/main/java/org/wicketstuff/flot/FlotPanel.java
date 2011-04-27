/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.flot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlotPanel extends Panel
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(FlotPanel.class);

	private final Map<String, Map<String, Object>> options = new HashMap<String, Map<String, Object>>();
	private boolean showTooltip = false;

	public FlotPanel(final String id, final IModel<List<Series>> model)
	{
		super(id, model);

		String[] optionsKeys = { "lines", "points", "legend", "xaxis", "yaxis", "x2axis", "y2axis",
				"selection", "grid" };

		for (String key : optionsKeys)
			options.put(key, new HashMap<String, Object>());

		options.get("selection").put("mode", "xy");
		options.get("grid").put("hoverable", true);
		options.get("grid").put("clickable", true);

		// JN - commented out to allow autoscaling if not set
		// options.get("yaxis").put("min", 0);
		// options.get("yaxis").put("max", 15);

		// This custom component fills the <script> tag with the script returned by getFlotScript().
		add(new WebComponent("flotScript")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTagBody(final MarkupStream markupStream,
				final ComponentTag openTag)
			{
				replaceComponentTagBody(markupStream, openTag, getFlotScript());
			}

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				// always transform the tag to <span></span> so even labels defined as <span/>
// render
				tag.setType(XmlTag.TagType.OPEN);
			}
		});
	}

	/**
	 * Returns Javascript code which renders the flot graph.
	 * 
	 * @throws RuntimeException
	 *             if FlotPanel.js cannot be loaded.
	 */
	private String getFlotScript()
	{
		try
		{
			final String str = getResourceContents(FlotPanel.class, "FlotPanel.js");

			LOGGER.info("{}", options);

			// final String options =
// "{ lines: { show: true }, points: { show: true }, selection: { mode: \"xy\" }, grid: { hoverable: true, clickable: true }, xaxis: { mode: 'time', min: 1243807200000, max: 1246312800000, timeformat: '%d.%m.%y' }, yaxis: { min: 0, max: 15 }}";

			@SuppressWarnings("unchecked")
			final List<Series> data = (List<Series>)getDefaultModelObject();

			final StringBuffer strData = new StringBuffer();
			strData.append("[");
			for (Series dataEntry : data)
			{
				strData.append(dataEntry.toString());
				strData.append(", ");
			}
			if (data.size() > 0)
				strData.setLength(strData.length() - 2);
			strData.append("]");

			String strOptions = mapToString(options);

			return String.format(str, strData, strOptions, (showTooltip ? "true" : "false"));
		}
		catch (IOException e)
		{
			throw new RuntimeException("Unable to load FlotPanel.js", e);
		}
	}

	private static String mapToString(final Map<?, ?> map)
	{
		final StringBuilder str = new StringBuilder();

		str.append("{");

		boolean first = true;

		for (Map.Entry<?, ?> entry : map.entrySet())
		{
			if (!first)
				str.append(", ");
			else
				first = false;

			str.append(entry.getKey());
			str.append(": ");

			Object value = entry.getValue();

			if (Map.class.isAssignableFrom(value.getClass()))
				str.append(mapToString((Map<?, ?>)value));
			else
			{
				boolean valueIsString = value.getClass().equals(String.class);

				if (valueIsString)
					str.append("\"");
				str.append(value);
				if (valueIsString)
					str.append("\"");
			}
		}

		str.append("}");

		return str.toString();
	}

	public void setAxisTimeModeX(boolean timeMode)
	{
		String mode = timeMode ? "time" : "null";
		options.get("xaxis").put("mode", mode);
	}

	public void setAxisTimeformatX(String timeFormat)
	{
		options.get("xaxis").put("timeformat", timeFormat);
	}

	public void setAxisMinX(Double axisMinX)
	{
		options.get("xaxis").put("min", axisMinX);
	}

	public Double getAxisMinX()
	{
		return (Double)options.get("xaxis").get("min");
	}

	public void setAxisMinY(Double axisMinY)
	{
		options.get("yaxis").put("min", axisMinY);
	}

	public Double getAxisMinY()
	{
		return (Double)options.get("yaxis").get("min");
	}

	public void setAxisMaxX(Double axisMaxX)
	{
		options.get("xaxis").put("max", axisMaxX);
	}

	public Double getAxisMaxX()
	{
		return (Double)options.get("xaxis").get("max");
	}

	public void setAxisMaxY(Double axisMaxY)
	{
		options.get("yaxis").put("max", axisMaxY);
	}

	public Double getAxisMaxY()
	{
		return (Double)options.get("yaxis").get("max");
	}

	public void setAxisTicksX(TickCollection tickCollection)
	{
		options.get("xaxis").put("ticks", tickCollection);
	}

	public TickCollection getAxisTicksX()
	{
		return (TickCollection)options.get("xaxis").get("ticks");
	}

	public void setAxisTicksY(TickCollection tickCollection)
	{
		options.get("yaxis").put("ticks", tickCollection);
	}

	public TickCollection getAxisTicksY()
	{
		return (TickCollection)options.get("yaxis").get("ticks");
	}

	public void setLegendPosition(LegendPosition position)
	{
		options.get("legend").put("position", position.getPosition());
	}

	public LegendPosition getLegendPosition()
	{
		return LegendPosition.toLegendPosition((String)options.get("legend").get("position"));
	}

	public void setForegroundColor(Color color)
	{
		options.get("grid").put("color", color);
	}

	public Color getForegroundColor()
	{
		return (Color)options.get("grid").get("color");
	}

	public void setBackgroundColor(Color color)
	{
		options.get("grid").put("backgroundColor", color);
	}

	public Color getBackgroundColor()
	{
		return (Color)options.get("grid").get("backgroundColor");
	}

	public void setTickColor(Color color)
	{
		options.get("grid").put("tickColor", color);
	}

	public Color getTickColor()
	{
		return (Color)options.get("grid").get("tickColor");
	}

	public void setClickable(boolean clickable)
	{
		options.get("grid").put("clickable", clickable);
	}

	public boolean getClickable()
	{
		return (Boolean)options.get("grid").get("clickable");
	}

	public boolean getHoverable()
	{
		return (Boolean)options.get("grid").get("hoverable");
	}

	public void setHoverable(boolean hoverable)
	{
		options.get("grid").put("hoverable", hoverable);
	}

	public void showTooltip(boolean showTooltip)
	{
		this.showTooltip = showTooltip;
		if (showTooltip)
			setHoverable(true);
	}

	/**
	 * Returns the contents of the given resource.
	 * 
	 * @param location
	 *            The resource path.
	 * @return The resource's content.
	 * @throws FileNotFoundException
	 *             If the resource cannot be found.
	 * @throws IOException
	 *             If anything goes wrong while reading the resource.
	 */
	private static String getResourceContents(Class<?> clazz, String location) throws IOException
	{
		InputStream in = clazz.getResourceAsStream(location);
		if (in == null)
			throw new FileNotFoundException("'" + location + "' not found.");
		return getStreamContents(new InputStreamReader(in));
	}

	/**
	 * Returns the complete content of the given stream.
	 * 
	 * @throws IOException
	 *             If anything goes wrong while reading the stream.
	 */
	private static String getStreamContents(InputStreamReader inputStreamReader) throws IOException
	{
		StringBuffer str = new StringBuffer();
		BufferedReader in = new BufferedReader(inputStreamReader);

		while (true)
		{
			String line = in.readLine();
			if (line == null)
				break;
			str.append(line);
			str.append("\n");
		}

		return str.toString();
	}
}
