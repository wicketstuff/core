package com.googlecode.wicket.jquery.ui.samples.pages.dialog;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.UserDialog;
import com.googlecode.wicket.jquery.ui.samples.component.UserDialog.User;

public class UserDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;
	
	private final List<User> users; 
	
	public UserDialogPage()
	{
		this.users = new ArrayList<User>();

		this.init();
	}
	
	private void init()
	{
		final Form<List<User>> form = new Form<List<User>>("form", new ListModel<User>(this.users));
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Dialog //
		final UserDialog dialog = new UserDialog("dialog", "User details") {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target)
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
			protected void onClose(AjaxRequestTarget target, String button)
			{
				target.add(form);
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
					protected void onSubmit(AjaxRequestTarget target, Form<?> form)
					{
						dialog.setModelObject(item.getModelObject());
						dialog.open(target);
					}
					
				});
			}
		});

		// Buttons //
		form.add(new AjaxButton("create") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.setModelObject(new User()); //Provides a new model object to the dialog
				dialog.open(target); //Important: onOpen() event has been overridden in UserDialog to re-attach the inner form, in order to reflect the updated model
			}
		});
	}
}
