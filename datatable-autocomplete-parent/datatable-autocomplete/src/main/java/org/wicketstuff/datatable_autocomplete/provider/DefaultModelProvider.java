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
package org.wicketstuff.datatable_autocomplete.provider;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


/**
 * @author mocleiri
 *
 */
public class DefaultModelProvider<C extends Serializable> implements IModelProvider<C> {

	/**
	 * 
	 */
	public DefaultModelProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.provider.IModelProvider#model(java.lang.Object)
	 */
	public IModel<C> model(C obj) {
		return new Model<C>(obj);
	}
	
	

}
