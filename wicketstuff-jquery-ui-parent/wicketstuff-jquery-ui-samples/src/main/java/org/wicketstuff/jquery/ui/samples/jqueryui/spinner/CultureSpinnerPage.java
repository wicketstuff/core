package org.wicketstuff.jquery.ui.samples.jqueryui.spinner;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.wicketstuff.jquery.core.JQueryBehavior;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.jquery.ui.form.button.Button;
import org.wicketstuff.jquery.ui.form.spinner.Spinner;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.samples.SampleApplication;
import org.wicketstuff.kendo.ui.form.dropdown.AjaxDropDownList;

public class CultureSpinnerPage extends AbstractSpinnerPage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedback;
	private CultureSpinner spinner;

	public CultureSpinnerPage()
	{
		final Form<Double> form = new Form<Double>("form", Model.of(Double.valueOf(1.5)));
		this.add(form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		form.add(this.feedback.setOutputMarkupId(true));

		// Spinner //
		this.spinner = new CultureSpinner("spinner", form.getModel());
		this.spinner.setMin(1).setMax(50);
		this.spinner.setStep(5).setPage(4);
		this.spinner.setRequired(true);

		form.add(this.spinner);

		// Radio //
		CultureDropDown dropdown = new CultureDropDown("dropdown") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelectionChanged(AjaxRequestTarget target)
			{
				spinner.setCulture(this.getModelObject());
				target.add(form);
			}
		};

		form.add(dropdown);

		// Buttons //
		form.add(new Button("submit") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				CultureSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(CultureSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				CultureSpinnerPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	private void info(Component component, Form<?> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}

	static class CultureSpinner extends Spinner<Double> // NOSONAR
	{
		private static final long serialVersionUID = 1L;
		static final String CULTURE = "fr-FR"; //default culture

		public CultureSpinner(String id, IModel<Double> model)
		{
			super(id, model, Double.class);

			this.setCulture(CULTURE);
//			this.setNumberFormat("C");
		}

		@Override
		public void onConfigure(JQueryBehavior behavior)
		{
			super.onConfigure(behavior);

			behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.en-US.js"));
			behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.de-DE.js"));
			behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.fr-FR.js"));
		}
	}

	class CultureDropDown extends AjaxDropDownList<String> // NOSONAR
	{
		private static final long serialVersionUID = 1L;

		public CultureDropDown(String id)
		{
			super(id, Model.of(CultureSpinner.CULTURE), Arrays.asList("en-US", "de-DE", CultureSpinner.CULTURE));
		}
	}
}
