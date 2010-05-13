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

package org.wicketstuff.datatable_autocomplete.web.panel;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.datatable_autocomplete.provider.TrieDataProvider;


/**
 * @author Michael O'Cleirigh (michael.ocleirigh@rivulet.ca)
 *
 */
public class MatchControlPanel extends Panel {

	private static final String SUBSTRING_MATCH = "SUBSTRING_MATCH";

	private static final String PREFIX_MATCH = "PREFIX_MATCH";
	
	private TextField<String> classNameFilterField;

	/**
	 * @param id
	 * @param methodProvider 
	 * @param onChangeComponent 
	 */
	public MatchControlPanel(String id, final TrieDataProvider<Method> methodProvider, final Component onChangeComponent) {
		super(id);
		
classNameFilterField = new TextField<String>("filter", new Model<String>(""));
		
classNameFilterField = new TextField<String>("filter", new Model<String>(""));






add(classNameFilterField);
		
		
		classNameFilterField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				
				if (target != null) {
					target.addComponent(onChangeComponent);
				}
				
				
			}
		
			
		});
		
	Form<?>form = new Form("settingsForm");
		
		form.add(classNameFilterField);
		
		DropDownChoice<String> findMethodDDC;
		form.add(findMethodDDC= new DropDownChoice<String>("findMethod", new IModel<String>() {

			/*
			 * This model toggles the substring to prefix string behaviour of the TrieDataProvider.
			 * 
			 */
			/* (non-Javadoc)
			 * @see org.apache.wicket.model.IModel#getObject()
			 */
			public String getObject() {
				
				if (methodProvider.isMatchAnyWhereInString())
					return SUBSTRING_MATCH;
				else
					return PREFIX_MATCH;
				
			}

			/* (non-Javadoc)
			 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
			 */
			public void setObject(String object) {
				
				if (object.equals(SUBSTRING_MATCH))
					methodProvider.setMatchAnyWhereInString(true);
				else
					methodProvider.setMatchAnyWhereInString(false);
				
			}

			/* (non-Javadoc)
			 * @see org.apache.wicket.model.IDetachable#detach()
			 */
			public void detach() {
				
				// intentionally not implemented.
				
			}},  new ListModel<String>(Arrays.asList(PREFIX_MATCH, SUBSTRING_MATCH))) {

				/* (non-Javadoc)
				 * @see org.apache.wicket.markup.html.form.DropDownChoice#wantOnSelectionChangedNotifications()
				 */
				@Override
				protected boolean wantOnSelectionChangedNotifications() {
					// cause the change to be recorded when the ddc is changed.
					return true;
				}});
	
		add(form);
		
	}

	
	
}
