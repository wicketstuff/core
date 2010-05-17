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
 * 
 */

package org.wicketstuff.datatable_autocomplete.web.page;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.datatable_autocomplete.WicketApplication;
import org.wicketstuff.datatable_autocomplete.comparator.DTAComparator;
import org.wicketstuff.datatable_autocomplete.panel.AutoCompletingTextField;
import org.wicketstuff.datatable_autocomplete.panel.DefaultAutocompleteRenderingHints;
import org.wicketstuff.datatable_autocomplete.panel.IAutocompleteControlPanelProvider;
import org.wicketstuff.datatable_autocomplete.provider.IModelProvider;
import org.wicketstuff.datatable_autocomplete.provider.IProviderSorter;
import org.wicketstuff.datatable_autocomplete.provider.ITrieProvider;
import org.wicketstuff.datatable_autocomplete.provider.TrieDataProvider;
import org.wicketstuff.datatable_autocomplete.selection.ITableRowSelectionHandler;
import org.wicketstuff.datatable_autocomplete.trie.ITrieFilter;
import org.wicketstuff.datatable_autocomplete.trie.Trie;
import org.wicketstuff.datatable_autocomplete.web.model.LoadableDetachableMethodModel;
import org.wicketstuff.datatable_autocomplete.web.panel.MatchControlPanel;
import org.wicketstuff.datatable_autocomplete.web.table.column.MethodColumn;
import org.wicketstuff.datatable_autocomplete.web.table.column.MethodColumn.MethodColumnType;

/**
 * @author mocleiri
 * 
 */
public class HomePage extends WebPage {

	
	
	private TextField<String>classNameFilterField;
	
	private AutoCompletingTextField<Method> field;
	private LoadableDetachableMethodModel selectedMethodModel;
	private Label selectedMethodField;
	
	@SuppressWarnings("serial")
	class MethodNameComparator extends DTAComparator<Method> {

		
		public MethodNameComparator(boolean ascending) {
			super(ascending);
		}

		/* (non-Javadoc)
		 * @see org.wicketstuff.datatable_autocomplete.comparator.DTAComparator#compareAscending(java.lang.Object, java.lang.Object)
		 */
		@Override
		protected int compareAscending(Method o1, Method o2) {

			String m1 = o1.getName();
			String m2 = o2.getName();

			return m1.compareTo(m2);
		}
	
		
	}
	
	@SuppressWarnings("serial")
	class MethodClassNameComparator extends DTAComparator<Method> {
		
		/**
		 * @param ascending
		 */
		public MethodClassNameComparator(boolean ascending) {
			super(ascending);
			// TODO Auto-generated constructor stub
		}
		
		

		/* (non-Javadoc)
		 * @see org.wicketstuff.datatable_autocomplete.comparator.DTAComparator#compareAscending(java.lang.Object, java.lang.Object)
		 */
		@Override
		protected int compareAscending(Method o1, Method o2) {
			
			String className1 = o1.getDeclaringClass().getName();
			String className2 = o2.getDeclaringClass().getName();
			
			return className1.compareTo(className2);
		}
		
	}
	/**
	 * 
	 */
	public HomePage() {

		super();

		IModel<String> stringModel = new Model<String>();
		
classNameFilterField = new TextField<String>("filter", new Model<String>(""));
		
		
		
		
		
		
		add(classNameFilterField);

		final TrieDataProvider<Method> methodProvider = new TrieDataProvider<Method>(
				new ITrieProvider<Method>() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.wicketstuff.datatable_autocomplete.provider.ITrieProvider
					 * #provideTrie()
					 */
					public Trie<Method> provideTrie() {
						return WicketApplication.getTrie();
					}

				},
				new ITrieFilter<Method>() {

					/* (non-Javadoc)
					 * @see org.wicketstuff.datatable_autocomplete.trie.TrieFilter#isVisible(java.lang.Object)
					 */
					public boolean isVisible(Method word) {
						
						String classNameFilter = classNameFilterField.getModelObject();
						
						if (classNameFilter == null || classNameFilter.trim().length() == 0)
							return true;
						
						if (classNameFilter != null && classNameFilter.trim().length() > 0) {
							
							String className = word.getDeclaringClass().getName();
							
							word.getDeclaringClass().getName();
							
							if (className.matches(".*" + classNameFilter + ".*"))
								return true;
						}
						
						// all other cases
						return false;
						
					}
					
				},
				
				stringModel, new IProviderSorter<Method>() {

					/*
					 * (non-Javadoc)
					 * 
					 * @seeorg.wicketstuff.datatable_autocomplete.provider.
					 * IProviderSorter
					 * #getComparatorForProperty(org.apache.wicket
					 * .extensions.markup.html.repeater.util.SortParam)
					 */
					public Comparator<Method> getComparatorForProperty(
							SortParam sort) {
						
						if (sort.getProperty().equals(MethodColumnType.CLASS_NAME))
							return new MethodNameComparator(true);
						else
							return new MethodClassNameComparator(true);
					}

				}, new IModelProvider<Method>() {

					/* (non-Javadoc)
					 * @see org.wicketstuff.datatable_autocomplete.provider.IModelProvider#model(java.lang.Object)
					 */
					public IModel<Method> model(Method obj) {
						
						return new LoadableDetachableMethodModel (obj);
					}
					
				});
		
		


		field = new AutoCompletingTextField<Method>(
				"field", stringModel, new IColumn[] { new MethodColumn(
						"Method Name", MethodColumnType.METHOD_NAME), new MethodColumn("Parameters", MethodColumnType.PARAMETERS),  new MethodColumn("Class Name", MethodColumnType.CLASS_NAME) }, methodProvider,
						new ITableRowSelectionHandler<Method>() {

							/**
							 * 
							 */
							private static final long serialVersionUID = 8814576423898775282L;

							public void handleSelection(int index, Method modelObject,
									AjaxRequestTarget target) {

								/*
								 * If the matched row were being used to build part of another object.
								 * 
								 * The assignment/setup of that other object can take place here.
								 * 
								 * i.e. it occurs outside of the standard form processing logic.
								 * 
								 */
								
								
//								target.prependJavascript("alert('selected method = "
//										+ modelObject.getName() + "');");

								selectedMethodModel.setObject(modelObject);
								
								target.addComponent(HomePage.this.field);
								target.addComponent(HomePage.this.selectedMethodField);
								
								
								
							}
						}, new IAutocompleteControlPanelProvider() {
							
							public Component getPanel(Component onChangeComponent, String controlPanelId) {
								
								return new MatchControlPanel(controlPanelId, methodProvider, onChangeComponent);
								
							}
						}, new DefaultAutocompleteRenderingHints(25, true));

		field.setOutputMarkupId(true);
		
		classNameFilterField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			/* (non-Javadoc)
			 * @see org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				
				if (target != null) {
					target.addComponent(field);
				}
				
				
			}
		
			
		});
		selectedMethodField = new Label("selectedMethod", selectedMethodModel = new LoadableDetachableMethodModel(null));
		
		selectedMethodField.setOutputMarkupId(true);
		
		add(field);
		
		add(selectedMethodField);
		
		add(new Label ("numberOfMethods", new AbstractReadOnlyModel<String>() {

			/* (non-Javadoc)
			 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
			 */
			@Override
			public String getObject() {
				
				final AtomicInteger counter = new AtomicInteger(0);
				
				return String.valueOf(WicketApplication.getTrie().size());
				
				
			}
			
		}));
		
		Label freqLabel;
		add(freqLabel = new Label ("alphabetFrequency", new AbstractReadOnlyModel<String>() {

			/* (non-Javadoc)
			 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
			 */
			@Override
			public String getObject() {
				
				StringBuffer buf = new StringBuffer();
				
				Trie<Method>trie = WicketApplication.getTrie();
				
				List<String>nextCharacterList = new LinkedList<String>();
				nextCharacterList.addAll(trie.getNextNodeCharacterSet());
				
				int count = 0;
				
				Collections.sort(nextCharacterList);
				
				for (String c : nextCharacterList) {
				
					int size = trie.getWordList(c).size();
					
					count += size;
					
					
					buf.append(c);
					buf.append(" (elements = ");
					buf.append(size);
					buf.append(")<br>");
					
				}
				
				
				buf.append("<p>total = " + count + "<br>");
				
				return buf.toString();
				
			}
			
		}));
		
		
		
		freqLabel.setEscapeModelStrings(false);
		

	
	
	}

}
