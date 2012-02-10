/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.datatable_autocomplete.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.DefaultItemReuseStrategy;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.datatable_autocomplete.selection.ITableRowSelectionHandler;
import org.wicketstuff.datatable_autocomplete.table.DTADataTable;
import org.wicketstuff.datatable_autocomplete.table.SelectableTableViewPanel;

/**
 * @author mocleiri
 * 
 */
public class AutoCompletingPanel<T> extends Panel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6173248750432002480L;

	private SelectableTableViewPanel<T> viewPanel;

	private final ISortableDataProvider<T> provider;

	private boolean initialRenderMode = false;

	private static final ResourceReference CSS = new PackageResourceReference(
		AutoCompletingPanel.class, "dta_auto_complete_overlay.css");

	private static final ResourceReference TABLE_CSS = new PackageResourceReference(
		AutoCompletingPanel.class, "dta_autocomplete_table.css");

	private Label theLabel;

	private Button closeButton;

	/**
	 * @param id
	 * @param ctx
	 */
	public AutoCompletingPanel(String id, IModel<String> fieldStringModel, IColumn<?>[] columns,
		SortableDataProvider<T> aliasDataProvider,
		ITableRowSelectionHandler<T> rowSelectionHandler,
		IAutocompleteControlPanelProvider controlPanelProvider,
		IAutocompleteRenderingHints renderingHints)
	{

		super(id);

		// NOTE: trying to stop page serialization on each update.
		setVersioned(false);

		this.provider = aliasDataProvider;

		add(new Behavior()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component c, IHeaderResponse response)
			{
				response.renderCSSReference(CSS);
			}
		});

		Component controlPanel = controlPanelProvider.getPanel(this, "controlPanel");

		if (controlPanel == null)
			controlPanel = new EmptyPanel("controlPanel");

		add(controlPanel);

		viewPanel = new SelectableTableViewPanel<T>("view", TABLE_CSS, "dta_data_table", "{title}",
			columns, aliasDataProvider, false, rowSelectionHandler, renderingHints);

		DTADataTable<T> dataTable = viewPanel.getDataTable();

		// redo the table on each update.
		dataTable.setItemReuseStrategy(new DefaultItemReuseStrategy());

// dataTable.setDisableRowHighlight(true);

		add(viewPanel);

		if (renderingHints.isVisibleOnZeroMatches())
		{
			viewPanel.setHideIfNoResults(false);
		}
		else
		{
			viewPanel.setHideIfNoResults(true);
		}

		add(theLabel = new Label("label_close", "Close"));

		add(closeButton = new Button("close_button", Model.of("x"))
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{

				// does nothing since it is a client side only action.
			}
		});

		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);

		closeButton.add(new AttributeModifier("onclick", new AbstractReadOnlyModel<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return "document.getElementById('" + AutoCompletingPanel.this.getMarkupId() +
					"').style.display='none';";
			}

		}));


	}


	public AutoCompletingPanel(String id, IModel<String> fieldStringModel, int resultsToShow,
		IColumn<?>[] columns, SortableDataProvider<T> dataProvider,
		ITableRowSelectionHandler<T> rowSelectionHandler,
		IAutocompleteControlPanelProvider controlPanelProvider, boolean paginationEnabled)
	{

		this(id, fieldStringModel, columns, dataProvider, rowSelectionHandler,
			controlPanelProvider, new DefaultAutocompleteRenderingHints(resultsToShow,
				paginationEnabled));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.markup.html.form.FormComponentPanel#onComponentTag(
	 * org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(ComponentTag tag)
	{

		tag.put("class", "dta_wicket_ajax_panel_overlay");

		super.onComponentTag(tag);
	}

	/**
	 * if value is true then on the inial non ajax rendering this component will be invisible.
	 * 
	 */
	public void setInitialRenderDisabledMode(boolean value)
	{

		this.initialRenderMode = value;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender()
	{

		if (!initialRenderMode)
		{
			this.viewPanel.updateVisibility();
			setVisible(this.viewPanel.isVisible());

		}
		else
			setVisible(false);

		super.onBeforeRender();

	}

}
