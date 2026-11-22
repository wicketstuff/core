package org.wicketstuff.examples.hugerte;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.examples.WicketExamplePage;

/**
 * @author Iulian-Corneliu COSTAN, JavaLuigi
 */
public class HugeRteBasePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HugeRteBasePage()
	{
		add(new BookmarkablePageLink<>("simple", SimpleHugeRtePage.class));
		add(new BookmarkablePageLink<>("full", FullFeaturedHugeRtePage.class));
		add(new BookmarkablePageLink<>("ajax", AjaxHugeRtePage.class));
	}
}
