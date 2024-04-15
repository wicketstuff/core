package com.googlecode.wicket.jquery.ui.samples.kendoui.chart;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.kendo.ui.dataviz.chart.Chart;
import com.googlecode.wicket.kendo.ui.dataviz.chart.series.DonutSeries;
import com.googlecode.wicket.kendo.ui.dataviz.chart.series.DonutSeries.DonutData;
import com.googlecode.wicket.kendo.ui.dataviz.chart.series.Series;

public class DonutChartPage extends AbstractChartPage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	public DonutChartPage()
	{
		// Chart //
		this.add(new Chart<DonutData>("chart", newModel(), newSeries(), newOptions()));
	}

	// statics //

	static Options newOptions()
	{
		Options options = new Options();
		options.set("title", "{ text: 'Sample Donut Chart' }");
		options.set("legend", "{ position: 'top' }");
		options.set("tooltip", "{ visible: true, format: '{0:n0}' }");

		return options;
	}  
	
	static List<Series> newSeries()
	{
		List<Series> series = Generics.newArrayList();
		series.add(new DonutSeries());

		return series;
	}

	static IModel<List<DonutData>> newModel()
	{
		return new LoadableDetachableModel<List<DonutData>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<DonutData> load()
			{
				return randomDonut();
			}
		};
	}

	static List<DonutData> randomDonut()
	{
		List<DonutData> data = Generics.newArrayList();

		for (int i = 0; i < 5; i++)
		{
			String category = "category #" + i;
			Double value = ListUtils.random(5, 20);
			Boolean explode = i == 0; // only first category is exploded

			data.add(new DonutData(category, value, explode));
		}

		return data;
	}
}
