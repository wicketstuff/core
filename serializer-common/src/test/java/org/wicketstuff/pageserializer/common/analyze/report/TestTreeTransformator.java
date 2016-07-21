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

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.report.filter.ITreeFilter;

public class TestTreeTransformator
{

	@Test
	public void compactWithNoChange() throws IOException
	{
		ISerializedObjectTree tree = Trees.fromResource(getClass(), "treeOf3");
		ISerializedObjectTree filtered = TreeTransformations.compact(tree, new AcceptAll());

		Trees.assertEqualsTree(tree, filtered);
		Assert.assertTrue(tree == filtered);
	}

	@Test
	public void removeAllNodesDeeperThan2() throws IOException
	{
		ISerializedObjectTree tree = Trees.fromResource(getClass(), "treeOf3");

		ISerializedObjectTree filtered = TreeTransformations.compact(tree, new MaxDepth(2));

		ISerializedObjectTree expected = Trees.fromResource(getClass(), "treeOf3-maxDepth1");
		
		Trees.assertEqualsTree(expected, filtered);
	}

	@Test
	public void removeAllBNodes() throws IOException
	{
		ISerializedObjectTree tree = Trees.fromResource(getClass(), "treeOf3");

		ISerializedObjectTree filtered = TreeTransformations.strip(tree, new NotDepth(2));

		ISerializedObjectTree expected = Trees.fromResource(getClass(), "treeOf3-noDepth2");
		
		Trees.assertEqualsTree(expected, filtered);
	}

	@Test
	public void removeAllCNodes() throws IOException
	{
		ISerializedObjectTree tree = Trees.fromResource(getClass(), "treeOf3");

		ISerializedObjectTree filtered = TreeTransformations.strip(tree, new NotDepth(3));

		ISerializedObjectTree expected = Trees.fromResource(getClass(), "treeOf3-noDepth3");
		
		Trees.assertEqualsTree(expected, filtered);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void canNotRemoveANodes() throws IOException
	{
		ISerializedObjectTree tree = Trees.fromResource(getClass(), "treeOf3");

		ISerializedObjectTree filtered = TreeTransformations.strip(tree, new NotDepth(0));
	}
	
	private static class AcceptAll implements ITreeFilter
	{
		@Override
		public boolean accept(ISerializedObjectTree source, Level level)
		{
			return true;
		}
	}

	private static class MaxDepth implements ITreeFilter
	{

		private final int level;

		public MaxDepth(int level)
		{
			this.level = level;
		}

		@Override
		public boolean accept(ISerializedObjectTree source, Level level)
		{
			return level.distanceFromTop() < this.level ;
		}

	}

	private static class NotDepth implements ITreeFilter
	{

		private final int level;

		public NotDepth(int level)
		{
			this.level = level;
		}

		@Override
		public boolean accept(ISerializedObjectTree source, Level level)
		{
			return this.level != level.distanceFromTop();
		}

	}

	static class Root
	{

	}
	
	static class A
	{

	}

	static class B
	{

	}

	static class C
	{

	}

	static class D
	{

	}
}
