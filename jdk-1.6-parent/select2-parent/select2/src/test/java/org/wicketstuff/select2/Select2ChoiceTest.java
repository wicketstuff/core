package org.wicketstuff.select2;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;

import junit.framework.TestCase;

/**
 * @author lexx
 */
public class Select2ChoiceTest extends TestCase
{

	private WicketTester wicketTester;

	@Before
	public void setUp() throws Exception
	{
		this.wicketTester = new WicketTester();
	}

	public void testSelect2ChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, Country.CA.name());
		formTester.submit();

		Assert.assertTrue(formTester.getForm().hasError());
		Assert.assertTrue(page.country.isValid());
		Assert.assertFalse(page.city.isValid());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	public void testSelect2ChoiceKeepsValueAfterFormSubmit() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, Country.CA.name());
		formTester.setValue(page.city, "Vancouver");
		formTester.submit();

		Assert.assertFalse(formTester.getForm().hasError());
		Assert.assertEquals(Country.CA, page.country.getModelObject());
		Assert.assertEquals("Vancouver", page.city.getModelObject());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	private static String expectedJavaScriptMethodCall()
	{
		return ".select2('data', {\"id\":\"CA\",\"text\":\"Canada\"});";
	}

}