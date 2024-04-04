package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import wicket.contrib.examples.WicketExamplePage;

/**
 * @author Iulian-Corneliu COSTAN, JavaLuigi
 */
public class TinyMCEBasePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public TinyMCEBasePage()
	{
		add(new BookmarkablePageLink("simple", SimpleTinyMCEPage.class));
		add(new BookmarkablePageLink("full", FullFeaturedTinyMCEPage.class));
		add(new BookmarkablePageLink("word", WordTinyMCEPage.class));
		add(new BookmarkablePageLink("ajax", AjaxTinyMCEPage.class));
		add(new BookmarkablePageLink("inline", InlineTinyMCEPage.class));
		add(new BookmarkablePageLink("image", ImageUploadTinyMCEPage.class));
	}
}
