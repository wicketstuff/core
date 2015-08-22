/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.security.examples.httplogin.digest.pages;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.security.examples.httplogin.basic.authentication.MyLoginContext;
import org.apache.wicket.security.examples.httplogin.digest.authentication.DigestLoginContext;
import org.apache.wicket.security.login.http.HttpDigestLoginPage;

/**
 * The login page. compared to login pages from the other examples it is a bit different
 * because there is no login form. Using http authentication the browser is going to take
 * care of that for us. In order to display a little message to the user this page is
 * rendered normally and the authentication is only requested after the login button is
 * pushed. If you do not want a login button you can request authentication immediately by
 * calling the {@link #doAuthentication()} directly in the constructor instead of when the
 * button is clicked.
 * 
 * @author marrink
 */
public class LoginPage extends HttpDigestLoginPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public LoginPage()
	{
		// http://localhost:8080/examples/digesthttp/?wicket:bookmarkablePage=%3Aorg.apache.wicket.security.examples.httplogin.digest.pages.LoginPage
		// stateless so the login page will not throw a timeout exception
		setStatelessHint(true);
		// I have added a nice feedbackpanel but this is not going to help us to
		// show messages to the user because as soon as doAuthentication has
		// been called the browser will not show this page anymore.
		add(new FeedbackPanel("feedback")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.Component#isVisible()
			 */
			@Override
			public boolean isVisible()
			{
				return anyMessage();
			}
		});
		// either allow the user to postpone logging in until the button has
		// been pushed
		add(new Link<Void>("link")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				doAuthentication();
			}
		});
		// or ask them for their username and password immediately
		// doAuthentication();
		// remember this page will not be shown by the browser because of the
		// 401 header after using doAuthentication

		// disable basic authentication for extra security
		setAllowBasicAuthenication(false);
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#getBasicLoginContext(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	protected Object getBasicLoginContext(String username, String password)
	{
		return new MyLoginContext(username, password);
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#getRealm(org.apache.wicket.protocol.http.WebRequest,
	 *      org.apache.wicket.protocol.http.WebResponse)
	 */
	@Override
	public String getRealm(WebRequest request, WebResponse response)
	{
		return "examples-digest"; // could be anything according to the http
		// spec
	}

	/**
	 * 
	 * @see org.apache.wicket.security.login.http.HttpDigestLoginPage#getClearTextPassword(java.lang.String)
	 */
	@Override
	protected String getClearTextPassword(String username)
	{
		return username;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.login.http.HttpDigestLoginPage#getDigestLoginContext(java.lang.String)
	 */
	@Override
	protected Object getDigestLoginContext(String username)
	{
		// password username combo is already verified in getClearTextPassword,
		// so all the context has to do is deliver the subject
		return new DigestLoginContext();
	}

	/**
	 * 
	 * @see org.apache.wicket.security.login.http.HttpDigestLoginPage#onMD5SessHashCalculated()
	 */
	@Override
	protected void onMD5SessHashCalculated()
	{
		// noop since by default we do not support md5-sess, see getAlgorithm
	}

}
