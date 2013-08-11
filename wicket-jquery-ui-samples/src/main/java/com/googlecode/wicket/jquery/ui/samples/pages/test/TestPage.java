package com.googlecode.wicket.jquery.ui.samples.pages.test;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;

public class TestPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	public TestPage()
	{
		this.init();
	}

	private void init()
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

//		WebApplication.get().

		// Ajax Button //
		AjaxButton button = new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				System.out.println("Component#onConfigure()");
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

				System.out.println("Component#onConfigure(JQueryBehavior)");
				System.out.println(this.isEnabledInHierarchy());
				behavior.setOption("active", this.isEnabledInHierarchy());
			}

			@Override
			protected void onBeforeRender()
			{
				super.onBeforeRender();

				System.out.println("Component#onBeforeRender()");
			}

			@Override
			public void onBeforeRender(JQueryBehavior behavior)
			{
				super.onBeforeRender(behavior);
				System.out.println("Component#onBeforeRender(JQueryBehavior)");
			}
		};

		form.add(button);
	}

}
