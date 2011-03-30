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
package org.wicketstuff.console.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.console.GroovyScriptEnginePanel;
import org.wicketstuff.console.templates.Lang;
import org.wicketstuff.console.templates.ScriptTemplate;
import org.wicketstuff.console.templates.ScriptTemplateSelectionTablePanel;

public class ScriptTemplatesGroovyEngineTestPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public ScriptTemplatesGroovyEngineTestPage(final PageParameters params) {
		super(params);

		final GroovyScriptEnginePanel enginePanel = new GroovyScriptEnginePanel(
				"scriptPanel");
		enginePanel.setOutputMarkupId(true);
		add(enginePanel);

		final ScriptTemplateSelectionTablePanel scriptTable = new ScriptTemplateSelectionTablePanel(
				"templatesTable", enginePanel, dataProvider(), 10);
		add(scriptTable);

		add(new TestPageLinksPanel("links"));
	}

	private ListDataProvider<ScriptTemplate> dataProvider() {
		return new ListDataProvider<ScriptTemplate>(scriptList());
	}

	private List<ScriptTemplate> scriptList() {
		final List<ScriptTemplate> templates = new ArrayList<ScriptTemplate>();

		templates.add(new ScriptTemplate("Hello World",
				"println 'Hello World'", Lang.GROOVY));
		templates.add(new ScriptTemplate("Component Hierarchy",
				"def parent = component\nwhile(parent) {\n  println \"${parent.class.simpleName} [${parent.id}]\"\n  parent=parent.parent\n}", Lang.GROOVY));

		return templates;
	}

}
