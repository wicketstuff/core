package org.wicketstuff.htmlcompressor.examples;

import org.wicketstuff.htmlcompressor.HtmlCompressingMarkupFactory;

public class HtmlCompressorApp extends AbstractApp
{

	@Override
	protected void init()
	{
		super.init();
		getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory());
	}
}
