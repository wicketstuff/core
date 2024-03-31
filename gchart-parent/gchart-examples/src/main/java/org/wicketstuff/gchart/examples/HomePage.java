/*
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
package org.wicketstuff.gchart.examples;

import org.wicketstuff.gchart.Chart;
import org.wicketstuff.gchart.ChartLibLoaderBehavior;
import org.wicketstuff.gchart.ChartType;
import org.wicketstuff.gchart.gchart.options.ClassicOptionHelper;
import org.wicketstuff.gchart.ColumnDeclaration;
import org.wicketstuff.gchart.ColumnRole;
import org.wicketstuff.gchart.ColumnType;
import org.wicketstuff.gchart.DataCell;
import org.wicketstuff.gchart.DataRow;
import org.wicketstuff.gchart.DataTable;
import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.wicketstuff.gchart.gchart.options.OptionHelper;
import org.wicketstuff.gchart.gchart.options.OptionModifier;
import org.wicketstuff.gchart.gchart.options.builder.OptionBuilder;

import com.github.openjson.JSONArray;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONFunction;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ComponentModel;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    private ChartLibLoaderBehavior chartLibLoader;
    private Boolean stackedPercent = false;
    private Chart chartBar;

    public HomePage(final PageParameters parameters) {
        super(parameters);
        chartLibLoader = new ChartLibLoaderBehavior();
        add(chartLibLoader);
        // demonstrate different charts on one page
        add(createChartPie());
        add(createChartLine());
        add(createChartDualLine());
        chartBar = createChartBar();
        chartBar.setOutputMarkupId(true);
        add(chartBar);

        final PropertyModel<Boolean> stackedPercentModel = new PropertyModel<>(HomePage.this, "stackedPercent");
        AjaxCheckBox ajaxCheckBox = new AjaxCheckBox("stackedPercent", stackedPercentModel) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                log.trace("stackedPercent {}", stackedPercent ? "True" : "False");
                chartBar.configureAjaxUpdate(target);
            }
        };
        add(ajaxCheckBox);
    }

    public boolean isStackedPercent() {
        return stackedPercent;
    }

    public void setStackedPercent(boolean stackedPercent) {
        this.stackedPercent = stackedPercent;
    }

    /**
     * Minimum example as in
     * <a href="https://developers.google.com/chart/interactive/docs/quick_start">Quick
     * Start</a>.
     *
     * @return Configured Pie Chart as in quick start example.
     */
    private Chart createChartPie() {
        IModel<ChartOptions> optionsModel = new IModel<ChartOptions>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ChartOptions getObject() {
                ChartOptions opts = new ChartOptions("options");
                opts.put("title", "How Much Pizza I Ate Last Night");
//                opts.put("width", 400);
//                opts.put("height", 300);
//                opts.put("colors", new JSONArray("['#e0440e', '#e6693e', '#ec8f6e', '#f3b49f', '#f6c7b6']"));

                return opts;
            }
        };

        IModel<DataTable> dataModel = new IModel<DataTable>() {
            private static final long serialVersionUID = 1L;

            @Override
            public DataTable getObject() {
                List<ColumnDeclaration> colDefs;
                List<DataRow> rows;
                colDefs = new ArrayList<>(2);
                colDefs.add(new ColumnDeclaration(ColumnType.STRING, "Topping"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Slices"));

                rows = new ArrayList<>(5);
                rows.add(new DataRow(Arrays.asList(new Object[]{"Mushrooms", 3})));
                rows.add(new DataRow(Arrays.asList(new Object[]{"Onions", 1})));
                rows.add(new DataRow(Arrays.asList(new Object[]{"Olives", 1})));
                rows.add(new DataRow(Arrays.asList(new Object[]{"Zucchini", 1})));
                rows.add(new DataRow(Arrays.asList(new Object[]{"Pepperoni", 2})));
                return new DataTable("data", colDefs, rows);
            }
        };

        return new Chart("chartPie", Model.of(ChartType.PIE), optionsModel, dataModel, chartLibLoader);
    }

    /**
     * More complicated example: Line chart with three series and two axes. Make
     * use of OptionHelper and use labels from i18n resources. Make use of
     * explicit formatting of cell values and axis labels Simple
     * IModel could be used if no i18n from Resources is needed.
     * Shows use of {@link OptionModifier} to demonstrate axis and series
     * configuring. Resulting options:
     * {@code var options = {"pointSize":5,"hAxis":{"format":"hh:mm a","title":"Ortszeit 19.07.2017","viewWindow":{"min":new Date(2017, 6, 19, 0, 0),"max":new Date(2017, 6, 20, 0, 0)},"gridlines":{"count":8}},"vAxes":{"0":{"title":"Temperatur (°C)","viewWindow":{"min":10,"max":30}},"1":{"format":"percent","title":"Rel. Luftfeuchte %","viewWindow":{"min":0.3,"max":0.6},"gridlines":{"count":8}}},"series":{"0":{"targetAxisIndex":0},"1":{"targetAxisIndex":0},"2":{"targetAxisIndex":1}},"title":"Metar Temperatur"};}
     *
     * @return Line chart ready to use on page.
     */
    private Chart createChartLine() {
        IComponentAssignedModel<ChartOptions> optionsModel = new ComponentModel<ChartOptions>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ChartOptions getObject(Component component) {
                OptionHelper optionHelper = new ClassicOptionHelper((Chart) component);
                ChartOptions opts = new ChartOptions("options");
                optionHelper.addTitle(opts);
                opts.put("pointSize", 5);

                OptionModifier temperatureModifier = new OptionModifier() {
                    @Override
                    public void modify(ChartOptions options) {
                        ChartOptions viewWindow = new ChartOptions();
                        viewWindow.put("min", 10);
                        viewWindow.put("max", 30);
                        options.put("viewWindow", viewWindow);
                    }
                };
                OptionModifier humidityModifier = new OptionModifier() {
                    @Override
                    public void modify(ChartOptions options) {
                        options.put("format", "percent");
                        ChartOptions viewWindow = new ChartOptions();
                        viewWindow.put("min", 0.30);
                        viewWindow.put("max", 0.60);
                        options.put("viewWindow", viewWindow);
                        ChartOptions vAxisGridline = new ChartOptions();
                        vAxisGridline.put("count", 8);
                        options.put("gridlines", vAxisGridline);
                    }
                };

                optionHelper.addDualAxisOptions(opts,
                        new String[]{"Temp", "Humidity"},
                        new String[]{"Temp", "Temp", "Humidity"},
                        // add for use with no i18n: new String[]{"Temperatur", "Rel. Luftfeuchte"},
                        new OptionModifier[]{temperatureModifier, humidityModifier},
                        null);

                ChartOptions hAxisOpts = new ChartOptions();
                hAxisOpts.put("title", "Ortszeit 19.07.2017");
                hAxisOpts.put("format", "hh:mm a"); // just for demonstration, delete line to see Google's format, which is more pretty here
                ChartOptions viewWinOpts = new ChartOptions();
                viewWinOpts.put("min", DataCell.getJsValue(new GregorianCalendar(2017, Calendar.JULY, 19, 0, 0)));
                viewWinOpts.put("max", DataCell.getJsValue(new GregorianCalendar(2017, Calendar.JULY, 20, 0, 0)));
                hAxisOpts.put("viewWindow", viewWinOpts);
                ChartOptions hAxisGridline = new ChartOptions();
                hAxisGridline.put("count", 8);
                hAxisOpts.put("gridlines", hAxisGridline);
                opts.put("hAxis", hAxisOpts);

                return opts;
            }
        };

        IComponentAssignedModel<DataTable> dataModel = new ComponentModel<DataTable>() {
            private static final long serialVersionUID = 1L;

            @Override
            public DataTable getObject(Component component) {

                OptionHelper optionHelper = new ClassicOptionHelper((Chart) component);
                List<ColumnDeclaration> colDefs;
                List<DataRow> rows;
                colDefs = new ArrayList<>();
                colDefs.add(new ColumnDeclaration(ColumnType.DATETIME, "Timestamp"));
// alternative  colDefs.add(new ColumnDeclaration(ColumnType.DATETIME, optionHelper.getChartLabelResourceModel("series.0.label")));

// alternative  colDefs.add(ColumnDeclaration.fromJSON("{'type': 'number', 'label':'Temperature'}"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, optionHelper.getChartLabelResourceModel("series.1.label")));
                colDefs.add(new ColumnDeclaration(ColumnType.STRING, ColumnRole.ANNOTATION));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, optionHelper.getChartLabelResourceModel("series.2.label")));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, optionHelper.getChartLabelResourceModel("series.3.label")));

                Object[][] exampleData = new Object[][]{
                    {new GregorianCalendar(2017, Calendar.JULY, 19, 8, 20).getTime(), 28, null, 12, 0.45},
                    {new GregorianCalendar(2017, Calendar.JULY, 19, 8, 50), 18, null, 13, 0.49},
                    {new GregorianCalendar(2017, Calendar.JULY, 19, 9, 20), 23, "Annotation", 14, 0.44},
                    {new GregorianCalendar(2017, Calendar.JULY, 19, 9, 50), 16, null, 14, 0.40}
                };
                // demonstration of explicitly formatting the data.
                // If simple object is added to row instead of DataCell, Google Charts formats the values for you.
                Format dateFormat = new SimpleDateFormat("dd.MM HH:mm", ((Chart) component).getLocale());
                Format celsiusFormat = new DecimalFormat("00.#°");
                Format percentFormat = new DecimalFormat("##0.0%");
                rows = new ArrayList<>();

                for (Object[] objects : exampleData) {
                    DataRow row = new DataRow(5);
                    row.add(new DataCell(objects[0], dateFormat)); // hAxis Date
                    row.add(new DataCell(objects[1], celsiusFormat)); // series 1
                    row.add(objects[2]); // Annotation for series 1
                    row.add(new DataCell(objects[3], celsiusFormat)); // series 2
                    row.add(new DataCell(objects[4], percentFormat)); // series 3
                    rows.add(row);
                }

                return new DataTable("data", colDefs, rows);
            }
        };

        return new Chart("chartLine", Model.of(ChartType.LINE), optionsModel, dataModel, chartLibLoader);
    }

    /**
     * Line chart with two series and two axes according to
     * <a href="https://developers.google.com/chart/interactive/docs/gallery/linechart#dual-y-charts">Google's
     * example</a>. Make use of OptionBuiler. Shows use of {@link OptionModifier}
     * to demonstrate series configuring.
     *
     * @return Line chart ready to use on page.
     */
    private Chart createChartDualLine() {
        IComponentAssignedModel<ChartOptions> optionsModel = new ComponentModel<ChartOptions>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ChartOptions getObject(Component component) {
                OptionBuilder builder = OptionBuilder.classic((Chart) component);
                ChartOptions opts = new ChartOptions("options");

                builder.title("Average Temperatures and Daylight in Iceland Throughout the Year");
                opts.put("width", 900);
                opts.put("height", 500);

                builder.axis("TEMP", "Temps (Celsius)").axis("DAYLIGHT", "Daylight");

                // change color of series 1 to orange to demonstrate use of modifiers
                OptionModifier daylightSeriesModifier = new OptionModifier() {

                    @Override
                    public void modify(ChartOptions options) {
                        options.put("color", "#FF7D0E");
                    }
                };
                builder.mapSeries("TEMP").mapSeries("DAYLIGHT", daylightSeriesModifier);

                ChartOptions hAxisOpts = new ChartOptions();
                JSONArray ticksJSON = new JSONArray();
                for (int month = 0; month < 12; month++) {
                    JSONFunction dateFun = new JSONFunction(String.format("new Date(2014, %d)", month));
                    ticksJSON.put(dateFun);
                }
                hAxisOpts.put("ticks", ticksJSON);
                opts.put("hAxis", hAxisOpts);

                ChartOptions vAxisOpts = new ChartOptions();
                ChartOptions viewWindowOpts = new ChartOptions();
                viewWindowOpts.put("max", 30);
                vAxisOpts.put("viewWindow", viewWindowOpts);
                opts.put("vAxis", vAxisOpts);

                opts.putAll(builder.build());
                return opts;
            }
        };

        IComponentAssignedModel<DataTable> dataModel = new ComponentModel<DataTable>() {
            private static final long serialVersionUID = 1L;

            @Override
            public DataTable getObject(Component component) {

                List<ColumnDeclaration> colDefs;
                List<DataRow> rows;
                colDefs = new ArrayList<>();
                colDefs.add(new ColumnDeclaration(ColumnType.DATE, "Month"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Average Temperature"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Average Hours of Daylight"));

                Object[][] exampleData = new Object[][]{
                    {new GregorianCalendar(2014, Calendar.JANUARY, 1, 0, 0), -0.5, 5.7},
                    {new GregorianCalendar(2014, Calendar.FEBRUARY, 1, 8, 0), .4, 8.7},
                    {new GregorianCalendar(2014, Calendar.MARCH, 1, 0, 0), .5, 12},
                    {new GregorianCalendar(2014, Calendar.APRIL, 1, 0, 0), 2.9, 15.3},
                    {new GregorianCalendar(2014, Calendar.MAY, 1, 0, 0), 6.3, 18.6},
                    {new GregorianCalendar(2014, Calendar.JUNE, 1, 0, 0), 9, 20.9},
                    {new GregorianCalendar(2014, Calendar.JULY, 1, 0, 0), 10.6, 19.8},
                    {new GregorianCalendar(2014, Calendar.AUGUST, 1, 0, 0), 10.3, 16.6},
                    {new GregorianCalendar(2014, Calendar.SEPTEMBER, 1, 0, 0), 7.4, 13.3},
                    {new GregorianCalendar(2014, Calendar.OCTOBER, 1, 0, 0), 4.4, 9.9},
                    {new GregorianCalendar(2014, Calendar.NOVEMBER, 1, 0, 0), 1.1, 6.6},
                    {new GregorianCalendar(2014, Calendar.DECEMBER, 1, 0, 0), -.2, 4.5}
                };
                rows = DataTable.fromArray(exampleData);

                return new DataTable("data", colDefs, rows);
            }
        };
        final Chart chart = new Chart("chartDualLine", Model.of(ChartType.LINE), optionsModel, dataModel, chartLibLoader);
        chart.setResponsive(false);
        return chart;
    }

    private Chart createChartBar() {
        // 100% stacked ba chart from https://developers.google.com/chart/interactive/docs/gallery/barchart

        IComponentAssignedModel<ChartOptions> optionsModel = new ComponentModel<ChartOptions>() {
            private static final long serialVersionUID = 1L;

            @Override
            public ChartOptions getObject(Component component) {
                ChartOptions opts = new ChartOptions("options");

                opts.put("width", 600);
                opts.put("height", 400);
//                opts.put("isStacked", "percent");
                opts.put("isStacked", stackedPercent ? "percent" : true);

                ChartOptions barOpt = new ChartOptions();
                barOpt.put("groupWidth", "75%");
                opts.put("bar", barOpt);

                ChartOptions legendOpt = new ChartOptions();
                legendOpt.put("position", "top");
                legendOpt.put("maxLines", 3);
                opts.put("legend", legendOpt);

                ChartOptions hAxisOpts = new ChartOptions();
                hAxisOpts.put("minValue", "0");
                hAxisOpts.put("ticks", new JSONArray("[0, .3, .6, .9, 1]"));
//                opts.put("hAxis", hAxisOpts);

                return opts;
            }
        };

        IComponentAssignedModel<DataTable> dataModel = new ComponentModel<DataTable>() {
            private static final long serialVersionUID = 1L;

            @Override
            public DataTable getObject(Component component) {

                List<ColumnDeclaration> colDefs = new ArrayList<>();
                List<DataRow> rows;

                colDefs.add(new ColumnDeclaration(ColumnType.STRING, "Year"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Fantasy & Sci Fi"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Romance"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Mystery/Crime"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "General"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Western"));
                colDefs.add(new ColumnDeclaration(ColumnType.NUMBER, "Literature"));
                colDefs.add(new ColumnDeclaration(ColumnType.STRING, ColumnRole.ANNOTATION));

                Object[][] exampleData = new Object[][]{
                    {"2010", 10, 24, 20, 32, 18, 5, ""},
                    {"2020", 16, 22, 23, 30, 16, 9, ""},
                    {"2030", 28, 19, 29, 30, 12, 13, ""}
                };

                rows = DataTable.fromArray(exampleData);

                return new DataTable("data", colDefs, rows);
            }
        };

        final Chart chartB = new Chart("chartBar", Model.of(ChartType.BAR), optionsModel, dataModel, chartLibLoader);
        chartB.setResponsive(false);
        return chartB;
    }
}
