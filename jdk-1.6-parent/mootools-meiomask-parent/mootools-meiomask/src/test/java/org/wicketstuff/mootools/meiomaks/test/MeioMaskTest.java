/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.mootools.meiomaks.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

/**
 * 
 * @author inaiat
 */
public class MeioMaskTest
{

	@Test
	public void fixedFieldsTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		formTester.setValue("fixed", "2010-12-30");
		formTester.setValue("fixedPhone", "(12) 1234-1234");
		formTester.setValue("fixedPhoneUs", "(123) 123-1234");
		formTester.setValue("fixedCpf", "123.456.789-01");
		formTester.setValue("fixedCnpj", "12.345.678/0001-01");
		formTester.setValue("fixedCep", "21940-480");
		formTester.setValue("fixedTime", "11:00");
		formTester.setValue("fixedCc", "1234 1234 1234 1234");

		formTester.submit();

		tester.assertModelValue("form:fixed", "20101230");
		tester.assertModelValue("form:fixedPhone", "1212341234");
		tester.assertModelValue("form:fixedPhoneUs", "1231231234");
		tester.assertModelValue("form:fixedCpf", "12345678901");
		tester.assertModelValue("form:fixedCnpj", "12345678000101");
		tester.assertModelValue("form:fixedCep", 21940480L);
		tester.assertModelValue("form:fixedTime", "1100");
		tester.assertModelValue("form:fixedCc", "1234123412341234");

	}

	@Test
	public void fixedDateTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		Locale locale = new Locale("pt", "BR");
		Session.get().setLocale(locale);
		formTester.setValue("fixedDate", "25/03/2011");
		formTester.submit();
		Calendar expected = Calendar.getInstance();
		expected.clear();
		expected.set(2011, Calendar.MARCH, 25);
		tester.assertModelValue("form:fixedDate", expected.getTime());
	}

	@Test
	public void fixedDateUsTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		Locale locale = new Locale("en", "US");
		Session.get().setLocale(locale);
		formTester.setValue("fixedDate", "03/25/2011");
		formTester.submit();
		Calendar expected = Calendar.getInstance();
		expected.clear();
		expected.set(2011, Calendar.MARCH, 25);
		tester.assertModelValue("form:fixedDate", expected.getTime());
	}

	@Test
	public void reverseIntegerTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		Locale locale = new Locale("pt", "BR");
		Session.get().setLocale(locale);
		formTester.setValue("reverseInteger", "5.321");
		formTester.submit();
		tester.assertModelValue("form:reverseInteger", 5321);
	}

	@Test
	public void reverseDecimalTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		Locale locale = new Locale("pt", "BR");
		Session.get().setLocale(locale);
		formTester.setValue("reverseDecimal", "10.492,56");
		formTester.setValue("reverseReais", "10.492,56");
		formTester.submit();
		tester.assertModelValue("form:reverseDecimal", BigDecimal.valueOf(10492.56).setScale(2));
		tester.assertModelValue("form:reverseReais", BigDecimal.valueOf(10492.56).setScale(2));
	}

	@Test
	public void reverseDecimalUsTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		Locale locale = new Locale("en", "US");
		Session.get().setLocale(locale);
		formTester.setValue("reverseDecimalUs", "10,492.56");
		formTester.setValue("reverseDollar", "10,492.56");
		formTester.submit();
		tester.assertModelValue("form:reverseDecimalUs", BigDecimal.valueOf(10492.56).setScale(2));
		tester.assertModelValue("form:reverseDollar", BigDecimal.valueOf(10492.56).setScale(2));
	}

	@Test
	public void regexTest()
	{
		WicketTester tester = new WicketTester();
		tester.startPage(TestPage.class);
		FormTester formTester = tester.newFormTester("form");
		Locale locale = new Locale("en", "US");
		Session.get().setLocale(locale);
		formTester.setValue("regexpIp", "192.168.1.1");
		formTester.setValue("regexpEmail", "test@domain.com");
		formTester.submit();
		tester.assertModelValue("form:regexpIp", "192.168.1.1");
		tester.assertModelValue("form:regexpEmail", "test@domain.com");
	}
}
