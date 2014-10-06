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

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.QueryParam;

import java.util.ArrayList;
import java.util.Iterator;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kare Nuorteva
 */
public class ContactsDataProviderTest extends Assert
{
	private ContactDao dao;

	@Before
	public void before() throws Exception
	{
		dao = EasyMock.createStrictMock(ContactDao.class);
	}

	@After
	public void tearDown() throws Exception
	{
		EasyMock.verify(dao);
	}

	@Test
	public void testFind() throws Exception
	{
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

	private Contact createMockContext()
	{
		Contact contact = new Contact();
		contact.setFirstname("James");
		contact.setLastname("Bond");
		return contact;
	}

	private Iterator<Contact> createContactResultList(Contact contact)
	{
		ArrayList<Contact> contacts = new ArrayList<>();
		contacts.add(contact);
		Iterator<Contact> result = contacts.iterator();
		return result;
	}
}
