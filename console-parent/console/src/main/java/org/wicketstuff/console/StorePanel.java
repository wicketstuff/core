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
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.console.templates.IScriptTemplateStore;

/**
 * Control to save the current script to an {@link IScriptTemplateStore}.
 * 
 * @author cretzel
 */
class StorePanel extends Panel
{

	private class SubmitButton extends AjaxButton implements IAjaxIndicatorAware
	{
		private static final long serialVersionUID = 1L;

		private SubmitButton(final String id, final Form<?> form)
		{
			super(id, form);
		}

		@Override
		protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
		{
			storeScriptTemplate(target);
		}

		@Override
		protected void onError(final AjaxRequestTarget target, final Form<?> form)
		{
		}

		public String getAjaxIndicatorMarkupId()
		{
			return enginePanel.getAjaxIndicatorMarkupId();
		}

	}

	private static final long serialVersionUID = 1L;

	private final ScriptEnginePanel enginePanel;

	private final SubmitButton submitButton;

	private final TextField<String> title;

	public StorePanel(final String id, final ScriptEnginePanel enginePanel)
	{
		super(id);
		this.enginePanel = enginePanel;

		submitButton = new SubmitButton("submit", enginePanel.getForm());
		add(submitButton);

		title = new TextField<String>("title", new Model<String>("Title"));
		add(title);

	}

	public void storeScriptTemplate(final AjaxRequestTarget target)
	{
		final String scriptTitle = title.getModelObject();
		enginePanel.storeScriptTemplate(target, scriptTitle);
	}

	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		final IScriptTemplateStore store = getStore();
		setVisible(store != null && !store.readOnly());
	}

	private IScriptTemplateStore getStore()
	{
		return enginePanel.getStore();
	}


}
