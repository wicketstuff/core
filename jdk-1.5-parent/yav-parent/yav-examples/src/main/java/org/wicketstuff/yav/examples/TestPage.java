package org.wicketstuff.yav.examples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.yav.YavBehavior;

/**
 * @author Zenika
 * 
 */
public class TestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public TestPage()
	{
		super();

		ValidationTestBean validationTestBean = new ValidationTestBean();

		// Add form to the page
		Form<ValidationTestBean> form = new Form<ValidationTestBean>("exampleForm",
			new CompoundPropertyModel<ValidationTestBean>(validationTestBean));
		add(form);

		form.add(new YavBehavior());

		form.add(new RequiredTextField<Date>("typeDate1"));

		form.add(new RequiredTextField<Date>("typeDate2"));

		form.add(new RequiredTextField<Integer>("typeInt"));

		form.add(new RequiredTextField<Float>("typeDecimal"));

		form.add(new RequiredTextField<BigDecimal>("typeBigDecimal"));

		form.add(new RequiredTextField<String>("maxLengthString").add(StringValidator.maximumLength(10)));

		form.add(new RequiredTextField<String>("minLengthString").add(StringValidator.minimumLength(10)));

		form.add(new RequiredTextField<String>("exactLengthString").add(StringValidator.exactLength(10)));

		form.add(new RequiredTextField<String>("lengthBetweenString").add(StringValidator.lengthBetween(
			10, 20)));

		form.add(new RequiredTextField<String>("email").add(EmailAddressValidator.getInstance()));

		form.add(new RequiredTextField<String>("pattern", new Model<String>()).add(new PatternValidator(
			".*\\.com")));

		FormComponent<Date> formComponent1 = new RequiredTextField<Date>("dateOfBirth1");
		FormComponent<Date> formComponent2 = new RequiredTextField<Date>("dateOfBirth2");
		form.add(formComponent1);
		form.add(formComponent2);
		form.add(new EqualInputValidator(formComponent1, formComponent2));

		form.add(new RequiredTextField<Long>("rangeLong", Long.class).add(new RangeValidator<Long>(
			10L, 20L)));

		// Seems not to be supported by Yav yet
		// form.add(new RequiredTextField("minInt").add(new MinimumValidator(10)));

		// Seems not to be supported by Yav yet
		// form.add(new RequiredTextField("maxInt").add(new MaximumValidator(100)));

		form.add(new Button("submit")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				System.out.println(getForm().getModelObject());
			}
		});
		add(new FeedbackPanel("feedback"));
	}
}
