package com.googlecode.wicket.jquery.ui.samples.pages.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.SliderDialog;

public class FormDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;
	
	public FormDialogPage()
	{
		this.init();
	}
	
	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));
		
		// Dialog //
		final SliderDialog dialog = new SliderDialog("dialog", "Slider dialog", new Model<Integer>(0)) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				this.info("The form has been submitted");
				this.info(String.format("The model object is: '%s'", this.getModelObject()));
			}

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
				this.info(button + " has been clicked");
				target.add(feedback);
			}
		};

		this.add(dialog);
		
		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.open(target);
			}
		});
	}
}
