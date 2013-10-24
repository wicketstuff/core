/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.pageserializer.common.analyze.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ImmutableTree;

/**
 * NOT A PUBLIC API
 * this builds a map of similar trees
 * 
 * @author mosmann
 * 
 */
class TreeTypeMap {

	Map<TreeType, List<ISerializedObjectTree>> map;

	public TreeTypeMap(List<? extends ISerializedObjectTree> source) {
		Map<TreeType, List<ISerializedObjectTree>> typeMap = new LinkedHashMap<TreeType, List<ISerializedObjectTree>>();

		for (ISerializedObjectTree tree : source) {
			TreeType key = new TreeType(tree);
			List<ISerializedObjectTree> list = typeMap.get(key);
			if (list == null) {
				list = new ArrayList<ISerializedObjectTree>();
				typeMap.put(key, list);
			}
			list.add(tree);
		}

		map = typeMap;
	}

	public List<ISerializedObjectTree> compressedResult() {
		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();

		for (Entry<TreeType, List<ISerializedObjectTree>> entry : map.entrySet()) {
			List<ISerializedObjectTree> list = entry.getValue();
			ret.add(list.size() == 1
					? list.get(0)
					: compress(list));
		}

		return ret;
	}

	/**
	 * join a list of nodes into one, label and size information will be aggregated
	 * 
	 * @param list
	 *          source nodes
	 * @return one node
	 */
	protected static ISerializedObjectTree compress(List<? extends ISerializedObjectTree> list) {
		ISerializedObjectTree first = list.get(0);
		final Class<?> type = first.type();
		final String label = allLables(list);

		int size = 0;
		for (ISerializedObjectTree t : list) {
			size = size + t.size();
		}

		return new ImmutableTree(null, type, label, size, compressedChildren(list));
	}

	/**
	 * all entries in the list are equal, join every matching child node for every entry
	 * 
	 * @param source
	 *          entries with same tree layout
	 * @return tree with compressed child layout
	 */
	protected static List<? extends ISerializedObjectTree> compressedChildren(List<? extends ISerializedObjectTree> source) {
		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
		List<List<? extends ISerializedObjectTree>> childrens = new ArrayList<List<? extends ISerializedObjectTree>>();

		int columns = 0;
		for (ISerializedObjectTree entry : source) {
			List<? extends ISerializedObjectTree> sortedChildren = sortByTreeType(entry.children());
			columns = sortedChildren.size();
			childrens.add(sortedChildren);
		}

		for (int i = 0; i < columns; i++) {
			List<ISerializedObjectTree> columnValues = new ArrayList<ISerializedObjectTree>();
			for (List<? extends ISerializedObjectTree> line : childrens) {
				columnValues.add(line.get(i));
			}
			ISerializedObjectTree compressed = compress(columnValues);
			ret.add(compressed);
		}
		return ret;
	}

	private static List<? extends ISerializedObjectTree> sortByTreeType(List<? extends ISerializedObjectTree> source) {
		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
		ret.addAll(source);
		Collections.sort(ret, new Comparator<ISerializedObjectTree>() {

			@Override
			public int compare(ISerializedObjectTree o1, ISerializedObjectTree o2) {
				return new TreeType(o1).compareTo(new TreeType(o2));
			}
		});
		return ret;
	}

	/**
	 * joins all labels from list into on
	 * 
	 * @param list
	 *          tree elements
	 * @return joined label
	 */
	protected static String allLables(List<? extends ISerializedObjectTree> list) {
		Set<String> labels = new LinkedHashSet<String>();
		for (ISerializedObjectTree t : list) {
			if (t.label() != null)
				labels.add(t.label());
		}
		if (labels.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String s : labels) {
			if (first) {
				first = false;
			} else {
				sb.append("|");
			}
			sb.append(s);
		}
		return sb.toString();
	}

	public boolean hasLessEntries(int size) {
		return map.size() < size;
	}
}
