package org.wicketstuff.facebook.behaviors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.wicketstuff.facebook.FacebookPermission;
import org.wicketstuff.facebook.FacebookSdk;
import org.wicketstuff.facebook.plugins.LoginButton;

public class LoginEventPage extends WebPage
{
	/**
	 * 
	 */
	public LoginEventPage()
	{
		add(new FacebookSdk("fb-root", "149850315074596"));
		add(new LoginButton("loginButton", FacebookPermission.user_about_me,
			FacebookPermission.friends_about_me));

		final Model<String> responseModel = new Model<String>();
		final MultiLineLabel responseLabel = new MultiLineLabel("response", responseModel);
		responseLabel.setOutputMarkupId(true);
		add(responseLabel);


		add(new AuthStatusChangeEventBehavior()
		{

			@Override
			protected void onSessionEvent(final AjaxRequestTarget target, final String status,
				final String userId, final String signedRequest, final String expiresIn,
				final String accessToken)
			{
				final StringBuilder sb = new StringBuilder();
				sb.append("status: ").append(status).append('\n');
				sb.append("signedRequest: ").append(signedRequest).append('\n');
				sb.append("expiresIn: ").append(expiresIn).append('\n');
				sb.append("accessToken: ").append(accessToken).append('\n');

				responseModel.setObject(sb.toString());

				target.add(responseLabel);
			}
		});

	}
}
