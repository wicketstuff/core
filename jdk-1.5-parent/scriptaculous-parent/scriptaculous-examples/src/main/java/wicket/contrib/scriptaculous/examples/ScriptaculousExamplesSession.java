/*
 * $Id: ScriptaculousExamplesSession.java 3994 2008-06-30 20:28:15Z svenmeier $
 * $Revision: 3994 $
 * $Date: 2008-06-30 15:28:15 -0500 (Mon, 30 Jun 2008) $
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
package wicket.contrib.scriptaculous.examples;

import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

/**
 * @author hillenius
 */
public class ScriptaculousExamplesSession extends WebSession
{
	private List cartItems;

	/**
	 * Construct.
	 * 
	 * @param application
	 * @param request
	 */
	public ScriptaculousExamplesSession(Request request)
	{
		super(request);
	}

	/**
	 * Gets cartItems.
	 * 
	 * @return cartItems
	 */
	public List getCartItems()
	{
		return cartItems;
	}

	/**
	 * Sets cartItems.
	 * 
	 * @param cartItems
	 *            cartItems
	 */
	public void setCartItems(List cartItems)
	{
		this.cartItems = cartItems;
	}

}
