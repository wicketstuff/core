package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.subnav.FoundationSubNav;
import org.wicketstuff.foundation.subnav.SubNavItem;

public class SubNavPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public SubNavPage(PageParameters params) {
		super(params);
		List<SubNavItem> items = new ArrayList<>();
		items.add(new SubNavItem("All", true));
		items.add(new SubNavItem("Active"));
		items.add(new SubNavItem("Pending"));
		items.add(new SubNavItem("Suspended"));
		add(new FoundationSubNav("basic", "FILTER", items) {
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
	}
}
