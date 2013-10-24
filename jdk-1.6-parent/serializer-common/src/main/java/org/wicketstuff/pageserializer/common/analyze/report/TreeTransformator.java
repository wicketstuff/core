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

import org.wicketstuff.pageserializer.common.analyze.AbstractTreeTransformingProcessor;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.report.filter.ITreeFilter;

/**
 * a tree processor which filters incoming tree
 * @author mosmann
 *
 */
public class TreeTransformator extends AbstractTreeTransformingProcessor
{
	private final IFilter[] filter;

	public TreeTransformator(ISerializedObjectTreeProcessor destination, IFilter... filter)
	{
		super(destination);
		this.filter = filter;
	}

	@Override
	protected ISerializedObjectTree transform(ISerializedObjectTree tree)
	{
		ISerializedObjectTree current = tree;
		for (IFilter f : filter)
		{
			switch (f.filterType())
			{
				case COMPACT :
					current = TreeTransformations.compact(current, f);
					break;
				case STRIP :
					current = TreeTransformations.strip(current, f);
					break;
			}
		}
		return current;
	}

	public enum FilterType {
		COMPACT, STRIP;
	}

	/**
	 * which tree transformation should be used
	 * @author mosmann
	 *
	 */
	public interface IFilter extends ITreeFilter
	{
		FilterType filterType();
	}
	
	static class Filter implements IFilter {

		private final ITreeFilter filter;
		private final FilterType type;
		
		public Filter(FilterType type, ITreeFilter filter)
		{
			this.type = type;
			this.filter = filter;
		}
		
		@Override
		public boolean accept(ISerializedObjectTree source, Level current)
		{
			return filter.accept(source, current);
		}

		@Override
		public FilterType filterType()
		{
			return type;
		}
		
	}

	/**
	 * @see TreeTransformations#strip(ISerializedObjectTree, ITreeFilter)
	 */
	public static IFilter strip(ITreeFilter filter)
	{
		return new Filter(FilterType.STRIP,filter);
	}
	
	/**
	 * @see TreeTransformations#compact(ISerializedObjectTree, ITreeFilter)
	 */
	public static IFilter compact(ITreeFilter filter)
	{
		return new Filter(FilterType.COMPACT,filter);
	}
}
