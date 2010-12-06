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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.parser.XmlTag;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mocleiri
 *
 */
public class DTARadio<T> extends Radio<T> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7457090902151030992L;
	private static final Logger	log	= LoggerFactory.getLogger(DTARadio.class);

	/**
	 * @param id
	 */
	public DTARadio(String id) {

		super(id);
	}

	/**
	 * @param id
	 * @param model
	 */
	public DTARadio(String id, IModel<T> model) {

		super(id, model);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.Radio#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {

		if (tag.isOpenClose()) {
			
			tag.setHasNoCloseTag(true);
			
			tag.setType(XmlTag.TagType.OPEN);
			
		}
		
		tag.setName("input");
		tag.getAttributes().put("type", "radio");
		
	
		
		// Default handling for component tag
		super.onComponentTag(tag);

	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.MarkupContainer#onComponentTagBody(org.apache.wicket.markup.MarkupStream, org.apache.wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {

		openTag.setName("input");
		
		
	
		
		markupStream.next();
		
	}
	
	

}
