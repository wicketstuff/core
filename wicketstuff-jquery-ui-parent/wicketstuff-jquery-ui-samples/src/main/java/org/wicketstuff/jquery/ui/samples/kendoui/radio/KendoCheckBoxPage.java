package org.wicketstuff.jquery.ui.samples.kendoui.radio;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.form.CheckBox;
import org.wicketstuff.kendo.ui.form.CheckBox.Label;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class KendoCheckBoxPage extends AbstractRadioPage
{
	private static final long serialVersionUID = 1L;

	private final Form<Boolean> form;

	public KendoCheckBoxPage()
	{
		// form //
		this.form = new Form<Boolean>("form", Model.of(Boolean.FALSE));
		this.add(this.form);

		// feedback //
		this.form.add(new KendoFeedbackPanel("feedback"));

		// checkbox //
		CheckBox checkbox = new CheckBox("check", this.form.getModel());
		Label label = new Label("label", "My checkbox", checkbox);
		this.form.add(checkbox, label);

		// buttons //
		this.form.add(this.newSubmitButton("submit"));
		this.form.add(this.newAjaxButton("button"));
	}

	private void info(Component component, IModel<Boolean> model)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + model.getObject());
	}

	private Button newSubmitButton(String id)
	{
		return new Button(id) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				KendoCheckBoxPage.this.info(this, form.getModel());
			}
		};
	}

	private AjaxButton newAjaxButton(String id)
	{
		return new AjaxButton(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				KendoCheckBoxPage.this.info(this, form.getModel());
				target.add(form);
			}
		};
	}
}