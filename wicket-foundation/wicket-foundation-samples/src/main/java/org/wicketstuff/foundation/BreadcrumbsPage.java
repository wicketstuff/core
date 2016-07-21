package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.breadcrumbs.BreadcrumbsItem;
import org.wicketstuff.foundation.breadcrumbs.FoundationBreadcrumbs;

public class BreadcrumbsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public BreadcrumbsPage(PageParameters params) {
		super(params);
		
		List<BreadcrumbsItem> basicItems = new ArrayList<>();
		basicItems.add(new BreadcrumbsItem("First"));
		basicItems.add(new BreadcrumbsItem("Second"));
		basicItems.add(new BreadcrumbsItem("Third"));
		basicItems.add(new BreadcrumbsItem("Fourth"));
		add(new FoundationBreadcrumbs("basic", basicItems) {
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
		
		List<BreadcrumbsItem> advancedItems = new ArrayList<>();
		advancedItems.add(new BreadcrumbsItem("First"));
		advancedItems.add(new BreadcrumbsItem("Second"));
		advancedItems.add(new BreadcrumbsItem("Third"));
		advancedItems.add(new BreadcrumbsItem("Fourth", true, false));
		advancedItems.add(new BreadcrumbsItem("Fifth", false, true));
		advancedItems.add(new BreadcrumbsItem("Sixth"));
		add(new FoundationBreadcrumbs("advanced", advancedItems) {
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
