package org.wicketstuff.jeeweb;

import java.io.File;

import javax.servlet.RequestDispatcher;

import org.apache.wicket.protocol.http.mock.MockServletContext;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

public class JEEWebResolverTest {

    @Test
    public void testServletsAndJSPsAreResolvedRight() throws Exception {
	TestApplication testApplication = new TestApplication();
	MockServletContext mockServletContext = new MockServletContext(
		testApplication, new File("src/test/webapp").getCanonicalPath());
	WicketTester wicketTester = new WicketTester(testApplication,
		mockServletContext);
	wicketTester.startPage(TestServletAndJSPPage.class);
	String lastResponse = wicketTester.getLastResponseAsString();
	Assert.assertTrue(lastResponse
		.contains("INCLUDE OF RESOURCE: /TestServlet"));
	Assert.assertTrue(lastResponse
		.contains("INCLUDE OF RESOURCE: /TestJSP.jsp"));
    }

    @Test(expected = org.apache.wicket.WicketRuntimeException.class)
    public void testJSPRequestIsFailingIfNotExist() throws Exception {
	TestApplication testApplication = new TestApplication();
	MockServletContext mockServletContext = new MockServletContext(
		testApplication, new File("src/main/webapp").getCanonicalPath());
	WicketTester wicketTester = new WicketTester(testApplication,
		mockServletContext);
	wicketTester.startPage(TestJSPFailPage.class);
    }

    @Test(expected = org.apache.wicket.WicketRuntimeException.class)
    public void testServletRequestIsFailingIfNoServletIsAvailable()
	    throws Exception {
	TestApplication testApplication = new TestApplication();
	MockServletContext mockServletContext = new MockServletContext(
		testApplication, new File("src/main/webapp").getCanonicalPath()) {

	    // The dispatcher is returned as null if the servlet is not found -
	    // so we have to do this here.
	    @Override
	    public RequestDispatcher getRequestDispatcher(String name) {
		return null;
	    }
	};
	WicketTester wicketTester = new WicketTester(testApplication,
		mockServletContext);
	wicketTester.startPage(TestServletFailPage.class);
    }

}
