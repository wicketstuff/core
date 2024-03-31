/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.enums.EnumConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.jqplot.lib.metadata.JqPlotPlugin;
import org.wicketstuff.jqplot.lib.support.JqPlotJsonMapHierarchicalWriter;

/**
 * Utility class to provide json from {@link Chart} object.
 * 
 * @author inaiat
 */
public class JqPlotUtils {

    /**
     * Retorna os recursos de javascript
     * @param chart Chart
     * @return lista de com os javascripts dos gráficos
     */
    public static List<String> retriveJavaScriptResources(Chart<?> chart) {
        List<String> resources = new ArrayList<String>();

        Class<?> clazz = chart.getClass();
        if (clazz.isAnnotationPresent(JqPlotPlugin.class)) {
            JqPlotResources[] jqPlotResourceses = clazz.getAnnotation(
                    JqPlotPlugin.class).values();
            for (JqPlotResources jqPlotResources : jqPlotResourceses) {
                resources.add(jqPlotResources.getResource());
            }
        }

        //can it/should we make this more generic?
        if(chart.getChartConfiguration().getHighlighter() != null) {
            resources.add(JqPlotResources.Highlighter.getResource());
        }

        return resources;
    }

    /**
     * Cria um comando jquery
     * @param chart Chart
     * @param divId id of element div
     * @return jquery javascript
     */
    public static String createJquery(Chart<?> chart, String divId) {
        return createJquery(chart, divId, null);
    }

    /**
     * Cria um comando jquery
     * @param chart Chart
     * @param divId id of element div
     * @param javaScriptVar javascript variable
     * @return jquery javascript
     */
    public static String createJquery(Chart<?> chart, String divId, String javaScriptVar) {
        StringBuilder builder = new StringBuilder();
        builder.append("$(document).ready(function(){\r\n");
        if (javaScriptVar != null) {
            builder.append("   var ").append(javaScriptVar).append("=");
        }
        builder.append("   $.jqplot('").append(divId).append("', ");
        builder.append(chart.getChartData().toJsonString());
        builder.append(", ");
        builder.append(jqPlotToJson(chart.getChartConfiguration()));
        builder.append(");\r\n");
        builder.append("});\r\n");
        return builder.toString();
    }

    /**
     * Retorna o um json a partir de uma configuração jqplot
     * @param jqPlot ChartConfiguration
     * @return String of JSON content
     */
    public static String jqPlotToJson(ChartConfiguration<?> jqPlot) {

        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {

            @Override
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JqPlotJsonMapHierarchicalWriter(writer, JsonWriter.DROP_ROOT_MODE) {

                    @Override
                    public void addAttribute(String name, String value) {
                        if (!name.contains("class")) {
                            super.addAttribute(name, value);
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
                if(source instanceof JqPlotResources) {
                    JqPlotResources plugin = (JqPlotResources) source;
                    writer.setValue(plugin.getClassName());
                } else {
                    super.marshal(source, writer, context);
                }

            }
        };

        converter.canConvert(JqPlotResources.class);

        xstream.registerConverter(converter);

        return xstream.toXML(jqPlot);
    }
}