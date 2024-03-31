package org.wicketstuff.examples.tinymce;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.examples.WicketExamplePage;

/**
 * @author Iulian-Corneliu COSTAN, JavaLuigi
 */
public class TinyMCEBasePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public TinyMCEBasePage()
	{
		add(new BookmarkablePageLink<>("simple", SimpleTinyMCEPage.class));
		add(new BookmarkablePageLink<>("full", FullFeaturedTinyMCEPage.class));
		add(new BookmarkablePageLink<>("ajax", AjaxTinyMCEPage.class));
	}
}
