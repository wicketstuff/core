package org.wicketstuff.select2;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;

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

	public void testSelect2MultiChoiceRequireValue() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, countriesAsString());
		formTester.submit();

		Assert.assertTrue(formTester.getForm().hasError());
		Assert.assertTrue(page.country.getFeedbackMessages().hasMessage(FeedbackMessage.ERROR));
		Assert.assertEquals("Please, choose at least one country",
				page.country.getFeedbackMessages().first().getMessage().toString());
	}

	public void testSelect2MultiChoiceOptionalValue() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		page.country.setRequired(false);
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, countriesAsString());
		formTester.submit();

		Assert.assertFalse(formTester.getForm().hasError());
		Assert.assertTrue(page.country.getModelObject().isEmpty());
	}

	public void testSelect2MultiChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, countriesAsString());
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
		formTester.setValue(page.country, countriesAsString());
		formTester.setValue(page.city, city());
		formTester.submit();

		Assert.assertFalse(formTester.getForm().hasError());
		Assert.assertTrue(page.country.getModelObject().containsAll(countriesAsList()));
		Assert.assertEquals(city(), page.city.getModelObject());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	private static String countriesAsString()
	{
		return Country.CA.name() + "," + Country.BE.name();
	}

	private static List<Country> countriesAsList()
	{
		return Arrays.asList(Country.CA, Country.BE);
	}

	private static String city()
	{
		return "Vancouver";
	}

	private static String expectedJavaScriptMethodCall()
	{
		return ".select2('data', [{\"id\":\"CA\",\"text\":\"Canada\"},{\"id\":\"BE\",\"text\":\"Belgium\"}]);";
	}

}