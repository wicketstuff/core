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
package org.wicketstuff.datatable_autocomplete.form.action;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;



/**
 * @author mocleiri
 * 
 * A way to implement the submission logic outside of the submitting component.
 *  
 */
public interface IFormSubmitAction<T> extends IClusterable {

	/**
	 * Logic called when the form successfully submits.
	 * 
	 * @param target
	 * @param form
	 * 
	 */
	public void onSubmit (AjaxRequestTarget target, Form<T> form);
	
	/**
	 * Logic called when the form fails to submit due to errors.
	 * 
	 * @param target
	 * @param form
	 */
	public void onError (AjaxRequestTarget target, Form<T> form);
	
	
}
