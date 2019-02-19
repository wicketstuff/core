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
package org.wicketstuff.dashboard.widgets.charts.settings;

import static org.wicketstuff.dashboard.DashboardContextInitializer.getDashboardContext;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.dashboard.Dashboard;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.DashboardPanel;
import org.wicketstuff.dashboard.web.WidgetPanel;
import org.wicketstuff.dashboard.widgets.charts.HighChartsWidget;
import org.wicketstuff.dashboard.widgets.charts.HighChartsWidgetView;

import com.googlecode.wickedcharts.highcharts.options.SeriesType;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class HighChartsSettingsPanel extends GenericPanel<HighChartsWidget> {
	private static final long serialVersionUID = 1L;

	private SeriesType seriesType;

	public HighChartsSettingsPanel(String id, IModel<HighChartsWidget> model) {
		super(id, model);

		setOutputMarkupPlaceholderTag(true);

		Form<Widget> form = new Form<>("form");
		add(form);

		seriesType = SeriesType.valueOf(getModelObject().getSettings().get(Settings.seriesType.name()));
		DropDownChoice<SeriesType> choice = new DropDownChoice<>(Settings.seriesType.name(),
				new PropertyModel<SeriesType>(this, Settings.seriesType.name()), Arrays.asList(SeriesType.values()));
		form.add(choice);

		form.add(new AjaxSubmitLink("submit") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				getModelObject().getSettings().put(Settings.seriesType.name(), seriesType.name());
				getModelObject().updateChart();

				Dashboard dashboard = findParent(DashboardPanel.class).getDashboard();
				getDashboardContext().getDashboardPersister().save(dashboard);

				hideSettingPanel(target);

				WidgetPanel widgetPanel = findParent(WidgetPanel.class);
				target.add(widgetPanel.getWidgetHeaderPanel());

				HighChartsWidgetView widgetView = (HighChartsWidgetView) widgetPanel.getWidgetView();
				target.add(widgetView);
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
			}
		});

		form.add(new AjaxLink<Void>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				hideSettingPanel(target);
			}
		});

	}

	private void hideSettingPanel(AjaxRequestTarget target) {
		setVisible(false);
		target.add(this);
	}

	public SeriesType getSeriesType() {
		return seriesType;
	}

	public void setSeriesType(SeriesType seriesType) {
		this.seriesType = seriesType;
	}
}
