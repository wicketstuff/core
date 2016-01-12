package ${packageName}.app;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.util.value.ValueMap;

/**
 * Panel for user authentication. Simple username password authentication.
 * 
 * @author marrink
 */
public abstract class UsernamePasswordSignInPanel extends Panel
{

	/**
	 * Constructor.
	 * 
	 * @param id component id
	 */
	public UsernamePasswordSignInPanel(final String id)
	{
		super(id);
		add(new SignInForm("signInForm").setOutputMarkupId(false));
	}

	/**
	 * The actual authentication process.
	 * 
	 * @param username
	 * @param password
	 * @throws LoginException if the user could not be authenticated
	 */
	public abstract void signIn(String username, String password) throws LoginException;

	/**
	 * Sign in form with username password fields. It also has a rememberMe checkbox which allows the browser to remember the username field using a cookie
	 */
	public final class SignInForm extends StatelessForm
	{

		private static final long serialVersionUID = 1L;

		/**
		 * remember username
		 */
		private boolean rememberMe = true;

		/**
		 * Constructor.
		 * 
		 * @param id id of the form component
		 */
		public SignInForm(final String id)
		{
			super(id, new CompoundPropertyModel(new ValueMap()));

			// only remember username, not passwords
			add(new TextField("username").setPersistent(rememberMe).setOutputMarkupId(false));
			add(new PasswordTextField("password").setOutputMarkupId(false));
			//checkbox updates form field rememberMe
			add(new CheckBox("rememberMe", new PropertyModel(this, "rememberMe")));
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		public final void onSubmit()
		{
			if(!rememberMe)
			{
				// delete persistent data
				getPage().removePersistedFormData(SignInForm.class, true);
			}

			ValueMap values = (ValueMap) getModelObject();
			String username = values.getString("username");
			String password = values.getString("password");

			try
			{
				signIn(username, password);
				// continue or homepage?
				if(!getPage().continueToOriginalDestination())
				{
					setResponsePage(Application.get().getHomePage());
				}
			}
			catch(LoginException e)
			{
				// Try the component based localizer first. If not found try the
				// application localizer. Else use the default
				error(getLocalizer().getString("exception.login", this, e.getLocalizedMessage()));
			}
		}

		/**
		 * @return true if formdata should be made persistent (cookie) for later logins.
		 */
		public boolean getRememberMe()
		{
			return rememberMe;
		}

		/**
		 * Remember form values for later logins?.
		 * 
		 * @param rememberMe true if formdata should be remembered
		 */
		public void setRememberMe(boolean rememberMe)
		{
			this.rememberMe = rememberMe;
			((FormComponent) get("username")).setPersistent(rememberMe);
		}
	}
}
