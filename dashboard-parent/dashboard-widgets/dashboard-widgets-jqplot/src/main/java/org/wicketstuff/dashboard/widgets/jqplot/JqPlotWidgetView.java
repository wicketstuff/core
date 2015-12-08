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
package org.wicketstuff.dashboard.widgets.jqplot;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;

import br.com.digilabs.jqplot.Chart;

/**
 * @author Decebal Suiu
 */
public class JqPlotWidgetView extends WidgetView {

	private static final long serialVersionUID = 1L;

	public JqPlotWidgetView(String id, Model<Widget> model) {
		super(id, model);

		final JqPlotWidget widget = (JqPlotWidget) model.getObject();
		IModel<Chart<?>> chartModel = new LoadableDetachableModel<Chart<?>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Chart<?> load() {
				return widget.getChart();
			}
			
		};
		add(new ChartContainer("chart", chartModel));
	}

}
