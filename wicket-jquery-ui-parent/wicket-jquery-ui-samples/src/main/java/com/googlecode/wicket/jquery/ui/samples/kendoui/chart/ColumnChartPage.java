package com.googlecode.wicket.jquery.ui.samples.kendoui.chart;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.kendo.ui.dataviz.chart.Chart;
import com.googlecode.wicket.kendo.ui.dataviz.chart.series.ColumnSeries;
import com.googlecode.wicket.kendo.ui.dataviz.chart.series.Series;

public class ColumnChartPage extends AbstractChartPage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	public ColumnChartPage()
	{
		// Chart //
		this.add(new Chart<MyData>("chart", newModel(), newSeries(), newOptions()));
	}

	// statics //

	static Options newOptions()
	{
		Options options = new Options();
		options.set("title", "{ text: 'Sample Column Chart' }");
		options.set("legend", "{ position: 'top' }");
		options.set("tooltip", "{ visible: true, template: '#= series.name #: #= kendo.toString(value, \"n0\") #' }");
		options.set("categoryAxis", "{ field: 'category' }"); // MyData#category field

		return options;
	}

	static List<Series> newSeries()
	{
		List<Series> series = Generics.newArrayList();
		series.add(new ColumnSeries("series 1", MyData.FIELD_1));
		series.add(new ColumnSeries("series 2", MyData.FIELD_2));

		return series;
	}

	static IModel<List<MyData>> newModel()
	{
		return new LoadableDetachableModel<List<MyData>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<MyData> load()
			{
				return randomColumns();
			}
		};
	}

	static List<MyData> randomColumns()
	{
		List<MyData> data = Generics.newArrayList();

		Double value1 = null;
		Double value2 = null;

		for (int i = 1; i <= 10; i++)
		{
			// smooth variation
			value1 = value1 != null ? value1 + ListUtils.random(-10, 10) : ListUtils.random(25, 50);
			value2 = value2 != null ? value2 + ListUtils.random(-10, 10) : ListUtils.random(25, 50);

			data.add(new MyData("cat. #" + i, value1, value2));
		}

		return data;
	}

	// classes //

	public static class MyData implements IClusterable
	{
		private static final long serialVersionUID = 1L;
		public static final String FIELD_1 = "value1"; // the value property
		public static final String FIELD_2 = "value2"; // the value property

		private final String category;
		private final Double value1;
		private final Double value2;

		public MyData(String category, Double value1, Double value2)
		{
			this.category = category;
			this.value1 = value1;
			this.value2 = value2;
		}

		public String getCategory()
		{
			return this.category;
		}

		public Double getValue1()
		{
			return this.value1;
		}

		public Double getValue2()
		{
			return this.value2;
		}
	}
}
