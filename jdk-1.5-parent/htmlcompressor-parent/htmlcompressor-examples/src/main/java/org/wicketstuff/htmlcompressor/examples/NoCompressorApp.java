package org.wicketstuff.htmlcompressor.examples;


/**
 * Standard wicket app not using htmlcompressor but using wickets own whitespace compression.
 * 
 * @author akiraly
 */
public class NoCompressorApp extends AbstractApp
{
	@Override
	protected void init()
	{
		super.init();
		if (usesDeploymentConfig())
		{
			getMarkupSettings().setCompressWhitespace(true);
		}
	}
}
