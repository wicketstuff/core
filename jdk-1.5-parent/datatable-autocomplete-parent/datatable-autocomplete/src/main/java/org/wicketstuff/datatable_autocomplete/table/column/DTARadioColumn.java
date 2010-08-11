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
package org.wicketstuff.datatable_autocomplete.table.column;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.datatable_autocomplete.radio.DTARadio;

/**
 * @author mocleiri
 * 
 * A radio for use where the table this column is added to is wrapped by a {@link RadioGroup}
 *
 */
public class DTARadioColumn<T> extends AbstractColumn<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -24109845922068732L;

	private List<Radio<T>>radioList = new LinkedList<Radio<T>>();
	
	
	/**
	 * @param displayModel
	 */
	public DTARadioColumn(IModel<String> displayModel) {
		super(displayModel);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param columnName
	 */
	public DTARadioColumn(String columnName) {
		this (new Model<String>(columnName));
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
	 */
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		
		DTARadio<T> rd;
		cellItem.add(rd = new DTARadio<T>(componentId, rowModel));
		
		rd.setOutputMarkupId(true);
		
		// should have a worst case size of visible page size.
		radioList.add(rd);

	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#detach()
	 */
	@Override
	public void detach() {
		super.detach();
//		this.radioList.clear();
		
	}
	
	/**
	 * Get the markupid for the radio representing a specific row.  Used to allow row onclick actions to trigger a radio selection DOM action.
	 * 
	 * @param index
	 * @return the markup id for the radio for the row index given. 
	 */
	public final String getRadioMarkupID (int index) {
		Radio<T> radio = radioList.get(index);
		
		if (radio == null)
			return null;
		else
			return radio.getMarkupId();
	}
	
	

}
