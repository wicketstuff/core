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

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.templates.ScriptTemplate;

/**
 * Base class for a {@link ModalWindow} cotaining a script engine panel.
 * 
 * @author cretzel
 */
public abstract class ScriptEngineWithTemplatesWindow extends ModalWindow {

	private static final long serialVersionUID = 1L;
	private final Lang lang;
	private ScriptEnginePanelWithTemplates enginePanelWithTemplates;
	private final IDataProvider<ScriptTemplate> dataProvider;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            id
	 * @param lang
	 *            source language
	 * @param windowTitle
	 *            window title, may be {@code null} for default
	 * @param dataProvider
	 *            data provider for script templates, may be {@code null} for
	 *            default
	 */
	public ScriptEngineWithTemplatesWindow(final String id, final Lang lang,
			final IModel<String> windowTitle,
			final IDataProvider<ScriptTemplate> dataProvider) {
		super(id);
		this.lang = lang;
		this.dataProvider = dataProvider;

		setTitle(windowTitle != null ? windowTitle : Model.of("Wicket Console"));
		setResizable(false);
		setInitialWidth(800);
		setInitialHeight(408);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		enginePanelWithTemplates = newEnginePanelWithTemplates(getContentId(),
				lang);

		setContent(enginePanelWithTemplates);
	}

	/**
	 * Creates a new engine panel with templates, override to customize.
	 * 
	 * @param wicketId
	 *            id
	 * @return a script engine panel
	 */
	protected ScriptEnginePanelWithTemplates newEnginePanelWithTemplates(
			final String wicketId, final Lang lang) {

		final ScriptEnginePanelWithTemplates panelWithTemplates = new ScriptEnginePanelWithTemplates(
				wicketId, lang, Model.of(""), dataProvider);

		return panelWithTemplates;
	}

}
