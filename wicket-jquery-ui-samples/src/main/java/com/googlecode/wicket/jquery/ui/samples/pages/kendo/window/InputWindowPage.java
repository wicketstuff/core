package com.googlecode.wicket.jquery.ui.samples.pages.kendo.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.window.InputWindow;

public class InputWindowPage extends AbstractWindowPage
{
	private static final long serialVersionUID = 1L;

	public InputWindowPage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Window //
		final InputWindow<String> window = new MyInputWindow("window") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onOpen(AjaxRequestTarget target)
			{
				super.onOpen(target); // important

				target.add(feedback); // clear previous messages
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				this.info("ModelObject: " + this.getModelObject());
				target.add(feedback);
			}

			@Override
			public void onClose(AjaxRequestTarget target)
			{
				this.info("Window closed");
				target.add(feedback);
			}
		};

		this.add(window);

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				window.open(target);
			}
		});
	}

	/**
	 * This input window is an inner class to ease the l10n of button.<br/>
	 * Therefore the new button value(s) are located in InputWindowPage$MyInputWindow.properties
	 */
	private static abstract class MyInputWindow extends InputWindow<String>
	{
		private static final long serialVersionUID = 1L;

		private MyInputWindow(String id)
		{
			super(id, "My Input Window", Model.of(""), "Please provide a value:");
		}
	}
}
