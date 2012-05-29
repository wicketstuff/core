package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public abstract class SliderDialog extends AbstractFormDialog<Integer>
{
	private static final long serialVersionUID = 1L;
	protected static final String BTN_SUBMIT = "Submit!";
	
	private Form<?> form;
	private FeedbackPanel feedback;
	
	public SliderDialog(String id, String title, IModel<Integer> model)
	{
		super(id, title, model, true);
		
		this.form = new Form<Integer>("form");
		this.add(this.form);
		
		// Slider //
		Label label = new Label("label");
		this.form.add(label);

		Slider slider = new Slider("slider", model, label);
		slider.setStep(5);
		slider.setRangeValidator(new RangeValidator<Integer>(10, 100));
		
		this.form.add(slider);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);	
	}

	@Override
	protected List<String> getButtons()
	{
		return Arrays.asList(BTN_SUBMIT, BTN_CANCEL);
	}

	@Override
	protected String getSubmitButton()
	{
		return BTN_SUBMIT;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}
	
	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		//re-attach the feedback panel to clear previously displayed error message(s)
		target.add(this.feedback);
	}

	@Override
	public void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}
}
