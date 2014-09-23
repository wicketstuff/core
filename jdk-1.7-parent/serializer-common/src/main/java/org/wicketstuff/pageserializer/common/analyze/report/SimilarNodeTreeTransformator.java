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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.common.analyze.AbstractTreeTransformingProcessor;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.common.analyze.ImmutableTree;

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

			TreeTypeMap typeMap = new TreeTypeMap(transformed);

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
}
