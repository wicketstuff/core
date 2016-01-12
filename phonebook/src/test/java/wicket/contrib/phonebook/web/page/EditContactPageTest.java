/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.phonebook.web.page;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.web.PhonebookApplicationForTesting;
import wicket.contrib.phonebook.web.PhonebookFixture;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kare Nuorteva
 */
public class EditContactPageTest extends Assert
{
	private WicketTester wicket;
	private PhonebookApplicationForTesting app;
	private PhonebookFixture fixture;

	private static final Page getTestPage()
	{
		Contact contact = new Contact();
		contact.setId(99);
		contact.setFirstname("James");
		contact.setLastname("Bond");
		return new EditContactPage(new ListContactsPage(), Model.of(contact));
	}

	@Before
	public void before() throws Exception
	{
		app = new PhonebookApplicationForTesting();
		fixture = new PhonebookFixture();
		fixture.addStubs(app.context);
		wicket = new WicketTester(app);
		wicket.startPage(getTestPage());
	}

	@After
	public void after() {
		wicket.destroy();
		wicket = null;
	}

	@Test
	public void testContainsFormComponents() throws Exception
	{
		wicket.assertComponent("contactForm", Form.class);
		wicket.assertComponent("contactForm:firstname", RequiredTextField.class);
		wicket.assertComponent("contactForm:lastname", RequiredTextField.class);
		wicket.assertComponent("contactForm:phone", RequiredTextField.class);
		wicket.assertComponent("contactForm:email", TextField.class);
		wicket.assertComponent("contactForm:cancel", Button.class);
		wicket.assertComponent("contactForm:save", Button.class);
	}

	@Test
	public void testCancelButtonsForwardsToBackPage() throws Exception
	{
		FormTester form = wicket.newFormTester("contactForm");
		form.submit("cancel");
		wicket.assertInfoMessages(new String[] { "Edit cancelled" });
		wicket.assertRenderedPage(ListContactsPage.class);
	}

	@Test
	public void testSaveButtonStoresChangesAndForwardsToBackPage() throws Exception
	{
		FormTester form = wicket.newFormTester("contactForm", true);
		form.setValue("email", "james.bond@007.com");
		form.setValue("phone", "007 123456");
		assertFalse(fixture.getContactData().isContactDaoSaveCalled());
		form.submit("save");
		assertTrue(fixture.getContactData().isContactDaoSaveCalled());
		wicket.assertInfoMessages(new String[] { "Changes to contact James Bond saved successfully" });
		wicket.assertRenderedPage(ListContactsPage.class);
	}
}
