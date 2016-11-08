package org.wicketstuff.clipboardjs.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;

public class PdfJsApplication extends WebApplication {
	@Override
	public Class<? extends Page> getHomePage()
	{
		return PdfJsDemoPage.class;
	}

	@Override
	protected void init() {
		super.init();

		getResourceSettings().setCachingStrategy(NoOpResourceCachingStrategy.INSTANCE);
	}
}
