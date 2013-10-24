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
import java.util.List;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;

/**
 * NOT A PUBLIC API
 * a tree as a key - this way you can compare trees with other trees
 * @author mosmann
 *
 */
public final class TreeType implements Comparable<TreeType>
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
		List<TreeType> ret = new ArrayList<TreeType>();
		for (ISerializedObjectTree t : list)
		{
			ret.add(new TreeType(t));
		}
		Collections.sort(ret);
		return ret;
	}


}