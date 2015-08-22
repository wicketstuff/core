package org.wicketstuff.select2;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;

/**
 * @author lexx
 */
public class Select2MultiChoiceRendererTest extends TestCase
{

	public void testSelect2MultiChoiceRendererInitialization() throws Exception
	{
		WicketTester wicketTester = new WicketTester();

		Select2MultiChoiceRendererPage page = new Select2MultiChoiceRendererPage();
		wicketTester.startPage(page);
		wicketTester.assertRenderedPage(Select2MultiChoiceRendererPage.class);
		Assert.assertTrue(wicketTester.getLastResponseAsString().contains(expectedClientSideJavaScriptInitialization()));

		FormTester formTester = wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.countries, countriesAsRawInput());
		formTester.submit();

		Assert.assertFalse(page.form.hasError());
		Assert.assertTrue(page.countries.getModelObject().containsAll(countries()));
		Assert.assertTrue(wicketTester.getLastResponseAsString().contains(expectedSelect2JavaScriptInitialization()));

		wicketTester.startPage(wicketTester.getLastRenderedPage()); // and refresh page again
		Assert.assertTrue(page.countries.getModelObject().containsAll(countries()));
		Assert.assertTrue(wicketTester.getLastResponseAsString().contains(expectedSelect2JavaScriptInitialization()));
	}

	private static String countriesAsRawInput()
	{
		return Country.AM.name() + "," + Country.MC.name();
	}

	private static Collection<Country> countries()
	{
		return Arrays.asList(Country.AM, Country.MC);
	}

	private static String expectedClientSideJavaScriptInitialization()
	{
		return "\"data\":{\"more\":false,\"results\":[{\"id\":\"AF\",\"text\":\"Afghanistan\"}";
	}

	private static String expectedSelect2JavaScriptInitialization()
	{
		return ".select2('data', [{\"id\":\"AM\",\"text\":\"Armenia\"},{\"id\":\"MC\",\"text\":\"Monaco\"}]);";
	}
}
