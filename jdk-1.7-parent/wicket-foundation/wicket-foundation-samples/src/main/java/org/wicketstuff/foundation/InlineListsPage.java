package org.wicketstuff.foundation;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.inlinelist.FoundationInlineList;

public class InlineListsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public InlineListsPage(PageParameters params) {
		super(params);
		List<String> titles = Arrays.asList("Link 1", "Link 2", "Link 3", "Link 4", "Link 5");
		FoundationInlineList inlineList = new FoundationInlineList("basic", titles) {
			@Override
			public AbstractLink createLink(String id, int idx) {
				return new AjaxLink<Void>(id) {

					@Override
					public void onClick(AjaxRequestTarget target) {
					}
					
				};
			}
		};
		add(inlineList);
	}
}
