package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.link.Link;

import wicket.contrib.examples.WicketExamplePage;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEBasePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public TinyMCEBasePage()
	{
		add(new Link("simple")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(SimpleTinyMCEPage.class);
			}
		});
		add(new Link("full")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(FullFeaturedTinyMCEPage.class);
			}
		});
		add(new Link("word")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(WordTinyMCEPage.class);
			}
		});
		add(new Link("ajax")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(AjaxTinyMCEPage.class);
			}

		});
		add(new Link("inline")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(InlineTinyMCEPage.class);
			}
		});
		add(new Link("image")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
				setResponsePage(ImageUploadTinyMCEPage.class);
			}
		});
	}
}
