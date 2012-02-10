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

/**
 * @author Kare Nuorteva
 */
public class PhonebookFixture
{
	private static final String[] FIRSTNAMES = new String[] { "Jacob", "Emily", "Michael", "Sarah",
			"Matthew", "Brianna", "Nicholas", "Samantha", "Christopher", "Hailey", "Abner", "Abby",
			"Joshua", "Douglas", "Jack", "Keith", "Gerald", "Samuel", "Willie", "Larry", "Jose",
			"Timothy", "Sandra", "Kathleen", "Pamela", "Virginia", "Debra", "Maria", "Linda" };

	private static final String[] LASTNAMES = { "Smiith", "Johnson", "Williams", "Jones", "Brown",
			"Donahue", "Bailey", "Rose", "Allen", "Black", "Davis", "Clark", "Hall", "Lee",
			"Baker", "Gonzalez", "Nelson", "Moore", "Wilson", "Graham", "Fisher", "Cruz", "Ortiz",
			"Gomez", "Murray" };

	private final ContactData contactData = new ContactData();

	public void addStubs(MockContext context)
	{
		context.putBean("contactDao", contactData.getContactDao());
		for (int i = 0; i < 30; i++)
		{
			Contact contact = new Contact();
			contact.setFirstname(randomString(FIRSTNAMES));
			contact.setLastname(randomString(LASTNAMES));
			contact.setPhone(generatePhoneNumber());
			contact.setEmail(createEmail(contact));
			contactData.newContact(contact);
		}
	}

	private String createEmail(Contact contact)
	{
		String email = contact.getFirstname() + "@" + contact.getLastname() + ".com";
		email = email.toLowerCase();
		return email;
	}

	private String randomString(String[] choices)
	{
		return choices[randomInt(0, choices.length)];
	}

	private String generatePhoneNumber()
	{
		return new StringBuilder().append(randomInt(2, 9))
			.append(randomInt(0, 9))
			.append(randomInt(0, 9))
			.append("-555-")
			.append(randomInt(1, 9))
			.append(randomInt(0, 9))
			.append(randomInt(0, 9))
			.append(randomInt(0, 9))
			.toString();
	}

	private int randomInt(int min, int max)
	{
		return (int)(Math.random() * (max - min) + min);
	}

	public ContactData getContactData()
	{
		return contactData;
	}
}
