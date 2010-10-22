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

import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mocleiri
 *
 */
public class DTARadioPanel<T> extends Panel implements IFormVisitorParticipant {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7457090902151030992L;
	private static final Logger	log	= LoggerFactory.getLogger(DTARadioPanel.class);

	private Radio<T>radio;
	
	


	/**
	 * @param id
	 * @param model
	 */
	public DTARadioPanel(String id, IModel<T> model) {
		super(id);
		
		add (radio = new Radio<T>("radio", model));
		
	}




	public Radio<T> getRadio() {
		return radio;
	}




	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IFormVisitorParticipant#processChildren()
	 */
	public boolean processChildren() {
		return true;
	}

	


}
