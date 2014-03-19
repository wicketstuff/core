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
package org.wicketstuff.console.templates;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

final class TitleColumn extends PropertyColumn<ScriptTemplate, Void>
{

	private final class TitleLink extends AjaxLink<ScriptTemplate>
	{
		private static final long serialVersionUID = 1L;

		private TitleLink(final String id, final IModel<ScriptTemplate> model)
		{
			super(id, model);
		}

		@Override
		public void onClick(final AjaxRequestTarget target)
		{
			tablePanel.onScriptTemplateSelected(getModel(), target);
		}
	}

	private static final long serialVersionUID = 1L;

	private final ScriptTemplateSelectionTablePanel tablePanel;

	TitleColumn(final ScriptTemplateSelectionTablePanel scriptTemplateSelectionTablePanel)
	{
		super(Model.of("Title"), "title");
		tablePanel = scriptTemplateSelectionTablePanel;
	}

	@Override
	public void populateItem(final Item<ICellPopulator<ScriptTemplate>> item,
		final String componentId, final IModel<ScriptTemplate> rowModel)
	{

		final AjaxLink<ScriptTemplate> link = new TitleLink("link", rowModel);
		link.add(new Label("label", createLabelModel(rowModel)));
		link.add(new AttributeAppender("title", new PropertyModel<String>(rowModel, "title")));

		final Fragment fragment = new Fragment(componentId, "titleFragment", tablePanel);
		fragment.add(link);
		item.add(fragment);
	}
}