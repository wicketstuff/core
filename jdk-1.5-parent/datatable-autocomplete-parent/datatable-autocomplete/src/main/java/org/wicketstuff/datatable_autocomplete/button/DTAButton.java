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
package org.wicketstuff.datatable_autocomplete.button;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.form.action.IFormSubmitAction;


/**
 * @author mocleiri
 * 
 * Data Table Autocomplete button that extends the standard wicket button to support IOnFormSubmitAction actions.
 *
 */
public class DTAButton extends Button implements DTAButtonProvider {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1829825558587963971L;

	private static final Logger	log	= LoggerFactory.getLogger(DTAButton.class);
	
	private IFormSubmitAction	submitAction = null;

	
	
	/**
	 * @param id
	 */
	public DTAButton(String id, String label, IFormSubmitAction submitAction) {
		
		
		super(id, new Model<String> (label));
		this.submitAction = submitAction;
		
	}

	public DTAButton(String id, String label) {

		this (id, label, null);
		
	}
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Button#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {

		// this allows the button to be used in a repeater
		tag.setName("input");
		tag.setHasNoCloseTag(true);
		
		
		
		super.onComponentTag(tag);
		
		tag.getAttributes().put("type", "submit");
	}


	

	

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
	 */
	@Override
	public void onSubmit() {

		
		
		if (submitAction != null)
			submitAction.onSubmit(null, getForm());
			
		

	}
	
	
	/**
	 * @param submitAction the submitAction to set
	 */
	public void setSubmitAction(IFormSubmitAction submitAction) {
	
		this.submitAction = submitAction;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender() {
		
		/*
		 * Defaults to hidden if there is no action defined.
		 */
		if (this.submitAction == null)
			setVisible(false);
		else
			setVisible(true);

		super.onBeforeRender();
	}

	public Button getButton() {

		if (!getId().equals(DTAButtonProvider.BUTTON_ID)) {
			// this is a problem
			throw new RuntimeException ("ID of button ("+getId()+" != DTAButtonProvider.BUTTON_ID");
		}
		
		return this;
	}

	
	
	
	
	
	

	
}
