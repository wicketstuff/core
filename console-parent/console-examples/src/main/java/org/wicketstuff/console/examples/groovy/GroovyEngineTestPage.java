/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.console.examples.groovy;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.examples.ConsoleBasePage;
import org.wicketstuff.console.groovy.GroovyScriptEnginePanel;
import org.wicketstuff.console.templates.PackagedScriptTemplates;
import org.wicketstuff.console.templates.ScriptTemplate;
import org.wicketstuff.console.templates.StoredScriptTemplateModel;

public class GroovyEngineTestPage extends ConsoleBasePage
{
	private static final long serialVersionUID = 1L;
	private final PackagedScriptTemplates store;

	public GroovyEngineTestPage()
	{
		store = new PackagedScriptTemplates();

		final GroovyScriptEnginePanel enginePanel = new GroovyScriptEnginePanel("scriptPanel")
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void addControls(final RepeatingView controls)
			{
				super.addControls(controls);

				final Label label = new Label(controls.newChildId(), "Favorites:");
				label.add(new AttributeAppender("style", Model.of("color:#ddd;")));
				controls.add(label);

				int i = 0;
				for (final ScriptTemplate template : store.findAll(Lang.GROOVY))
				{
					final QuickAction action = new QuickAction(controls.newChildId(), this,
						new StoredScriptTemplateModel(store, template), i++);
					controls.add(action);

				}
			}

		};

		enginePanel.setOutputMarkupId(true);
		add(enginePanel);
	}


}
