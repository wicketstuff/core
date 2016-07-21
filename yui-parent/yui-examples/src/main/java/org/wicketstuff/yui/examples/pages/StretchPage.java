/*
 * $Id: CalendarPage.java 4640 2006-02-26 10:41:53Z eelco12 $ $Revision: 4640 $ $Date: 2006-02-26 18:41:53 +0800 (Sun, 26 Feb 2006) $
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
package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.Component;
import org.wicketstuff.yui.behavior.animation.YuiEasing;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.anim.stretch.StretchPanel;
import org.wicketstuff.yui.markup.html.calendar.Calendar;

/**
 * Page that displays the expanding/collapsing panels
 * 
 * @author Johan Compagner
 */
public class StretchPage extends WicketExamplePage
{
	/**
	 * Construct.
	 */
	public StretchPage()
	{
		StretchPanel sp = new StretchPanel("stretch", "My Expanding Calendar")
		{
			@Override
			protected Component getStretchPanel(String id)
			{
				return new Calendar(id);
			}
		};

		sp.setCollapsedHeight(0);
		sp.setExpandedHeight(140);
		sp.setDelayTime(2);
		sp.setDefaultState(StretchPanel.EXPANDED);
		sp.setEasing(YuiEasing.easeOutStrong);
		add(sp);
	}
}
