package org.wicketstuff.foundation.orbitslider;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Unit test for orbit slider component.
 *
 */
public class FoundationOrbitSliderTest {

	@Test
	public void testFoundationOrbitSlider() {
		WicketTester tester = new WicketTester();
		List<OrbitSliderItem> items = new ArrayList<>();
		items.add(new OrbitSliderItem("1", new PackageResourceReference(this.getClass(), "andromeda-orbit.jpg"), "Caption 1"));
		items.add(new OrbitSliderItem("2", new PackageResourceReference(this.getClass(), "launch-orbit.jpg"), "Caption 2"));
		items.add(new OrbitSliderItem("3", new PackageResourceReference(this.getClass(), "satelite-orbit.jpg"), "Caption 3"));
		FoundationOrbitSlider orbitSlider = new FoundationOrbitSlider("foobar", new ListModel<>(items));
		tester.startComponentInPage(orbitSlider);
		tester.debugComponentTrees();
		tester.dumpPage();
		Assert.assertTrue(tester.getTagByWicketId("container").hasAttribute("data-orbit"));
		Assert.assertEquals(3, tester.getTagsByWicketId("img").size());
		Assert.assertEquals(3, tester.getTagsByWicketId("captionContainer").size());
		Assert.assertEquals(3, tester.getTagsByWicketId("caption").size());
	}
	
	@Test
	public void testFoundationOrbitContentSlider() {
		WicketTester tester = new WicketTester();
		List<OrbitSliderContent> items = new ArrayList<>();
		items.add(new OrbitSliderContent("1", "Heading 1", "Subheading 1"));
		items.add(new OrbitSliderContent("2", "Heading 2", "Subheading 2"));
		items.add(new OrbitSliderContent("3", "Heading 3", "Subheading 3"));
		FoundationOrbitContentSlider orbitSlider = new FoundationOrbitContentSlider("foobar", new ListModel<>(items));
		tester.startComponentInPage(orbitSlider);
		tester.debugComponentTrees();
		tester.dumpPage();
		Assert.assertTrue(tester.getTagByWicketId("container").hasAttribute("data-orbit"));
		Assert.assertEquals(3, tester.getTagsByWicketId("heading").size());
		Assert.assertEquals(3, tester.getTagsByWicketId("subheading").size());
	}
}
