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
package org.apache.wicket.security.examples.secureform;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.wicket.security.examples.secureform.pages.LoginPage;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the base class to extend new test classes from if you like to use a
 * wicket-security enabled test. It Checks the project structure in order to set the
 * proper web application directory and connects the secure application to the test.
 * 
 * @author Olger Warnier
 */
public class AbstractSecurePageTestBase extends TestCase
{

	protected WicketTester tester;

	private static final Logger log = LoggerFactory.getLogger(TestMySecurePage.class);

	/**
	 * Check the base path and construct the proper secure webapplication.
	 */
	@Override
	public void setUp()
	{
		String webAppLocation = null;

		if (tester == null)
		{
			File currentDir = new File(".");
			try
			{
				if (currentDir.getCanonicalPath().endsWith("examples/secureforms-example"))
				{
					webAppLocation = "src/main/webapp";
				}
				else
				{
					webAppLocation = "examples/secureforms-example/src/main/webapp";
				}
			}
			catch (IOException e)
			{
				log.error("Could not get the current directory", e);
			}

			tester = new WicketTester(new SecureFormWicketApplication(), webAppLocation);
		}
	}

	@Override
	public void tearDown()
	{
		tester.destroy();
		tester = null;
	}

	public void loginAs(String user)
	{
		tester.startPage(LoginPage.class);
		tester.assertRenderedPage(LoginPage.class);

		FormTester formTester = tester.newFormTester("signInForm");
		formTester.setValue("username", user);
		formTester.setValue("password", user);
		formTester.submit();

		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();

	}

	public void logout()
	{

	}
}
