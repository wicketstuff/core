package org.wicketstuff.clipboardjs.example;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.clipboardjs.ClipboardJsBehavior;

/**
 * Demo page that just adds the default BrowserIdPanel and a feedback panel to show any errors
 */
public class ClipboardJsDemoPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public ClipboardJsDemoPage(final PageParameters parameters)
	{
		super(parameters);

		TextArea target = new TextArea("target");
		add(target);

		final WebMarkupContainer copyBtn = new WebMarkupContainer("copyBtn");
		final ClipboardJsBehavior clipboardJsBehavior = new ClipboardJsBehavior();
		clipboardJsBehavior.setTarget(target);
		copyBtn.add(clipboardJsBehavior);
		add(copyBtn);
	}
}
