/* 
 * Copyright 2017 Dieter Tremel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.gchart;

/**
 * Selection of chart types.
 * Complete for classic charts, but does not implement the
 * <a href="https://developers.google.com/chart/interactive/docs/gallery/linechart#creating-material-line-charts">Material chart types</a>.
 *
 * @author Dieter Tremel
 */
public enum ChartType implements JavaScriptable {
    ANNOTATION("AnnotationChart", "annotationchart"),
    AREA("AreaChart"),
    BAR("BarChart"),
    BUBBLE("BubbleChart"),
    CALENDAR("Calendar", "calendar"),
    CANDLESTICK("CandlestickChart"),
    COLUMN("ColumnChart"),
    COMBO("ComboChart"),
    GANTT("Gantt", "gantt"),
    GAUGE("Gauge", "gauge"),
    /** Geocharts need Maps API key, see {@link Chart#setMapsApiKey(java.lang.String) } */
    GEO("GeoChart", "geochart"),
    HISTOGRAM("Histogram"),
    LINE("LineChart"),
    LINE_MATERIAL("Line", "line"),
    /** Map charts need Maps API key, see {@link Chart#setMapsApiKey(java.lang.String) } */
    MAP("Map", "map"),
    ORG("OrgChart", "orgchart"),
    /** PieChart. For Donut charts use this with Option {@code pieHole}. */
    PIE("PieChart"),
    SANKEY("Sankey", "sankey"),
    SCATTER("ScatterChart"),
    STEPPEDAREA("SteppedAreaChart"),
    TABLE("Table", "table"),
    TIMELINE("Timeline", "timeline"),
    TREEMAP("TreeMap", "treemap"),
    WORDTREE("WordTree", "wordtree");

    /** Name of Google chart core package */
    private static final String CORE_PACKAGE = "corechart";

    private final String chartClass;
    private final String loadPackage;

    private ChartType(String chartClass) {
        this.chartClass = chartClass;
        this.loadPackage = CORE_PACKAGE;
    }

    private ChartType(String chartClass, String loadPackage) {
        this.chartClass = chartClass;
        this.loadPackage = loadPackage;
    }

    /**
     * Get the class name of the chart type.
     *
     * @return Class name of Google implementation.
     */
    public String getChartClass() {
        return chartClass;
    }

    /**
     * Package name of the type to use in the loader.
     *
     * @return Name of the charts package name. This defaults to
     * {@link #CORE_PACKAGE} for most types.
     */
    public String getLoadPackage() {
        return loadPackage;
    }

    @Override
    public String toJavaScript() {
        return "google.visualization." + getChartClass();
    }
}
