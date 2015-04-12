package org.wicketstuff.select2;

import java.util.Arrays;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;

import junit.framework.TestCase;

/**
 * @author lexx
 */
public class Select2MultiChoiceTest extends TestCase
{

	private WicketTester wicketTester;

	@Before
	public void setUp() throws Exception
	{
		this.wicketTester = new WicketTester();
	}

	public void testSelect2MultiChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, Country.CA.name() + "," + Country.BE.name());
		formTester.submit();

		Assert.assertTrue(formTester.getForm().hasError());
		Assert.assertTrue(page.country.isValid());
		Assert.assertFalse(page.city.isValid());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	public void testSelect2MultiChoiceKeepsValueAfterFormSubmit() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, Country.CA.name() + "," + Country.BE.name());
		formTester.setValue(page.city, "Vancouver");
		formTester.submit();

		Assert.assertFalse(formTester.getForm().hasError());
		Assert.assertTrue(page.country.getModelObject().containsAll(
			Arrays.asList(Country.CA, Country.BE)));
		Assert.assertEquals("Vancouver", page.city.getModelObject());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	private static String expectedJavaScriptMethodCall()
	{
		return ".select2('data', [{\"id\":\"CA\",\"text\":\"Canada\"},{\"id\":\"BE\",\"text\":\"Belgium\"}]);";
	}

}