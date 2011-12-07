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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.QueryParam;

/**
 * @author Kare Nuorteva
 */
public class ContactData
{
	private final List<Contact> contacts = new ArrayList<Contact>();
	private final MockContactDao contactDao = new MockContactDao();

	public void newContact(Contact contact)
	{
		contacts.add(contact);
	}

	public ContactDao getContactDao()
	{
		return contactDao;
	}

	public boolean isContactDaoDeleteCalled()
	{
		return contactDao.deleteCalled;
	}

	public boolean isContactDaoSaveCalled()
	{
		return contactDao.saveCalled;
	}

	private final class MockContactDao implements ContactDao
	{
		private boolean deleteCalled;
		private boolean saveCalled;

		public int count(Contact filter)
		{
			return contacts.size();
		}

		public void delete(long id)
		{
			deleteCalled = true;
			contacts.remove(load(id));
		}

		public Iterator<Contact> find(QueryParam qp, Contact filter)
		{
			return contacts.iterator();
		}

		public List<String> getUniqueLastNames()
		{
			Set<String> names = new HashSet<String>();
			for (Contact contact : contacts)
			{
				names.add(contact.getLastname());
			}
			return new ArrayList<String>(names);
		}

		public Contact load(long id)
		{
			for (Contact contact : contacts)
			{
				if (contact.getId() == id)
				{
					return contact;
				}
			}
			return null;
		}

		public Contact save(Contact contact)
		{
			saveCalled = true;
			contacts.add(contact);
			return contact;
		}
	}
}
