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
