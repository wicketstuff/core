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

import static org.wicketstuff.dashboard.DashboardContextInitializer.getDashboardContext;

import de.agilecoders.wicket.webjars.WicketWebjars;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.DefaultDashboard;
import org.wicketstuff.dashboard.examples.jqplot.DemoChartFactory;
import org.wicketstuff.dashboard.examples.justgage.DemoJustGageFactory;
import org.wicketstuff.dashboard.web.DashboardContext;
import org.wicketstuff.dashboard.widgets.jqplot.JqPlotWidget;
import org.wicketstuff.dashboard.widgets.jqplot.JqPlotWidgetDescriptor;
import org.wicketstuff.dashboard.widgets.justgage.JustGageWidget;
import org.wicketstuff.dashboard.widgets.justgage.JustGageWidgetDescriptor;
import org.wicketstuff.dashboard.widgets.loremipsum.LoremIpsumWidgetDescriptor;

/**
 * @author Decebal Suiu
 */
public class WicketApplication extends WebApplication {

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	@Override
	public void init() {
		super.init();
		WicketWebjars.install(this);

		getCspSettings().blocking().disabled();

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
		dashboardContext.setDashboardPersister(new ExampleDashboardPersister());
		dashboardContext.getWidgetRegistry()
			.registerWidget(new LoremIpsumWidgetDescriptor())
			.registerWidget(new JqPlotWidgetDescriptor())
			.registerWidget(new JustGageWidgetDescriptor());

		// add a custom action for all widgets
		dashboardContext.setWidgetActionsFactory(new DemoWidgetActionsFactory());

		// set some (data) factory
		JqPlotWidget.setChartFactory(new DemoChartFactory());
		JustGageWidget.setJustGageFactory(new DemoJustGageFactory());

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
		Dashboard dashboard = getDashboardContext().getDashboardPersister().load();
		if (dashboard == null) {
			dashboard = new DefaultDashboard("default", "Default");
		}
		return dashboard;
	}

}
