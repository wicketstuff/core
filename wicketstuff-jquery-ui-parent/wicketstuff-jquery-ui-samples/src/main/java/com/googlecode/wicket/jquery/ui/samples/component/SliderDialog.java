package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

public abstract class SliderDialog extends AbstractFormDialog<Integer>
{
	private static final long serialVersionUID = 1L;
	protected final DialogButton btnSubmit = new DialogButton(SUBMIT, Model.of("Submit!"));
	protected final DialogButton btnCancel = new DialogButton(CANCEL, LBL_CANCEL);

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

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(SliderDialog.class));
	}

	// Properties //

	@Override
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnSubmit, this.btnCancel);
	}

	@Override
	public DialogButton getSubmitButton()
	{
		return this.btnSubmit;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	// Events //

	@Override
	protected void onOpen(IPartialPageRequestHandler handler)
	{
		// re-attach the feedback panel to clear previously displayed error message(s)
		handler.add(this.feedback);
	}

	@Override
	public void onError(AjaxRequestTarget target, DialogButton button)
	{
		target.add(this.feedback);
	}
}
