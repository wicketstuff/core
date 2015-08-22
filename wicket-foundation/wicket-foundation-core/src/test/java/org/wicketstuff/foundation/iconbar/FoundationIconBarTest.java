package org.wicketstuff.foundation.iconbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.icon.IconType;

public class FoundationIconBarTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-arrow-down.svg"), "Down"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-arrow-left.svg"), "Left"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-arrow-right.svg"), "Right"));
		FoundationIconBar iconBar = new FoundationIconBar("id", items);
		tester.startComponentInPage(iconBar);
	}
	@Test
	public void testIcon() {
		WicketTester tester = new WicketTester();
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarFontItem(IconType.ARROW_DOWN, "Down"));
		items.add(new IconBarFontItem(IconType.ARROW_LEFT, "Left"));
		items.add(new IconBarFontItem(IconType.ARROW_RIGHT, "Right"));
		FoundationIconBar iconBar = new FoundationIconBar("id", items);
		tester.startComponentInPage(iconBar);
	}
	@Test
	public void testVertical() {
		WicketTester tester = new WicketTester();
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-arrow-down.svg"), "Down"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-arrow-left.svg"), "Left"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-arrow-right.svg"), "Right"));
		FoundationIconBar iconBar = new FoundationIconBar("id", new IconBarOptions(IconBarVerticalStyle.VERTICAL), items);
		tester.startComponentInPage(iconBar);
	}
}
