/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
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

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class TestTreeTransformator
{

	@Test
	public void compactWithNoChange()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformations.compact(tree, new AcceptAll());

		Assert.assertTrue(Trees.equals(tree, filtered));
		Assert.assertTrue(tree == filtered);
	}

	@Test
	public void removeAllNodesDeeperThan2()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformations.compact(tree, new MaxDepth(1));

		ISerializedObjectTree expected = Trees.build(A.class, 30)
			.withChild(B.class, 40)
			.multiply(2)
			.asTree();
		Assert.assertTrue(Trees.equals(expected, filtered));
	}

	@Test
	public void removeAllBNodes()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformations.strip(tree, new NotDepth(1));

		ISerializedObjectTree expected = Trees.build(A.class, 70)
			.withChild(C.class, 10)
			.multiply(4)
			.asTree();

		Assert.assertTrue(Trees.equals(expected, filtered));
	}

	@Test
	public void removeAllCNodes()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformations.strip(tree, new NotDepth(2));

		ISerializedObjectTree expected = Trees.build(A.class, 30)
			.withChild(B.class, 40)
			.multiply(2)
			.asTree();
		
		Assert.assertTrue(Trees.equals(expected, filtered));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void canNotRemoveANodes()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformations.strip(tree, new NotDepth(0));
	}
	
	private ISerializedObjectTree treeOf3()
	{
		ISerializedObjectTree tree = Trees.build(A.class, 30)
			.withChild(B.class, 20)
			.withChild(C.class, 10)
			.multiply(2)
			.parent()
			.multiply(2)
			.asTree();
		return tree;
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
