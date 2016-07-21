package org.wicketstuff.html5.markup.html;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.html5.BasePage;

public class ProgressDemo extends BasePage
{

	private static final long serialVersionUID = 1L;

	private final int max = 200;

	private int current = 0;

	public ProgressDemo(final PageParameters parameters)
	{
		super(parameters);

		Progress progress = new Progress("progress", new PropertyModel<Integer>(this, "current"),
			Model.of(max));
		add(progress);
		progress.setOutputMarkupId(true);

		progress.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1))
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onPostProcessTarget(final AjaxRequestTarget target)
			{
				current += 10;
				if (current == max)
				{
					stop(target);
				}
			}
		});
	}
}
