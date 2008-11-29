/*
 * $Id: PhonebookApplication.java 517 2006-01-04 21:53:23Z ivaynberg $
 * $Revision: 517 $
 * $Date: 2006-01-04 13:53:23 -0800 (Wed, 04 Jan 2006) $
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

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Kare Nuorteva
 */
public class PhonebookApplication extends BasePhonebookApplication
{
	@Override
	public ApplicationContext context()
	{
		return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}
}
