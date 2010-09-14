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
package org.wicketstuff.datatable_autocomplete.table;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.IClusterable;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.LoggerFactory;

/**
 * @author mocleiri
 * 
 *         Almost an exact copy of the wicket AjaxFallbackDefaultDataTable except that we have control over when the pagination toolbar is added.
 *         
 */
public class DTADataTable<T> extends DataTable<T> {

	private static final org.slf4j.Logger						log								= LoggerFactory
																							.getLogger(DTADataTable.class);

	public static interface DTADataTableItemModifier<T> extends IClusterable {
		
		/**
		 * Allows the row item to be modified when it is created.  Typcially used for on row click listeners.
		 * 
		 * @param item
		 */
		public void modifyRowItem (Item<T> item);
		
		
	}
	/**
	 * 
	 */
	private static final long						serialVersionUID				= -1878257266564660026L;
	
	

	private DTADataTableItemModifier<T>	itemModifier;

	
	

	
	

	/**
	 * @param id
	 * @param columns
	 * @param dataProvider
	 * @param rowsPerPage
	 */
	public DTADataTable(String id, String cssClassName,
			IColumn[] columns, ISortableDataProvider<T> dataProvider,
			IDTATableRenderingHints hints) {

		super(id, columns, dataProvider, hints.getPageSize());

		setItemReuseStrategy(new ReuseIfModelsEqualStrategy());

		// set the table class
		add(new AttributeModifier("class", new Model<String>(cssClassName)));
		
		setOutputMarkupId(true);
		setVersioned(false);
		
		
		if (hints.isPaginationEnabled())
			addTopToolbar(new AjaxNavigationToolbar("navigationToolbar", this));
		
		addTopToolbar(new AjaxFallbackHeadersToolbar("headerToolbar", this, dataProvider));
		
		if (hints.showNoRecordsToolbar())
			addBottomToolbar(new NoRecordsToolbar("noRecordsToolbar", this));

	}


	/**
	 * @param id
	 * @param tableColumns
	 * @param provider
	 * @param i
	 */
	public DTADataTable(String id, IColumn[] tableColumns,
			ISortableDataProvider<T> provider, IDTATableRenderingHints hints) {

		this(id, "dta_data_table", tableColumns, provider, hints);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.markup.html.panel.Panel#onComponentTag(org.apache.wicket
	 * .markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {

		// force the name of the tag to be table.
		// this allows us to use this component on a span or div tag.
		tag.setName("table");

		super.onComponentTag(tag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.markup.html.panel.Panel#onComponentTagBody(org.apache
	 * .wicket.markup.MarkupStream, org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {

		// force the name of the tag to be table.
		// this allows us to use this component on a span or div tag.
		openTag.setName("table");

		super.onComponentTagBody(markupStream, openTag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
	 * #newRowItem(java.lang.String, int, org.apache.wicket.model.IModel)
	 */
	@Override
	protected Item<T> newRowItem(String id, int index, IModel<T> model) {

		Item<T> rowItem = super.newRowItem(id, index, model);
		
		if (itemModifier != null) {
			
			// optionally modify the 'row' item.
			// a good place to put onclick listeners.
			itemModifier.modifyRowItem(rowItem);
			
		}
		

		return rowItem;
	}

	

	/**
	 * Set the item modifier.
	 */
	public final void setItemModifier(DTADataTableItemModifier<T> itemModifier) {
		this.itemModifier = itemModifier;

	}


}