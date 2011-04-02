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

package org.wicketstuff.console;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.PackagedScriptTemplates;
import org.wicketstuff.console.templates.ScriptTemplate;
import org.wicketstuff.console.templates.ScriptTemplateSelectionTablePanel;

/**
 * A combination of an engine panel and a script template selection table.
 * 
 * @author cretzel
 */
public class ScriptEnginePanelWithTemplates extends Panel {

	private static final long serialVersionUID = 1L;
	private final Lang lang;
	private ScriptEnginePanel enginePanel;
	private ScriptTemplateSelectionTablePanel selectionTable;
	private IDataProvider<ScriptTemplate> dataProvider;
	private final IModel<String> title;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            id
	 * @param lang
	 *            source language
	 * @param title
	 *            title, may be {@code null} for default
	 * @param dataProvider
	 *            data provider for script templates, may be {@code null} for
	 *            default
	 */
	public ScriptEnginePanelWithTemplates(final String id, final Lang lang,
			final IModel<String> title,
			final IDataProvider<ScriptTemplate> dataProvider) {
		super(id);

		this.lang = lang;
		this.title = title;
		this.dataProvider = dataProvider;

		if (dataProvider == null) {
			this.dataProvider = PackagedScriptTemplates
					.packagedScriptTemplatesDataProvider(lang);
		}

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		enginePanel = newEnginePanel();
		enginePanel.setOutputMarkupId(true);
		add(enginePanel);

		selectionTable = newSelectionTable("selectionPanel", enginePanel,
				dataProvider);
		add(selectionTable);
	}

	/**
	 * Create the engine panel, override to customize.
	 */
	protected ScriptEnginePanel newEnginePanel() {
		return ScriptEnginePanel.create("enginePanel", lang, title);
	}

	/**
	 * Create the selection table, override to customize.
	 */
	protected ScriptTemplateSelectionTablePanel newSelectionTable(
			final String wicketId, final ScriptEnginePanel enginePanel,
			final IDataProvider<ScriptTemplate> dataProvider) {

		return new ScriptTemplateSelectionTablePanel(wicketId, enginePanel,
				dataProvider, 100);
	}

	public ScriptEnginePanel getEnginePanel() {
		return enginePanel;
	}

	public ScriptTemplateSelectionTablePanel getSelectionTable() {
		return selectionTable;
	}

}
