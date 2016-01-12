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

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.console.engine.Lang;

/**
 * Base class for a {@link ModalWindow} cotaining a script engine panel.
 * 
 * @author cretzel
 */
public abstract class ScriptEngineWindow extends ModalWindow
{

	private static final long serialVersionUID = 1L;
	private final Lang lang;
	private final ScriptEnginePanel enginePanel;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            id
	 * @param lang
	 *            source language
	 * @param windowTitle
	 *            window title, may be {@code null} for default
	 */
	public ScriptEngineWindow(final String id, final Lang lang, final IModel<String> windowTitle)
	{
		super(id);
		this.lang = lang;

		setTitle(windowTitle != null ? windowTitle : Model.of("Wicket Console"));
		setAutoSize(true);
		setInitialHeight(300);
		setResizable(true);

		enginePanel = newEnginePanel(getContentId(), lang);
		setContent(enginePanel);

	}

	/**
	 * Creates a new engine panel, override to customize.
	 * <p>
	 * Attention: This is called from the constructor.
	 * 
	 * @param wicketId
	 *            id
	 * @return a script engine panel
	 */
	protected ScriptEnginePanel newEnginePanel(final String wicketId, final Lang lang)
	{

		final ScriptEnginePanel panel = ScriptEnginePanel.create(wicketId, lang);
		panel.add(new AttributeAppender("style", Model.of("width:500px"), ";"));

		return panel;
	}

	public ScriptEnginePanel getEnginePanel()
	{
		return enginePanel;
	}

	public Lang getLang()
	{
		return lang;
	}

}
