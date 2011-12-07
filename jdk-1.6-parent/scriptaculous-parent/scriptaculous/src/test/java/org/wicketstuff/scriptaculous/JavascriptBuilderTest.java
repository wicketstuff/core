package org.wicketstuff.scriptaculous;

import java.util.Collections;

import org.wicketstuff.scriptaculous.JavascriptBuilder;


import junit.framework.TestCase;

public class JavascriptBuilderTest extends TestCase {

	public void testEmptyOptionsFormattedAsEmptyHash() {
		JavascriptBuilder builder = new JavascriptBuilder();
		
		String value = builder.formatAsJavascriptHash(Collections.EMPTY_MAP);
		assertEquals("{}", value);
	}
}
