package org.wicketstuff.pdfjs.example;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
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
		final IPackageResourceGuard packageResourceGuard = getResourceSettings().getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard) {
			SecurePackageResourceGuard securePackageResourceGuard = (SecurePackageResourceGuard) packageResourceGuard;
			securePackageResourceGuard.addPattern("+*.pdf");
		}
	}
}
