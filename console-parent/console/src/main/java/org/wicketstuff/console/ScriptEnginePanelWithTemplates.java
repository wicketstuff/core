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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.IScriptTemplateStore;
import org.wicketstuff.console.templates.ScriptTemplate;
import org.wicketstuff.console.templates.ScriptTemplateSelectionTablePanel;
import org.wicketstuff.console.templates.ScriptTemplateStoreDataProvider;

/**
 * A combination of an engine panel and a script template selection table.
 * 
 * @author cretzel
 */
public class ScriptEnginePanelWithTemplates extends Panel
{

	private static final long serialVersionUID = 1L;
	private final Lang lang;
	private ScriptEnginePanel enginePanel;
	private ScriptTemplateSelectionTablePanel selectionTable;
	private final IDataProvider<ScriptTemplate> dataProvider;
	private final IScriptTemplateStore store;

	public ScriptEnginePanelWithTemplates(final String id, final Lang lang,
		final IDataProvider<ScriptTemplate> dataProvider)
	{
		super(id);

		this.lang = lang;
		this.dataProvider = dataProvider;
		store = null;

		init();
	}


	public ScriptEnginePanelWithTemplates(final String id, final Lang lang,
		final IScriptTemplateStore store)
	{
		super(id);

		this.lang = lang;
		dataProvider = new ScriptTemplateStoreDataProvider(store, lang);
		this.store = store;

		init();
	}

	private void init()
	{
		enginePanel = newEnginePanel(store);
		enginePanel.setOutputMarkupId(true);
		add(enginePanel);

		selectionTable = newSelectionTable("selectionPanel", enginePanel, dataProvider);
		selectionTable.setOutputMarkupId(true);
		add(selectionTable);
	}


	/**
	 * Create the engine panel, override to customize.
	 * <p>
	 * Attention: This is called from the constructor.
	 * 
	 * @param store
	 *            Store for ScriptTemplates
	 */
	protected ScriptEnginePanel newEnginePanel(final IScriptTemplateStore store)
	{
		return new ScriptEnginePanel("enginePanel", lang, store)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void storeScriptTemplate(final AjaxRequestTarget target, final String scriptTitle)
			{
				super.storeScriptTemplate(target, scriptTitle);
				target.add(selectionTable);
			}

		};
	}

	/**
	 * Create the selection table, override to customize.
	 * <p>
	 * Attention: This is called from the constructor.
	 */
	protected ScriptTemplateSelectionTablePanel newSelectionTable(final String wicketId,
		final ScriptEnginePanel enginePanel, final IDataProvider<ScriptTemplate> dataProvider)
	{
		return new ScriptTemplateSelectionTablePanel(wicketId, enginePanel, dataProvider, 100);
	}

	public ScriptEnginePanel getEnginePanel()
	{
		return enginePanel;
	}

	public ScriptTemplateSelectionTablePanel getSelectionTable()
	{
		return selectionTable;
	}

	public IDataProvider<ScriptTemplate> getDataProvider()
	{
		return dataProvider;
	}

}
