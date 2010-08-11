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

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.ITestPageSource;
import org.apache.wicket.util.tester.WicketTester;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.web.PhonebookApplicationForTesting;
import wicket.contrib.phonebook.web.PhonebookFixture;

/**
 * @author Kare Nuorteva
 */
public class DeleteContactPageTest extends TestCase {
	private WicketTester wicket;
	private PhonebookApplicationForTesting app;
	private PhonebookFixture fixture;

	private static final class MockDeleteContactPage implements ITestPageSource {
		public Page getTestPage() {
			Contact contact = new Contact();
			contact.setId(99);
			contact.setFirstname("Kare");
			contact.setLastname("Nuorteva");
			return new DeleteContactPage(new ListContactsPage(), new Model<Contact>(
					contact));
		}
	}

	@Override
	protected void setUp() throws Exception {
		app = new PhonebookApplicationForTesting();
		fixture = new PhonebookFixture();
		fixture.addStubs(app.context);
		wicket = new WicketTester(app);
		wicket.startPage(new MockDeleteContactPage());
	}

	public void testContainsRequiredComponents() throws Exception {
		wicket.assertComponent("name", Label.class);
	}

	public void testConfirmLinkDeletesContactAndSetsInfoMessageAndForwardsBack()
			throws Exception {
		wicket.assertComponent("confirm", Link.class);
		assertFalse(fixture.getContactData().isContactDaoDeleteCalled());
		wicket.clickLink("confirm");
		wicket
				.assertInfoMessages(new String[] { "Contact Kare Nuorteva successfully deleted" });
		assertTrue(fixture.getContactData().isContactDaoDeleteCalled());
		wicket.assertRenderedPage(ListContactsPage.class);
	}

	public void testCancelLinkSetsInfoMessageAndForwardsBAck() throws Exception {
		wicket.assertComponent("cancel", Link.class);
		wicket.clickLink("cancel");
		wicket
				.assertInfoMessages(new String[] { "Deletion of contact Kare Nuorteva cancelled" });
		wicket.assertRenderedPage(ListContactsPage.class);
	}
}
