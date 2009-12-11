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

import java.util.Comparator;

import org.apache.wicket.IClusterable;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;


/**
 * @author mocleiri
 * 
 * Defines how a trie list data can be sorted
 */
public interface IProviderSorter<C> extends IClusterable {

	/**
	 * @param sort the string value of the Model for C Type.name
	 * @return the comparator for sorting a list of C by the property name given.
	 */
	Comparator<C> getComparatorForProperty(SortParam sort);
	
	

}
