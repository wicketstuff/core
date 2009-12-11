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

package org.wicketstuff.datatable_autocomplete.trie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author mocleiriing a built Trie<?> to disk.
 *
 */
public final class TrieUtils {

	/**
	 * 
	 */
	private TrieUtils() {
	}

	/**
	 * Save the Trie into the file named.  This can be useful since creating the Trie takes more memory than storing the simplified/compressed Patricia Trie.
	 * 
	 * @param dataFile
	 * @param trie
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void save(File dataFile, Trie<?>trie) throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				dataFile, false));

		oos.writeObject(trie);

		oos.close();

	}

	/**
	 * Load a Trie from the file named.
	 * 
	 * @param dataFile
	 * @return trie loaded from the file name provided.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Trie<?> load(File dataFile) throws FileNotFoundException, IOException,
			ClassNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				dataFile));

		Trie<?> trie = (Trie<?>) ois.readObject();

		ois.close();
		
		return trie;

	}
}
