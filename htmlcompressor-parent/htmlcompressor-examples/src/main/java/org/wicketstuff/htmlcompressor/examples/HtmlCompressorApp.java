package org.wicketstuff.htmlcompressor.examples;

import org.wicketstuff.htmlcompressor.HtmlCompressingMarkupFactory;

/**
 * Wicket app using htmlcompressor.
 * 
 * @author akiraly
 */
public class HtmlCompressorApp extends AbstractApp
{
	@Override
	protected void init()
	{
		super.init();
		if (usesDeploymentConfig())
		{
			getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory());
			// if we want custom settings for our compressor we could do this instead:
			// HtmlCompressor compressor = new HtmlCompressor();
			// compressor.setPreserveLineBreaks(true);
			// getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory(compressor));
		}
	}
}
