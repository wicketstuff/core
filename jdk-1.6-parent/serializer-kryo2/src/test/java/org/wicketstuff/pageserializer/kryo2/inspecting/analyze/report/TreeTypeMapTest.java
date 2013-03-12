package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;

public class TreeTypeMapTest {

	@Test
	public void joinLabels() throws IOException {
		ISerializedObjectTree source = Trees.fromResource(getClass(), "labels");
		Assert.assertEquals("a|b|c", TreeTypeMap.allLables(source.children()));
	}
	@Test
	public void noLabels() throws IOException {
		Assert.assertEquals(null, TreeTypeMap.allLables(new ArrayList<ISerializedObjectTree>()));
	}
	
	@Test
	public void compressNothing() throws IOException {
		ISerializedObjectTree source = Trees.fromResource(getClass(), "compress");
		ISerializedObjectTree match= Trees.fromResource(getClass(), "compressed");
//		ISerializedObjectTree compressed = TreeTypeMap.compress(source.children());
		ISerializedObjectTree compressed = SimilarNodeTreeTransformator.transformTree(source);
		Trees.assertEqualsTree(match, compressed);
	}

	static class Root {

	}

	static class A {

	}

	static class B {

	}

	static class C {

	}

	static class D {

	}

}
