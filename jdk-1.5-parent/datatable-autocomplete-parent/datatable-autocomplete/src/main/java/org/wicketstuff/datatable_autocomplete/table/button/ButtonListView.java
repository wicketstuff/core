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
package org.wicketstuff.datatable_autocomplete.table.button;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.datatable_autocomplete.button.DTAAjaxFallbackButton;
import org.wicketstuff.datatable_autocomplete.form.action.AbstractFormOnSubmitAction;
import org.wicketstuff.datatable_autocomplete.form.action.IFormOnSubmitAction;
import org.wicketstuff.datatable_autocomplete.model.MarkupIDInStringModel;
import org.wicketstuff.datatable_autocomplete.table.ISelectableTableViewPanelButtonProvider;

/**
 * @author mocleiri
 *
 * Expects the markup to look like:
 * <code>
 *<span wicket:id="any tag you want">
 * <span wicket:id="button" />
 * </span>
 * </code>
 */
public class ButtonListView extends ListView<ISelectableTableViewPanelButtonProvider> {

	public static final String BUTTON_ID = "button";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8897933872966515782L;
	private final Form<?>		form;
	private final String	displayEntityName;
	private FormComponent<?> selectedContextField;

	/**
	 * @param id
	 * @param displayEntityName
	 * @param list
	 */
	public ButtonListView(String id, Form<?> form, String displayEntityName, FormComponent<?>selectedContextField) {

		super(id);
		this.form = form;
		this.displayEntityName = displayEntityName;
		this.selectedContextField = selectedContextField;
		setReuseItems(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.markup.html.list.ListView#populateItem(org.apache
	 * .wicket.markup.html.list.ListItem)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void populateItem(ListItem<ISelectableTableViewPanelButtonProvider> item) {

		final ISelectableTableViewPanelButtonProvider buttonProvider = item.getModelObject();
		
		IFormOnSubmitAction buttonAction = buttonProvider
				.getButtonAction();

		if (buttonAction == null) {
			Label label;
			// not visible
			item.add(label = new Label(BUTTON_ID));

			label.setVisible(false);

			item.add(label);

			return;
		}

			// if the tow is required then
			buttonAction = new AbstractFormOnSubmitAction() {
			

				/**
				 * 
				 */
				private static final long serialVersionUID = 5910571859628875165L;

				
				public void onSubmit(AjaxRequestTarget target, Form form) {

					if (buttonProvider.isSelectedRowRequired()) {
						
						if (selectedContextField.getModelObject() == null) {
							if (target != null) {
								target.prependJavascript("alert ('A selected row is required.');");
							}
							else
								error("A selected row is required.");
							
							return;
						}
						
						// fall through
					}
					
					// run the user logic
					buttonProvider.getButtonAction().onSubmit(target, form);


					// else the action is defined.
					if (buttonProvider.isClearSelectedRowOnAction()) {
						// clear the value in the form.
						selectedContextField.clearInput();
					}

				}

			};

		DTAAjaxFallbackButton button;

//		if (buttonProvider.isSelectedRowRequired()) {
//			
//			IModel<String> requireASelectedRow;
//			
//			final List<Radio>radioList = new LinkedList<Radio>();
//			
//			if (selectedContextField instanceof RadioGroup) {
//				RadioGroup rg = (RadioGroup) selectedContextField;
//				
//				rg.visitChildren(new IVisitor() {
//
//					/* (non-Javadoc)
//					 * @see org.apache.wicket.Component.IVisitor#component(org.apache.wicket.Component)
//					 */
//					public Object component(Component component) {
//						
//						if (component instanceof Radio) {
//							
//							radioList.add((Radio) component);
//						}
//						
//						return IVisitor.CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
//					}
//					
//				});
//				
//				
//				requireASelectedRow = createRadioRequireSelectedRowModel (radioList);
//				
//			}
//			else {
//				// String hidden field
//			
//			
//			requireASelectedRow = new MarkupIDInStringModel(
//					"if (Wicket.$('" + MarkupIDInStringModel.MARKUP_ID_TAG
//							+ "').value == '') {"
//							+ "\nalert ('A selected row is required.');"
//							+ "\nreturn false;" + "}", selectedContextField);
//			}
//			
//			button = new DTAAjaxFallbackButton(BUTTON_ID, buttonProvider
//					.getButtonLabelText(displayEntityName), this.form,
//					requireASelectedRow);
//
//			button.setSubmitAction(buttonAction);
//		}
//		else {

			button = new DTAAjaxFallbackButton(BUTTON_ID, buttonProvider
					.getButtonLabelText(displayEntityName), this.form,
					buttonAction);
//		}
		
		String cssClass = buttonProvider.getCSSClassName();
		
		if (cssClass != null) {
			button.add(new AttributeModifier("class", true, new Model<String>(cssClass)));
		}

		item.add(button);

	}

	private IModel<String> createRadioRequireSelectedRowModel(
			final List<Radio> radioList) {
		
		return new LoadableDetachableModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8113932643386163719L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.model.LoadableDetachableModel#load()
			 */
			@Override
			protected String load() {
				
				StringBuffer template = new StringBuffer();
				
				template.append("if (");
				
				
				for (int i = 0; i < radioList.size(); i++) {
					
					Radio radio = radioList.get(i);
					
					template.append ("(Wicket.$('"+ radio.getMarkupId(true) +"').checked == false)");
				
					if (i > 0 && (i < (radioList.size()-1))) {
						template.append (" and ");
					}
					radio.setOutputMarkupId(true);
				}
				
				template.append("{"
					+ "\nalert ('A selected row is required.');"
					+ "\nreturn false;" + "\n}");
				
				
				
				return template.toString();
				
			}
			
		};
		
	}
}
