package org.wicketstuff.select2;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;

/**
 * @author lexx
 */
public class Select2ChoiceRendererTest extends TestCase
{

	public void testSelect2ChoiceRendererInitialization() throws Exception
	{
		WicketTester wicketTester = new WicketTester();

		Select2ChoiceRendererPage page = new Select2ChoiceRendererPage();
		wicketTester.startPage(page);
		wicketTester.assertRenderedPage(Select2ChoiceRendererPage.class);
		Assert.assertTrue(wicketTester.getLastResponseAsString().contains(expectedClientSideJavaScriptInitialization()));

		FormTester formTester = wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country().name());
		formTester.submit();

		Assert.assertFalse(page.form.hasError());
		Assert.assertSame(country(), page.country.getModelObject());
		Assert.assertTrue(wicketTester.getLastResponseAsString().contains(expectedSelect2JavaScriptInitialization()));

		wicketTester.startPage(wicketTester.getLastRenderedPage()); // and refresh page again
		Assert.assertSame(country(), page.country.getModelObject());
		Assert.assertTrue(wicketTester.getLastResponseAsString().contains(expectedSelect2JavaScriptInitialization()));
	}

	private static Country country()
	{
		return Country.BR;
	}

	private static String expectedClientSideJavaScriptInitialization()
	{
		return "\"data\":{\"more\":false,\"results\":[{\"id\":\"AF\",\"text\":\"Afghanistan\"}";
	}

	private static String expectedSelect2JavaScriptInitialization()
	{
		return ".select2('data', {\"id\":\"BR\",\"text\":\"Brazil\"});";
	}
}
