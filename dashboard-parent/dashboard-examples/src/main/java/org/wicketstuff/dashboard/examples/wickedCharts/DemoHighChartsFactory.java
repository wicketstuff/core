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
package org.wicketstuff.dashboard.examples.wickedCharts;

import org.wicketstuff.dashboard.widgets.charts.HighChartsFactory;
import org.wicketstuff.dashboard.widgets.charts.HighChartsWidget;
import org.wicketstuff.dashboard.widgets.charts.options.AngularGaugeOptions;
import org.wicketstuff.dashboard.widgets.charts.options.AreaRangeOptions;
import org.wicketstuff.dashboard.widgets.charts.options.AreaSplineOptions;
import org.wicketstuff.dashboard.widgets.charts.options.BasicAreaOptions;
import org.wicketstuff.dashboard.widgets.charts.options.BasicBarOptions;
import org.wicketstuff.dashboard.widgets.charts.options.BasicLineOptions;
import org.wicketstuff.dashboard.widgets.charts.options.BubbleChart3DOptions;
import org.wicketstuff.dashboard.widgets.charts.options.ColumnRangeOptions;
import org.wicketstuff.dashboard.widgets.charts.options.PieWithLegendOptions;
import org.wicketstuff.dashboard.widgets.charts.options.ScatterPlotOptions;
import org.wicketstuff.dashboard.widgets.charts.options.SplineUpdatingOptions;
import org.wicketstuff.dashboard.widgets.charts.options.StackedAndGroupedColumnOptions;
import org.wicketstuff.dashboard.widgets.charts.settings.Settings;

import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class DemoHighChartsFactory implements HighChartsFactory {

    @Override
    public Options createOptions(HighChartsWidget widget) {
        Options options;
        SeriesType seriesType = SeriesType.valueOf(widget.getSettings().get(Settings.seriesType.name()));
        switch(seriesType) {
            case AREA: {
                options = new BasicAreaOptions();
                break;
            }
            case AREASPLINE: {
                options = new AreaSplineOptions();
                break;
            }
            case BAR: {
                options = new BasicBarOptions();
                break;
            }
            case COLUMN: {
                options = new StackedAndGroupedColumnOptions();
                break;
            }
            case LINE: {
                options = new BasicLineOptions();
                break;
            }
            case PIE: {
                options = new PieWithLegendOptions();
                break;
            }
            case SCATTER: {
                options = new ScatterPlotOptions();
                break;
            }
            case SPLINE: {
                options = new SplineUpdatingOptions();
                break;
            }
            case GAUGE: {
                options = new AngularGaugeOptions();
                break;
            }
            case COLUMNRANGE: {
                options = new ColumnRangeOptions();
                break;
            }
            case AREARANGE: {
                options = new AreaRangeOptions();
                break;
            }
            case BUBBLE: {
                options = new BubbleChart3DOptions();
                break;
            }
            default: {
                options = new Options();
                break;
            }
        }
        return options;
    }
}
