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
package org.wicketstuff.datatable_autocomplete.provider;



import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.provider.utils.DataProviderUtils;
import org.wicketstuff.datatable_autocomplete.trie.Trie;
import org.wicketstuff.datatable_autocomplete.trie.ITrieFilter;

/**
 * @author mocleiri
 * 
 * A SortableDataProvider connector which loads the data from the backing Trie<C>.
 * 
 */
public class TrieDataProvider<C> extends SortableDataProvider<C> {

	private static final Logger						log						= LoggerFactory
																					.getLogger(TrieDataProvider.class);

	private static final long						serialVersionUID		= 6404076914195910834L;

	// prevents the underlying Trie from being serialized with the provider.
	// it is a big performance problem if the Trie is serialized (especially in AJAX cases).
	protected final ITrieProvider<C>					trieProvider;

	private List<C>									currentListData			= null;

	

	private final IModel<String>							fieldStringModel;
	
	private final IProviderSorter<C>					sorter;
	
	private ITrieFilter<C>							trieResultFilter;
	

	// default to show no results for an empty string
	private boolean									noResultsOnEmptyString	= true;

	private boolean									matchAnyWhereInString = false;

	private final IModelProvider<C> modelProvider;

	protected IModel<String> getInputModel() {

		return fieldStringModel;
	}

	/**
	 * @param noResultsOnEmptyString
	 *            the noResultsOnEmptyString to set
	 */
	public void setNoResultsOnEmptyString(boolean noResultsOnEmptyString) {

		this.noResultsOnEmptyString = noResultsOnEmptyString;
	}

	
	
	/**
	 * @param matchAnyWhereInString the matchAnyWhereInString to set
	 */
	public void setMatchAnyWhereInString(boolean matchAnyWhereInString) {
	
		this.matchAnyWhereInString = matchAnyWhereInString;
	}

	
	/**
	 * @return the matchAnyWhereInString
	 */
	public boolean isMatchAnyWhereInString() {
		return matchAnyWhereInString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
	 * #detach()
	 */
	@Override
	public void detach() {

		/*
		 * If we want to support selectable values then we need to make the list last longer than just one rendered pass.
		 */
		clear();
		super.detach();
	}

	public final void clear() {

		if (currentListData != null) {
			currentListData.clear();
			currentListData = null;
		}
	}

	/**
	 * @param fieldStringModel
	 * 
	 */
	public TrieDataProvider(ITrieProvider<C> trieProvider, ITrieFilter<C>resultFilter,
			IModel<String> fieldStringModel, IProviderSorter<C> sorter, IModelProvider<C> modelProvider) {

		super();
		this.trieProvider = trieProvider;
		trieResultFilter = resultFilter;

		this.fieldStringModel = fieldStringModel;
		this.sorter = sorter;
		this.modelProvider = modelProvider;
	}

	
	public int size() {

		String prefix = (String) fieldStringModel.getObject();

		if (prefix == null) {
			return 0;
		}
		else if (noResultsOnEmptyString && prefix.length() == 0) {
			// show no results since no input filter specified.
			return 0;
		}
		else {
			// if no input is given any element in the entire trie is selectable.
			if (currentListData == null) {

				Trie<C>trie = trieProvider.provideTrie();
				
				if (trie == null) {
					log.warn("trie is unexpectantly null!");
					return 0;
				}
				
				if (matchAnyWhereInString) {
					// substring matching
					// not the most efficent but it works.
					
					if (trieResultFilter != null)
						currentListData = trieProvider.provideTrie()
							.getAnyMatchingWordList(prefix, trieResultFilter, -1);
					else
						currentListData = trieProvider.provideTrie()
						.getAnyMatchingWordList(prefix);
				}
				else {
					// prefix matching
					
					if (trieResultFilter != null)
						currentListData = trieProvider.provideTrie().getWordList(
							prefix, trieResultFilter, -1);
					else
						currentListData = trieProvider.provideTrie().getWordList(
								prefix);
				}
			}

			return currentListData.size();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int,
	 * int)
	 */
	public Iterator<C> iterator(int first, int count) {

		String prefix = (String) fieldStringModel.getObject();

		if (prefix == null || currentListData == null)
			return new LinkedList<C>().iterator();

		SortParam sort = super.getSort();

		if (sort != null && this.currentListData.size() > 1) {

			Comparator<C> c = sorter.getComparatorForProperty(sort);

			if (c != null)
				Collections.sort(this.currentListData, c);

		}

		int size = currentListData.size();
		
		int last = DataProviderUtils.getLastIndex(size, first, count);

		if (first > last) {
			log.warn("indexing problem");
			last = DataProviderUtils.getLastIndex(size, first, count);
		}

		return currentListData.subList(first, last).iterator();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
	 */
	public IModel<C> model(C object) {
		
		return modelProvider.model (object);
	}

	
	
	



	
	

}
