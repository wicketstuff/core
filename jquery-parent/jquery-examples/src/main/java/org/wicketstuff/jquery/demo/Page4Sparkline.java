package org.wicketstuff.jquery.demo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.jquery.sparkline.Sparkline;
import org.wicketstuff.jquery.sparkline.SparklineOptions;
import org.wicketstuff.jquery.sparkline.SparklineOptions.TYPE;
import org.wicketstuff.jquery.sparkline.SparklineWrapper;

@SuppressWarnings("serial")
public class Page4Sparkline extends PageSupport
{

	public Page4Sparkline()
	{

		RepeatingView rv = new RepeatingView("spot");
		add(rv);


		WebMarkupContainer spot = new WebMarkupContainer(rv.newChildId());
		Sparkline s = new Sparkline("chart", 5, 6, 7, 9, 9, 5, 3, 2, 2, 4, 6, 7);
		spot.add(s);
		spot.add(new Label("js", s.getSparklineJS().toString()));
		rv.add(spot);

		// BAR
		SparklineOptions options = new SparklineOptions(TYPE.bar);
		spot = new WebMarkupContainer(rv.newChildId());
		s = new Sparkline("chart", options, 5, 6, 7, 2, 0, -4, -2, 4);
		spot.add(s);
		spot.add(new Label("js", s.getSparklineJS().toString()));
		rv.add(spot);

		// TRISTATE
		options = new SparklineOptions(TYPE.tristate);
		spot = new WebMarkupContainer(rv.newChildId());
		s = new Sparkline("chart", options, -1, 1, 1, 2, 0, -1, -2, 1, 1);
		spot.add(s);
		spot.add(new Label("js", s.getSparklineJS().toString()));
		rv.add(spot);


		// DISCRETE
		options = new SparklineOptions(TYPE.discrete);
		spot = new WebMarkupContainer(rv.newChildId());
		s = new Sparkline("chart", options, 5, 6, 7, 9, 9, 5, 3, 2, 2, 4, 6, 7);
		spot.add(s);
		spot.add(new Label("js", s.getSparklineJS().toString()));
		rv.add(spot);


		// PIE
		options = new SparklineOptions(TYPE.pie);
		spot = new WebMarkupContainer(rv.newChildId());
		s = new Sparkline("chart", options, 1, 2, 4);
		spot.add(s);
		spot.add(new Label("js", s.getSparklineJS().toString()));
		rv.add(spot);

		// --------------------------------
		options = new SparklineOptions(TYPE.line);
		final LinkedList<Integer> values = new LinkedList<Integer>();
		values.add(5);
		values.add(6);
		values.add(8);
		values.add(5);
		s = new Sparkline(SparklineWrapper.SPARKID,
			new AbstractReadOnlyModel<Collection<Integer>>()
			{
				@Override
				public Collection<Integer> getObject()
				{
					return values;
				}
			}, options);
		final SparklineWrapper wrap = new SparklineWrapper("animated", s);
		wrap.setOutputMarkupId(true);
		add(wrap);


		// Refresh the view every second...
		add(new AbstractAjaxTimerBehavior(Duration.seconds(1))
		{
			Random rand = new Random();

			@Override
			protected void onTimer(final AjaxRequestTarget target)
			{
				int last = values.getLast() + (rand.nextInt(10) - 5);
				values.addLast(last);
				if (values.size() > 100)
				{
					values.removeFirst();
				}
				target.add(wrap);
			}
		});
	}

}
