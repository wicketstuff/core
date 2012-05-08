package com.googlecode.wicket.jquery.ui.samples.component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicket.jquery.ui.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.UserDialog.User;

public abstract class UserDialog extends AbstractFormDialog<User>
{
	private static final long serialVersionUID = 1L;
	protected static final String BTN_SUBMIT = "Save";
	
	private Form<?> form;
	private FeedbackPanel feedback;
	
	public UserDialog(String id, String title)
	{
		super(id, title, true);
		
		this.form = new Form<Integer>("form");
		this.add(this.form);
		
		// Slider //
		this.form.add(new RequiredTextField<String>("name"));
		this.form.add(new EmailTextField("mail"));
		this.form.add(new RadioChoice<String>("role", Arrays.asList("Admin", "User", "Guest")).setRequired(true));

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);	
	}

	// AbstractFormDialog //
	@Override
	protected List<String> getButtons()
	{
		return Arrays.asList(BTN_SUBMIT, BTN_CANCEL);
	}

	@Override
	protected String getSubmitButton()
	{
		return BTN_SUBMIT;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}
	
	public void setModelObject(User user)
	{
		this.setDefaultModel(new CompoundPropertyModel<User>(user));
	}

	// Events //
	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		target.add(this.form);
	}

	@Override
	public void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}

	public static class User implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private String name;
		private String mail;
		private String role;
		
		public User()
		{
			this.name = "";
			this.mail = "";
			this.role = "";
		}
		
		public User(String name, String mail, String role)
		{
			this.name = name;
			this.mail = mail;
			this.role = role;
		}

		public String getName()
		{
			return this.name;
		}
		
		public String getMail()
		{
			return this.mail;
		}
		
		public String getRole()
		{
			return this.role;
		}
		
		@Override
		public String toString()
		{
			return String.format("%s [%s] - %s", this.name, this.mail, this.role);
		}
	}
}
