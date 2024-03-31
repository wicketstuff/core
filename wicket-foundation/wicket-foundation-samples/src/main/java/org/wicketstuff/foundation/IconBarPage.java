package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.foundation.icon.IconType;
import org.wicketstuff.foundation.iconbar.FoundationIconBar;
import org.wicketstuff.foundation.iconbar.IconBarFontItem;
import org.wicketstuff.foundation.iconbar.IconBarItem;
import org.wicketstuff.foundation.iconbar.IconBarOptions;
import org.wicketstuff.foundation.iconbar.IconBarResourceItem;
import org.wicketstuff.foundation.iconbar.IconBarVerticalStyle;

public class IconBarPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public IconBarPage(PageParameters params) {
		super(params);
		createBasic();
		createVertical();
		createLargeVertical();
		createAdvanced();
	}

	private void createBasic() {
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-bookmark.svg"), "Bookmark"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-home.svg"), "Home"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-info.svg"), "Info"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-like.svg"), "Like"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-mail.svg"), "Mail"));
		FoundationIconBar iconBar = new FoundationIconBar("basic", items);
		add(iconBar);
	}
	
	private void createVertical() {
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-bookmark.svg"), "Bookmark"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-home.svg"), "Home"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-info.svg"), "Info"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-like.svg"), "Like"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-mail.svg"), "Mail"));
		IconBarOptions options = new IconBarOptions(IconBarVerticalStyle.VERTICAL);
		FoundationIconBar iconBar = new FoundationIconBar("vertical", options, items);
		add(iconBar);
	}
	
	private void createLargeVertical() {
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-bookmark.svg"), "Bookmark"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-home.svg"), "Home"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-info.svg"), "Info"));
		items.add(new IconBarResourceItem(new PackageResourceReference(this.getClass(), "fi-like.svg"), "Like"));
		IconBarOptions options = new IconBarOptions(IconBarVerticalStyle.LARGE_VERTICAL);
		FoundationIconBar iconBar = new FoundationIconBar("largeVertical", options, items);
		add(iconBar);
	}
	
	private void createAdvanced() {
		List<IconBarItem> items = new ArrayList<>();
		items.add(new IconBarFontItem(IconType.BOOKMARK, "Bookmark"));
		items.add(new IconBarFontItem(IconType.HOME, "Home"));
		items.add(new IconBarFontItem(IconType.INFO, "Info"));
		items.add(new IconBarFontItem(IconType.LIKE, "Like"));
		items.add(new IconBarFontItem(IconType.MAIL, "Mail"));
		FoundationIconBar iconBar = new FoundationIconBar("advanced", items);
		add(iconBar);
	}
}
