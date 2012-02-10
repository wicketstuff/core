package org.wicketstuff.closurecompiler.testapp;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.closurecompiler.ClosureCompilerJavaScriptCompressor;

public class ClosureCompilerApp extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init()
	{
		ClosureCompilerJavaScriptCompressor compressor = new ClosureCompilerJavaScriptCompressor();
		// currently something goes wrong when using advanced and optimization is too aggressive
		// compressor.setLevel(CompilationLevel.ADVANCED_OPTIMIZATIONS);
		getResourceSettings().setJavaScriptCompressor(compressor);
	}
}
