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

import wicket.contrib.phonebook.web.PhonebookApplicationForTesting;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

/**
 * @author Kare Nuorteva
 */
public class BasePageTest
{
    @Test
	public void testContainsFeedbackPanel() throws Exception
	{
		WicketTester wicket = new WicketTester(new PhonebookApplicationForTesting());
		wicket.startPage(BasePage.class);
		wicket.assertComponent("status", FeedbackPanel.class);
	}
}
