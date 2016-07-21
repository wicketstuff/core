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
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.console.ScriptEnginePanel;

/**
 * A table displaying {@link ScriptTemplate}s.
 * <p>
 * Script templates can be used to represent frequently used scripts. Typically such scripts would
 * be stored in some kind of storage. A data provider on top of this storage could then be used to
 * provide these scripts as {@link ScriptTemplate}s to this table, which displays them as a list.
 * When a script is selected this table copies it over to the attached
 * {@link AbstractScriptEnginePanel}'s input field so that the script can be executed immediately.
 * <p>
 * Example:
 * 
 * <pre>
 * GroovyScriptEnginePanel enginePanel = new GroovyScriptEnginePanel(&quot;scriptPanel&quot;);
 * enginePanel.setOutputMarkupId(true);
 * add(enginePanel);
 * 
 * ScriptTemplateSelectionTablePanel scriptTable = new ScriptTemplateSelectionTablePanel(
 * 	&quot;templatesTable&quot;, enginePanel, dataProvider(), 10);
 * add(scriptTable);
 * </pre>
 * 
 * Markup:
 * 
 * <pre>
 * &lt;div wicket:id=&quot;scriptPanel&quot;&gt;&lt;/div&gt;
 * &lt;div wicket:id=&quot;templatesTable&quot;&gt;&lt;/div&gt;
 * </pre>
 * 
 * @author cretzel
 */
public class ScriptTemplateSelectionTablePanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private static final ResourceReference CSS = new PackageResourceReference(
		ScriptTemplateSelectionTablePanel.class,
		ScriptTemplateSelectionTablePanel.class.getSimpleName() + ".css");

	private final ScriptEnginePanel enginePanel;

	private DataTable<ScriptTemplate, Void> table;

	public ScriptTemplateSelectionTablePanel(final String id, final ScriptEnginePanel enginePanel,
		final IDataProvider<ScriptTemplate> dataProvider, final int rowsPerPage)
	{
		super(id);
		this.enginePanel = enginePanel;
		checkEnginePanelOutputMarkupId(enginePanel);

		init(dataProvider, rowsPerPage);
	}

	private void init(final IDataProvider<ScriptTemplate> dataProvider, final int rowsPerPage)
	{
		table = new DataTable<ScriptTemplate, Void>("table", createColumns(), dataProvider, rowsPerPage);
		add(table);
	}

	protected List<IColumn<ScriptTemplate, Void>> createColumns()
	{

		final List<IColumn<ScriptTemplate, Void>> columns = new ArrayList<IColumn<ScriptTemplate, Void>>();
		columns.add(new TitleColumn(this));
		columns.add(new LangColumn(this));

		return columns;
	}

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		final ResourceReference css = getCSS();
		if (css != null)
		{
			response.render(CssHeaderItem.forReference(css));
		}

	}

	protected ResourceReference getCSS()
	{
		return CSS;
	}

	private void checkEnginePanelOutputMarkupId(final ScriptEnginePanel enginePanel)
	{
		if (enginePanel != null)
		{
			if (!enginePanel.getOutputMarkupId())
			{
				throw new IllegalStateException("Set enginePanel.setOutputMarkupId(true) to use "
					+ "it with ScriptTemplateSelectionTablePanel");
			}
		}
	}

	public void onScriptTemplateSelected(final IModel<ScriptTemplate> model,
		final AjaxRequestTarget target)
	{

		final ScriptTemplate template = model.getObject();
		final String script = template.script;

		if (enginePanel != null)
		{
			enginePanel.setInput(script);
			target.add(enginePanel.getInputTf());
		}

	}

}
