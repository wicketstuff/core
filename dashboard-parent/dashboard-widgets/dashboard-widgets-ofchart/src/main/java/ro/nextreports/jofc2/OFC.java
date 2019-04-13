/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
 */
package ro.nextreports.jofc2;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.SingleValueConverter;

import ro.nextreports.jofc2.model.Chart;
import ro.nextreports.jofc2.model.Text;
import ro.nextreports.jofc2.model.axis.Axis;
import ro.nextreports.jofc2.model.axis.Label;
import ro.nextreports.jofc2.model.axis.XAxis;
import ro.nextreports.jofc2.model.axis.XAxisLabels;
import ro.nextreports.jofc2.model.axis.YAxis;
import ro.nextreports.jofc2.model.elements.AnimatedElement;
import ro.nextreports.jofc2.model.elements.AreaHollowChart;
import ro.nextreports.jofc2.model.elements.BarChart;
import ro.nextreports.jofc2.model.elements.Element;
import ro.nextreports.jofc2.model.elements.FilledBarChart;
import ro.nextreports.jofc2.model.elements.HorizontalBarChart;
import ro.nextreports.jofc2.model.elements.LineChart;
import ro.nextreports.jofc2.model.elements.NullElement;
import ro.nextreports.jofc2.model.elements.PieChart;
import ro.nextreports.jofc2.model.elements.ScatterChart;
import ro.nextreports.jofc2.model.elements.ShapeChart;
import ro.nextreports.jofc2.model.elements.SketchBarChart;
import ro.nextreports.jofc2.model.elements.StackedBarChart;
import ro.nextreports.jofc2.model.elements.Tooltip;
import ro.nextreports.jofc2.model.metadata.Alias;
import ro.nextreports.jofc2.model.metadata.Converter;

/**
 * <p>
 * This is the class responsible for converting a Chart object into the JSON
 * string which feeds the charting widget. There is no need to make explicit use
 * of this class, but if necessary, there are several ways to do so:
 * </p>
 * <ol>
 * <li>The "instance" field contains a static instance of an OFC object.</li>
 * <li>The Chart object overrides toString() and uses this instance to render
 * itself.</li>
 * <li>For tricky threading situations, you may prefer to create and manage
 * instances of OFC yourself.</li>
 * </ol>
 * <p>
 * Theoretically, XStream (the JSON conversion library used here) is
 * thread-safe, but it does not hurt to have the option to synchronize or to
 * have thread local instances, whatever may be necessary.
 * </p>
 */
public class OFC {

	private static final Class<?>[] models = new Class<?>[] {
			Axis.class,
			Text.class,
			XAxis.class,
			YAxis.class,
			XAxisLabels.class,
			Label.class,
			Element.class,
			Axis.class,
			BarChart.class,
			PieChart.class,
			HorizontalBarChart.class,
			LineChart.class,
			ScatterChart.class,
			AreaHollowChart.class,
			PieChart.Slice.class,
			HorizontalBarChart.Bar.class,
			Label.Rotation.class,
			ScatterChart.Point.class,
			FilledBarChart.class,
			SketchBarChart.class,
            StackedBarChart.class,
			StackedBarChart.StackValue.class,
			StackedBarChart.Stack.class,
            StackedBarChart.Key.class,
            BarChart.Bar.class,
			FilledBarChart.Bar.class,
			SketchBarChart.Bar.class,
			LineChart.Dot.class,
            LineChart.Style.class,
            NullElement.class,
			Chart.class,
            ShapeChart.class,
            ShapeChart.Point.class,
            Tooltip.class,
            Tooltip.Type.class,
            AnimatedElement.class,
            AnimatedElement.OnShow.class
    };
	private final XStream converter = new XStream(new OFCJSONDriver());

	/**
	 * Sole constructor.
	 */
	public OFC() {
		for (Class<?> c : models) {
			doAlias(c);
			doRegisterConverter(c);
		}
	}

	public static OFC getInstance() {
		return LazyInstance.instance;
	}

	private void doAlias(Class<?> c) {
		if (c.isAnnotationPresent(Alias.class)) {
			converter.alias(c.getAnnotation(Alias.class).value(), c);
		}
		for (Field f : c.getDeclaredFields()) {
			if (f.isAnnotationPresent(Alias.class)) {
				converter.aliasField(f.getAnnotation(Alias.class).value(), c, f.getName());
			}
		}
	}

	private void doRegisterConverter(Class<?> c) {
		if (c.isAnnotationPresent(Converter.class)) {
			Class<? extends ConverterMatcher> clazz = c.getAnnotation(Converter.class).value();
			try {
				if (SingleValueConverter.class.isAssignableFrom(clazz)) {
					converter.registerConverter((SingleValueConverter) clazz.newInstance());
				} else {
					converter.registerConverter((com.thoughtworks.xstream.converters.Converter) clazz.newInstance());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Use this method in your applications to send data back to the chart
	 * widget.
	 *
	 * @param c
	 *           the chart to render
	 * @throws OFCException
	 *            when rendering fails
	 * @return the JSONified chart data
	 */
	public String render(Chart c) throws OFCException {
		String json = converter.toXML(c);
		try {
			return new JSONObject(json).getString(Chart.class.getName());
		} catch (JSONException je) {
			throw new OFCException(json, je);
		}
	}

	/**
	 * Use this method for debugging purposes.
	 *
	 * @param c
	 *           the chart to render
	 * @param indentationLevel
	 *           number of spaces to use for indentation
	 * @throws OFCException
	 *            when rendering fails
	 * @return pretty-printed JSONified chart data
	 */
	public String prettyPrint(Chart c, int indentationLevel) throws OFCException {
		String json = converter.toXML(c);
		try {
			return new JSONObject(json).getJSONObject(Chart.class.getName())
					.toString(indentationLevel);
		} catch (JSONException je) {
			throw new OFCException(json, je);
		}
	}

	/**
	 * Convenience method for converting Collections to Arrays. You can use this
	 * where the API has limited support for collections:
	 * getLabels().addLabels(OFC.toArray(stringList, String.class));
	 *
	 * @param collection
	 *           The collection to use
	 * @param type
	 *           The supertype for the collection. This will commonly be Integer,
	 *           Number, etc.
	 * @return the array of the collection
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> collection, Class<? extends T> type) {
		return collection.toArray((T[]) Array.newInstance(type, collection.size()));
	}

	/**
	 * Convenience method to generate labels from a collection of Objects, if so
	 * desired.
	 *
	 * @param source
	 *           the source collection holding Objects.
	 * @return a collection of all the objects toString() method invoked
	 */
	public static List<String> stringify(List<?> source) {
		List<String> strings = new ArrayList<String>(source.size());
		for (Object o : source) {
			strings.add(o.toString());
		}
		return strings;
	}

	/**
	 * Convenience method to generate labels from an array of disparate Objects.
	 *
	 * @param objects
	 *           the array of objects to convert
	 * @return a collection of all the objects toString() result
	 */
	public static String[] stringify(Object... objects) {
		return stringify(Arrays.asList(objects)).toArray(new String[objects.length]);
	}
}

class LazyInstance {

	protected static final OFC instance = new OFC();
}
