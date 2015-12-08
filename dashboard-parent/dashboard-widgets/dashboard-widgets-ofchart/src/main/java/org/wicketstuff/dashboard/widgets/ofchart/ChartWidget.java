/*
 * Copyright 2012 Decebal Suiu
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
package org.wicketstuff.dashboard.widgets.ofchart;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dashboard.AbstractWidget;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;

/**
 * @author Decebal Suiu
 */
public class ChartWidget extends AbstractWidget {

	private static final long serialVersionUID = 1L;
		
	public static final String BAR_TYPE = "Bar";
	public static final String DOUBLE_BAR_TYPE = "DoubleBar";
	public static final String LINE_TYPE = "Line";
	public static final String DOTED_LINE_TYPE = "DotedLine";
	public static final String PIE_TYPE = "Pie";
	public static final String SCATTER_TYPE = "Scatter";

	public static final List<String> TYPES = Arrays.asList(new String[] {
			BAR_TYPE,
			DOUBLE_BAR_TYPE,
			LINE_TYPE,
			DOTED_LINE_TYPE,
			PIE_TYPE,
			SCATTER_TYPE
	});

	private static ChartDataFactory chartDataFactory;	
	
	public ChartWidget() {
		super();
		
		title = "Chart";
	}

	public static ChartDataFactory getChartDataFactory() {
		return chartDataFactory;
	}

	public static void setChartDataFactory(ChartDataFactory chartDataFactory) {
		ChartWidget.chartDataFactory = chartDataFactory;
	}

	public String getChartData() {
		if (chartDataFactory == null) {
			throw new RuntimeException("ChartDataFactory cannot be null. Use ChartWidget.setChartDataFactory(...)");
		}
		
		return chartDataFactory.createChartData(this);
	}
	
	public WidgetView createView(String viewId) {
		return new ChartWidgetView(viewId, new Model<Widget>(this));
	}

	@Override
	public void init() {
		if (!settings.containsKey("chartType")) {
			settings.put("chartType", ChartWidget.BAR_TYPE);
		}
	}

	@Override
	public boolean hasSettings() {
		return true;
	}

	@Override
	public Panel createSettingsPanel(String settingsPanelId) {
		return new ChartSettingsPanel(settingsPanelId, new Model<ChartWidget>(this));
	}

}
