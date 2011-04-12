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

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;
import org.wicketstuff.eidogo.Eidogo;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
		// start and render the test page
		tester.startPage(ExampleHomePage.class);

		// assert rendered page class
		tester.assertRenderedPage(ExampleHomePage.class);

		// assert rendered label component
		tester.assertComponent("eidogo", Eidogo.class);
	}
}
