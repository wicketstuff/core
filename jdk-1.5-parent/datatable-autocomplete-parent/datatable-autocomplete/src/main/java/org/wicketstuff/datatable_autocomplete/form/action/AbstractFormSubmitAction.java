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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * @author mocleiri
 * 
 * A base IFormSubmitAction implementation that supports a common case where a single container needs to be updated via ajax for either a 
 * successful submit or error case.
 *
 */
public abstract class AbstractFormSubmitAction<T> implements IFormSubmitAction<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9039664701112891380L;
	private final Component onUpdateComponent;


	/**
	 * 
	 */
	public AbstractFormSubmitAction(Component onUpdateComponent) {
		this.onUpdateComponent = onUpdateComponent;
		
	}
	
	/**
	 * 
	 */
	public AbstractFormSubmitAction() {
		this (null);
	}



	/**
	 * Add in the components that 
	 * @param target
	 */
	protected void onUpdate (AjaxRequestTarget target) {
		if (target != null && onUpdateComponent != null) {
		
			target.addComponent(onUpdateComponent);
		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.form.action.IFormSubmitAction#onError(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
	 */
	public final void onError(AjaxRequestTarget target, Form<T> form) {
		onUpdate(target);
		processError(target, form);
	}


	/**
	 * Implement this method to control what happens during the error case.
	 * 
	 * @param target
	 * @param form
	 */
	protected void processError(AjaxRequestTarget target, Form<T> form) {}


	/* (non-Javadoc)
	 * @see org.wicketstuff.datatable_autocomplete.form.action.IFormSubmitAction#onSubmit(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
	 */
	public final void onSubmit(AjaxRequestTarget target, Form<T> form) {
		onUpdate(target);
		processSubmit(target, form);
		
	}


	/**
	 * Implement this method to control what happens during the form submission.
	 * 
	 * @param target
	 * @param form
	 */
	protected void processSubmit(AjaxRequestTarget target, Form<T> form) {};
	
	

	
}
