package org.apache.wicket.security.examples.springsecurity;

import javax.servlet.http.HttpSession;

import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.examples.springsecurity.security.SpringSecureLoginContext;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Olger Warnier
 */
public class LoginPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	private Form<Void> form;

	TextField<String> userName;

	TextField<String> password;

	// protected AuthenticationDetailsSource authenticationDetailsSource = new
	// WebAuthenticationDetailsSource();

	@SpringBean(name = "authenticationManager")
	AuthenticationManager authManager;

	public LoginPage()
	{
		// stateless so the login page will not throw a timeout exception
		// note that is only a hint we need to have stateless components on the
		// page for this to work, like a statelessform
		InjectorHolder.getInjector().inject(this);

		setStatelessHint(true);
		HttpSession session = ((WebRequest) getRequest()).getHttpServletRequest().getSession();
		Object lastUsername =
			session
				.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY);
		Model<String> userNameModel = null;
		if (lastUsername != null && lastUsername instanceof String)
		{
			userNameModel = new Model<String>((String) lastUsername);
		}
		else
		{
			userNameModel = new Model<String>();
		}

		userName = new TextField<String>("userName", userNameModel);
		userName.setRequired(true);

		password = new TextField<String>("password", new Model<String>());
		password.setRequired(true);

		form = new Form<Void>("loginForm")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit()
			{
				super.onSubmit();
				String username = userName.getDefaultModelObjectAsString();
				username = username.trim();

				HttpSession httpSession =
					((WebRequest) getRequest()).getHttpServletRequest().getSession();
				httpSession.setAttribute(
					UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY, Strings
						.escapeMarkup(username));

				UsernamePasswordAuthenticationToken authRequest =
					new UsernamePasswordAuthenticationToken(username, password
						.getDefaultModelObjectAsString());

				LoginContext context = new SpringSecureLoginContext(authRequest);
				try
				{
					((WaspSession) getSession()).login(context);
					// TODO get the page originally requested
					setResponsePage(getApplication().getHomePage());
				}
				catch (LoginException e)
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("Authentication failed", e);
						SecurityContextHolder.getContext().setAuthentication(null);
						httpSession.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e);
						((WaspSession) getSession()).invalidateNow();
					}
					throw new RestartResponseAtInterceptPageException(Application.get()
						.getApplicationSettings().getAccessDeniedPage());
				}

			}
		};
		form.add(new FeedbackPanel("feedback"));
		form.add(userName);
		form.add(password);

		SubmitLink submit = new SubmitLink("submit");
		form.add(submit);

		add(form);
	}
}
