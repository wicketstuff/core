/*
 * $Id: BasePage.java 634 2006-03-27 02:28:10Z ivaynberg $
 * $Revision: 634 $
 * $Date: 2006-03-26 18:28:10 -0800 (Sun, 26 Mar 2006) $
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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Base page class used for phonebook web pages.
 * <p>
 * The markup of this files provides some common html as well as includes a
 * reference to the css file that all other pages inherit through wicket's
 * markup inheritance feature.
 *
 * @author igor
 */
public class BasePage extends WebPage
{
	public BasePage()
	{
		add(new FeedbackPanel("status"));
	}
}
