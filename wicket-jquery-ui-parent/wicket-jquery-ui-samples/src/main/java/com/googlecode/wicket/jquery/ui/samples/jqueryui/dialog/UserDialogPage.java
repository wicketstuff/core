package com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.bean.User;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

public class UserDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;

	private final List<User> users;

	public UserDialogPage()
	{
		this.users = Generics.newArrayList();

		this.initialize();
	}

	// Methods //

	private void initialize()
	{
		final Form<List<User>> form = new Form<List<User>>("form", Model.ofList(this.users));
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Dialog //
		final UserDialog dialog = new UserDialog("dialog", "User details") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, DialogButton button)
			{
				User user = this.getModelObject();

				if (!users.contains(user))
				{
					users.add(user);
					this.info(String.format("User '%s' created", user.getName()));
				}
				else
				{
					this.info(String.format("User '%s' updated", user.getName()));
				}
			}

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button)
			{
				handler.add(form);
			}
		};

		this.add(dialog);

		// ListView //
		form.add(new PropertyListView<User>("user", form.getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<User> item)
			{
				item.add(new Label("name"));
				item.add(new Label("mail"));
				item.add(new Label("role"));
				item.add(new AjaxButton("edit") {

					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target)
					{
						User user = item.getModelObject();

						dialog.setTitle(target, "Update user " + user.getName());
						dialog.setModelObject(user);
						dialog.open(target);
					}
				});
			}
		});

		// Buttons //
		form.add(new AjaxButton("create") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				dialog.setTitle(target, "Create new user");
				dialog.setModelObject(new User()); // Provides a new model object to the dialog
				dialog.open(target); // Important: onOpen() event has been overridden in UserDialog to re-attach the inner form, in order to reflect the updated model
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(UserDialogPage.class));
	}

	// Classes //

	/**
	 * This dialog class is located here for convenience in this sample<br>
	 * Associated markup file is UserDialogPage$UserDialog.html
	 */
	abstract static class UserDialog extends AbstractFormDialog<User>
	{
		private static final long serialVersionUID = 1L;
		protected final DialogButton btnSubmit = new DialogButton(SUBMIT, "Save", JQueryIcon.CHECK);
		protected final DialogButton btnCancel = new DialogButton(CANCEL, LBL_CANCEL, JQueryIcon.CANCEL);

		private Form<?> form;
		private FeedbackPanel feedback;

		public UserDialog(String id, String title)
		{
			super(id, title, true);

			this.form = new Form<User>("form", new CompoundPropertyModel<User>(this.getModel()));
			this.add(this.form);

			// Slider //
			this.form.add(new RequiredTextField<String>("name"));
			this.form.add(new EmailTextField("mail"));
			this.form.add(new RadioChoice<String>("role", Arrays.asList("Admin", "User", "Guest")).setRequired(true));

			// FeedbackPanel //
			this.feedback = new JQueryFeedbackPanel("feedback");
			this.form.add(this.feedback);
		}
		
		// Methods //

		@Override
		protected IModel<?> initModel()
		{
			return new Model<User>();
		}
		

		@Override
		public void renderHead(IHeaderResponse response)
		{
			super.renderHead(response);

			response.render(CssReferenceHeaderItem.forReference(new CssResourceReference(UserDialogPage.class, "UserDialogPage$UserDialog.css")));
		}

		// AbstractFormDialog //

		@Override
		protected List<DialogButton> getButtons()
		{
			return Arrays.asList(this.btnSubmit, this.btnCancel);
		}

		@Override
		public DialogButton getSubmitButton()
		{
			return this.btnSubmit;
		}

		@Override
		public Form<?> getForm()
		{
			return this.form;
		}

		// Events //

		@Override
		protected void onOpen(IPartialPageRequestHandler handler)
		{
			handler.add(this.form);
		}

		@Override
		public void onError(AjaxRequestTarget target, DialogButton button)
		{
			target.add(this.feedback);
		}
	}
}
