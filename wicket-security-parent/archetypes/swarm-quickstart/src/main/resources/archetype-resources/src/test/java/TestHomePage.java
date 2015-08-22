
package ${packageName};

import junit.framework.TestCase;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import ${packageName}.app.LoginPage;
import ${packageName}.app.WicketApplication;
import ${packageName}.web.SecureHomePage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{

	private WicketTester tester;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication(), "src/main/webapp");
	}
	/**
	 * Test to check if a user trying to access the {@link SecureHomePage} is properly intercepted by the
	 * {@link LoginPage} before rendering the home page.
	 */
	public void testRenderHomePage()
	{
		// start and render the test page
		tester.startPage(SecureHomePage.class);

		// assert login page
		tester.assertRenderedPage(LoginPage.class);
		// login
		FormTester form = tester.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();

		// assert rendered page class
		tester.assertRenderedPage(SecureHomePage.class);

		// assert rendered label component
		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
