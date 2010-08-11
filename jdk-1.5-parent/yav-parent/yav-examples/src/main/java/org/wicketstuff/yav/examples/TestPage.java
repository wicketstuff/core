package org.wicketstuff.yav.examples;

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
public class TestPage extends WebPage {

	@SuppressWarnings("unchecked")
	public TestPage() {
		super();

		ValidationTestBean validationTestBean = new ValidationTestBean();

		// Add form to the page
		Form form = new Form("exampleForm", new CompoundPropertyModel(validationTestBean));
		add(form);

		form.add(new YavBehavior());

		form.add(new RequiredTextField("typeDate1"));
		
		form.add(new RequiredTextField("typeDate2"));
		
		form.add(new RequiredTextField("typeInt"));
		
		form.add(new RequiredTextField("typeDecimal"));

		form.add(new RequiredTextField("typeBigDecimal"));
		
		form.add(new RequiredTextField("maxLengthString")
				.add(StringValidator.MaximumLengthValidator.maximumLength(10)));

		form.add(new RequiredTextField("minLengthString")
				.add(StringValidator.MaximumLengthValidator.minimumLength(10)));

		form.add(new RequiredTextField("exactLengthString")
				.add(StringValidator.ExactLengthValidator.exactLength(10)));

		form.add(new RequiredTextField("lengthBetweenString")
				.add(StringValidator.LengthBetweenValidator.lengthBetween(10, 20)));

		form.add(new RequiredTextField("email")
				.add(EmailAddressValidator.getInstance()));

		form.add(new RequiredTextField("pattern", new Model())
				.add(new PatternValidator(".*\\.com")));

		FormComponent formComponent1 = new RequiredTextField("dateOfBirth1");
		FormComponent formComponent2 = new RequiredTextField("dateOfBirth2");
		form.add(formComponent1);
		form.add(formComponent2);
		form.add(new EqualInputValidator(formComponent1, formComponent2));

		form.add(new RequiredTextField("rangeLong", Long.class)
				.add(new RangeValidator(10L, 20L)));

		// Seems not to be supported by Yav yet
		// form.add(new RequiredTextField("minInt").add(new MinimumValidator(10)));

		// Seems not to be supported by Yav yet
		// form.add(new RequiredTextField("maxInt").add(new MaximumValidator(100)));

		form.add(new Button("submit") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				System.out.println(getForm().getModelObject());
			}
		});
		add(new FeedbackPanel("feedback"));
	}
}
