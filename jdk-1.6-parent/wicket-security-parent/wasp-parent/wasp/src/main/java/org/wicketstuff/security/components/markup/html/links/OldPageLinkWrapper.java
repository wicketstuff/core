package org.wicketstuff.security.components.markup.html.links;

import org.apache.wicket.Page;

@SuppressWarnings("deprecation")
public class OldPageLinkWrapper implements IPageLink {
	private static final long serialVersionUID = 1L;
	private org.apache.wicket.markup.html.link.IPageLink oldLink;

	public OldPageLinkWrapper(
			org.apache.wicket.markup.html.link.IPageLink oldLink) {
		this.oldLink = oldLink;
	}

	@Override
	public Page getPage() {
		return oldLink.getPage();
	}

	@Override
	public Class<? extends Page> getPageIdentity() {
		return oldLink.getPageIdentity();
	}
}
