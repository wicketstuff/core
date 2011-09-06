package org.wicketstuff.htmlcompressor.examples;


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
