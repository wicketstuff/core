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
package org.wicketstuff.theme;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public abstract class WicketThemeBasePage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8601976301347808433L;

	public WicketThemeBasePage()
	{


		add(new Label("title", getTitleModel()));

		add(new Behavior()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 3548799040289197907L;

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				super.renderHead(component, response);

				getThemeFactory().renderHead(component, response);

			}
		});
	}


	protected abstract IModel<String> getTitleModel();

	protected abstract IThemeFactory getThemeFactory();
}
