/* *******************************************************************************
 * This file is part of Wicket-EidoGo.
 *
 * Wicket-EidoGo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Wicket-EidoGo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/agpl.html>.
 ********************************************************************************/
package org.wicketstuff.eidogo.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.eidogo.Eidogo;

/**
 * Homepage to test Wicket-EidoGo player functionalities.
 */
public class ExampleHomePage extends WebPage
{

	private static final long serialVersionUID = 1L;


	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ExampleHomePage(final PageParameters parameters)
	{
		// Add an example of eidogo player.
		Eidogo eidogo = new Eidogo("eidogo", "/sgf/example.sgf");
		add(eidogo);
		eidogo.setShowComments(true);
		eidogo.setShowGameInfo(true);
		eidogo.setShowOtions(true);
		eidogo.setShowPlayerInfo(true);
		eidogo.setShowTools(true);
	}
}
