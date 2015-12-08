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
package org.wicketstuff.dashboard.widgets.charts;

import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dashboard.AbstractWidget;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;
import org.wicketstuff.dashboard.widgets.charts.options.ShowcaseOptions;
import org.wicketstuff.dashboard.widgets.charts.settings.HighChartsSettingsPanel;
import org.wicketstuff.dashboard.widgets.charts.settings.Settings;

import java.util.Map;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class HighChartsWidget extends AbstractWidget {
    private static final long serialVersionUID = 1L;

    private static HighChartsFactory highChartsFactory;

    private transient Chart chart;

    public HighChartsWidget() {
        super();

        setTitle("HighCharts");

        Map<String, String> settings = getSettings();
        settings.put(Settings.seriesType.name(), SeriesType.SPLINE.name());
    }

    public static HighChartsFactory getHighChartsFactory() {
        if (highChartsFactory == null) {
            throw new RuntimeException("HighChartsFactory cannot be null. Use HighChartsWidget.getHighChartsFactory(...)");
        }
        return highChartsFactory;
    }

    public static void setHighChartsFactory(HighChartsFactory highChartsFactory) {
        HighChartsWidget.highChartsFactory = highChartsFactory;
    }

    public Options newOptions() {
        return HighChartsWidget.getHighChartsFactory().createOptions(this);
    }

    public Chart getChart(String id) {
        chart = new Chart(id, newOptions());
        setTitle(((ShowcaseOptions)chart.getOptions()).getLabel());
        return chart;
    }

    public void updateChart() {
        chart.setOptions(newOptions());
        setTitle(((ShowcaseOptions)chart.getOptions()).getLabel());
    }

    @Override
    public boolean hasSettings() {
        return true;
    }

    @Override
    public Panel createSettingsPanel(String settingsPanelId) {
        return new HighChartsSettingsPanel(settingsPanelId, new Model<HighChartsWidget>(this));
    }

    public WidgetView createView(String viewId) {
        return new HighChartsWidgetView(viewId, new Model<Widget>(this));
    }
}
