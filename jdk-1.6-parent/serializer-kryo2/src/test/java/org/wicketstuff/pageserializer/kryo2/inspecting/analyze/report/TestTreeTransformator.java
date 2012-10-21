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

		ISerializedObjectTree filtered = TreeTransformator.compact(tree, new AcceptAll());

		Assert.assertTrue(Trees.equals(tree, filtered));
		Assert.assertTrue(tree == filtered);
	}

	@Test
	public void removeAllNodesDeeperThan2()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformator.compact(tree, new MaxDepth(1));

		ISerializedObjectTree expected = Trees.build(A.class, 30)
			.withChild(B.class, 40)
			.multiply(2)
			.asTree();
		Assert.assertTrue(Trees.equals(expected, filtered));
	}

	@Test
	public void strip()
	{
		ISerializedObjectTree tree = treeOf3();

		ISerializedObjectTree filtered = TreeTransformator.strip(tree, new NotDepth(1));

		ISerializedObjectTree expected = Trees.build(A.class, 70)
			.withChild(C.class, 10)
			.multiply(4)
			.asTree();

		Assert.assertTrue(Trees.equals(expected, filtered));
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
