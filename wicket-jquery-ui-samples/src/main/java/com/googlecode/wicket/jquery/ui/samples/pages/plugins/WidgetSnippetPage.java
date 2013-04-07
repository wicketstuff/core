package com.googlecode.wicket.jquery.ui.samples.pages.plugins;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.SnippetLabel;

public class WidgetSnippetPage extends AbstractSnippetPage
{
	private static final long serialVersionUID = 1L;

	public WidgetSnippetPage()
	{
		Options options = new Options();
		options.set("style", Options.asString("ide-eclipse"));

		this.add(new SnippetLabel("code", "html", options, this.newSnippetModel()));
	}

	/**
	 * @return the Model to be used as source for the SnippetLabel
	 */
	private IModel<String> newSnippetModel()
	{
		return new LoadableDetachableModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected String load()
			{
				return	"<h1>H1 tag!</h1>\n" +
					"<iframe src=\"index.html\"></iframe>\n" +
					"<div id=\"foo\">\n" +
					"<h3>H3 html is awesome!</h3>\n" +
					"<span class=\"bar\">Womp.</span>\n" +
					"<!-- comment -->\n" +
					"click here\n" +
					"</div>\n";
			}
		};
	}
}
