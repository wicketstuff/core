package org.wicketstuff.mootools.meiomask.examples;

import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Time;
import org.wicketstuff.mootools.meiomask.CustomMaskField;
import org.wicketstuff.mootools.meiomask.MaskType;
import org.wicketstuff.mootools.meiomask.MeioMaskField;


public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;
	private TestModel testModel = new TestModel();

	public HomePage(final PageParameters parameters)
	{

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedBack");
		add(feedbackPanel);

		Form<TestModel> form = new Form<TestModel>("form", new CompoundPropertyModel<TestModel>(
			testModel))
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				info("custom: " + getModelObject().getCustom());
				info("custom2: " + getModelObject().getCustom2());
				info("fixed-phone: " + getModelObject().getFixedPhone());
				info("fixed-phone-us: " + getModelObject().getFixedPhoneUs());
				info("fixed-cpf: " + getModelObject().getFixedCpf());
				info("fixed-cnpj: " + getModelObject().getFixedCnpj());
				info("fixed-cep: " + getModelObject().getFixedCep());
				info("fixed-cc: " + getModelObject().getFixedCc());
				info("fixed-date: " + getModelObject().getFixedDate());
				info("reverse.integer: " + getModelObject().getReverseInteger());

			}
		};

		add(form);

		form.add(new CustomMaskField<String>("custom", "#### LL"));
		form.add(new CustomMaskField<Integer>("custom2", "##:##"));
		form.add(new MeioMaskField<String>("fixedPhone", MaskType.FixedPhone));
		form.add(new MeioMaskField<String>("fixedPhoneUs", MaskType.FixedPhoneUs));
		form.add(new MeioMaskField<String>("fixedCpf", MaskType.FixedCpf));
		form.add(new MeioMaskField<String>("fixedCnpj", MaskType.FixedCnpj));
		form.add(new MeioMaskField<String>("fixedCep", MaskType.FixedCep));
		form.add(new MeioMaskField<Time>("fixedTime", MaskType.FixedTime));
		form.add(new MeioMaskField<String>("fixedCc", MaskType.FixedCc));
		form.add(new MeioMaskField<Date>("fixedDate", MaskType.FixedDate));

		form.add(new MeioMaskField<Integer>("reverseInteger", MaskType.ReverseInteger));


	}
}
