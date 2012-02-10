/*
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
package org.wicketstuff.theme.tester;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.theme.IThemeFactory;
import org.wicketstuff.theme.WicketThemeBasePage;
import org.wicketstuff.theme.standard.ThemeFactory;

public class ThemeTestPage extends WicketThemeBasePage
{
	private static final long serialVersionUID = 1L;

	public ThemeTestPage()
	{
		add(new FeedbackPanel("feedback"));

		for (int i = 0; i < 2; i++)
		{
			info("Info " + i);
			warn("Warn " + i);
			error("Error " + i);
		}

	}

	@Override
	protected IModel<String> getTitleModel()
	{
		return Model.of("Theme Test");
	}

	@Override
	protected IThemeFactory getThemeFactory()
	{
		return new ThemeFactory();
	}
}
