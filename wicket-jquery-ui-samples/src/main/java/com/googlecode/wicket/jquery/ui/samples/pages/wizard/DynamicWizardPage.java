package com.googlecode.wicket.jquery.ui.samples.pages.wizard;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.dynamic.DynamicWizardModel;
import org.apache.wicket.extensions.wizard.dynamic.DynamicWizardStep;
import org.apache.wicket.extensions.wizard.dynamic.IDynamicWizardStep;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.bean.User;
import com.googlecode.wicket.jquery.ui.samples.data.bean.User.Avatar;
import com.googlecode.wicket.jquery.ui.widget.wizard.AbstractWizard;

public class DynamicWizardPage extends AbstractWizardPage
{
	private static final long serialVersionUID = 1L;

	public DynamicWizardPage()
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

				this.info(String.format("Created user: '%s' - %s [%s] with %s", user.getName(), user.getMail(), user.getRole(), (user.getAvatar() != null ? user.getAvatar() : "no avatar" )));
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

		private final IDynamicWizardStep step0;
		private final IDynamicWizardStep step1;
		private final IDynamicWizardStep step2;
		private final IDynamicWizardStep step3;
		private final IDynamicWizardStep step4;

		private IModel<Boolean> enableAvatarStepModel = new Model<Boolean>(Boolean.FALSE);

		public UserWizard(String id, String title)
		{
			super(id, title, new Model<User>(new User()));

			this.step0 = new Step0();
			this.step1 = new Step1();
			this.step2 = new Step2();
			this.step3 = new Step3();
			this.step4 = new Step4();

			final IWizardModel wizardModel = new DynamicWizardModel(this.step0);
			this.init(wizardModel);
		}

		@Override
		public void setModelObject(User user)
		{
			this.setDefaultModel(new CompoundPropertyModel<User>(user));
		}

		@Override
		public int getWidth()
		{
			return 520;
		}

		class Step0 extends DynamicWizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step0()
			{
				super(null, "Welcome!", "Introduction to the user creation wizard.");

				// Radio Button //
				this.add(new RadioChoice<Boolean>("enableAvatarStep", enableAvatarStepModel, Arrays.asList(Boolean.TRUE, Boolean.FALSE), new IChoiceRenderer<Boolean>() {

					private static final long serialVersionUID = 1L;

					@Override
					public Object getDisplayValue(Boolean bool)
					{
						return (bool ? "Yes" : "No");
					}

					@Override
					public String getIdValue(Boolean bool, int index)
					{
						return Integer.toString(index);
					}
				}));
			}

			@Override
			public boolean isLastStep()
			{
				return false;
			}

			@Override
			public IDynamicWizardStep next()
			{
				return UserWizard.this.step1;
			}

			// Events //
			@Override
			public void applyState()
			{
				super.applyState();

				// reset the avatar to null if the option has been changed since the avatar has been set
				// (ie, the users chooses 'yes', set the avatar and then choose no by going steps backward)
				if (UserWizard.this.enableAvatarStepModel.getObject() == false)
				{
					User user = UserWizard.this.getModelObject();

					if (user != null)
					{
						user.setAvatar(null);
					}
				}
			}
		}

		/**
		 * Provides the 'Name and email' step<br/>
		 * Associated markup file is DefaultWizardPage$UserWizard$Step1.html
		 */
		class Step1 extends DynamicWizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step1()
			{
				super(UserWizard.this.step0, "Name & email", "Please provides a user name and an email");

				this.add(new RequiredTextField<String>("name"));
				this.add(new EmailTextField("mail"));
			}

			@Override
			public boolean isLastStep()
			{
				return false;
			}

			@Override
			public IDynamicWizardStep next()
			{
				return UserWizard.this.step2;
			}
		}

		/**
		 * Provides the 'Role' step
		 * Associated markup file is DefaultWizardPage$UserWizard$Step2.html
		 */
		class Step2 extends DynamicWizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step2()
			{
				super(UserWizard.this.step1, "User role", "Please select the user role");

				this.add(new RadioChoice<String>("role", Arrays.asList("Admin", "User", "Guest")).setRequired(true));
			}

			@Override
			public boolean isLastStep()
			{
				return false;
			}

			@Override
			public IDynamicWizardStep next()
			{
				//defines if wizard goes to avatar step or finish step
				return (UserWizard.this.enableAvatarStepModel.getObject() ? UserWizard.this.step3 : UserWizard.this.step4);
			}
		}

		/**
		 * Provides the 'avatar' step
		 * Associated markup file is DefaultWizardPage$UserWizard$Step3.html
		 */
		class Step3 extends DynamicWizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step3()
			{
				super(UserWizard.this.step2, "Avatar", "Please select an avatar");

				final WebMarkupContainer container = new WebMarkupContainer("selectable");
				this.add(container.setOutputMarkupId(true));

				// Avatar ListView //
				container.add(new ListView<Avatar>("items", AVATARS) {

					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(final ListItem<Avatar> item)
					{
						final Avatar avatar = item.getModelObject();

						item.add(new ContextImage("item", avatar.getImagePath()));
						item.add(new AjaxEventBehavior("onclick") {

							private static final long serialVersionUID = 1L;

							@Override
							protected void onEvent(AjaxRequestTarget target)
							{
								UserWizard.this.getModelObject().setAvatar(avatar);
								target.add(container);
							}
						});

						// adds the css class
						if (UserWizard.this.getModelObject().getAvatar() != null && UserWizard.this.getModelObject().getAvatar().getId() == item.getModelObject().getId())
						{
							item.add(new JQueryAbstractBehavior("item-selected") {

								private static final long serialVersionUID = 1L;

								@Override
								protected String $()
								{
									return String.format("jQuery('#%s').addClass('selected');", item.getMarkupId());
								}
							});
						}

					}
				});
			}

			@Override
			public boolean isLastStep()
			{
				return false;
			}

			@Override
			public IDynamicWizardStep next()
			{
				return UserWizard.this.step4;
			}
		}

		/**
		 * Provides the 'summary' step
		 * Associated markup file is DefaultWizardPage$UserWizard$Step4.html
		 */
		class Step4 extends DynamicWizardStep
		{
			private static final long serialVersionUID = 1L;

			public Step4()
			{
				super(UserWizard.this.step2, "Summary", "Please review information below:");

				this.add(new Label("name"));
				this.add(new Label("mail"));
				this.add(new Label("role"));
				this.add(new Label("avatar"));
			}

			@Override
			public boolean isLastStep()
			{
				return true;
			}

			@Override
			public IDynamicWizardStep previous()
			{
				return (UserWizard.this.enableAvatarStepModel.getObject() ? UserWizard.this.step3 : super.previous());
			}

			@Override
			public IDynamicWizardStep next()
			{
				return UserWizard.this.step4;
			}
		}
	}

	// List of Avatar(s) //
	static final List<Avatar> AVATARS = Arrays.asList(
			new Avatar(1, "avatar01.jpg"),
			new Avatar(2, "avatar02.jpg"),
			new Avatar(3, "avatar03.jpg"),
			new Avatar(4, "avatar04.jpg"),
			new Avatar(5, "avatar05.jpg"),
			new Avatar(6, "avatar06.jpg"),
			new Avatar(7, "avatar07.jpg"),
			new Avatar(8, "avatar08.jpg"),
			new Avatar(9, "avatar09.jpg"),
			new Avatar(10, "avatar10.jpg"),
			new Avatar(11, "avatar11.jpg"),
			new Avatar(12, "avatar12.jpg"),
			new Avatar(13, "avatar13.jpg"),
			new Avatar(14, "avatar14.jpg"),
			new Avatar(15, "avatar15.jpg"));

}
