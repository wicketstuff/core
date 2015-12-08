/*
 * Copyright 2014 Paul Bors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.widgets.charts.options;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Stacking;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class StackedAndGroupedColumnOptions extends ShowcaseOptions {
    private static final long serialVersionUID = 1L;

    public StackedAndGroupedColumnOptions() {
        ChartOptions chartOptions = new ChartOptions();
        chartOptions.setType(SeriesType.COLUMN);
        setChartOptions(chartOptions);

        Title title = new Title("Total fruit consumption, grouped by gender");
        setTitle(title);

        Axis xAxis = new Axis();
        xAxis
            .setCategories(Arrays
                .asList(new String[]{"Apples", "Oranges", "Pears", "Grapes",
                    "Bananas"}));
        setxAxis(xAxis);

        Axis yAxis = new Axis();
        yAxis.setAllowDecimals(Boolean.FALSE);
        yAxis.setMin(0);
        yAxis.setTitle(new Title("Number of fruits"));
        setyAxis(yAxis);

        setTooltip(new Tooltip());

        PlotOptions plotOptions = new PlotOptions();
        plotOptions.setStacking(Stacking.NORMAL);
        PlotOptionsChoice choice = new PlotOptionsChoice();
        choice.setColumn(plotOptions);
        setPlotOptions(choice);

        // create data
        Random r = new Random();
        List<Number> sales = new ArrayList<Number>();
        for (int i = 0; i <= 5; i++) {
            sales
                .add(r
                    .nextInt(10));
        }
        List<Number> sales2 = new ArrayList<Number>();
        for (int i = 0; i <= 5; i++) {
            sales2
                .add(r
                    .nextInt(10));
        }
        List<Number> sales3 = new ArrayList<Number>();
        for (int i = 0; i <= 5; i++) {
            sales3
                .add(r
                    .nextInt(10));
        }
        List<Number> sales4 = new ArrayList<Number>();
        for (int i = 0; i <= 5; i++) {
            sales4
                .add(r
                    .nextInt(10));
        }

        // add data to the chart
        Series<Number> series1 = new SimpleSeries();
        series1.setData(sales);
        series1.setStack("male");
        series1.setName("John");
        addSeries(series1);

        Series<Number> series2 = new SimpleSeries();
        series2.setData(sales2);
        series2.setStack("male");
        series2.setName("Joe");
        addSeries(series2);

        Series<Number> series3 = new SimpleSeries();
        series3.setData(sales3);
        series3.setStack("female");
        series3.setName("Jane");
        addSeries(series3);

        Series<Number> series4 = new SimpleSeries();
        series4.setData(sales4);
        series4.setStack("female");
        series4.setName("Janet");
        addSeries(series4);
    }

    @Override
    public String getLabel() {
		return super.getLabel() + "Stacked and grouped column";
    }
}
