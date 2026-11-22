/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.util.tester.FormTester;
import org.junit.jupiter.api.Test;

/**
 * @author lexx
 */
public class Select2MultiChoiceTest extends AbstarctSelect2Test {
	@Test
	public void testSelect2MultiChoiceRequireValue() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
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
		tester.startPage(page);
		tester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		formTester.setValue(page.city, countriesAsString());
		formTester.submit();

		assertFalse(formTester.getForm().hasError());
		assertTrue(page.country.getModelObject().isEmpty());
	}

	@Test
	public void testSelect2MultiChoiceKeepsValueAfterFormValidation() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		for (Country c: countriesAsList()) {
			tester.getRequest().addParameter(page.country.getInputName(), c.name());
		}
		formTester.submit();

		assertTrue(formTester.getForm().hasError());
		assertTrue(page.country.isValid());
		assertFalse(page.city.isValid());

		String responseAsString = tester.getLastResponseAsString();
		assertTrue(responseAsString.contains(expectedOptions()));
	}

	@Test
	public void testSelect2MultiChoiceKeepsValueAfterFormSubmit() throws Exception
	{
		Select2MultiChoicePage page = new Select2MultiChoicePage();
		tester.startPage(page);
		tester.assertRenderedPage(Select2MultiChoicePage.class);

		FormTester formTester = tester.newFormTester(page.form.getPageRelativePath());
		for (Country c: countriesAsList()) {
			tester.getRequest().addParameter(page.country.getInputName(), c.name());
		}
		formTester.setValue(page.city, city());
		formTester.submit();

		assertFalse(formTester.getForm().hasError());
		assertTrue(page.country.getModelObject().containsAll(countriesAsList()));
		assertEquals(city(), page.city.getModelObject());

		String responseAsString = tester.getLastResponseAsString();
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
