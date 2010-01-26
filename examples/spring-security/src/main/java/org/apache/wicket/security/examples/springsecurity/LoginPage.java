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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.util.TextUtils;

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

	@SpringBean
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
			session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY);
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
					AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, TextUtils
						.escapeEntities(username));

				UsernamePasswordAuthenticationToken authRequest =
					new UsernamePasswordAuthenticationToken(username, password
						.getDefaultModelObjectAsString());
				// authRequest.setDetails(authenticationDetailsSource.buildDetails(((WebRequest)getRequest()).getHttpServletRequest()));
				/*
				 * Authentication authResult; try { authResult =
				 * authManager.authenticate(authRequest); } catch (AuthenticationException
				 * failed) { // Authentication failed
				 * //unsuccessfulAuthentication(request, response, failed);
				 * SecurityContextHolder.getContext().setAuthentication(null);
				 * session.setAttribute
				 * (AuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY,
				 * failed); error(failed.getLocalizedMessage()); return; }
				 * SecurityContextHolder.getContext().setAuthentication(authResult);
				 * info("authentication succeeded"); LoginContext context = new
				 * SpringSecureLoginContext(authResult);
				 */

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
						httpSession.setAttribute(
							AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, e);
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
