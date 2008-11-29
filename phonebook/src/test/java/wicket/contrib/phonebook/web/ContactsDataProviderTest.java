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
package wicket.contrib.phonebook.web;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.QueryParam;

/**
 * @author Kare Nuorteva
 */
public class ContactsDataProviderTest extends TestCase {
	private ContactDao dao;

	@Override
	protected void setUp() throws Exception {
		dao = EasyMock.createStrictMock(ContactDao.class);
	}

	@Override
	protected void tearDown() throws Exception {
		EasyMock.verify(dao);
	}

	public void testFind() throws Exception {
		QueryParam qp = new QueryParam(0, 10, "foo", true);
		Contact contact = createMockContext();
		Iterator<Contact> expected = createContactResultList(contact);
		EasyMock.expect(dao.find(qp, contact)).andReturn(expected);
		EasyMock.replay(dao);
		ContactsDataProvider provider = new ContactsDataProvider(dao);
		provider.setQueryParam(qp);
		provider.setFilterState(contact);
		Iterator<Contact> result = provider.iterator(0, 10);
		assertEquals(expected, result);
	}

	private Contact createMockContext() {
		Contact contact = new Contact();
		contact.setFirstname("James");
		contact.setLastname("Bond");
		return contact;
	}

	private Iterator<Contact> createContactResultList(Contact contact) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		contacts.add(contact);
		Iterator<Contact> result = contacts.iterator();
		return result;
	}
}
