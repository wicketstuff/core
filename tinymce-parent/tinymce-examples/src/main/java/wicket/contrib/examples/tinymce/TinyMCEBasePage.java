package wicket.contrib.examples.tinymce;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

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

			public void onClick()
			{
				setResponsePage(SimpleTinyMCEPage.class);
			}
		});
		add(new Link("full")
		{

			public void onClick()
			{
				setResponsePage(FullFeaturedTinyMCEPage.class);
			}
		});
		add(new Link("word")
		{

			public void onClick()
			{
				setResponsePage(WordTinyMCEPage.class);
			}
		});
		add(new Link("ajax")
		{

			public void onClick()
			{
				setResponsePage(AjaxTinyMCEPage.class);
			}
			
		});
		add(new Link("inline")
		{
			
			public void onClick()
			{
				setResponsePage(InlineTinyMCEPage.class);
			}
		});
		add(new Link("image")
		{
			
			public void onClick()
			{
				setResponsePage(ImageUploadTinyMCEPage.class);
			}
		});
	}
}
