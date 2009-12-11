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
package org.wicketstuff.datatable_autocomplete.radio;

import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;

/**
 * @author mocleiri
 *  
 * A Custom radio group that requires a valid selection.
 *
 */
public class DTARadioGroup<T> extends RadioGroup<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3054061206074600757L;

	/**
	 * @param id
	 */
	public DTARadioGroup(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param model
	 */
	public DTARadioGroup(String id, IModel<T> model) {
		super(id, model);
		
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.FormComponent#isInputNullable()
	 */
	@Override
	public boolean isInputNullable() {
		// override the default
		return false;
	}
	
	

}
