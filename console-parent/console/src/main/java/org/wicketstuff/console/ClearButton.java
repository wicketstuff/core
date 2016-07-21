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

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

class ClearButton extends Button
{
	private static final long serialVersionUID = 1L;
	private final ScriptEnginePanel enginePanel;

	ClearButton(final String id, final ScriptEnginePanel scriptEnginePanel)
	{
		super(id);
		enginePanel = scriptEnginePanel;
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		addClearOnClick(enginePanel.getInputTf());
		addClearOnClick(enginePanel.getOutputTf());
		addClearOnClick(enginePanel.getReturnValueTf());
	}

	private void addClearOnClick(final Component component)
	{
		final String markupId = component.getMarkupId();
		final String clearJs = String.format("clearValue('%s')", markupId);
		add(new AttributeAppender("onclick", Model.of(clearJs), ";"));
	}

}