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

import org.apache.wicket.IClusterable;
import org.apache.wicket.model.IModel;


/**
 * @author mocleiri
 *
 */
public interface IModelProvider<C> extends IClusterable {

	/**
	 * 
	 * @param obj
	 * @return create a new model for the object provided.  Typically a loadable detachable model.
	 */
	public IModel<C>model (C obj);
}
