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
import java.util.List;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ImmutableTree;
import org.wicketstuff.pageserializer.common.analyze.report.filter.ITreeFilter;

/**
 * tree transformation utility class
 * @author mosmann
 *
 */
public class TreeTransformations
{
	static final Level TOP=new Level();

	private TreeTransformations()
	{
		// no instance
	}

	/**
	 * compact all child nodes into the marked by filter
	 * @param source tree
	 * @param filter filter
	 * @return modified tree
	 */
	public static ISerializedObjectTree compact(ISerializedObjectTree source, ITreeFilter filter)
	{
		return compact(source, filter, TOP);
	}

	private static ISerializedObjectTree compact(ISerializedObjectTree source, ITreeFilter filter, Level level)
	{
		if (filter.accept(source, level))
		{
			if (!source.children().isEmpty())
			{
				boolean changed = false;

				List<ISerializedObjectTree> filteredList = new ArrayList<ISerializedObjectTree>();
				for (ISerializedObjectTree child : source.children())
				{
					ISerializedObjectTree filtered = compact(child, filter, level.down());
					filteredList.add(filtered);
					if (filtered != child)
					{
						changed = true;
					}
				}

				if (changed)
				{
					return new ImmutableTree(source.id(),source.type(), source.label(), source.size(),
						filteredList);
				}
			}
			return source;
		}
		else
		{
			return new ImmutableTree(source.id(),source.type(), source.label(), source.size() +
				source.childSize(), new ArrayList<ISerializedObjectTree>());
		}
	}

	/**
	 * removes matching nodes an reassign the child nodes
	 * @param source tree
	 * @param filter filter
	 * @return modified tree
	 */
	public static ISerializedObjectTree strip(ISerializedObjectTree source, ITreeFilter filter)
	{
		Level level = TOP;

		if (!filter.accept(source, level))
			throw new IllegalArgumentException("can not strip top level element");

		return strip(source, filter, level);
	}

	private static ISerializedObjectTree strip(ISerializedObjectTree source, ITreeFilter filter,
		Level level)
	{

		boolean changed = false;
		int localSize = 0;

		List<ISerializedObjectTree> filteredList = new ArrayList<ISerializedObjectTree>();
		
		Level levelOneDown = level.down();
		
		for (ISerializedObjectTree child : source.children())
		{
			if (filter.accept(child, levelOneDown))
			{
				ISerializedObjectTree filtered = strip(child, filter, levelOneDown);
				filteredList.add(filtered);
				if (filtered != child)
				{
					changed = true;
				}
			}
			else
			{
				changed = true;
				localSize = localSize + child.size();
				for (ISerializedObjectTree childOfChild : child.children())
				{
					ISerializedObjectTree filtered = strip(childOfChild, filter, levelOneDown);
					filteredList.add(filtered);
				}
			}
		}

		if (changed)
		{
			return new ImmutableTree(source.id(),source.type(), source.label(), source.size() + localSize,
				filteredList);
		}
		return source;
	}

}
