package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.sidenav.FoundationSideNav;
import org.wicketstuff.foundation.sidenav.SideNavDividerItem;
import org.wicketstuff.foundation.sidenav.SideNavHeaderItem;
import org.wicketstuff.foundation.sidenav.SideNavItem;
import org.wicketstuff.foundation.sidenav.SideNavLinkItem;

public class SideNavPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public SideNavPage(PageParameters params) {
		super(params);
		List<SideNavItem> basicItems = new ArrayList<>();
		basicItems.add(new SideNavLinkItem("Link 1"));
		basicItems.add(new SideNavLinkItem("Link 2"));
		basicItems.add(new SideNavLinkItem("Link 3"));
		basicItems.add(new SideNavLinkItem("Link 4"));
		add(new FoundationSideNav("basic", basicItems) {
			@Override
			public AbstractLink createLink(String id, final int idx) {
				return new AjaxLink<Void>(id) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						target.appendJavaScript(String.format("alert('%d');", idx));
					}
				};
			}
		});
		
		List<SideNavItem> advancedItems = new ArrayList<>();
		advancedItems.add(new SideNavHeaderItem("Header"));
		advancedItems.add(new SideNavLinkItem("Link 1", true));
		advancedItems.add(new SideNavLinkItem("Link 2"));
		advancedItems.add(new SideNavDividerItem());
		advancedItems.add(new SideNavLinkItem("Link 3"));
		advancedItems.add(new SideNavLinkItem("Link 4"));
		add(new FoundationSideNav("advanced", advancedItems) {
			@Override
			public AbstractLink createLink(final String id, final int idx) {
				return new AjaxLink<Void>(id) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						target.appendJavaScript(String.format("alert('%d');", idx));
					}
				};
			}
		});		
	}
}
