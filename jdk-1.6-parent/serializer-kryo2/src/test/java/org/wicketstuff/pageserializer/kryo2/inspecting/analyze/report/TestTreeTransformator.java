package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class TestTreeTransformator {

	@Test
	public void noop() {
		TreeSizeReport tr = new TreeSizeReport();

		ISerializedObjectTree tree = Trees.build(String.class, 30)
				.withChild(String.class, 20).withChild(String.class, 10)
				.multiply(2).parent().multiply(2).asTree();

		tr.process(tree);

		ISerializedObjectTree filtered = TreeTransformator.compact(tree,
				new AcceptAll());
		Assert.assertTrue(Trees.equals(tree, filtered));
		tr.process(filtered);

		filtered = TreeTransformator.compact(tree, new MaxDepth(1));
		Assert.assertFalse(Trees.equals(tree, filtered));
		tr.process(filtered);
		
		ISerializedObjectTree expected = Trees.build(String.class, 30)
				.withChild(String.class, 40).multiply(2).asTree();
		Assert.assertTrue(Trees.equals(expected, filtered));
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

}
