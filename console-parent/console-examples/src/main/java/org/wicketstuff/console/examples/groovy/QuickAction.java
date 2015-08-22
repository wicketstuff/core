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

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.console.ScriptEnginePanel;
import org.wicketstuff.console.templates.ScriptTemplate;

class QuickAction extends Label implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;

	private final IModel<ScriptTemplate> model;
	private final ScriptEnginePanel enginePanel;

	QuickAction(final String id, final ScriptEnginePanel enginePanel,
		final IModel<ScriptTemplate> model, final int nr)
	{
		super(id, Model.of(nr));
		this.enginePanel = enginePanel;
		this.model = model;

		add(new AttributeAppender("title", new PropertyModel<String>(model, "title")));
		add(new AttributeAppender("class", Model.of("quickAction"), " "));
		add(new QuickActionClickBehavior("onclick", enginePanel, model));
	}


	@Override
	public void detachModels()
	{
		super.detachModels();
		model.detach();
	}

	public String getAjaxIndicatorMarkupId()
	{
		return enginePanel.getAjaxIndicatorMarkupId();
	}

}