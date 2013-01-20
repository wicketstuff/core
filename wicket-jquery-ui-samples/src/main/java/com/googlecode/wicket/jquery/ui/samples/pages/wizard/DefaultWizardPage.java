package com.googlecode.wicket.jquery.ui.samples.pages.wizard;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.wizard.StaticContentStep;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.bean.User;
import com.googlecode.wicket.jquery.ui.widget.wizard.AbstractWizard;

public class DefaultWizardPage extends AbstractWizardPage
{
	private static final long serialVersionUID = 1L;

	public DefaultWizardPage()
	{
		// Form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Wizard //
		final UserWizard wizard = new UserWizard("wizard", "Create a user") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onCancel(AjaxRequestTarget target)
			{
				this.info("Canceled...");
				target.add(feedback);
			}

			@Override
			protected void onFinish(AjaxRequestTarget target)
			{
				User user = this.getModelObject();

				this.info(String.format("Created user: '%s' - %s [%s]", user.getName(), user.getMail(), user.getRole()));
				target.add(feedback.setEscapeModelStrings(false));
			}
		};

		this.add(wizard);

		// Button //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryIcon getIcon()
			{
				return JQueryIcon.GEAR;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				wizard.setModelObject(new User());
				wizard.open(target);
			}
		});
	}

	/**
	 * This wizard class is located here for convenience in this sample
	 */
	abstract class UserWizard extends AbstractWizard<User>
	{
		private static final long serialVersionUID = 1L;

		public UserWizard(String id, String title)
		{
			super(id, title);

			WizardModel wizardModel = new WizardModel();

			wizardModel.add(new Step1());
			wizardModel.add(new Step2());
			wizardModel.add(new Step3());
			wizardModel.setLastVisible(true); //makes the 'last step button' visible

			this.init(wizardModel);
		}

		@Override
		public void setModelObject(User user)
		{
			this.setDefaultModel(new CompoundPropertyModel<User>(user));
		}

		/**
		 * Provides the 'Name and email' step<br/>
		 * Associated markup file is DefaultWizardPage$UserWizard$Step1.html
		 */
		class Step1 extends WizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step1()
			{
				super("Name & email", "Please provides a user name and an email");

				this.add(new RequiredTextField<String>("name"));
				this.add(new EmailTextField("mail"));
			}
		}

		/**
		 * Provides the 'Role' step
		 * Associated markup file is DefaultWizardPage$UserWizard$Step2.html
		 */
		class Step2 extends WizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step2()
			{
				super("User role", "Please select the user role");

				this.add(new RadioChoice<String>("role", Arrays.asList("Admin", "User", "Guest")).setRequired(true));
			}
		}

		/**
		 * Provides the 'summary' step
		 */
		class Step3 extends StaticContentStep
		{
			private static final long serialVersionUID = 1L;

			public Step3()
			{
				super("Summary", "Please review information below:", Model.of(), true);
			}

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				User user = UserWizard.this.getModelObject();

				StringBuilder builder = new StringBuilder("<ul>");
				builder.append("<li>").append("User name: ").append(user.getName()).append("</li>");
				builder.append("<li>").append("User mail: ").append(user.getMail()).append("</li>");
				builder.append("<li>").append("User role: ").append(user.getRole()).append("</li>");
				builder.append("</ul>");

				this.setContentModel(new Model<String>(builder.toString()));
			}
		}
	}
}
