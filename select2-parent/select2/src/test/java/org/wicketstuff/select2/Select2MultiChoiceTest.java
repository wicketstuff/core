package org.wicketstuff.select2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author lexx
 */
public class Select2MultiChoiceTest
{

	private WicketTester wicketTester;

	@BeforeEach
	public void setUp() throws Exception
	{
		this.wicketTester = new WicketTester();
	}

	@Test
	public void testSelect2MultiChoiceRequireValue() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, countriesAsString());
		formTester.submit();

		assertTrue(formTester.getForm().hasError());
		assertTrue(page.country.getFeedbackMessages().hasMessage(FeedbackMessage.ERROR));
		assertEquals("Please, choose at least one country",
				page.country.getFeedbackMessages().first().getMessage().toString());
	}

	@Test
	public void testSelect2MultiChoiceOptionalValue() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		page.country.setRequired(false);
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, countriesAsString());
		formTester.submit();

		assertFalse(formTester.getForm().hasError());
		assertTrue(page.country.getModelObject().isEmpty());
	}

	@Test
	public void testSelect2MultiChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		for (Country c: countriesAsList()) {
			wicketTester.getRequest().addParameter(page.country.getInputName(), c.name());
		}
		formTester.submit();

		assertTrue(formTester.getForm().hasError());
		assertTrue(page.country.isValid());
		assertFalse(page.city.isValid());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		assertTrue(responseAsString.contains(expectedOptions()));
	}

	@Test
	public void testSelect2MultiChoiceKeepsValueAfterFormSubmit() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		this.wicketTester.startPage(page);
		this.wicketTester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = this.wicketTester.newFormTester(page.form.getPageRelativePath());
		for (Country c: countriesAsList()) {
			wicketTester.getRequest().addParameter(page.country.getInputName(), c.name());
		}
		formTester.setValue(page.city, city());
		formTester.submit();

		assertFalse(formTester.getForm().hasError());
		assertTrue(page.country.getModelObject().containsAll(countriesAsList()));
		assertEquals(city(), page.city.getModelObject());

		String responseAsString = this.wicketTester.getLastResponseAsString();
		assertTrue(responseAsString.contains(expectedOptions()));
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

	private static String expectedOptions()
	{
		return "<option selected=\"selected\" value=\"CA\">Canada</option><option selected=\"selected\" value=\"BE\">Belgium</option>";
	}
}
