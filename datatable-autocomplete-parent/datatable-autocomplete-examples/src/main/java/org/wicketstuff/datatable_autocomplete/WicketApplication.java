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

package org.wicketstuff.datatable_autocomplete;

import java.lang.reflect.Method;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.data.TrieBuilder;
import org.wicketstuff.datatable_autocomplete.trie.PatriciaTrie;
import org.wicketstuff.datatable_autocomplete.web.page.HomePage;


/**
 * @author mocleiri
 */

public class WicketApplication extends WebApplication {

	/*
	 * The number of methods to load into the Trie index. 
	 */
	private static final int methodLimit = 125000;


	private static Logger log = LoggerFactory.getLogger(WicketApplication.class);
	
	
	private static PatriciaTrie<Method> trie;

	/**
	 * 
	 */
	public WicketApplication() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init() {
		super.init();
		
		TrieBuilder builder = new TrieBuilder();
		
		builder.buildTrie(methodLimit);

		WicketApplication.trie = builder.getTrie();
		
		
	}

	public static PatriciaTrie<Method> getTrie() {
		
		return trie;
	}
	
	

}
