/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.chart;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.core.utils.ListUtils;
import org.wicketstuff.kendo.ui.dataviz.chart.Chart;
import org.wicketstuff.kendo.ui.dataviz.chart.series.BulletSeries;
import org.wicketstuff.kendo.ui.dataviz.chart.series.Series;
import org.wicketstuff.kendo.ui.dataviz.chart.series.BulletSeries.BulletData;

public class BulletChartPage extends AbstractChartPage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	public BulletChartPage()
	{
		// Chart //
		this.add(new Chart<BulletData>("chart", newModel(), newSeries(), newOptions()));
	}

	// statics //

	static Options newOptions()
	{
		Options options = new Options();
		options.set("title", "{ text: 'Sample Bullet Chart' }");
		options.set("tooltip", "{ visible: true, format: 'current: {0:n2} / target: {1:n2}' }");

		return options;
	}

	static List<Series> newSeries()
	{
		List<Series> series = Generics.newArrayList();
		series.add(new BulletSeries());

		return series;
	}

	static IModel<List<BulletData>> newModel()
	{
		return new LoadableDetachableModel<List<BulletData>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<BulletData> load()
			{
				return randomBullets();
			}
		};
	}

	static List<BulletData> randomBullets()
	{
		List<BulletData> data = Generics.newArrayList();

		for (int i = 0; i < 26; i++)
		{
			String category = String.valueOf(Character.toChars(65 + i));
			Double current = ListUtils.random(1600, 1900);
			Double target = ListUtils.random(1700, 1800);

			data.add(new BulletData(category, current, target));
		}

		return data;
	}
}
