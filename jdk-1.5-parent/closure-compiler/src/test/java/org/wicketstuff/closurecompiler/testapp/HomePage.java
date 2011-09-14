package org.wicketstuff.closurecompiler.testapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.closurecompiler.ClosureCompilerJavaScriptCompressor;

public class HomePage extends WebPage
{
	private static final long serialVersionUID = -6623549607771622662L;
	private static final Logger log = LoggerFactory.getLogger(HomePage.class);

	private String source;
	private String result;

	public HomePage()
	{
		add(form("form"));
		add(new FeedbackPanel("status"));
		// TODO add source and compressed size stats to page
	}

	private Form<Void> form(String id)
	{
		Form<Void> form = new Form<Void>(id)
		{
			private static final long serialVersionUID = -3175260671078394969L;

			@Override
			protected void onSubmit()
			{
				try
				{
					result = compress(source);
				}
				catch (Exception e)
				{
					error(e.getMessage());
					log.error(e.getMessage(), e);
				}
			}
		};
		form.add(new TextArea<String>("source", new PropertyModel<String>(this, "source")).setOutputMarkupId(true));
		form.add(new TextArea<String>("result", new PropertyModel<String>(this, "result")).setOutputMarkupId(true));
		return form;
	}

	private String compress(String source) throws Exception
	{
		return new ClosureCompilerJavaScriptCompressor().compressSource(source);
	}
}
