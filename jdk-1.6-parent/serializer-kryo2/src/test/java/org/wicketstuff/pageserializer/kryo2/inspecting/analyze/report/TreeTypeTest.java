package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;


public class TreeTypeTest {

	@Test
	public void notEqual() throws IOException {
		ISerializedObjectTree source = Trees.fromResource(getClass(), "notEqual");
		ISerializedObjectTree match= Trees.fromResource(getClass(), "notEqual-match");
		TreeType sourceType=new TreeType(source);
		TreeType matchType=new TreeType(match);
		Assert.assertFalse(matchType.equals(sourceType));
		Assert.assertFalse(matchType.compareTo(sourceType)==0);
	}

	@Test
	public void equal() throws IOException {
		ISerializedObjectTree source = Trees.fromResource(getClass(), "equal");
		ISerializedObjectTree match= Trees.fromResource(getClass(), "equal-match");
		TreeType sourceType=new TreeType(source);
		TreeType matchType=new TreeType(match);
		Assert.assertEquals(matchType,sourceType);
		Assert.assertTrue(matchType.compareTo(sourceType)==0);
	}
}
