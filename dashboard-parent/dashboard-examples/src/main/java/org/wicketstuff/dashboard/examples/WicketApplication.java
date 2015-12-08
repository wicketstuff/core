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
package org.wicketstuff.dashboard.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.DashboardContextInitializer;
import org.wicketstuff.dashboard.DefaultDashboard;
import org.wicketstuff.dashboard.examples.jqplot.DemoChartFactory;
import org.wicketstuff.dashboard.examples.justgage.DemoJustGageFactory;
import org.wicketstuff.dashboard.examples.ofchart.DemoChartDataFactory;
import org.wicketstuff.dashboard.examples.wickedCharts.DemoHighChartsFactory;
import org.wicketstuff.dashboard.web.DashboardContext;
import org.wicketstuff.dashboard.widgets.charts.HighChartsWidget;
import org.wicketstuff.dashboard.widgets.charts.HighChartsWidgetDescriptor;
import org.wicketstuff.dashboard.widgets.jqplot.JqPlotWidget;
import org.wicketstuff.dashboard.widgets.jqplot.JqPlotWidgetDescriptor;
import org.wicketstuff.dashboard.widgets.justgage.JustGageWidget;
import org.wicketstuff.dashboard.widgets.justgage.JustGageWidgetDescriptor;
import org.wicketstuff.dashboard.widgets.loremipsum.LoremIpsumWidgetDescriptor;
import org.wicketstuff.dashboard.widgets.ofchart.ChartWidget;
import org.wicketstuff.dashboard.widgets.ofchart.ChartWidgetDescriptor;

/**
 * @author Decebal Suiu
 */
public class WicketApplication extends WebApplication {

	private Dashboard dashboard;
	
	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	@Override
	public void init() {
		super.init();		

		// markup settings
		getMarkupSettings().setStripWicketTags(true);
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		// exception settings
		getResourceSettings().setThrowExceptionOnMissingResource(false);
		
		// mounts
		mountPage("add-widget", AddWidgetPage.class);
		mountPage("widget", WidgetPage.class);
		
		// >>> begin dashboard settings
		
		// register some widgets
		DashboardContext dashboardContext = getDashboardContext();
		dashboardContext.getWidgetRegistry()
			.registerWidget(new LoremIpsumWidgetDescriptor())
			.registerWidget(new ChartWidgetDescriptor())
			.registerWidget(new JqPlotWidgetDescriptor())
			.registerWidget(new JustGageWidgetDescriptor())
            .registerWidget(new HighChartsWidgetDescriptor());
		
		// add a custom action for all widgets
		dashboardContext.setWidgetActionsFactory(new DemoWidgetActionsFactory());

		// set some (data) factory
        ChartWidget.setChartDataFactory(new DemoChartDataFactory());
		JqPlotWidget.setChartFactory(new DemoChartFactory());
		JustGageWidget.setJustGageFactory(new DemoJustGageFactory());
        HighChartsWidget.setHighChartsFactory(new DemoHighChartsFactory());
				
        // init dashboard from context
        initDashboard();
        
        // <<< end dashboard settings
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	/*
	// for test locale
	@Override
	public Session newSession(Request request, Response response) {
		Session session = super.newSession(request, response);
		session.setLocale(new Locale("ro", "RO"));

		return session;
	}
	*/

	public Dashboard getDashboard() {
		return dashboard;
	}

	private DashboardContext getDashboardContext() {
		return getMetaData(DashboardContextInitializer.DASHBOARD_CONTEXT_KEY);
	}
	
	private void initDashboard() {
		dashboard = getDashboardContext().getDashboardPersister().load();
    	if (dashboard == null) {
    		dashboard = new DefaultDashboard("default", "Default");
    	}
	}

}
