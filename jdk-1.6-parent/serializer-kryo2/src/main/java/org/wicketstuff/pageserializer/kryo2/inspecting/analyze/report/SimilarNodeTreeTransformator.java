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
package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.AbstractTreeTransformingProcessor;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ImmutableTree;

public class SimilarNodeTreeTransformator extends AbstractTreeTransformingProcessor
{
	private final static Logger LOG = LoggerFactory.getLogger(SimilarNodeTreeTransformator.class);

	public SimilarNodeTreeTransformator(ISerializedObjectTreeProcessor parent)
	{
		super(parent);
	}

	@Override
	protected ISerializedObjectTree transform(ISerializedObjectTree tree)
	{
		return transformTree(tree);
	}

	public static ISerializedObjectTree transformTree(ISerializedObjectTree tree)
	{
		ISerializedObjectTree ret = tree;
		if (!tree.children().isEmpty())
		{
			List<ISerializedObjectTree> transformed = new ArrayList<ISerializedObjectTree>();
			for (ISerializedObjectTree t : tree.children())
			{
				transformed.add(transformTree(t));
			}

			TreeTypeMap typeMap = new TreeTypeMap();
			for (ISerializedObjectTree t : transformed)
			{
				typeMap.add(t);
			}

			if (typeMap.hasLessEntries(transformed.size()))
			{
//				LOG.error("Compress {}",tree.type());
				
				List<ISerializedObjectTree> result = typeMap.compressedResult();
				ret = new ImmutableTree(ret.id(), ret.type(), ret.label(), ret.size(), result);
//				new TreeSizeReport().process(tree);
//				new TreeSizeReport().process(ret);
			} else {
				if (!sameEntries(tree.children(), transformed)) {
					ret = new ImmutableTree(ret.id(), ret.type(), ret.label(), ret.size(), transformed);
				}
			}
		}
		return ret;
	}

	static <T> boolean sameEntries(List<? extends T> a, List<? extends T> b) {
		if (a.size()!=b.size()) return false;
		for (int i=0,s=a.size();i<s;i++) {
			if (a.get(i)!=b.get(i)) return false;
		}
		return true;
	}
	
	static class TreeTypeMap
	{

		Map<TreeType, List<ISerializedObjectTree>> map = new LinkedHashMap<SimilarNodeTreeTransformator.TreeType, List<ISerializedObjectTree>>();

		public void add(ISerializedObjectTree tree)
		{
			TreeType key = new TreeType(tree);
			List<ISerializedObjectTree> list = map.get(key);
			if (list == null)
			{
				list = new ArrayList<ISerializedObjectTree>();
				map.put(key, list);
			}
			list.add(tree);
		}

		public List<ISerializedObjectTree> compressedResult()
		{
			List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();

			for (Entry<TreeType, List<ISerializedObjectTree>> entry : map.entrySet())
			{
				List<ISerializedObjectTree> list = entry.getValue();
				ret.add(list.size() == 1 ? list.get(0) : compress(list));
			}

			return ret;
		}

		private ISerializedObjectTree compress(List<ISerializedObjectTree> list)
		{
			ISerializedObjectTree first = list.get(0);
			final Class<?> type = first.type();
			final String label = allLables(list);

			int size = 0;
			for (ISerializedObjectTree t : list)
			{
				size = size + t.size();
			}

			return new ImmutableTree(null, type, label, size, compressedChildren(list));
		}

		private List<? extends ISerializedObjectTree> compressedChildren(
			List<ISerializedObjectTree> list)
		{
			ISerializedObjectTree first = list.get(0);
			List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
			for (int childIndex = 0, childCount = first.children().size(); childIndex < childCount; childIndex++)
			{
				List<ISerializedObjectTree> children = new ArrayList<ISerializedObjectTree>();
				for (ISerializedObjectTree entry : list)
				{
					children.add(entry.children().get(childIndex));
				}
				ret.add(compress(children));
			}
			return ret;
		}

		private String allLables(List<ISerializedObjectTree> list)
		{
			Set<String> labels = new LinkedHashSet<String>();
			for (ISerializedObjectTree t : list)
			{
				if (t.label() != null)
					labels.add(t.label());
			}
			if (labels.isEmpty())
			{
				return null;
			}
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (String s : labels)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					sb.append("|");
				}
				sb.append(s);
			}
			return sb.toString();
		}

		public boolean hasLessEntries(int size)
		{
			return map.size() < size;
		}
	}

	protected static final class TreeType implements Comparable<TreeType>
	{
		final Class<?> type;
		final List<TreeType> children;

		public TreeType(ISerializedObjectTree tree)
		{
			this.type = tree.type();
			this.children = children(tree.children());
		}

		@Override
		public int compareTo(TreeType o)
		{
			if (this.equals(o))
				return 0;
			int typeResult = type.getName().compareTo(o.type.getName());
			if (typeResult != 0)
				return typeResult;
			int sizeDiff = children.size() - o.children.size();
			if (sizeDiff != 0)
				return sizeDiff > 0 ? 1 : -1;
			for (int i = 0, s = children.size(); i < s; i++)
			{
				int childCompare = children.get(i).compareTo(o.children.get(i));
				if (childCompare != 0)
					return childCompare;
			}
			return 0;
		}

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			format(sb, 0);
			return sb.toString();
		}

		private void format(StringBuilder sb, int level)
		{
			indent(sb, level);
			sb.append(type.getName());
			if (!children.isEmpty())
			{
				indent(sb, level);
				sb.append("{\n");
				for (TreeType c : children) {
					c.format(sb,level+1);
					sb.append(",\n");
				}
				indent(sb, level);
				sb.append("}\n");
			}
		}

		private void indent(StringBuilder sb, int level)
		{
			for (int i=0;i<level;i++) {
				sb.append(" ");
			}
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((children == null) ? 0 : children.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TreeType other = (TreeType)obj;
			if (children == null)
			{
				if (other.children != null)
					return false;
			}
			else if (!children.equals(other.children))
				return false;
			if (type == null)
			{
				if (other.type != null)
					return false;
			}
			else if (!type.equals(other.type))
				return false;
			return true;
		}

		private static List<TreeType> children(List<? extends ISerializedObjectTree> list)
		{
			List<TreeType> ret = new ArrayList<SimilarNodeTreeTransformator.TreeType>();
			for (ISerializedObjectTree t : list)
			{
				ret.add(new TreeType(t));
			}
			Collections.sort(ret);
			return ret;
		}


	}
}
