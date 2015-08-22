package org.wicketstuff.select2;

import junit.framework.TestCase;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;

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

	public void testSelect2ChoiceRequireValue() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, city());
		formTester.submit();

		Assert.assertTrue(formTester.getForm().hasError());
		Assert.assertTrue(page.country.getFeedbackMessages().hasMessage(FeedbackMessage.ERROR));
		Assert.assertEquals("Please, choose country", page.country.getFeedbackMessages().first().getMessage().toString());
	}

	public void testSelect2ChoiceOptionalValue() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		page.country.setRequired(false);
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, city());
		formTester.submit();

		Assert.assertFalse(formTester.getForm().hasError());
		Assert.assertNull(page.country.getModelObject());
	}

	public void testSelect2ChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country());
		formTester.submit();

		Assert.assertTrue(formTester.getForm().hasError());
		Assert.assertTrue(page.country.isValid());
		Assert.assertFalse(page.city.isValid());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	public void testSelect2ChoiceKeepsValueAfterPageReRender() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country());
		formTester.submit();

		this.wicketTester.startPage(this.wicketTester.getLastRenderedPage());
		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	public void testSelect2ChoiceKeepsValueAfterFormSubmit() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country());
		formTester.setValue(page.city, city());
		formTester.submit();

		Assert.assertFalse(formTester.getForm().hasError());
		Assert.assertEquals(Country.CA, page.country.getModelObject());
		Assert.assertEquals(city(), page.city.getModelObject());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		Assert.assertTrue(responseAsString.contains(expectedJavaScriptMethodCall()));
	}

	private static String city()
	{
		return "Vancouver";
	}

	private static String country()
	{
		return Country.CA.name();
	}

	private static String expectedJavaScriptMethodCall()
	{
		return ".select2('data', {\"id\":\"CA\",\"text\":\"Canada\"});";
	}

}