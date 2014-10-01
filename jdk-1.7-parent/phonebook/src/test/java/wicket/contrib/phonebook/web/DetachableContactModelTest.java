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

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kare Nuorteva
 */
public class DetachableContactModelTest extends Assert
{
    @Test
	public void testLoad() throws Exception
	{
		ContactDao dao = EasyMock.createMock(ContactDao.class);
		Contact expected = new Contact();
		expected.setId(007);
		expected.setFirstname("James");
		expected.setLastname("Bond");
		expected.setEmail("james.bond@mi5.gov.uk");
		EasyMock.expect(dao.load(expected.getId())).andReturn(expected);
		EasyMock.replay(dao);
		DetachableContactModel model = new DetachableContactModel(expected, dao);
		assertEquals(expected, model.load());
		EasyMock.verify(dao);
	}
}
