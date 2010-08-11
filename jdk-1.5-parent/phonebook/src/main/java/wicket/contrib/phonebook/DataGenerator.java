/*
 * $Id: DataGenerator.java 366 2005-10-11 16:06:21 -0700 (Tue, 11 Oct 2005) ivaynberg $
 * $Revision: 366 $
 * $Date: 2005-10-11 16:06:21 -0700 (Tue, 11 Oct 2005) $
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
package wicket.contrib.phonebook;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author ivaynberg
 * @author Kare Nuorteva
 */
public class DataGenerator implements InitializingBean {
	private static final String[] FIRSTNAMES = { "Jacob", "Emily", "Michael", "Sarah",
			"Matthew", "Brianna", "Nicholas", "Samantha", "Christopher",
			"Hailey", "Abner", "Abby", "Joshua", "Douglas", "Jack",
			"Keith", "Gerald", "Samuel", "Willie", "Larry", "Jose",
			"Timothy", "Sandra", "Kathleen", "Pamela", "Virginia", "Debra",
			"Maria", "Linda" };
	private static final String[] LASTNAMES = { "Smiith", "Johnson", "Williams", "Jones",
			"Brown", "Donahue", "Bailey", "Rose", "Allen", "Black",
			"Davis", "Clark", "Hall", "Lee", "Baker", "Gonzalez", "Nelson",
			"Moore", "Wilson", "Graham", "Fisher", "Cruz", "Ortiz",
			"Gomez", "Murray" };
	private ContactDao dao;
	private int count = 30;

	public void setContactDao(ContactDao dao) {
		this.dao = dao;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void afterPropertiesSet() throws Exception {
		for (int i = 0; i < count; i++) {
			Contact contact = new Contact();
			contact.setFirstname(randomString(FIRSTNAMES));
			contact.setLastname(randomString(LASTNAMES));
			contact.setPhone(generatePhoneNumber());

			String email = contact.getFirstname() + "@" + contact.getLastname()
					+ ".com";
			email = email.toLowerCase();

			contact.setEmail(email);
			dao.save(contact);
		}
	}

	private String randomString(String[] choices) {
		return choices[rint(0, choices.length)];
	}

	private String generatePhoneNumber() {
		return new StringBuffer().append(rint(2, 9)).append(rint(0, 9)).append(
				rint(0, 9)).append("-555-").append(rint(1, 9)).append(
				rint(0, 9)).append(rint(0, 9)).append(rint(0, 9)).toString();
	}

	private int rint(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
}
