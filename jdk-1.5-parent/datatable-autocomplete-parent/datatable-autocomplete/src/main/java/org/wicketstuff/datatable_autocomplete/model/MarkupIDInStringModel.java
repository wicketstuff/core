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
package org.wicketstuff.datatable_autocomplete.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mocleiri
 * 
 * Used for situations like rendering javascript method calls where we need the markupid for the component.
 * 
 * The markupid is not available until the component is on a page so we need the model to defer the question until rendering time which by
 * definition the context will exist.
 *
 */
public class MarkupIDInStringModel implements IModel<String> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 548831417478329002L;

	private static final Logger	log	= LoggerFactory
											.getLogger(MarkupIDInStringModel.class);
	
	/**
	 * Used by child classes to define the markupid placeholder in their attached template
	 */
	public static final String	MARKUP_ID_TAG	= ":markupID:";
	
	private final String	template;
	private final Component	target;
	private final Map<String, IModel<? extends Serializable>>	templateArgMap;

	
	/**
	 * 
	 */
	public MarkupIDInStringModel(String template, Component target, Map<String, IModel<? extends Serializable>>templateArgMap) {
		super();
		this.template = template;
		this.target = target;
		this.templateArgMap = templateArgMap;
		
		target.setOutputMarkupId(true);

	}
	
	public MarkupIDInStringModel (String template, Component target) {
		this (template, target, new LinkedHashMap<String, IModel<? extends Serializable>>());
	
	}
	
	protected void storeTemplateArgument (String alias, IModel<? extends Serializable> valueModel) {
		this.templateArgMap.put(alias, valueModel);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IModel#getObject()
	 */
	public final String getObject() {

		/*
		 * Render the string using the template and replacing the content as dictated by the parameters.
		 */
		
		String renderedMarkupIDString = template;
		
		if (target != null)
			renderedMarkupIDString = renderedMarkupIDString.replaceAll(MARKUP_ID_TAG, target.getMarkupId());
		
		for (String id : templateArgMap.keySet()) {
			
			IModel<? extends Serializable> replacement = templateArgMap.get(id);
			
			String replacementValue = null;
			
			if (replacement == null || replacement.getObject() == null)
				replacementValue = "";
			else
				replacementValue = replacement.getObject().toString();
			
			renderedMarkupIDString = renderedMarkupIDString.replaceAll(id, replacementValue);
		}
		
		return renderedMarkupIDString;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
	 */
	public void setObject(String object) {

		// does nothing

	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.model.IDetachable#detach()
	 */
	public void detach() {

		// does nothing

	}

	
}
