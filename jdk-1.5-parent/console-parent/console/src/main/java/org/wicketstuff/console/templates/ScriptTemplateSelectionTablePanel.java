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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.convert.IConverter;
import org.wicketstuff.console.AbstractScriptEnginePanel;

public class ScriptTemplateSelectionTablePanel extends Panel {

	private final class TitleColumn extends PropertyColumn<ScriptTemplate> {
		private static final long serialVersionUID = 1L;

		private TitleColumn(final IModel<String> displayModel,
				final String propertyExpression) {
			super(displayModel, propertyExpression);
		}

		@Override
		public void populateItem(
				final Item<ICellPopulator<ScriptTemplate>> item,
				final String componentId, final IModel<ScriptTemplate> rowModel) {

			final Fragment fragment = new Fragment(componentId,
					"titleFragment", ScriptTemplateSelectionTablePanel.this);

			final AjaxLink<Void> link = new AjaxLink<Void>("link") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(final AjaxRequestTarget target) {
					final ScriptTemplate template = rowModel.getObject();
					final String script = template.script;
					if (enginePanel != null) {
						enginePanel.setInput(script);
						target.add(enginePanel);
					}

				}
			};
			link.add(new Label("label", createLabelModel(rowModel)));
			fragment.add(link);
			item.add(fragment);
		}
	}

	private static final long serialVersionUID = 1L;
	private static final ResourceReference CSS = new CompressedResourceReference(
			ScriptTemplateSelectionTablePanel.class,
			ScriptTemplateSelectionTablePanel.class.getSimpleName() + ".css");
	private final AbstractScriptEnginePanel enginePanel;

	public ScriptTemplateSelectionTablePanel(final String id,
			final IDataProvider<ScriptTemplate> dataProvider,
			final int rowsPerPage) {
		this(id, null, dataProvider, rowsPerPage);
	}

	public ScriptTemplateSelectionTablePanel(final String id,
			final AbstractScriptEnginePanel enginePanel,
			final IDataProvider<ScriptTemplate> dataProvider,
			final int rowsPerPage) {
		super(id);
		this.enginePanel = enginePanel;
		checkEnginePanelOutputMarkupId(enginePanel);

		init(dataProvider, rowsPerPage);
	}

	private void init(final IDataProvider<ScriptTemplate> dataProvider,
			final int rowsPerPage) {
		final DataTable<ScriptTemplate> table = new DataTable<ScriptTemplate>(
				"table", createColumns(), dataProvider, rowsPerPage);
		add(table);
	}

	private List<IColumn<ScriptTemplate>> createColumns() {

		final IColumn<ScriptTemplate> titleColumn = new TitleColumn(
				Model.of("Title"), "title");
		final IColumn<ScriptTemplate> langColumn = new PropertyColumn<ScriptTemplate>(
				Model.of("Language"), "lang") {

			@Override
			public void populateItem(
					final Item<ICellPopulator<ScriptTemplate>> item,
					final String componentId,
					final IModel<ScriptTemplate> rowModel) {
				final Label label = new Label(componentId,
						createLabelModel(rowModel)) {

					@Override
					public <C> IConverter<C> getConverter(final Class<C> type) {
						return (IConverter<C>) new LangConverter();
					}

				};
				item.add(label);
			}

		};

		final List<IColumn<ScriptTemplate>> columns = new ArrayList<IColumn<ScriptTemplate>>();
		columns.add(titleColumn);
		columns.add(langColumn);

		return columns;
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		final ResourceReference css = getCSS();
		if (css != null) {
			response.renderCSSReference(css);
		}

	}

	protected ResourceReference getCSS() {
		return CSS;
	}

	private void checkEnginePanelOutputMarkupId(
			final AbstractScriptEnginePanel enginePanel) {
		if (enginePanel != null) {
			if (!enginePanel.getOutputMarkupId()) {
				throw new IllegalStateException(
						"Set enginePanel.setOutputMarkupId(true) to use "
								+ "it with ScriptTemplateSelectionTablePanel");
			}
		}
	}

}
