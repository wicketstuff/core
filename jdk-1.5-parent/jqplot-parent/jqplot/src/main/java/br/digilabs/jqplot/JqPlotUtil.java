package br.digilabs.jqplot;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


import br.digilabs.jqplot.chart.Chart;
import br.digilabs.jqplot.chart.config.ChartConfiguration;
import br.digilabs.jqplot.metadata.JqPlotPlugin;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.enums.EnumConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * 
 * @author inaiat
 */
public class JqPlotUtil {

	public static List<String> retriveJavaScriptResources(Chart<?> chart) {
		List<String> resources = new ArrayList<String>();
		Class<?> klazz = chart.getClass();
		if (klazz.isAnnotationPresent(JqPlotPlugin.class)) {
			JqPlotResources[] jqPlotResourceses = klazz.getAnnotation(
					JqPlotPlugin.class).values();
			for (JqPlotResources jqPlotResources : jqPlotResourceses) {
				resources.add(jqPlotResources.getResource());
			}
		}
		return resources;
	}

	public static String createJquery(Chart<?> chart, String divId) {
		StringBuilder builder = new StringBuilder();
		builder.append("$(document).ready(function(){\r\n");
		builder.append("   $.jqplot('").append(divId).append("', ");
		builder.append(chart.getChartData().toJsonString());
		builder.append(", ");
		builder.append(jqPlotToJson(chart.getChartConfiguration()));
		builder.append(");\r\n");
		builder.append("});\r\n");
		return builder.toString();
	}

	public static String jqPlotToJson(ChartConfiguration jqPlot) {

		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {

			@Override
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE) {

					@Override
					public void addAttribute(String name, String value) {
						if (!name.contains("class")) {
							super.addAttribute(name, value);
						}
					}

					@Override
					protected void addValue(String value, Type type) {
						// TODO: See if it's the best way to do this.
						// Passing null to avoid having quotes on $. object.
						// With null is being serialized like a JSObject.
						if (value.contains("$")) {
							super.addValue(value, null);
						} else {
							super.addValue(value, type);
						}
					}
				};
			}
		}) {
		};

		EnumConverter converter = new EnumConverter() {

			@Override
			public void marshal(Object source, HierarchicalStreamWriter writer,
					MarshallingContext context) {
				JqPlotResources plugin = (JqPlotResources) source;
				writer.setValue(plugin.getClassName());
			}
		};

		converter.canConvert(JqPlotResources.class);

		xstream.registerConverter(converter);

		return xstream.toXML(jqPlot);
	}

}
