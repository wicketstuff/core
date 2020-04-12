package com.googlecode.wicket.jquery.ui.samples.kendoui.radio;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.form.Check;
import com.googlecode.wicket.kendo.ui.form.Check.Label;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class KendoCheckPage extends AbstractRadioPage
{
	private static final long serialVersionUID = 1L;

	private final Form<List<String>> form;

	public KendoCheckPage()
	{
		// form //
		this.form = new Form<List<String>>("form", Model.ofList(new ArrayList<String>()));
		this.add(this.form);

		// feedback //
		this.form.add(new KendoFeedbackPanel("feedback"));

		// checks //
		final CheckGroup<String> group = new CheckGroup<String>("checkgroup", form.getModel());
		this.form.add(group);

		Check<?> check1 = new Check<String>("check1", Model.of("My check 1"), group);
		Label label1 = new Label("label1", "My check 1", check1);
		group.add(check1, label1);

		Check<?> check2 = new Check<String>("check2", Model.of("My check 2"), group);
		Label label2 = new Label("label2", "My check 2", check2);
		group.add(check2, label2);

		Check<?> check3 = new Check<String>("check3", Model.of("My check 3"), group);
		Label label3 = new Label("label3", "My check 3", check3);
		group.add(check3.setEnabled(false), label3);

		// buttons //
		this.form.add(this.newSubmitButton("submit"));
		this.form.add(this.newAjaxButton("button"));
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(KendoCheckPage.class));
	}

	private void info(Component component, IModel<List<String>> model)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + model.getObject());
	}

	// Factories //

	private Button newSubmitButton(String id)
	{
		return new Button(id) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				KendoCheckPage.this.info(this, form.getModel());
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
				KendoCheckPage.this.info(this, form.getModel());
				target.add(form);
			}
		};
	}
}
