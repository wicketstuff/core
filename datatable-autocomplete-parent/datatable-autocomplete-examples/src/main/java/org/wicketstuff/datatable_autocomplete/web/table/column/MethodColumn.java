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

package org.wicketstuff.datatable_autocomplete.web.table.column;

import java.lang.reflect.Method;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author mocleiri
 * 
 * A Column that knows how to extract various attributes from the Method row model object.
 *
 */
public class MethodColumn extends AbstractColumn<Method> {

	private final MethodColumnType type;


	public static enum MethodColumnType { METHOD_NAME, CLASS_NAME, PARAMETERS };
	/**
	 * @param displayModel
	 */
	public MethodColumn(IModel<String> displayModel, MethodColumnType type) {
		super(displayModel);
		this.type = type;
	}

	public MethodColumn (String display, MethodColumnType type) {
		this (new Model<String>(display), type);
	}
	

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
	 */
	public void populateItem(Item<ICellPopulator<Method>> cellItem, String componentId, final IModel<Method> rowModel) {

		cellItem.add(new Label (componentId, new AbstractReadOnlyModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4014268651055132209L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
			 */
			@Override
			public String getObject() {
				
				Method m = rowModel.getObject();
				
				switch (MethodColumn.this.type) {
				case CLASS_NAME:
					
					return m.getDeclaringClass().getName();

				case METHOD_NAME:
					
					return m.getName();
					
				case PARAMETERS:
					
					String name = m.toString();
					
					int startIndex = name.indexOf("(");
					int endIndex = name.indexOf(")");
					
					// exclude the brackets
					String parameters = name.substring(startIndex+1, endIndex);
					
					// add better comma spacing
					parameters = parameters.replace(",", ", ");
					
					return parameters;
				default:
					return "";
				}
				
			}
			
		}));
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#getSortProperty()
	 */
	@Override
	public String getSortProperty() {
		return type.name();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn#isSortable()
	 */
	@Override
	public boolean isSortable() {
		return true;
	}

	
}
