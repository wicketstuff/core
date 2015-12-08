/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.web.util;

import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

/**
 * @author Decebal Suiu
 */
public abstract class AjaxConfirmLink<T> extends AjaxLink<T> {

	private static final long serialVersionUID = 1L;

	private String confirmMessage;
	
	public AjaxConfirmLink(String id) {
		super(id);
	}
	
	public AjaxConfirmLink(String id, IModel<T> model) {
		super(id, model);
	}
	
	public AjaxConfirmLink(String id, String confirmMessage) {
		super(id);
		
		this.confirmMessage = confirmMessage;
	}

	public AjaxConfirmLink(String id, IModel<T> model, String confirmMessage) {
		super(id, model);
		
		this.confirmMessage = confirmMessage;
	}


	public void setConfirmMessage(String confirmMessage) {
		this.confirmMessage = confirmMessage;
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		
		if (!Strings.isEmpty(confirmMessage)) {
			attributes.getAjaxCallListeners().add(new ConfirmAjaxCallListener(confirmMessage));
		}
	}

}
