package org.wicketstuff.foundation.thumbnail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;

public class FoundationThumbnailTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		FoundationThumbnail thumb = new FoundationThumbnail("test", new PackageResourceReference(this.getClass(), "space.jpg"),
				new PackageResourceReference(this.getClass(), "space-th-sm.jpg"));
		tester.startComponentInPage(thumb);
		tester.dumpPage();
		TagTester fullImageLink = tester.getTagByWicketId("fullImageLink");
		assertEquals("th", fullImageLink.getAttribute("class"));
		assertEquals(1, tester.getTagsByWicketId("fullImageLink").size());
		assertEquals(1, tester.getTagsByWicketId("thumbnail").size());
	}
}
