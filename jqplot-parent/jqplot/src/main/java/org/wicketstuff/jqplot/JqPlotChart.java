/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jqplot;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.jqplot.behavior.JqPlotBehavior;
import org.wicketstuff.jqplot.lib.Chart;


/**
 *
 * @author inaiat
 */
public class JqPlotChart extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	public JqPlotChart(String id, Chart<?> chart)
	{
		super(id);
		setOutputMarkupId(true);
		add(new JqPlotBehavior(chart, getMarkupId()));
	}
}
