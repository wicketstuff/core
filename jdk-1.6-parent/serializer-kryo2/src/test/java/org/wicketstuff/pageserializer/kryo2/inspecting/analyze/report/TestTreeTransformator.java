package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class TestTreeTransformator {

	@Test
	public void compact() {
		ISerializedObjectTree tree = treeOf3Strings();

		ISerializedObjectTree filtered = TreeTransformator.compact(tree,
				new AcceptAll());
		Assert.assertTrue(Trees.equals(tree, filtered));

		filtered = TreeTransformator.compact(tree, new MaxDepth(1));
		Assert.assertFalse(Trees.equals(tree, filtered));
		
		ISerializedObjectTree expected = Trees.build(String.class, 30)
				.withChild(String.class, 40).multiply(2).asTree();
		Assert.assertTrue(Trees.equals(expected, filtered));
	}
	
	@Test
	public void strip() {
		ISerializedObjectTree tree = treeOfStringsAndNumbers();
		
		ISerializedObjectTree filtered = TreeTransformator.strip(tree,
				new NotDepth(1));
		
		ISerializedObjectTree expected = Trees.build(String.class, 70)
				.withChild(Double.class, 10)
				.multiply(4).asTree();
		
		Assert.assertTrue(Trees.equals(expected, filtered));
	}

	private ISerializedObjectTree treeOf3Strings() {
		ISerializedObjectTree tree = Trees.build(String.class, 30)
				.withChild(String.class, 20).withChild(String.class, 10)
				.multiply(2).parent().multiply(2).asTree();
		return tree;
	}

	private ISerializedObjectTree treeOfStringsAndNumbers() {
		ISerializedObjectTree tree = Trees.build(String.class, 30)
				.withChild(Integer.class, 20).withChild(Double.class, 10)
				.multiply(2).parent().multiply(2).asTree();
		return tree;
	}

	private static class AcceptAll implements ITreeFilter {
		@Override
		public boolean accept(ISerializedObjectTree source, int level) {
			return true;
		}
	}

	private static class MaxDepth implements ITreeFilter {

		private final int level;

		public MaxDepth(int level) {
			this.level = level;
		}

		@Override
		public boolean accept(ISerializedObjectTree source, int level) {
			return this.level > level;
		}

	}
	
	private static class NotDepth implements ITreeFilter {

		private final int level;

		public NotDepth(int level) {
			this.level = level;
		}

		@Override
		public boolean accept(ISerializedObjectTree source, int level) {
			return this.level != level;
		}

	}

}
