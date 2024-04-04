package com.googlecode.wicket.jquery.ui.samples.kendoui.radio;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.form.Radio;
import com.googlecode.wicket.kendo.ui.form.Radio.Label;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class DefaultRadioPage extends AbstractRadioPage
{
	private static final long serialVersionUID = 1L;

	public DefaultRadioPage()
	{
		// form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// feedback //
		form.add(new KendoFeedbackPanel("feedback"));

		// radios //
		final IModel<String> radioModel = new Model<String>();

		final RadioGroup<String> group = new RadioGroup<String>("radiogroup", radioModel);
		form.add(group);

		Radio<String> radio1 = new Radio<String>("radio1", Model.of("My radio 1"), group);
		Label label1 = new Label("label1", "My radio 1", radio1);
		group.add(radio1, label1);

		Radio<String> radio2 = new Radio<String>("radio2", Model.of("My radio 2"), group);
		Label label2 = new Label("label2", "My radio 2", radio2);
		group.add(radio2, label2);

		Radio<String> radio3 = new Radio<String>("radio3", Model.of("My radio 3"), group);
		Label label3 = new Label("label3", "My radio 3", radio3);
		group.add(radio3.setEnabled(false), label3);

		// buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultRadioPage.this.info(this, radioModel);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				DefaultRadioPage.this.info(this, radioModel);
				target.add(form);
			}
		});
	}
	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DefaultRadioPage.class));
	}

	private void info(Component component, IModel<String> model)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + model.getObject());
	}
}
