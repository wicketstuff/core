package com.googlecode.wicket.jquery.ui.samples.pages.test;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.spinner.Spinner;
import com.googlecode.wicket.jquery.ui.kendo.dropdown.AjaxDropDownList;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.SampleApplication;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;

public class OptionSpinnerPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedback;
	private CultureSpinner spinner;

	public OptionSpinnerPage()
	{
		this.init();
	}

	private void init()
	{
		final Form<Double> form = new Form<Double>("form", new Model<Double>(new Double(1.5)));
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
		CultureDropDown dropdown = new CultureDropDown("dropdown") {

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
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				OptionSpinnerPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(OptionSpinnerPage.this.feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				OptionSpinnerPage.this.info(this, form);
				target.add(form);
			}
		});

	}

	private void info(Component component, Form<?> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}

	class CultureSpinner extends Spinner<Double>
	{
		private static final long serialVersionUID = 1L;
		static final String CULTURE = "fr-FR"; //default culture
//		static final String CULTURE = "en-US"; //default culture

		public CultureSpinner(String id, IModel<Double> model)
		{
			super(id, model, Double.class);

			this.setCulture(CULTURE);
//			this.setNumberFormat("C");
		}

		@Override
		protected void onConfigure(JQueryBehavior behavior)
		{
			super.onConfigure(behavior);

			behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.en-US.js"));
			behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.de-DE.js"));
			behavior.add(new JavaScriptResourceReference(SampleApplication.class, "globalize.culture.fr-FR.js"));
		}

		@Override
		public <C> IConverter<C> getConverter(Class<C> type)
		{
//			if (Double.class.isAssignableFrom(type))
//			{
//				return (IConverter<C>) new CurrencyConverter<Double>(this) {
//
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					protected Class<Double> getTargetType()
//					{
//						return Double.class;
//					}
//
//				};
//			}

			return super.getConverter(type);
		}
	}

	class CultureDropDown extends AjaxDropDownList<String>
	{
		private static final long serialVersionUID = 1L;

		public CultureDropDown(String id)
		{
			super(id, Model.of(CultureSpinner.CULTURE), Arrays.asList("en-US", "de-DE", CultureSpinner.CULTURE));
		}
	}
}
