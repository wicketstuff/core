package org.wicketstuff.dashboard;

import static org.hamcrest.CoreMatchers.*;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Page;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.DummyHomePage;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.dashboard.web.DashboardContext;
import org.wicketstuff.dashboard.web.DashboardPanel;
import org.wicketstuff.dashboard.web.WidgetView;

public class AbstractWidgetTest {
	private static final String MARKUP = "<html><body><div wicket:id=\"dashboard\"></div></body></html>";
	private WicketTester browser;

	public static MetaDataKey<Dashboard> DASHBOARD_KEY = new MetaDataKey<Dashboard>() {
		private static final long serialVersionUID = 1L;
	};

	public static class WebApp extends WebApplication {
		@Override
		public Class<? extends Page> getHomePage() {
			return DummyHomePage.class;
		}

		@Override
		protected void init() {
			super.init();
			DashboardContext dashboardContext = this.getMetaData(DashboardContextInitializer.DASHBOARD_CONTEXT_KEY);
			WidgetDescriptor widgetDescriptor;
			dashboardContext.getWidgetRegistry().registerWidget(widgetDescriptor = new MyWidgetDescriptor());

			Dashboard dashboard = dashboardContext.getDashboardPersister().load();

			if (dashboard == null) {
				dashboard = new DefaultDashboard("default", "Default");
				dashboard.setColumnCount(3);
			}

			WidgetFactory widgetFactory = dashboardContext.getWidgetFactory();
			Widget widget = widgetFactory.createWidget(widgetDescriptor);
			dashboard.addWidget(widget);

			this.setMetaData(DASHBOARD_KEY, dashboard);
		}

		public static class MyWidgetDescriptor implements WidgetDescriptor {
			private static final long serialVersionUID = 1L;

			@Override
			public String getTypeName() {
				return "widget.foo";
			}

			@Override
			public String getName() {
				return "Foo widget";
			}

			@Override
			public String getProvider() {
				return "wicketstuff";
			}

			@Override
			public String getDescription() {
				return "The foo widget";
			}

			@Override
			public String getWidgetClassName() {
				return MyWidget.class.getName();
			}
		}

		public static class MyWidget extends AbstractWidget {
			private static final long serialVersionUID = 1L;

			@Override
			public WidgetView createView(String viewId) {
				return new WidgetView(viewId, Model.<Widget> of(this));
			}
		}
	}

	@Before
	public void setup() {
		browser = new WicketTester(new WebApp());
	}

	@Test
	public void canExposeConfigurationAsString() {
		Dashboard dashboard = Application.get().getMetaData(DASHBOARD_KEY);
		browser.startComponentInPage(new DashboardPanel("dashboard", Model.<Dashboard> of(dashboard)), Markup.of(MARKUP));
		browser.assertNoErrorMessage();
		Assert.assertThat(
				browser.getComponentFromLastRenderedPage("dashboard:columns:0:column:columnContainer:widgetList:0:widget:content").getDefaultModelObjectAsString(),
				is(not(nullValue()))
		);
	}
}
