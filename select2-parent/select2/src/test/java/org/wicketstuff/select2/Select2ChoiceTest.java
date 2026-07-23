package org.wicketstuff.select2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.junit.jupiter.api.Test;

/**
 * @author lexx
 */
class Select2ChoiceTest extends AbstarctSelect2Test {
	@Test
	public void testSelect2ChoiceRequireValue() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, city());
		formTester.submit();

		assertTrue(formTester.getForm().hasError());
		assertTrue(page.country.getFeedbackMessages().hasMessage(FeedbackMessage.ERROR));
		assertEquals("Please, choose country", page.country.getFeedbackMessages().first().getMessage().toString());
	}

	@Test
	public void testSelect2ChoiceOptionalValue() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		page.country.setRequired(false);
		tester.startPage(page);
		tester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, city());
		formTester.submit();

		assertFalse(formTester.getForm().hasError());
		assertNull(page.country.getModelObject());
	}

	@Test
	public void testSelect2ChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country());
		formTester.submit();

		assertTrue(formTester.getForm().hasError());
		assertTrue(page.country.isValid());
		assertFalse(page.city.isValid());

		String responseAsString = tester.getLastResponseAsString();
		assertTrue(responseAsString.contains(expectedOption()));
	}

	@Test
	public void testSelect2ChoiceKeepsValueAfterPageReRender() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country());
		formTester.submit();

		tester.startPage(tester.getLastRenderedPage());
		String responseAsString = tester.getLastResponseAsString();
		assertTrue(responseAsString.contains(expectedOption()));
	}

	@Test
	public void testSelect2ChoiceKeepsValueAfterFormSubmit() throws Exception
	{
		Select2ChoicePage page = new Select2ChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2ChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.country, country());
		formTester.setValue(page.city, city());
		formTester.submit();

		assertFalse(formTester.getForm().hasError());
		assertEquals(Country.CA, page.country.getModelObject());
		assertEquals(city(), page.city.getModelObject());

		String responseAsString = tester.getLastResponseAsString();
		assertTrue(responseAsString.contains(expectedOption()));
	}

	private static String city()
	{
		return "Vancouver";
	}

	private static String country()
	{
		return Country.CA.name();
	}

	private static String expectedOption()
	{
		return "<option selected=\"selected\" value=\"CA\">Canada</option>";
	}
}
