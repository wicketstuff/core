package org.wicketstuff.foundation.thumbnail;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

public class FoundationThumbnailTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		FoundationThumbnail thumb = new FoundationThumbnail("test", new PackageResourceReference(this.getClass(), "space.jpg"), 
				new PackageResourceReference(this.getClass(), "space-th-sm.jpg"));
		tester.startComponentInPage(thumb);
		tester.dumpPage();
		TagTester fullImageLink = tester.getTagByWicketId("fullImageLink");
		Assert.assertEquals("th", fullImageLink.getAttribute("class"));
		Assert.assertEquals(1, tester.getTagsByWicketId("fullImageLink").size());
		Assert.assertEquals(1, tester.getTagsByWicketId("thumbnail").size());
	}
}
