/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.chart;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.kendo.ui.dataviz.chart.Chart;
import org.wicketstuff.kendo.ui.dataviz.chart.series.BubbleSeries;
import org.wicketstuff.kendo.ui.dataviz.chart.series.Series;
import org.wicketstuff.kendo.ui.dataviz.chart.series.BubbleSeries.BubbleData;

public class BubbleChartPage extends AbstractChartPage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	public BubbleChartPage()
	{
		// Chart //
		this.add(new Chart<BubbleData>("chart", newModel(), newSeries(), newOptions()));
	}

	// statics //

	static Options newOptions()
	{
		Options options = new Options();
		options.set("title", "{ text: 'Sample Bubble Chart' }");
		options.set("tooltip", "{ visible: true, format: '{3}: {2:n0}' }");

		return options;
	}

	static List<Series> newSeries()
	{
		List<Series> series = Generics.newArrayList();
		series.add(new BubbleSeries());

		return series;
	}

	static IModel<List<BubbleData>> newModel()
	{
		return new LoadableDetachableModel<List<BubbleData>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<BubbleData> load()
			{
				return randomBubbles();
			}
		};
	}

	static List<BubbleData> randomBubbles()
	{
		List<BubbleData> data = Generics.newArrayList();

		for (int i = 0; i < 25; i++)
		{
			String category = "category #" + i;
			Double x = Math.random() * 1000;
			Double y = Math.random() * 10000;
			Double size = Math.random() * 100000;

			data.add(new BubbleData(category, x, y, size));
		}

		return data;
	}
}
