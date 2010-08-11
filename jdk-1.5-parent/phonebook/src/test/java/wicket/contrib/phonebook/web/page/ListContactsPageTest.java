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

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;

import wicket.contrib.phonebook.web.PhonebookApplicationForTesting;
import wicket.contrib.phonebook.web.PhonebookFixture;

/**
 * @author Kare Nuorteva
 */
public class ListContactsPageTest extends TestCase {
	private WicketTester wicket;

	@Override
	protected void setUp() throws Exception {
		PhonebookApplicationForTesting app = new PhonebookApplicationForTesting();
		PhonebookFixture fixture = new PhonebookFixture();
		fixture.addStubs(app.context);
		wicket = new WicketTester(app);
		wicket.startPage(ListContactsPage.class);
		wicket.assertRenderedPage(ListContactsPage.class);
	}

	public void testContainsLinkToCreateContacs() throws Exception {
		wicket.clickLink("createLink");
		wicket.assertRenderedPage(EditContactPage.class);
	}

	public void testContainsFilterForm() throws Exception {
		wicket.assertComponent("filter-form", FilterForm.class);
	}

	public void testContainsUserList() throws Exception {
		wicket.assertComponent("filter-form:users", DefaultDataTable.class);
	}

	public void testDeleteLinkOpensConfirmPage() throws Exception {
		wicket.assertComponent("filter-form:users:rows:1:cells:2:cell:deleteLink",
				Link.class);
		wicket.clickLink("filter-form:users:rows:1:cells:2:cell:deleteLink");
		wicket.assertRenderedPage(DeleteContactPage.class);
	}

	public void testEditLinkOpensContactEditor() throws Exception {
		wicket
				.assertComponent("filter-form:users:rows:1:cells:2:cell:editLink",
						Link.class);
		wicket.clickLink("filter-form:users:rows:1:cells:2:cell:editLink");
		wicket.assertRenderedPage(EditContactPage.class);
	}
}
