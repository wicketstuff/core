package org.wicketstuff.jslibraries;

import junit.framework.TestCase;

public class VersionDescriptorTest extends TestCase {

	public void testLatestOfVersionDescriptor() throws Exception {
		VersionDescriptor vd = VersionDescriptor.alwaysLatestOfVersion(Library.JQUERY, 1, 1);
		assertEquals(new Version(1, 1, 4), vd.getVersion());
	}

	public void testExactVersionDescriptor() throws Exception {
		VersionDescriptor vd = VersionDescriptor.exactVersion(Library.JQUERY, 1, 2);
		assertEquals(new Version(1, 2), vd.getVersion());
	}

	public void testAlwaysLatestDescriptor() throws Exception {
		// this test will obviously fail every time we add a new JQUERY version
		VersionDescriptor vd = VersionDescriptor.alwaysLatest(Library.JQUERY);
		assertEquals(new Version(1, 2, 6), vd.getVersion());
	}
	
	public void testLibraryWithManyVersionDepths() throws Exception {
		assertEquals(new Version(1, 5, 1, 2), VersionDescriptor.alwaysLatestOfVersion(Library.PROTOTYPE, 1, 5).getVersion());
		assertEquals(new Version(1, 5, 1, 2), VersionDescriptor.alwaysLatestOfVersion(Library.PROTOTYPE, 1, 5, 1).getVersion());
		assertEquals(new Version(1, 6, 0), VersionDescriptor.exactVersion(Library.PROTOTYPE, 1, 6, 0).getVersion());
		// this test will obviously fail every time we add a new PROTOTYPE version
		assertEquals(new Version(1, 6, 0, 3), VersionDescriptor.alwaysLatest(Library.PROTOTYPE).getVersion());
		
	}
}
