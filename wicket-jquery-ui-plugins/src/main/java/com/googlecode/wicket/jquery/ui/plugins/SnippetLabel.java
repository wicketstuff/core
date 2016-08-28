package com.googlecode.wicket.jquery.ui.plugins;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

public class SnippetLabel extends Label implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	
	private final String language;
	
	protected final Options options;

	public SnippetLabel(String id, String language, IModel<String> model)
	{
		this(id, language, model, new Options());
	}

	public SnippetLabel(String id, String language, IModel<String> model, Options options)
	{
		super(id, model);

		this.language = Args.notNull(language, "language");
		this.options = Args.notNull(options, "options");
	}

	// Properties //

	public SnippetLabel setStyle(String style)
	{
		this.options.set("style", Options.asString(style));

		return this;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
		// shortcut for:
		// this.add(this.newWidgetBehavior("#" + this.setOutputMarkupId(true).getMarkupId()));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SnippetBehavior(selector, this.language, this.options);
	}
}
