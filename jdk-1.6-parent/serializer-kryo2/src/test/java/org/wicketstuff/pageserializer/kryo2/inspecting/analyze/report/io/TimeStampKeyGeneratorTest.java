package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;


public class TimeStampKeyGeneratorTest {

	@Test
	public void formatedDateShouldContainAllParts() {
		String key = TimeStampKeyGenerator.format(TimeStampKeyGenerator.DEFAULT_WITH_MILLISECONDS, new Date(123456l*2345678l));
		assertEquals("1979-03-06--060703-168",key);
	}
}
