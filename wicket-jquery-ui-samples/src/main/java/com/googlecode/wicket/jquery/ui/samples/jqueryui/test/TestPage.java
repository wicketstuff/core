package com.googlecode.wicket.jquery.ui.samples.jqueryui.test;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

public class TestPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TestPage.class);

	public TestPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		final Form<?> form = new Form<Void>("form");
		// this.form.setMultiPart(true);
		this.add(form);

		// WebApplication.get().

		// Ajax Button //
		AjaxButton button = new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				LOG.info("Component#onConfigure()");
				// this.setEnabled(false);

				this.add(new JQueryBehavior(JQueryWidget.getSelector(this)) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onConfigure(Component component)
					{
						component.setEnabled(false);

						// super should be called in last, for #onConfigure(JQueryBehavior) to be called after component.setEnabled(false);
						super.onConfigure(component);
					}
				});
			}

			@Override
			public void onConfigure(JQueryBehavior behavior)
			{
				super.onConfigure(behavior);

				LOG.info("Component#onConfigure(JQueryBehavior)");
				behavior.setOption("active", this.isEnabledInHierarchy());
			}

			@Override
			protected void onBeforeRender()
			{
				super.onBeforeRender();

				LOG.info("Component#onBeforeRender()");
			}

			@Override
			public void onBeforeRender(JQueryBehavior behavior)
			{
				super.onBeforeRender(behavior);
				LOG.info("Component#onBeforeRender(JQueryBehavior)");
			}
		};

		form.add(button);
	}
}
