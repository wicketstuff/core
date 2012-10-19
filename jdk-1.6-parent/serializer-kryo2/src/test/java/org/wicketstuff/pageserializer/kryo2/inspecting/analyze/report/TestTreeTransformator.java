package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ImmutableTree;

public class TestTreeTransformator {

	@Test
	public void noop() {
		TreeSizeReport tr=new TreeSizeReport();
		
		ISerializedObjectTree tree = buildTree(3, 2, new Factor(1, 2),
				new Factor(10, 1));
		
		tr.process(tree);
		
		ISerializedObjectTree filtered=TreeTransformator.compact(tree, new AcceptAll());
		Assert.assertTrue(Trees.equals(tree,filtered));
		tr.process(filtered);
		
		filtered=TreeTransformator.compact(tree, new MaxDepth(1));
		Assert.assertFalse(Trees.equals(tree,filtered));
		tr.process(filtered);
	}

	private ISerializedObjectTree buildTree(int level, int subNodes,
			Factor childFactor, Factor sizeFactor) {
		int curSize = sizeFactor.calc(level);
		int curSubNodes = childFactor.calc(subNodes);
		List<ISerializedObjectTree> children = new ArrayList<ISerializedObjectTree>();
		if (level>1) {
			for (int i = 0; i < subNodes; i++) {
				children.add(buildTree(level - 1, subNodes, childFactor, sizeFactor));
			}
		}
		return new ImmutableTree(String.class, null, curSize, children);
	}

	private static class AcceptAll implements ITreeFilter {
		@Override
		public boolean accept(ISerializedObjectTree source,int level) {
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
			return this.level>level;
		}
		
	}

	static class Factor {
		private final int mul;
		private final int div;

		public Factor(int mul, int div) {
			this.mul = mul;
			this.div = div;
		}

		public int calc(int source) {
			return (source * mul) / div;
		}
	}
}
