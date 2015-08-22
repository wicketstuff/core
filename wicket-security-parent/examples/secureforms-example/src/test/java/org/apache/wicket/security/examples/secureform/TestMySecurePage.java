package org.apache.wicket.security.examples.secureform;

import org.apache.wicket.security.examples.secureform.pages.LoginPage;
import org.apache.wicket.security.examples.secureform.pages.MySecurePage;
import org.apache.wicket.util.tester.TagTester;

/**
 * Test The MySecurePage and form components
 * 
 * @author Olger Warnier
 */
public class TestMySecurePage extends AbstractSecurePageTestBase
{

	/*
	 * Plainly starting the MySecurePage will end up with a login screen as the
	 * MySecurePage is a secured page that is added to the hive.
	 */
	public void testRenderMySecurePage()
	{
		// start and render the test page
		tester.startPage(MySecurePage.class);

		// assert rendered page class, this should be the login page
		tester.assertRenderedPage(LoginPage.class);
	}

	public void testFormLoadedDisabled()
	{
		loginAs("user");
		tester.startPage(MySecurePage.class);
		tester.assertRenderedPage(MySecurePage.class);
		tester.dumpPage();
		TagTester tag = tester.getTagByWicketId("sampleForm");
		assertEquals("div", tag.getName());

		tag = tester.getTagByWicketId("name");
		assertEquals("disabled", tag.getAttribute("disabled"));

		tag = tester.getTagByWicketId("information");
		assertEquals("disabled", tag.getAttribute("disabled"));

	}

	/**
	 * Load the form with the proper user in order to enable the form.
	 * 
	 */
	public void testFormLoadedEnabled()
	{
		// sets the admin principal, that has the rights to use the form
		loginAs("admin");
		tester.startPage(MySecurePage.class);
		tester.assertRenderedPage(MySecurePage.class);

		TagTester tag = tester.getTagByWicketId("sampleForm");
		assertEquals("form", tag.getName());

		tag = tester.getTagByWicketId("name");
		assertNull("Form field name contains the disabled tag", tag.getAttribute("disabled"));

		tag = tester.getTagByWicketId("information");
		assertNull("Form field information contains the disabled tag", tag.getAttribute("disabled"));

	}

}