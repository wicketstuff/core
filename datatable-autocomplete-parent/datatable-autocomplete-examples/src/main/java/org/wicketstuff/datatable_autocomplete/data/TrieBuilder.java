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

package org.wicketstuff.datatable_autocomplete.data;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration;
import org.wicketstuff.datatable_autocomplete.trie.Trie;

/**
 * @author mocleirihe boot class path for the rt.jar and then loading X methods where X is large.
 *
 */
public final class TrieBuilder {

	private static final Logger log = LoggerFactory.getLogger(TrieBuilder.class);
	
	// holds the count of first charcter to count
	private Map<String, List<Method>>map = new LinkedHashMap<String, List<Method>>();

	private Trie<Method> trie;
	
	/**
	 * 
	 */
	public TrieBuilder() {
		trie = new Trie<Method>(new ITrieConfiguration<Method>() {

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#getWord(java.lang.Object)
			 */
			public String getWord(Method ctx) {
				
				return ctx.getName();
			}

			/* (non-Javadoc)
			 * @see org.wicketstuff.datatable_autocomplete.trie.ITrieConfiguration#isIndexCaseSensitive()
			 */
			public boolean isIndexCaseSensitive() {
				// default to not distinuishing between the case of the strings in the index.
				return false;
			}
			
			
		});
	}
	
	

	public void buildTrie (int maxElements) {
		
		
		String path = System.getProperty("sun.boot.class.path");

		String[] parts = path.split(":");

		String targetPart = null;

		int addedElements = 0;
		
		for (String p : parts) {

			if (p.endsWith("rt.jar")) {
				targetPart = p;
				break;
			}
		}

		try {
			JarFile jar = new JarFile(targetPart);
			
			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements()) {
				
				JarEntry jarEntry = (JarEntry) entries.nextElement();

				if (!jarEntry.getName().contains(".class"))
					continue;
				
				Class clazz;

				try {

					String className = jarEntry.getName().replace("/", ".")
							.replace(".class", "");

//					System.out.println(className);

					ClassLoader.getSystemClassLoader().loadClass(className);

					clazz = ClassLoader.getSystemClassLoader().loadClass(className);

					Method[] m = clazz.getDeclaredMethods();

									
					for (Method method : m) {

						if (!Modifier.isPublic(method.getModifiers()))
							continue; // skip non public methods
						
						trie.index(method);
						
						addedElements++;
						
						String firstCharacter = method.getName().substring(0, 1);
						
						List<Method> methodList = this.map.get(firstCharacter);
						
						if (methodList == null) {
							methodList = new LinkedList<Method>();
							this.map.put(firstCharacter, methodList);
						}
						
						methodList.add(method);
						
						if (addedElements >= maxElements) {
							
							log.info ("indexed " + addedElements + " elements.");
							trie.simplifyIndex();
							return;
						}
						
//						System.out.println(clazz.getCanonicalName() + " "
//								+ method.getName() + " ( " + parameterList + " )");
					}

				} catch (ClassNotFoundException e) {
					// continue
				}

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info ("indexed " + addedElements + " elements.");
		// compact the trie.
		
		trie.simplifyIndex();
	}

	public List<Method> getListForFirstCharacter (String character) {
		
		return this.map.get(character);
			
	}

	public Trie<Method> getTrie() {
		return trie;
	}
}
